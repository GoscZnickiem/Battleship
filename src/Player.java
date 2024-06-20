import java.awt.Graphics2D;
import java.util.ArrayList;

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

    public boolean hasWon()
    {
        return homeBoard.shipsNumber > 0 && shootingBoard.shipsNumber <= 0;
    }

    public boolean hasLost()
    {
        return homeBoard.shipsNumber <= 0 && shootingBoard.shipsNumber > 0;
    }

    // if board == true then on homeBoard
    // if board == false then on ShootingBoard
    public Position getPosOnBoard(boolean homeBd, Mouse mouse, Ship ship)
    {
        Board board = null;
        if (homeBd) board = this.homeBoard;
        else board = this.shootingBoard;
        Position clickedPos = mouse.clickedPosition();
        if (clickedPos == null) return null;
        int mult = ship.length - 1;
        if (ship.orientation == Orientation.HORIZONTAL)
        {
            clickedPos.x -= mult * Board.SPACE_SIZE / 2;
        }
        else 
        {
            clickedPos.y -= mult * Board.SPACE_SIZE / 2;
        }
        return board.convertToBoardPosition(clickedPos);
    }

    public boolean correctShootingPos(Position pos)
    {
        if (pos != null && this.shootingBoard.correctPos(pos)) return true;
        return false;
    }

    public boolean correctShipPos(Position pos, Ship ship)
    {
        if (pos == null) return false;
        
        Position a = new Position(pos.x, pos.y);
        boolean cond = ship.orientation == Orientation.HORIZONTAL;
        for (int i = 0; i < ship.length; i++)
        {
            if (!this.homeBoard.correctPos(a)) return false;
            if (cond) 
                a.x += 1;
            else
                a.y += 1;
        }

        return true;
    }

    public void putShip(Position pos, Ship ship)
    {
        boolean cond = ship.orientation == Orientation.HORIZONTAL;
        Position a = new Position(pos.x, pos.y);
        for (int i = 0; i < ship.length; i++)
        {
            this.homeBoard.addShip(a, ship);
            if (cond) 
                a.x += 1;
            else
                a.y += 1;
        }
        ship.snap_to_space(new Position (this.homeBoard.position.x + pos.x * Board.SPACE_SIZE, this.homeBoard.position.y + pos.y * Board.SPACE_SIZE));
        ship.onBoard = true;
    }

    // if board == true then on homeBoard
    // if board == false then on ShootingBoard
    public void updateHitsOnPlayerBoard(boolean homeBd, Position pos, ShootingResponse response, ArrayList<Position> shipSpaces, ArrayList<Position> borderSpaces)
    {
        Board board = null;
        if (homeBd) 
            board = this.homeBoard;
        else 
            board = this.shootingBoard;
        board.updateHits(pos, response, shipSpaces, borderSpaces);
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

	public void render(Graphics2D g, GameScene.Stage stage) {
		shootingBoard.render(g, stage);
		homeBoard.render(g, stage);
	}
}
