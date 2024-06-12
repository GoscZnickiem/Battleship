import java.awt.Graphics2D;

public class Space {
    public static enum HitValue {
        HIT,
        MISS,
        NONE
    };
    public static final String hidden_water = "hidden_water";
    public static final String empty_water = "empty_water";
    public static final String exploded_water = "exploded_water";
    public static final String ship_space = "ship_space";

    public boolean active;
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
        this.active = true;
		this.pos = pos;
        hasShip = false;
        value = HitValue.NONE;
		button = new Button(g, pos.x, pos.y, size, size, hidden_water, hidden_water + (this.active ? "A" : "S"));
    }

    public Position position()
    {
        return new Position(pos.x, pos.y);
    }

    public void setHit(HitValue value) 
    {
        switch (value) {
            case NONE:
                if (this.hasShip)
                {
                    this.button.setTex(ship_space, ship_space + (this.active ? "A" : "S"));
                }
                else
                {
                    this.button.setTex(hidden_water, hidden_water + (this.active ? "A" : "S"));
                }
                break;
        
            case MISS:
                this.button.setTex(empty_water, empty_water + (this.active ? "A" : "S"));
                break;
            
            case HIT:
                this.button.setTex(exploded_water, exploded_water + (this.active ? "A" : "S"));
                break;
        }
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
