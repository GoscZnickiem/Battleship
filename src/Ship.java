import java.util.ArrayList;


public class Ship
{
    public Position position = new Position(0, 0);  
    public int length;
    public Orientation orientation;
    private Button shipButton;
    private ArrayList<Space> spaces;
    private ArrayList<Space> border;

    public Ship(Position pos, Orientation ornt, int length, Game g)
    {
        this.shipButton = new Button(g, pos.x, pos.y, length * Board.SPACE_SIZE, Board.SPACE_SIZE, null, null);
        this.length = length;
        this.position = pos;
        this.orientation = ornt;
    }

    public ArrayList<Space> spaces()
    {
        return spaces;
    }

    public ArrayList<Space> border()
    {
        return border;
    }

    public boolean dead()
    {
        for (Space sp : spaces)
        {
            if (sp.getHitVal() == Space.HitValue.NONE)
                return false;
        }
        return true;
    }

    public static ArrayList<Ship> initialize(ArrayList<Ship> arr, Game g)
    {
        arr.add(new Ship(new Position(100, 30), Orientation.HORIZONTAL, 1, g));
        arr.add(new Ship(new Position(170, 30), Orientation.HORIZONTAL, 1, g));
        arr.add(new Ship(new Position(240, 30), Orientation.HORIZONTAL, 1, g));
        arr.add(new Ship(new Position(310, 30), Orientation.HORIZONTAL, 1, g));
        
        arr.add(new Ship(new Position(380, 30), Orientation.HORIZONTAL, 2, g));
        arr.add(new Ship(new Position(490, 30), Orientation.HORIZONTAL, 2, g));
        arr.add(new Ship(new Position(600, 30), Orientation.HORIZONTAL, 2, g));
        arr.add(new Ship(new Position(710, 30), Orientation.HORIZONTAL, 3, g));
        arr.add(new Ship(new Position(860, 30), Orientation.HORIZONTAL, 3, g));
        
        arr.add(new Ship(new Position(1010, 30), Orientation.HORIZONTAL, 4, g));
        return arr;
    }

    public static Ship getSelectedShip(ArrayList<Ship> ships)
    {
        for (Ship sp : ships)
        {
            if (sp.shipButton.isClicked())
            {
                return sp;
            }
        }
        return null;
    }

    public void rotate()
    {
        this.orientation = ((this.orientation == Orientation.VERTICAL) ? Orientation.HORIZONTAL : Orientation.VERTICAL);
        this.shipButton.setVals(this.position.x, this.position.y, this.shipButton.getHeight(), this.shipButton.getWidth(), null);
    }

}