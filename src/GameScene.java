import java.awt.*;
import java.util.Scanner;

public class GameScene implements Scene 
{
	private boolean activeTurn;
	private NetworkDevice networkDevice;
	private Player player;
	private Game game;
	private Stage stage;
	private enum Stage { SHOOTING, SETTING }

	public GameScene(Game g, String name, boolean activeTurn, NetworkDevice networkDevice) 
	{
		this.stage = Stage.SETTING;
		this.game = g;
		this.player = new Player(name, game);
		this.activeTurn = activeTurn;
		this.networkDevice = networkDevice;
	}

	@Override
	public void update() 
	{
		switch (stage) {
			case SETTING:
				
				break;
		
			case SHOOTING:
				if (activeTurn)
				{
					// getting position for the shoot
					Position chosenPos = player.getShootingPos();
					if (chosenPos == null) return;

					// sending postion to the other player
					GamePackage messagePackage = new GamePackage(chosenPos);
					this.networkDevice.sendPackage(messagePackage);

					// waiting for the response
					GamePackage receivedPackage = networkDevice.receivePackage();
					ShootingResponse receivedResponse = receivedPackage.shootingStatus;

					// updating the ShootingBoard to reflect whether the shot is a miss, a wound or a kill
					player.updateHitsOnPlayerBoard(false, chosenPos, receivedResponse);

			
					// sending package to let the other player know that it is their turn
					messagePackage = new GamePackage(true);
					this.networkDevice.sendPackage(messagePackage);
					activeTurn = false;
				}
				else
				{
					// waiting for the package with the position of the other player's shot
					GamePackage receivedPackage = networkDevice.receivePackage();
					Position receivedPosition = receivedPackage.position;

					// checking whether a shot is a kill, a wound or a miss
					ShootingResponse response = player.getShootingResponse(receivedPosition);
					player.updateHitsOnPlayerBoard(true, receivedPosition, response);

					// sending the information to the other player
					GamePackage messagePackage = new GamePackage(response);
					this.networkDevice.sendPackage(messagePackage);

					// waiting for the my turn package
					receivedPackage = networkDevice.receivePackage();
					activeTurn = true;
				}

				break;
		}

		// nie wiadomo po co to
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
			else if(command.equalsIgnoreCase("r"))
			{
				break;
			}
		}
	}

	@Override
	public void render(Graphics2D g) 
	{
		System.out.println("Renderuje GameScene");
	}
}
