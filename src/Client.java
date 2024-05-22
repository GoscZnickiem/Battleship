import java.io.*;
import java.net.*;

public class Client extends NetworkDevice
{
	public Client() 
	{
		connected = false;
		port = 8900;
		ip = "localhost";
	}

	@Override
	public void connect()
	{
		new Thread(() -> {
			while(!connected)
			{
				try 
				{
					socket = new Socket(ip, port);
					connected = true;
					System.out.println("Connected to server.");
				} 
				catch (IOException e)
				{
					e.printStackTrace();
					System.out.println("retry connection");
					try {
						Thread.sleep(1000); 
					} catch (InterruptedException e2) {
						e2.printStackTrace();
					}
				}
			}
        }).start();
	}

	private String ip;
	private int port;
}
