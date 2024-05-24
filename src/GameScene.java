import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GameScene implements Scene 
{
	private boolean activeTurn;
	private NetworkDevice networkDevice;
	private Player player;
	private Game game;
	private Mouse mouse;
	private Stage stage;
	private boolean done;
	private Ship selectedShip;
	private int shipsToSet = 10;
	private enum Stage { SHOOTING, SETTING }
	private Button submit;
	private ArrayList<Ship> ships;

	public GameScene(Game g, String name, boolean activeTurn, NetworkDevice networkDevice) 
	{
		this.shipsToSet = 10;
		this.submit = new Button(g, 100, 600, 40, 75, "start_shooting", "start_shooting");
		this.stage = Stage.SETTING;
		this.game = g;
		this.mouse = g.getMouse();
		this.player = new Player(name);
		this.activeTurn = activeTurn;
		this.networkDevice = networkDevice;
		this.done = false;
		this.selectedShip = null;
		this.ships = new ArrayList<>();
		ships = Ship.initialize(ships, game);
	}

	@Override
	public void update() 
	{
		switch (stage) {
			case SETTING:
			{
				if (done == true)
				{
					this.networkDevice.sendPackage(new GamePackage(true));
					networkDevice.receivePackage();
					this.stage = Stage.SHOOTING;
					return;
				}
				if (this.selectedShip == null)
				{
					Ship selectedShip = Ship.getSelectedShip(ships);
					if (selectedShip == null) return;
				}
				
				Position chosenPos = player.getPosOnBoard(true, mouse);
				Orientation chosenOrientation = (mouse.getMouseScroll() % 2 == 0) ? Orientation.VERTICAL : Orientation.HORIZONTAL;
				if (this.selectedShip.orientation != chosenOrientation) this.selectedShip.rotate();
				if (player.correctShipPos(chosenPos, chosenOrientation, selectedShip.length) == false) return;

				player.putShip(chosenPos, chosenOrientation, selectedShip);
				this.selectedShip = null;
				this.shipsToSet -= 1;

				if (submit.isClicked() && this.shipsToSet == 0)
				{
					this.done = true;
				}
				break;
			}
			case SHOOTING:
			{
				if (activeTurn)
				{
					// getting position for the shoot
					Position chosenPos = player.getPosOnBoard(false, mouse);
					if (player.correctShootingPos(chosenPos) == false) return;

					// sending postion to the other player
					GamePackage messagePackage = new GamePackage(chosenPos);
					this.networkDevice.sendPackage(messagePackage);

					// waiting for the response
					GamePackage receivedPackage = networkDevice.receivePackage();
					ShootingResponse receivedResponse = receivedPackage.shootingStatus;
					Ship ship = receivedPackage.ship;

					// updating the ShootingBoard to reflect whether the shot is a miss, a wound or a kill
					player.updateHitsOnPlayerBoard(false, chosenPos, receivedResponse, ship);

			
					// sending package to let the other player know that it is their turn
					this.networkDevice.sendPackage(new GamePackage(true));
					activeTurn = false;
				}
				else
				{
					// waiting for the package with the position of the other player's shot
					GamePackage receivedPackage = networkDevice.receivePackage();
					Position receivedPosition = receivedPackage.position;

					// checking whether a shot is a kill, a wound or a miss
					ShootingResponse response = player.getShootingResponse(receivedPosition);
					Ship ship = player.getShip(receivedPosition);
					player.updateHitsOnPlayerBoard(true, receivedPosition, response, ship);

					// sending the information to the other player
					GamePackage messagePackage = new GamePackage(response, ship);
					this.networkDevice.sendPackage(messagePackage);

					// waiting for the my turn package
					networkDevice.receivePackage();
					activeTurn = true;
				}
				break;
			}
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
