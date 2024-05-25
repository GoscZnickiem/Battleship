import java.io.*;
import java.net.*;

public class Client extends NetworkDevice
{
	public Client() 
	{
		connected = false;
		connecting = false;
		port = 8900;
		ip = "localhost";
	}

	public void setIP(String ip) {
		this.ip = ip;
	}

	public String getIP() {
		return ip;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getPort() {
		return port;
	}

	@Override
	public void connect()
	{
		if(connecting || connected) {
			return;
		}
		connecting = true;
		connectThread = new Thread(() -> {
			while(!connected && connecting) {
				try {
					socket = new Socket(ip, port);
					connected = true;
					connecting = false;
					System.out.println("Connected to server.");
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("retry connection");
					try {
						Thread.sleep(1000); 
					} catch (InterruptedException e2) {
					e2.printStackTrace();
					}
				}
			}
		});
		connectThread.start();
	}

	@Override
	public void disconnect() {
		connecting = false;
        if (connectThread != null) {
            connectThread.interrupt();
        }
		System.out.println("disconnecting...");
		try {
			if (socket != null && !socket.isClosed()) {
				socket.close();
			}
			connected = false;
			System.out.println("Connection closed.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String ip;
	private int port;
	private Thread connectThread;
}
