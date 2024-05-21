import java.io.*;
import java.net.*;

public class Client implements NetworkDevice
{
	public Client() 
	{
		connected = false;
		port = 8900;
		ip = "localhost";
	}

	@Override
	public void sendPackage(GamePackage pkg) 
	{
		try 
		{
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(pkg);
            out.flush();
        }
		catch (IOException e) 
		{
            e.printStackTrace();
        }
	}

	@Override
	public GamePackage receivePackage()
	{
	    try 
		{
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            return (GamePackage) in.readObject();
        }
		catch (IOException | ClassNotFoundException e) 
		{
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public void connect()
	{
		new Thread(() -> {
			while(!connected)
			{
				try 
				{
					if (!connected) 
					{
						socket = new Socket(ip, port);
						connected = true;
						System.out.println("Connected to server.");
					}
					else
					{
						System.out.println("Client is already connected.");
					}
				} 
				catch (IOException e)
				{
					e.printStackTrace();
					System.out.println("retry connection");
					try {
						Thread.sleep(100); 
					} catch (InterruptedException e2) {
						e2.printStackTrace();
					}
				}
			}
        }).start();
	}

	@Override
	public boolean connected()
	{
		return connected;
	}

	private Socket socket;
	private boolean connected;
	private String ip;
	private int port;
}
