import java.awt.*;
import java.net.*;
import java.util.Random;

public class StartGameScene implements Scene {
	public StartGameScene(Game g) {
		game = g;
		netDevice = new Server();

		back = new Button(game, Game.WIDTH / 2, 630, 160, 80, "goback_button", "goback_buttonA");
		connect = new Button(game, Game.WIDTH / 2, 530, 160, 80, "connect_button", "connect_buttonA");
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
			this.connecting = false;
			game.changeScene(new TransitionScene(game, this, new MenuScene(game)));
		} else if(connect.isClicked()) {
			this.connecting = true;
			netDevice.connect();
		}

		if(netDevice.connected()) {
			boolean myTurn = new Random().nextBoolean();
			netDevice.sendPackage(new GamePackage(!myTurn));
			game.changeScene(new TransitionScene(game, this, new GameScene(game, "Zbyszek", myTurn, netDevice)));
		}

	}

	@Override
	public void render(Graphics2D g) {

		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 30));
			FontMetrics fm = g.getFontMetrics();
			int textWidth = fm.stringWidth(ip);
			int textHeight = fm.getHeight();
			int centerX = (Game.WIDTH - textWidth) / 2;
			int centerY = (Game.HEIGHT - textHeight) / 2 + fm.getAscent() - 60;
			g.drawString(ip, centerX, centerY);
		}

		if (connecting)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.PLAIN, 25));
			FontMetrics fm = g.getFontMetrics();
			String text = "Wait for the other player to join the game...";
			int textWidth = fm.stringWidth(text);
			int textHeight = fm.getHeight();
			int centerX = (Game.WIDTH - textWidth) / 2;
			int centerY = (Game.HEIGHT - textHeight) / 2 + fm.getAscent() - 10;
			g.drawString(text, centerX, centerY);

		}

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
	private boolean connecting;
}
