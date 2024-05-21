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
			System.out.print("\nWpisz q aby wyjść, s żeby zacząć grać lub r żeby opuścić zrobić render: ");
			String command = scanner.nextLine();

			if(command.equalsIgnoreCase("q"))
			{
				game.exit();
				break;
			}
			else if(command.equalsIgnoreCase("s"))
			{
				game.changeScene(new GameScene(game));
				break;
			}
			else if(command.equalsIgnoreCase("r"))
			{
				break;
			}
		}
	}

	@Override
	public void render() 
	{
		System.out.println("Renderuje MainMenu");
	}

	private Game game;
}
