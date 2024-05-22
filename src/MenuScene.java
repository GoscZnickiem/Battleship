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
	public void render() 
	{
		System.out.println("Renderuje MainMenu");
	}

	private Game game;
}
