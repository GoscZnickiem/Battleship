import java.io.*;
import java.net.*;

public abstract class NetworkDevice
{

	public abstract void connect();

	public boolean connected() {
		return connected;
	}

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

    protected Socket socket;
	protected boolean connected;

}
