import java.awt.*;
import java.net.*;

public class StartGameScene implements Scene {
	public StartGameScene(Game g) {
		game = g;
		netDevice = new Server();

		back = new Button(game, Game.WIDTH / 2, 650, 160, 80, "t1", "t2");
		connect = new Button(game, Game.WIDTH / 2, 550, 160, 80, "t1", "t2");
		ip = "IP: localhost";
		try {
			ip = "IP: " + InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}
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

		g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        FontMetrics fm = g.getFontMetrics();

        int textWidth = fm.stringWidth(ip);
        int textHeight = fm.getHeight();
        int centerX = (Game.WIDTH - textWidth) / 2;
        int centerY = (Game.HEIGHT - textHeight) / 2 + fm.getAscent() - 100;
        g.drawString(ip, centerX, centerY);

		back.render(g);
		connect.render(g);
	}

	private Server netDevice;
	private Button back;
	private Button connect;
	private String ip;
	private Game game;
}
