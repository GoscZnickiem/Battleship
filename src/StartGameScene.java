import java.awt.*;

public class StartGameScene implements Scene {
	public StartGameScene(Game g) {
		game = g;
		netDevice = new Server();

		back = new Button(game, Game.WIDTH / 2, 650, 160, 80, "t1", "t2");
		connect = new Button(game, Game.WIDTH / 2, 500, 160, 80, "t1", "t2");
	}

	@Override
	public void update() {
		if(back.isClicked()) {
			netDevice.disconnect();
			game.changeScene(new MenuScene(game));
		} else if(connect.isClicked()) {
			netDevice.connect();
		} 

		if(netDevice.connected()) {
			boolean myTurn = true;
			netDevice.sendPackage(new GamePackage(!myTurn));
			game.changeScene(new GameScene(game, "Stefan", myTurn, netDevice));
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

		back.render(g);
		connect.render(g);
	}

	private Server netDevice;
	private Button back;
	private Button connect;
	private Game game;
}
