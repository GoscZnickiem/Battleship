import java.io.*;
import java.net.*;

public class Server extends NetworkDevice 
{
	public Server() 
	{
		connected = false;
		port = 8900;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public void connect()
	{
		if(connecting) {
			return;
		}
		connecting = true;
		new Thread(() -> 
		{
			try 
			{
				if (socket == null || socket.isClosed()) 
				{
					serverSocket = new ServerSocket(port);
					socket = serverSocket.accept();
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

	private ServerSocket serverSocket;
	private int port;
}
