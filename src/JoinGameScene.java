import java.awt.*;
import java.util.Scanner;

public class JoinGameScene implements Scene {
	public JoinGameScene(Game g) {
		game = g;
		netDevice = new Client();
	}

	@Override
	public void update() {
		Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.print("\n q - quit\n c - connect\n s - send package\n r - read package\n");
			String command = scanner.nextLine();

			if(command.equalsIgnoreCase("q")) {
				netDevice.disconnect();
				game.changeScene(new MenuScene(game));
				break;
			}
			else if(command.equalsIgnoreCase("c")) {
				if(!netDevice.connected()) {
					netDevice.connect();
				} else {
					System.out.println("already connected");
				}
				break;
			}
			else if(command.equalsIgnoreCase("s")) {
				GamePackage messagePackage = new GamePackage(0, command);
				netDevice.sendPackage(messagePackage);
				break;
			}
			else if(command.equalsIgnoreCase("r")) {
				GamePackage receivedPackage = netDevice.receivePackage();
				String receivedMessage = receivedPackage.message;
				System.out.println("Odebrano wiadomość: " + receivedMessage);
				break;
			}
		}
	}

	@Override
	public void render(Graphics2D g) {
		String text = "Join gam";

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

	private Client netDevice;
	private Game game;
}
