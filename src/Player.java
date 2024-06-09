import java.awt.Graphics2D;

public class Player 
{
    private String name;
    private HomeBoard homeBoard;
    private ShootingBoard shootingBoard;

    public Player(Game g, String name)
    {
        this.name = new String(name);
        this.homeBoard = new HomeBoard(g);
        this.shootingBoard = new ShootingBoard(g);
    }

    public String getName()
    {
        return this.name;
    }

    // if board == true then on homeBoard
    // if board == false then on ShootingBoard
    public Position getPosOnBoard(boolean homeBd, Mouse mouse)
    {
        Board board = null;
        if (homeBd) board = this.homeBoard;
        else board = this.shootingBoard;
        Position mouse_pos = mouse.clickedPosition();
        Position pos = null;
        if (mouse_pos != null)
        {
            pos = board.convertToBoardPosition(mouse_pos); 
        }
        return pos;
    }

    public boolean correctShootingPos(Position pos)
    {
        if (pos != null && this.shootingBoard.correctPos(pos)) return true;
        return false;
    }

    public boolean correctShipPos(Position pos, Orientation orientation, int length)
    {
        if (pos == null) return false;
        Position a = new Position(pos.x, pos.y);
        boolean cond = orientation == Orientation.HORIZONTAL;
        for (int i = 0; i < length; i++)
        {
            if (this.homeBoard.correctPos(a)) return false;
            if (cond) 
                a.x += 1;
            else
                a.y += 1;
        }
        return true;
    }

    public void putShip(Position pos, Orientation orientation, Ship ship)
    {
        boolean cond = orientation == Orientation.HORIZONTAL;
        Position a = new Position(pos.x, pos.y);
        for (int i = 0; i < ship.length; i++)
        {
            this.homeBoard.addShip(a, ship);
            if (cond) 
                a.x += 1;
            else
                a.y += 1;
        }
    }

    // if board == true then on homeBoard
    // if board == false then on ShootingBoard
    public void updateHitsOnPlayerBoard(boolean homeBd, Position pos, ShootingResponse response, Ship ship)
    {
        Board board = null;
        if (homeBd) 
            board = this.homeBoard;
        else 
            board = this.shootingBoard;
        board.updateHits(pos, response, ship);
    }

    public ShootingResponse getShootingResponse(Position pos)
    {
        if (this.homeBoard.hasShip(pos))
        {
            if (this.homeBoard.killedShip(pos))
                return ShootingResponse.KILLED;
            else
                return ShootingResponse.WOUNDED;
        }
        else
        {
            return ShootingResponse.MISSED;
        }
    }

    public Ship getShip(Position pos)
    {
        return this.homeBoard.getShip(pos);
    }

	public void render(Graphics2D g) {
		shootingBoard.render(g);
		homeBoard.render(g);
	}
}
