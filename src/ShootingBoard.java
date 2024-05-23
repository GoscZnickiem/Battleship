
public class ShootingBoard extends Board {


    public ShootingBoard()
    {
        super();
        this.POSITION = new Position(700, 100);
    }

    // returns true if a space is shootable, false otherwise
    public boolean shootable(Position a)
    {
        if (this.spaces[a.x][a.y].getHitVal() == Space.HitValue.NONE) return true;
        else return false;
    }
    

}


