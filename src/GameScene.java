import java.util.Scanner;

public class GameScene implements Scene 
{
	public GameScene(Game g) 
	{
		game = g;
	}

	@Override
	public void update() 
	{
		Scanner scanner = new Scanner(System.in);
		while(true) 
		{
			System.out.print("\nWpisz q aby wyjść do menu, r żeby zrobić render lub a żeby zrobić the funny: ");
			String command = scanner.nextLine();

			if(command.equalsIgnoreCase("q"))
			{
				game.changeScene(new MenuScene(game));
				break;
			}
			if(command.equalsIgnoreCase("a"))
			{
				System.out.println("coś robie idk");
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
		System.out.println("Renderuje GameScene");
	}

	private Game game;
}
