import java.awt.Graphics2D;
import java.awt.*;

public class TransitionScene implements Scene {
	private Game game;
	private Scene prevScene;
	private Scene nextScene;

	private int time = 0;
	private int alpha = 0;

	private final int duration = 30;

	TransitionScene(Game g, Scene s1, Scene s2) {
		game = g;
		prevScene = s1;
		nextScene = s2;
	}

	@Override
	public void update() {
		if(time < duration / 2) {
			alpha = (int)((float)time * 2 / (float)duration * 255);
		} else {
			alpha = (int)((float)(duration - time) * 2 / (float)duration * 255);
		}
		if(time == duration) {
			game.changeScene(nextScene);
		}
		time++;
	}

	@Override
	public void render(Graphics2D g) {
		if(time < duration / 2) {
			prevScene.render(g);
		} else {
			nextScene.render(g);
		}
		g.setColor(new Color(0, 0, 0, alpha));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
	}
}
