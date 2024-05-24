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

    public Ship getShip()
    {
        return ship;
    }

    public void addShip(Ship ship)
    {
        this.hasShip = true;
        this.ship = ship;
    }

    public Space() 
    { 
        this.hasShip = false;
        this.value = HitValue.NONE;
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
}