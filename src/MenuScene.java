import java.awt.*;

public class MenuScene implements Scene 
{
	public MenuScene(Game g) 
	{
		game = g;
		quitButton = new Button(game, Game.WIDTH / 2, 650, 160, 80, "t1", "t2");
		startButton = new Button(game, Game.WIDTH / 2, 550, 160, 80, "t1", "t2");
		joinButton = new Button(game, Game.WIDTH / 2, 450, 160, 80, "t1", "t2");
	}

	@Override
	public void update() 
	{
		if(quitButton.isClicked()) {
			game.exit();
		} else if(startButton.isClicked()) {
			game.changeScene(new StartGameScene(game));
		} else if(joinButton.isClicked()) {
			game.changeScene(new JoinGameScene(game));
		}
	}

	@Override
	public void render(Graphics2D g) 
	{
		String text = "Main Menu";
		g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        int centerX = (Game.WIDTH - textWidth) / 2;
        int centerY = (Game.HEIGHT - textHeight) / 3 + fm.getAscent();
        g.drawString(text, centerX, centerY);

		g.drawImage(game.getTextureManager().getTexture("test"), 100, 200, null);

		quitButton.render(g);
		startButton.render(g);
		joinButton.render(g);
	}

	private Button quitButton;
	private Button startButton;
	private Button joinButton;
	private Game game;
}
