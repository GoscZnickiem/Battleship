import java.util.Scanner;

public class NetworkTestScene implements Scene 
{
	public NetworkTestScene(Game g, boolean server) 
	{
		game = g;
		this.server = server;
		if (server) 
		{
            networkDevice = new Server();
        } else 
		{
            networkDevice = new Client();
        }
	}

	@Override
	public void update() 
	{
		networkDevice.connect(); // It should not try to connect many times
        while (!networkDevice.connected()) {
            try {
                Thread.sleep(1000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
		Scanner scanner = new Scanner(System.in);
		while(true) 
		{
			System.out.print("\nWpisz cos (q aby wyjsc): ");
			String command = scanner.nextLine();

			if(command.equalsIgnoreCase("q"))
			{
				game.changeScene(new MenuScene(game));
				break;
			}
			else if(command.equalsIgnoreCase("r"))
			{
				GamePackage receivedPackage = networkDevice.receivePackage();
				String receivedMessage = receivedPackage.message;
				System.out.println("Odebrano wiadomość: " + receivedMessage);
				break;
			}
			else 
			{
				GamePackage messagePackage = new GamePackage(0, command);
				networkDevice.sendPackage(messagePackage);
			}
		}
	}

	@Override
	public void render() 
	{
		System.out.println("Renderuje Client/Server");
	}

	private NetworkDevice networkDevice;
	private boolean server;
	private Game game;
}
