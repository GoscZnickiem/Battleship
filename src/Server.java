import java.io.*;
import java.net.*;

public class Server extends NetworkDevice 
{
	public Server() 
	{
		connected = false;
		connecting = false;
		port = 8900;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public void connect()
	{
		if(connecting || connected) {
			return;
		}
		connecting = true;
		connectThread = new Thread(() -> {
			try {
				if (socket == null || socket.isClosed()) {
					serverSocket = new ServerSocket(port);
					socket = serverSocket.accept();
					connected = true;
					connecting = false;
					System.out.println("Client connected.");
				} else {
					System.out.println("Server is already connected.");
				}         
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		connectThread.start();
	}

	@Override
	public void disconnect() {
        if (connectThread != null) {
            connectThread.interrupt();
        }
		try {
			if (socket != null && !socket.isClosed()) {
				socket.close();
			}
			if (serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}
			connected = false;
			System.out.println("Connection closed.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ServerSocket serverSocket;
	private int port;
	private Thread connectThread;
}
