import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class Game {
	public Game() {
		m_isRunning = true;
	}

	public void run() {
		long minTime = 2000000000 / getMonitorRefreshRate();
		minTime = 2000000000;	//temp

		while (m_isRunning) {
			long startTime = System.nanoTime();

			update();
			render();

			long endTime = System.nanoTime();

			long tooEarly = minTime - (endTime - startTime);
			if(tooEarly > 0) {
				try {
                    Thread.sleep(tooEarly / 1000000, (int) (tooEarly % 1000000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
			}
		}
	}

	private void update() {
		System.out.println("updat");
	}

	private void render() {
		System.out.println("rendr");
	}

	private int getMonitorRefreshRate() {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = graphicsEnvironment.getScreenDevices();
        GraphicsDevice device = devices[0];
        DisplayMode displayMode = device.getDisplayMode();
        return displayMode.getRefreshRate();
	}

	private boolean m_isRunning;
	// Tu pewnie m_canvas lub m_window (jeszcze to ogarne narazie nie przejmujmy sie grafiką)
}
