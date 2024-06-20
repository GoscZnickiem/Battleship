import java.awt.*;
import java.net.*;

public class StartGameScene implements Scene {
	public StartGameScene(Game g) {
		game = g;
		netDevice = new Server();

		back = new Button(game, Game.WIDTH / 2, 600, 160, 80, "goback_button", "goback_buttonA");
		connect = new Button(game, Game.WIDTH / 2, 500, 160, 80, "connect_button", "connect_buttonA");
		logo = new AnimatedSprite(game, Game.WIDTH / 2, 150, 1200, 200, "air", 1, 1);
		logo.setForeground("logo");
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
			game.changeScene(new TransitionScene(game, this, new MenuScene(game)));
		} else if(connect.isClicked()) {
			netDevice.connect();
		}

		if(netDevice.connected()) {
			boolean myTurn = true;
			netDevice.sendPackage(new GamePackage(!myTurn));
			game.changeScene(new TransitionScene(game, this, new GameScene(game, "Stefan", myTurn, netDevice)));
		}

	}

	@Override
	public void render(Graphics2D g) {

		g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        FontMetrics fm = g.getFontMetrics();

        int textWidth = fm.stringWidth(ip);
        int textHeight = fm.getHeight();
        int centerX = (Game.WIDTH - textWidth) / 2;
        int centerY = (Game.HEIGHT - textHeight) / 2 + fm.getAscent() - 50;
        g.drawString(ip, centerX, centerY);

		back.render(g);
		connect.render(g);
		logo.render(g);
	}

	private Server netDevice;
	private Button back;
	private Button connect;
	private AnimatedSprite logo;
	private String ip;
	private Game game;
}
