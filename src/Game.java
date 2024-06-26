import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Game extends JPanel {
	public Game(JFrame frame) 
	{
		mouse = new Mouse();
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        addMouseWheelListener(mouse);

		texMan = new TextureManager();

		currentScene = new MenuScene(this);
		this.frame = frame;

		background = texMan.getTexture("bg");

		loop = new Timer();
		loop.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				update();
				repaint();
			}
		}, 100, 1000 / 60);
	}

	public void changeScene(Scene newScene) {
		currentScene = newScene;
	}

	public void exit() {
		loop.cancel();
		frame.dispose();
	}

	public Mouse getMouse() {
		return mouse;
	}

	public TextureManager getTextureManager() {
		return texMan;
	}

	private void update()  {
		currentScene.update();
		mouse.update();
	}

	private void render(Graphics2D g) {
		g.drawImage(background, 0, 0, WIDTH, HEIGHT, 0, 0, background.getWidth(), background.getHeight(), null);
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

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	private Scene currentScene;
	private BufferedImage background;
	private Timer loop;
	private TextureManager texMan;
	private Mouse mouse;
	private JFrame frame;

	private static final long serialVersionUID = 1L;
}
