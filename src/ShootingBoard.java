
public class ShootingBoard extends Board {


    public ShootingBoard(Game g)
    {
        super(g, 2 * (Game.WIDTH - SIZE * SPACE_SIZE * 2) / 3 + SIZE * SPACE_SIZE + SPACE_SIZE / 2, 150, "Shooting Board");
    }

    // returns true if a space is shootable, false otherwise
    @Override
    public boolean correctPos(Position a)
    {
        if (this.spaces[a.x][a.y].getHitVal() == Space.HitValue.NONE) return true;
        else return false;
    }

}


