import java.awt.*;

public class MenuScene implements Scene 
{
	public MenuScene(Game g) 
	{
		game = g;
		quitButton = new Button(game, Game.WIDTH / 2, 600, 160, 80, "quitgame_button", "quitgame_buttonA");
		joinButton = new Button(game, Game.WIDTH / 2, 500, 160, 80, "joingame_button", "joingame_buttonA");
		startButton = new Button(game, Game.WIDTH / 2, 400, 160, 80, "hostgame_button", "hostgame_buttonA");
		logo = new AnimatedSprite(game, Game.WIDTH / 2, 150, 1200, 200, "air", 1, 1);
		logo.setForeground("logo");
	}

	@Override
	public void update() {
		if(quitButton.isClicked()) {
			game.exit();
		} else if(startButton.isClicked()) {
			game.changeScene(new TransitionScene(game, this, new StartGameScene(game)));
		} else if(joinButton.isClicked()) {
			game.changeScene(new TransitionScene(game, this, new JoinGameScene(game)));
		}
	}

	@Override
	public void render(Graphics2D g) {

		String text = "Main Menu";
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 40));
		FontMetrics fm = g.getFontMetrics();
		int textHeight = fm.getHeight();
		int textWidth = fm.stringWidth(text);
		int centerX = (Game.WIDTH - textWidth) / 2;
		int centerY = (Game.HEIGHT - textHeight) / 2 - 50 + fm.getAscent();
		g.drawString(text, centerX, centerY);


		quitButton.render(g);
		startButton.render(g);
		joinButton.render(g);
		logo.render(g);
	}

	private Button quitButton;
	private Button startButton;
	private Button joinButton;
	private AnimatedSprite logo;
	private Game game;
}
