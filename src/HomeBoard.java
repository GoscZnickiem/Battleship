public class HomeBoard extends Board 
{
    public HomeBoard(Game g)
    {
        super(g, (Game.WIDTH - SIZE * SPACE_SIZE * 2) / 3 + SPACE_SIZE / 2, 150, "Home Board");
    }
    
    public boolean hasShip(Position pos)
    {
        return this.spaces[pos.x][pos.y].hasShip();
    }

    public boolean killedShip(Position pos)
    {

        this.spaces[pos.x][pos.y].setHit(Space.HitValue.HIT);
        Ship ship = this.spaces[pos.x][pos.y].getShip();
        if (ship == null) return false;
        else return ship.dead();
    }

    @Override
    public boolean correctPos(Position a)
    {
        if (a.x < 0 || a.x >= SIZE || a.y < 0 || a.y >= SIZE || this.spaces[a.x][a.y].hasShip())
        {
            return false;
        }
        for (int i = -1; i <= 1; i++)
        {
            for (int j = -1; j <= 1; j++)
            {
                if (a.x + i >= 0 && a.x + i < SIZE && a.y + j >= 0 && a.y + j < SIZE && this.spaces[a.x + i][a.y + j].hasShip())
                {
                    return false;
                }
            }
        }
        return true;
    }

    public Ship getShip(Position pos)
    {
        return this.spaces[pos.x][pos.y].getShip();
    }

    public void addShip(Position pos, Ship ship)
    {
        this.spaces[pos.x][pos.y].addShip(ship);
        ship.addShipSpace(this.spaces[pos.x][pos.y]);
        for (int i = -1; i <= 1; i++)
        {
            for (int j = -1; j <= 1; j++)
            {
                if (pos.x + i >= 0 && pos.x + i < Board.SIZE && pos.y + j >= 0 && pos.y + j < Board.SIZE)
                    ship.addBorderSpace(this.spaces[pos.x + i][pos.y + j]);
            }
        }
    }


}
