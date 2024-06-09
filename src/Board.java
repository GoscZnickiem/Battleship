import java.awt.Graphics2D;

public class Board {

    // we assume that a board is a square and every space is a square
    public static final int SIZE = 10;
    public static final int SPACE_SIZE = 40; // size of single square on a board
    public static final int BOARD_SIZE = Board.SIZE * Board.SPACE_SIZE; // size of a whole board
    public Position position;

    protected Space spaces[][];

    public Board(Game g, int x, int y)
    {
        this.position = new Position(x, y);
        this.spaces = new Space[10][10];
        for (int i = 0; i < SIZE; i++)
        {
            for (int j = 0; j < SIZE; j++)
            {
                spaces[i][j] = new Space(g, new Position(position.x + i * SPACE_SIZE, position.y + j * SPACE_SIZE), SPACE_SIZE);
            }
        }
    }

    public boolean correctPos(Position a) { return false; }
 
    public Position convertToBoardPosition(Position mousePosition)
    {
        Position ans = new Position((mousePosition.x - position.x) / SPACE_SIZE, (mousePosition.x - position.y) / SPACE_SIZE);
        if (ans.x >= 0 && ans.x < SIZE && ans.y >= 0 && ans.y < SIZE)
            return ans;
        return null;
    }



    public void updateHits(Position pos, ShootingResponse response, Ship ship)
    {
        switch (response) {
            case MISSED:
                this.spaces[pos.x][pos.y].setHit(Space.HitValue.MISS);
                break;
            case WOUNDED:
                this.spaces[pos.x][pos.y].setHit(Space.HitValue.HIT);
                break;
            case KILLED:
                this.markKilledShip(ship);
                break;
        }
    }
    
    public void markKilledShip(Ship ship)
    {
        // has to do it this way so that if a ship is an object sent from a different computer it still works
        for (Space sp : ship.spaces()) 
        {
            Position pos = sp.position();
            this.spaces[pos.x][pos.y].setHit(Space.HitValue.HIT);
        }
        for (Space sp : ship.border())
        {
            Position pos = sp.position();
            this.spaces[pos.x][pos.y].setHit(Space.HitValue.MISS);
        }
    }

	public void render(Graphics2D g) {
		for (Space[] spaces2 : spaces) {
			for (Space space : spaces2) {
				space.render(g);
			}
		}
	}
}
