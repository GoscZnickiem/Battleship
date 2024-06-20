import java.awt.*;
import javax.swing.*;


public class JoinGameScene implements Scene {
	public JoinGameScene(Game g) {
		game = g;
		netDevice = new Client();

		back = new Button(game, Game.WIDTH / 2, 650, 160, 80, "goback_button", "goback_buttonA");
		connect = new Button(game, Game.WIDTH / 2, 550, 160, 80, "connect_button", "connect_buttonA");
		setIP = new Button(game, Game.WIDTH / 2, 450, 160, 80, "setip_button", "setip_buttonA");
		ip = "IP: localhost";
	}

	@Override
	public void update() {
		if(back.isClicked()) {
			netDevice.disconnect();
			game.changeScene(new TransitionScene(game, this, new MenuScene(game)));
		} else if(connect.isClicked()) {
			netDevice.connect();
		} else if(setIP.isClicked()) {
			String newip = JOptionPane.showInputDialog(game, "Input IP:");
			if (newip != null && !newip.trim().isEmpty()) {
				ip = "IP: " + newip;
				netDevice.setIP(ip);
			}
		}

		if(netDevice.connected()) {
			GamePackage gp = netDevice.receivePackage();
			game.changeScene(new TransitionScene(game, this, new GameScene(game, "Mariusz", gp.done, netDevice)));
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
		setIP.render(g);
	}

	private Client netDevice;
	private Button back;
	private Button connect;
	private Button setIP;
	private String ip;
	private Game game;
}
