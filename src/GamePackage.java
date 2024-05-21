import java.io.Serializable;

public class GamePackage implements Serializable
{
	public GamePackage(int num, String msg)
	{
		number = num;
		message = msg;
	}

	public int number;
	public String message;
}
