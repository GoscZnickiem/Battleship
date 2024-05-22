import java.awt.*;
import java.util.Scanner;

public class MenuScene implements Scene 
{
	public MenuScene(Game g) 
	{
		game = g;
	}

	@Override
	public void update() 
	{
		Scanner scanner = new Scanner(System.in);
		while(true) 
		{
			System.out.print("\nWpisz polecenie: \n q - wyjdź\n s - start game\n j - join game\n r - wykonaj render\n client - otwórz test clienta\n server - otwórz test servera\n-> ");
			String command = scanner.nextLine();

			if(command.equalsIgnoreCase("q"))
			{
				game.exit();
				break;
			}
			else if(command.equalsIgnoreCase("s"))
			{
				game.changeScene(new StartGameScene(game));
				break;
			}
			else if(command.equalsIgnoreCase("j"))
			{
				game.changeScene(new JoinGameScene(game));
				break;
			}
			else if(command.equalsIgnoreCase("r"))
			{
				break;
			}
			else if(command.equalsIgnoreCase("client"))
			{
				game.changeScene(new NetworkTestScene(game, false));
				break;
			}
			else if(command.equalsIgnoreCase("server"))
			{
				game.changeScene(new NetworkTestScene(game, true));
				break;
			}
		}
	}

	@Override
	public void render(Graphics2D g) 
	{
		String text = "Main Menu";
		g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        int centerX = (Game.WIDTH - textWidth) / 2;
        int centerY = (Game.HEIGHT - textHeight) / 2 + fm.getAscent();
        g.drawString(text, centerX, centerY);

		g.drawImage(game.getTexture("test"), 100, 200, null);
	}

	private Game game;
}
