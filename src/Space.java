import java.awt.Graphics2D;

public class Space {
    public static enum HitValue {
        HIT,
        MISS,
        NONE
    };

    public boolean hasShip;
    private HitValue value;
    private Ship ship;
    private Position pos;
	private Button button;

    public Ship getShip()
    {
        return ship;
    }

    public void addShip(Ship ship)
    {
        this.hasShip = true;
        this.ship = ship;
    }

    public Space(Game g, Position pos, int size) 
    { 
		this.pos = pos;
        hasShip = false;
        value = HitValue.NONE;
		button = new Button(g, pos.x, pos.y, size, size, "water", "waterS");
    }

    public Position position()
    {
        return new Position(pos.x, pos.y);
    }

    public void setHit(HitValue value) 
    {
        this.value = value;
    }

    public HitValue getHitVal() 
    {
        return this.value;
    }

    public boolean hasShip()
    {
        return hasShip;
    }

	public void render(Graphics2D g) {
		button.render(g);
	}
}
