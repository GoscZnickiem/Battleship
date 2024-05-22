import java.awt.*;

public class StartGameScene implements Scene {
	public StartGameScene(Game g) {
		game = g;
		netDevice = new Server();
	}

	@Override
	public void update() {
		if(!netDevice.connected()) {
			netDevice.connect();
		} else {
			System.out.println("yo");
		}
	}

	@Override
	public void render(Graphics2D g) {
		String text = "Start gam";

		g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        int centerX = (Game.WIDTH - textWidth) / 2;
        int centerY = (Game.HEIGHT - textHeight) / 2 + fm.getAscent();
        g.drawString(text, centerX, centerY);

		String con = netDevice.connected() ? "conected!" : "not connected";
		g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        FontMetrics fm2 = g.getFontMetrics();
        int textWidth2 = fm2.stringWidth(con);
        int textHeight2 = fm2.getHeight();
        int centerX2 = (Game.WIDTH - textWidth2) / 2;
        int centerY2 = (Game.HEIGHT - textHeight2) / 4 + fm2.getAscent();
        g.drawString(con, centerX2, centerY2);
	}

	private Server netDevice;
	private Game game;
}
