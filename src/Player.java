import java.util.Scanner;

public class Player 
{
    private String name;
    private HomeBoard homeBoard;
    private ShootingBoard shootingBoard;
    private Game game;

    public Player(String name, Game game)
    {
        this.name = new String(name);
        this.homeBoard = new HomeBoard();
        this.shootingBoard = new ShootingBoard();
        this.game = game;
    }

    public String getName()
    {
        return this.name;
    }

    public Position getShootingPos()
    {
        System.out.print("\nKliknij w pole w które chcesz strzelić");
        Position mouse_pos = game.getMouse().clickedMousePosition();
        Position pos = null;
        if (mouse_pos == null)
        {
            pos = shootingBoard.convertToBoardPosition(mouse_pos); 
        }
        if (pos != null && shootingBoard.shootable(pos)) return pos;
        return null;
    }

    // if board == true then on homeBoard
    // if board == false then on ShootingBoard
    public void updateHitsOnPlayerBoard(boolean homeBd, Position pos, ShootingResponse response)
    {
        Board board = null;
        if (homeBd) 
            board = this.homeBoard;
        else 
            board = this.shootingBoard;
        board.updateHits(pos, response);
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
}