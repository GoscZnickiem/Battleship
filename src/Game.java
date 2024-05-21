import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class Game 
{
	public Game() 
	{
		isRunning = true;
		currentScene = new MenuScene(this);
	}

	public void run() 
	{
		long minTime = 2000000000 / getMonitorRefreshRate();
		minTime = 2000000000;	//temp

		while (isRunning) 
		{
			long startTime = System.nanoTime();

			render();
			update();

			long endTime = System.nanoTime();

			long tooEarly = minTime - (endTime - startTime);
			if(tooEarly > 0) 
			{
				try 
				{
                    Thread.sleep(tooEarly / 1000000, (int) (tooEarly % 1000000));
                } 
				catch (InterruptedException e) 
				{
                    e.printStackTrace();
                }
			}
		}
	}

	public void changeScene(Scene newScene)
	{
		currentScene = newScene;
	}

	public void exit()
	{
		isRunning = false;
	}

	private void update() 
	{
		currentScene.update();
	}

	private void render() 
	{
		currentScene.render();
	}

	private int getMonitorRefreshRate() 
	{
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = graphicsEnvironment.getScreenDevices();
        GraphicsDevice device = devices[0];
        DisplayMode displayMode = device.getDisplayMode();
        return displayMode.getRefreshRate();
	}

	private Scene currentScene;
	private boolean isRunning;
	// Tu pewnie canvas lub window (jeszcze to ogarne narazie nie przejmujmy sie grafiką)
}
