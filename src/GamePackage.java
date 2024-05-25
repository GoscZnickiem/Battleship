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
}
