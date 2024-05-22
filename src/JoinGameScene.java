public class JoinGameScene implements Scene {
	public JoinGameScene(Game g) {
		game = g;
		netDevice = new Client();
	}

	@Override
	public void update() {

	}

	@Override
	public void render() {
		System.out.println("Renderuje JoinGameScene");
	}

	private Client netDevice;
	private Game game;
}
