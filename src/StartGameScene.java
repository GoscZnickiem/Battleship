public class StartGameScene implements Scene {
	public StartGameScene(Game g) {
		game = g;
		netDevice = new Server();
	}

	@Override
	public void update() {

	}

	@Override
	public void render() {
		System.out.println("Renderuje StartGameScene");
	}

	private Server netDevice;
	private Game game;
}
