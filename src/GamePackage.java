import java.io.Serializable;
import java.util.ArrayList;

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
		this.shipSpaces = ship.spaces();
		this.borderSpaces = ship.border();
	}
	public boolean done = false;
	public Position position = null;
	public ShootingResponse shootingStatus = null;
	public ArrayList<Position> shipSpaces = null;
	public ArrayList<Position> borderSpaces = null;

	public GamePackage(int err) {
		errorMsg = err;
	}
	public int errorMsg = 0;
}
