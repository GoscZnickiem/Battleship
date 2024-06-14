import java.awt.image.BufferedImage;
import java.awt.*;

public class Button {
	public Button(Game g, int x, int y, int w, int h, String tex, String texHover) {
		game = g;
		this.x = x;
		this.y = y;
		width = w;
		height = h;
		texture = g.getTextureManager().getTexture(tex);
		hoverTexture = g.getTextureManager().getTexture(texHover);
		this.visible = true;
	}

	public boolean isClicked() {
		Mouse m = game.getMouse();
		return m.isClicked() && isHovered();
	}

	public boolean isHovered() {
		Mouse m = game.getMouse();
		return
		m.getX() >= x - width / 2 &&
		m.getX() <= x + width / 2 &&
		m.getY() >= y - height / 2 &&
		m.getY() <= y + height / 2;
	}

	private void drawImage(BufferedImage i, Graphics2D g) {
		g.drawImage(i, x - width / 2, y - height / 2, x + width / 2, y + height / 2, 0, 0, i.getWidth(), i.getHeight(), null);
	}

	public void render(Graphics2D g) {
		if(!this.visible) return;
		if(isHovered()) {
			drawImage(hoverTexture, g);
		} else {
			drawImage(texture, g);
		}

		g.setColor(new Color(255, 255, 255, 255));
		g.drawRect(x - width / 2, y - height / 2, width, height);
	}

	public int getWidth() { return width; }
	public int getHeight() { return height; }

	public void setPos(Position pos) {
		x = pos.x;
		y = pos.y;
	}

	public void setSize(int w, int h) {
		width = w;
		height = h;
	}

	public void setTex(String tex) {
		texture = game.getTextureManager().getTexture(tex);
	}

	public void setTex(String tex, String texHover) {
		texture = game.getTextureManager().getTexture(tex);
		hoverTexture = game.getTextureManager().getTexture(texHover);
	}
	
	private int x;
	private int y;
	private int width;
	private int height;
	private BufferedImage texture;
	private BufferedImage hoverTexture;
	private Game game;
	public boolean visible;
}
