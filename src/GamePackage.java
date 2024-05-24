import java.io.Serializable;

public class GamePackage implements Serializable
{
	public GamePackage(boolean done)
	{
		this.done = done;
	}
	public GamePackage(Position pos)
	{
		this.position = pos;
	}
	public GamePackage(ShootingResponse a, Ship ship)
	{
		this.shootingStatus = a;
		this.ship = ship;
	}
	public boolean done = false;
	public Position position = null;
	public ShootingResponse shootingStatus = null;
	public Ship ship;


	
	// residuals which are not deleted because they are used in Maciek's code
	public int number = 0;
	public String message = null;
	public GamePackage(int number, String msg)
	{
		this.number = number;
		this.message = msg;
	}
}
