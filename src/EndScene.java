import java.awt.*;

public class EndScene implements Scene {

    private Game game;
    private Button menuButton;
    private String[] text;
    private static final String[] texts[] = {
        {"Victory!", 
        "Congratulations, Admiral!", 
        "You have successfully outmaneuvered your opponent and emerged victorious.", 
        "Your strategic brilliance and decisive actions have led your fleet to a glorious triumph.", 
        "The sea belongs to you!"}, 
        {"Defeat", 
        "Your fleet has been overpowered by your opponent's cunning strategy.", 
        "Despite your best efforts, the tides of battle have turned against you.", 
        "Reflect on your tactics and return stronger for the next encounter.", 
        "The ocean awaits your next command!"}};

	public EndScene(Game g, boolean won) 
	{
        this.game = g;
        this.text = texts[won ? 0 : 1];
        this.menuButton = new Button(g, Game.WIDTH / 2, 500, 160, 80, "mainmenu_button", "mainmenu_buttonA");
	}

	@Override
	public void update() 
	{
		if (menuButton.isClicked())
        {
            game.changeScene(new MenuScene(game));
        }
	}

	@Override
	public void render(Graphics2D g) {
		
        this.menuButton.render(g);
        
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 25));
        FontMetrics fm = g.getFontMetrics();
        int textHeight = fm.getHeight();

        for (int i = 0; i < this.text.length; i++)
        {
            String t = text[i]; 
            int textWidth = fm.stringWidth(t);
            int X = (Game.WIDTH / 2 - textWidth / 2);
            int Y = (100 + 2 * i * textHeight) + fm.getAscent();
            g.drawString(t, X, Y);
        }
    }
}
