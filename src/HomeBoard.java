public class HomeBoard extends Board 
{
    public HomeBoard()
    {
        super();
        this.POSITION = new Position(100, 100);
    }
    
    public boolean hasShip(Position pos)
    {
        return this.spaces[pos.x][pos.y].hasShip();
    }

    public boolean killedShip(Position pos)
    {

        this.spaces[pos.x][pos.y].setHit(Space.HitValue.HIT);
        return this.spaces[pos.x][pos.y].getShip().dead(); 
    }

}