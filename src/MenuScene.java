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
		String command = "chuj";

		if(command.equalsIgnoreCase("q") || game.getMouse().isClicked())
		{
			game.exit();
		}
		else if(command.equalsIgnoreCase("s"))
		{
			game.changeScene(new StartGameScene(game));
		}
		else if(command.equalsIgnoreCase("j"))
		{
			game.changeScene(new JoinGameScene(game));
		}
		else if(command.equalsIgnoreCase("client"))
		{
			game.changeScene(new NetworkTestScene(game, false));
		}
		else if(command.equalsIgnoreCase("server"))
		{
			game.changeScene(new NetworkTestScene(game, true));
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
