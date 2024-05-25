import java.io.*;
import java.net.*;

public abstract class NetworkDevice
{

	public abstract void connect();

	public abstract void disconnect();

	public boolean connected() {
		return connected;
	}

	public boolean connecting() {
		return connecting;
	}

	public boolean sendPackage(GamePackage pkg) 
	{
		if(!connected) {
			return false;
		}
		try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(pkg);
            out.flush();
        }
		catch (IOException e) {
            e.printStackTrace();
        }
		return true;
	}

	public GamePackage receivePackage() {
		if(!connected) {
			return new GamePackage(1);
		}
	    try 
		{
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            return (GamePackage) in.readObject();
        }
		catch (IOException | ClassNotFoundException e) 
		{
            e.printStackTrace();
			return new GamePackage(1);
        }
	}

    protected Socket socket;
	protected volatile boolean connected;
	protected volatile boolean connecting;

}
