import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Game extends JPanel {
	public Game() 
	{
		currentScene = new MenuScene(this);

		loop = new Timer();
		loop.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				update();
				repaint();
			}
		}, 100, 1000 / 30);
	}

	public void changeScene(Scene newScene)
	{
		currentScene = newScene;
	}

	public void exit()
	{
		loop.cancel();
	}

	private void update() 
	{
		currentScene.update();
	}

	private void render(Graphics2D g) 
	{
		currentScene.render(g);
	}

    private final Stroke stroke = new BasicStroke(3);

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(stroke);
		render(g2d);
	}

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	private Scene currentScene;
	private Timer loop;

	private static final long serialVersionUID = 1L;
}
