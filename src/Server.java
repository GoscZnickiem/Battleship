import java.io.*;
import java.net.*;

public class Server implements NetworkDevice
{
	public Server() 
	{
		connected = false;
		port = 8900;
	}

	@Override
	public void sendPackage(GamePackage pkg) 
	{
		try 
		{
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
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
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
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
		new Thread(() -> 
		{
			try 
			{
				if (clientSocket == null || clientSocket.isClosed()) 
				{
					serverSocket = new ServerSocket(port);
					clientSocket = serverSocket.accept();
					connected = true;
					System.out.println("Client connected.");
				} 
				else 
				{
					System.out.println("Server is already connected.");
				}         
			} 
			catch (IOException e) 
			{
                e.printStackTrace();
            }
        }).start();
	}

	@Override
	public boolean connected()
	{
		return connected;
	}

	private ServerSocket serverSocket;
    private Socket clientSocket;
	private boolean connected;
	private int port;
}
