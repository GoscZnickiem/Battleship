public class Space {
    public static enum HitValue {
        HIT,
        MISS,
        NONE
    };

    public boolean hasShip;
    private HitValue value;
    private Ship ship;

    public Ship getShip()
    {
        return ship;
    }

    public Space() 
    { 
        this.hasShip = false;
        this.value = HitValue.NONE;
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