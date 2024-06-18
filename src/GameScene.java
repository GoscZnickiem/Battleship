import java.awt.*;
import java.util.ArrayList;

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
		this.shipsToSet = 1;
		this.submit = new Button(g, Game.WIDTH / 2, 640, 100, 50, "start_shooting", "t1");
		this.stage = Stage.SETTING;
		this.game = g;
		this.mouse = g.getMouse();
		this.player = new Player(g, name);
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
				if (done)
				{
					networkDevice.sendPackage(new GamePackage(true));
					System.out.println("Package sent");
					if (networkDevice.receivePackage().done)
					stage = Stage.SHOOTING;
					System.out.println("Changing scene");
					return;
				}

				if (submit.isClicked() && shipsToSet <= 0)
				{
					done = true;
					submit.visible = false;
					return;
				}

				if (selectedShip == null)
				{
					selectedShip = Ship.getSelectedShip(ships);
					return;
				}

				selectedShip.move(mouse.getPos());
				if (mouse.getMouseScroll() != 0) selectedShip.rotate();

				Position chosenPos = player.getPosOnBoard(true, mouse, selectedShip);
				if (chosenPos == null) return;
				System.out.println(chosenPos.x);
				System.out.println(chosenPos.y);

				System.out.println(player.correctShipPos(chosenPos, selectedShip));
				if (!player.correctShipPos(chosenPos, selectedShip)) return;

				player.putShip(chosenPos, selectedShip);
				selectedShip = null;
				shipsToSet -= 1;

				break;
			}
			case SHOOTING:
			{
				if (activeTurn)
				{
					// getting position for the shoot
					Position chosenPos = player.getPosOnBoard(false, mouse, new Ship());
					if (!player.correctShootingPos(chosenPos)) return;

					// sending postion to the other player
					GamePackage messagePackage = new GamePackage(chosenPos);
					networkDevice.sendPackage(messagePackage);

					// waiting for the response
					GamePackage receivedPackage = networkDevice.receivePackage();
					ShootingResponse receivedResponse = receivedPackage.shootingStatus;
					ArrayList<Position> shipSpaces = receivedPackage.shipSpaces;
					ArrayList<Position> borderSpaces = receivedPackage.borderSpaces;


					// updating the ShootingBoard to reflect whether the shot is a miss, a wound or a kill
					player.updateHitsOnPlayerBoard(false, chosenPos, receivedResponse, shipSpaces, borderSpaces);

			
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
					if (ship == null) ship = new Ship();
					player.updateHitsOnPlayerBoard(true, receivedPosition, response, ship.spaces(), ship.border());

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
	}

	@Override
	public void render(Graphics2D g) {
		player.render(g);

		submit.render(g);
		
		if (stage == Stage.SETTING)
		{


			if(selectedShip != null)
				selectedShip.render(g);
			for (Ship ship : ships) {
				ship.render(g);
			}
		}
	}
}
