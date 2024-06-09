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
				if (done == true)
				{
					networkDevice.sendPackage(new GamePackage(true));
					networkDevice.receivePackage(); // Wait... czekasz na odpowiedź i jej nie czytasz?
					stage = Stage.SHOOTING;
					return;
				}
				if (selectedShip == null)
				{
					selectedShip = Ship.getSelectedShip(ships);
					return;
					// Tu trochę naprawiłem. Wcześniej tworzyłeś nowy statek zamiast faktycznie ustawić selectedShip na odpowiednią wartość
				}

				selectedShip.move(mouse.getPos());
				if (mouse.getMouseScroll() != 0) selectedShip.rotate();

				Position chosenPos = player.getPosOnBoard(true, mouse);

				if (!player.correctShipPos(chosenPos, selectedShip.orientation, selectedShip.length)) return;
				// Skoro correctShipPos sprawdza czy pozycja statku jest poprawna to może przekazanie całego statku jako argument
				// byłoby nieco bardziej czytelne niż przekazanie tylko jego parametrów

				player.putShip(chosenPos, selectedShip.orientation, selectedShip); // Tu też
				selectedShip = null;
				shipsToSet -= 1;

				if (submit.isClicked() && shipsToSet == 0)
				{
					done = true;
				}
				break;

				// Ten komentarz daje tu bo dotyczy całego tego bloku:
				// Kiedy zaznaczony statek zostaje wybrany i postawiony to tak jakby "o tym nie wie"
				// W sensie przekazujesz planszy informacje o tym gdzie ma umieścić statek i ona jakoś go tam sobie reprezentuje
				// Ale to w praktyce tworzy dwa statki: jeden jako przycisk, drugi jako zbiór pól na planszy
				// Nie powiem że to nierozsądna implementacja ale trzeba się przy niej upewnić, że obie kopie statku wiedzą
				// co powinno się z nimi dziać - obecnie statek-przycisk tego nie wie i można go wybrać kilka razy... właściwie
				// to nic nie powstrzymuje gracza przed wybraniem 6 razy statku 1x1.
			}
			case SHOOTING:
			{
				if (activeTurn)
				{
					// getting position for the shoot
					Position chosenPos = player.getPosOnBoard(false, mouse);
					if (!player.correctShootingPos(chosenPos)) return;

					// sending postion to the other player
					GamePackage messagePackage = new GamePackage(chosenPos);
					networkDevice.sendPackage(messagePackage);

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
		// // to po chuj to tu zostawiasz xD
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
	public void render(Graphics2D g) {
		player.render(g);

		submit.render(g);

		if(selectedShip != null)
			selectedShip.render(g);

		for (Ship ship : ships) {
			ship.render(g);
		}

	}
}
