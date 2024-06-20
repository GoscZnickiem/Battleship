import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.*;


public class Board {

    // we assume that a board is a square and every space is a square
    public static final int SIZE = 10;
    public static final int SPACE_SIZE = 48; // size of single square on a board
    public static final int BOARD_SIZE = Board.SIZE * Board.SPACE_SIZE; // size of a whole board
    public Position position;
    private int shipsNumber;

    public String name;

    protected Space spaces[][];

    public Board(Game g, int x, int y, String n)
    {
        this.name = n;
        this.shipsNumber = 20;
        this.position = new Position(x, y);
        this.spaces = new Space[10][10];
        for (int i = 0; i < SIZE; i++)
        {
            for (int j = 0; j < SIZE; j++)
            {
                spaces[i][j] = new Space(g, new Position(i, j), new Position(position.x + i * SPACE_SIZE, position.y + j * SPACE_SIZE), SPACE_SIZE);
            }
        }

    }



    public boolean correctPos(Position a) { return false; }
 
    public Position convertToBoardPosition(Position pos)
    {
        Position ans = new Position((pos.x - position.x + SPACE_SIZE / 2) / SPACE_SIZE, (pos.y - position.y + SPACE_SIZE / 2) / SPACE_SIZE);
        if (ans.x >= 0 && ans.x < SIZE && ans.y >= 0 && ans.y < SIZE)
            return ans;
        return null;
    }

    public void updateHits(Position pos, ShootingResponse response, ArrayList<Position> shipSpaces, ArrayList<Position> borderSpaces)
    {
        switch (response) {
            case MISSED:
                this.spaces[pos.x][pos.y].setHit(Space.HitValue.MISS);
                break;
            case WOUNDED:
                shipsNumber -= 1;
                this.spaces[pos.x][pos.y].setHit(Space.HitValue.HIT);
                break;
            case KILLED:
                shipsNumber -= 1;
                this.markKilledShip(shipSpaces, borderSpaces);
                break;
        }
    }
    
    public void markKilledShip(ArrayList<Position> shipSpaces, ArrayList<Position> borderSpaces)
    {
        System.out.println("----");
        for (Position pos : shipSpaces) 
        {
            System.out.println(pos.x);
            System.out.println(pos.y);
            this.spaces[pos.x][pos.y].setHit(Space.HitValue.HIT);
        }
        System.out.println("----");
        for (Position pos : borderSpaces)
        {
            System.out.println(pos.x);
            System.out.println(pos.y);
            this.spaces[pos.x][pos.y].setHit(Space.HitValue.MISS);
        }
        System.out.println("----");
    }

	public void render(Graphics2D g, GameScene.Stage stage) {
        
        for (Space[] spaces2 : spaces) {
            for (Space space : spaces2) {
                space.render(g);
			}
		}
    
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g.getFontMetrics();
        int textHeight = fm.getHeight();

        if (stage == GameScene.Stage.SHOOTING)
        {
            String text = "Left ships pieces:  " + shipsNumber;
            int textWidth = fm.stringWidth(text);
            int X = (position.x - Board.SPACE_SIZE / 2 + Board.SIZE * Board.SPACE_SIZE / 2 - textWidth / 2);
            int Y = (position.y - Board.SPACE_SIZE / 2 + Board.SIZE * Board.SPACE_SIZE) + fm.getAscent();
            g.drawString(text, X, Y);
        }

        int textWidth = fm.stringWidth(name);
        int X = (position.x - Board.SPACE_SIZE / 2 + Board.SIZE * Board.SPACE_SIZE / 2 - textWidth / 2);
        int Y = (position.y - Board.SPACE_SIZE / 2 - 3 * textHeight / 2) + fm.getAscent();
        g.drawString(name, X, Y);

	}
}
