import java.awt.Graphics2D;

public class StartGameScene implements Scene {
	public StartGameScene(Game g) {
		game = g;
		netDevice = new Server();
	}

	@Override
	public void update() {

	}

	@Override
	public void render(Graphics2D g) {
		System.out.println("Renderuje StartGameScene");
	}

	private Server netDevice;
	private Game game;
}
