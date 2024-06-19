import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.ArrayList;

public class AnimatedSprite {

	private Game game;
	private ArrayList<BufferedImage> textures;
	private int ticksPerFrame;
	private int frames;
	private int frame = 0;
	private int time = 0;
	private int x;
	private int y;
	private int width;
	private int height;

	public AnimatedSprite(Game g, int x, int y, int w, int h, String name, int speed, int frames) {
		this.x = x;
		this.y = y;
		width = w;
		height = h;
		game = g;
		ticksPerFrame = speed;
		this.frames = frames;

		textures = new ArrayList<>();
		for(int i = 0; i < frames; i++) {
			textures.add(game.getTextureManager().getTexture(name + "_f" + i));
		}
	}

	public void setAnimation(String name, int speed, int frames) {
		ticksPerFrame = speed;
		this.frames = frames;
		time = 0;
		frame = 0;

		textures = new ArrayList<>();
		for(int i = 0; i < frames; i++) {
			textures.add(game.getTextureManager().getTexture(name + "_f" + i));
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setSize(int w, int h) {
		width = w;
		height = h;
	}

	private void drawImage(BufferedImage i, Graphics2D g) {
		g.drawImage(i, x - width / 2, y - height / 2, x + width / 2, y + height / 2, 0, 0, i.getWidth(), i.getHeight(), null);
	}

	public void render(Graphics2D g) {
		drawImage(textures.get(frame), g);
		
		time += 1;
		if (time == ticksPerFrame) {
			frame += 1;
			if(frame == frames) frame = 0;
			time = 0;
		}
	}

}
