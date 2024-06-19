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
	private int shipsToSet = 1;
	public static enum Stage { SHOOTING, SETTING }
	private Button submit;
	private ArrayList<Ship> ships;

	public GameScene(Game g, String name, boolean activeTurn, NetworkDevice networkDevice) 
	{
		this.shipsToSet = 1;
		this.submit = new Button(g, Game.WIDTH / 2, 50, 200, 48, "start_shooting", "t1");
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
					for (Ship ship : ships)
					{
						if (!ship.onBoard)
							ship.make_invisible();
					}
					return;
				}

				if (selectedShip == null)
				{
					selectedShip = Ship.getSelectedShip(ships);
					return;
				}

				if (selectedShip.onBoard) 
				{
					selectedShip.removeShip();
					shipsToSet += 1;
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
				shipsToSet -= 1;
				selectedShip = null;

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
		player.render(g, stage);

		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 25));
		FontMetrics fm = g.getFontMetrics();
		int textHeight = fm.getHeight();

		switch (stage)
		{
			case SETTING:
				if(selectedShip != null)
					selectedShip.render(g);

				if (shipsToSet > 0)
				{
					String text[] = {"Set your ships on your Home Board", "Click to select the ship, scroll to rotate, and click on the chosen space"};
					int pos[] = {30, 60};

					
					for (int i = 0; i < 2; i++)
					{
						int textWidth = fm.stringWidth(text[i]);
						int X = (Game.WIDTH / 2 - textWidth / 2);
						int Y = (pos[i] - textHeight / 2) + fm.getAscent();
						g.drawString(text[i], X, Y);
					}
		
				}
		
				if (shipsToSet <= 0)
				{
					submit.render(g);
				}

				break;
			case SHOOTING:

				if (activeTurn)
				{
					String text = "Your Turn! Choose the space on the Shooting Board to shoot";
					int textWidth = fm.stringWidth(text);
					int X = (Game.WIDTH / 2 - textWidth / 2);
					int Y =	(50 - textHeight / 2) + fm.getAscent();
					g.drawString(text, X, Y);
				}
				else
				{
					String text = "Your Opponenent's turn! Wait for them to make their shot";
					int textWidth = fm.stringWidth(text);
					int X = (Game.WIDTH / 2 - textWidth / 2);
					int Y =	(50 - textHeight / 2) + fm.getAscent();
					g.drawString(text, X, Y);
				}

				
				break;
		}
		

		for (Ship ship : ships) {
			ship.render(g);
		}
	}
}
