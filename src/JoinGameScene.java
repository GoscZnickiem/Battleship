import java.awt.*;
import javax.swing.*;


public class JoinGameScene implements Scene {
	public JoinGameScene(Game g) {
		game = g;
		netDevice = new Client();
		this.connecting = false;
		back = new Button(game, Game.WIDTH / 2, 630, 160, 80, "goback_button", "goback_buttonA");
		connect = new Button(game, Game.WIDTH / 2, 530, 160, 80, "connect_button", "connect_buttonA");
		setIP = new Button(game, Game.WIDTH / 2, 430, 160, 80, "setip_button", "setip_buttonA");
		logo = new AnimatedSprite(game, Game.WIDTH / 2, 150, 1200, 200, "air", 1, 1);
		logo.setForeground("logo");
		ip = "IP: localhost";
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
		} else if(setIP.isClicked()) {
			this.connecting = false;
			netDevice.cancel();
			String newip = JOptionPane.showInputDialog(game, "Input IP:");
			if (newip != null && !newip.trim().isEmpty()) {
				ip = "IP: " + newip.trim();
				netDevice.setIP(newip.trim());
			}
		}

		if(netDevice.connected()) {
			GamePackage gp = netDevice.receivePackage();
			game.changeScene(new TransitionScene(game, this, new GameScene(game, "Marcin", gp.done, netDevice)));
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
			String text = "Wait for the other player to host the game...";
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.PLAIN, 25));
			FontMetrics fm = g.getFontMetrics();
			int textWidth = fm.stringWidth(text);
			int textHeight = fm.getHeight();
			int centerX = (Game.WIDTH - textWidth) / 2;
			int centerY = (Game.HEIGHT - textHeight) / 2 + fm.getAscent() - 10;
			g.drawString(text, centerX, centerY);

		}

		back.render(g);
		connect.render(g);
		setIP.render(g);
		logo.render(g);
	}

	private Client netDevice;
	private Button back;
	private Button connect;
	private Button setIP;
	private AnimatedSprite logo;
	private String ip;
	private Game game;
	private boolean connecting;
}
