import java.awt.Graphics2D;
import java.util.ArrayList;
import java.io.Serializable;


public class Ship implements Serializable
{
    public Position position = new Position(0, 0);  
    public int length;
    public Orientation orientation;
    private Button shipButton;
    private ArrayList<Space> spaces;
    private ArrayList<Space> border;

    public void make_invisible()
    {
        this.shipButton.visible = false;
    }

    public Ship(Position pos, Orientation ornt, int length, Game g)
    {
        this.shipButton = new Button(g, pos.x, pos.y, length * Board.SPACE_SIZE, Board.SPACE_SIZE, null, null);
        this.length = length;
        this.position = pos;
        this.orientation = ornt;
        this.spaces = new ArrayList<>();
        this.border = new ArrayList<>();
    }

    // default ship to allow using player.getPosOnBoard without offset because offset is (ship.length - 1) / 2 * Board.SPACE_SIZE so in case of ship.length = 1 it is zero
    public Ship()
    {
        this.orientation = Orientation.HORIZONTAL;
        this.length = 1;
        this.spaces = new ArrayList<>();
        this.border = new ArrayList<>();
    }

    public ArrayList<Position> spaces()
    {
        ArrayList<Position> ans = new ArrayList<>();
        for (Space sp : spaces)
        {
            ans.add(sp.boardPosition());
        }
        return ans;
    }

    public ArrayList<Position> border()
    {
        ArrayList<Position> ans = new ArrayList<>();
        for (Space sp : border)
        {
            ans.add(sp.boardPosition());
        }
        return ans;
    }

    public void addBorderSpace(Space space)
    {
        for (Space sp : spaces)
        {
            if (sp == space)
            {
                return;
            }
        }
        this.border.add(space);
    }

    public void addShipSpace(Space space)
    {
        while (border.remove(space));
        spaces.add(space);
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
        int single_length = Board.SPACE_SIZE;
        int pos_x = 50 - single_length;
        int pos_y = 580;
        int lengths[] = {1,1,1,1,2,2,2,3,3,4};
        for (int len : lengths)
        {
            pos_x += len * single_length / 2;
            arr.add(new Ship(new Position(pos_x, pos_y), Orientation.HORIZONTAL, len, g));
            pos_x += len * single_length / 2 + single_length / 2;
        }
        return arr;
    }

    public static Ship getSelectedShip(ArrayList<Ship> ships)
    {
        for (Ship sp : ships)
        {
            if (sp.shipButton.isClicked())
            {
                if (sp.shipButton.visible)
                {
                    sp.shipButton.visible = true;
                    return sp;
                }
            }
        }
        return null;
    }

    public void rotate()
    {
        orientation = ((orientation == Orientation.VERTICAL) ? Orientation.HORIZONTAL : Orientation.VERTICAL);
        shipButton.setPos(position);
        shipButton.setSize(shipButton.getHeight(), shipButton.getWidth());
    }

	public void move(Position pos) {
		position = pos;
		shipButton.setPos(pos);
	}

    public void snap_to_space(Position pos)
    {
        if (orientation == Orientation.HORIZONTAL)
        {
            pos.x = pos.x + (length - 1) * Board.SPACE_SIZE / 2;
        }
        else
        {
            pos.y = pos.y + (length - 1) * Board.SPACE_SIZE / 2;
        }
        move(pos);
    }

	public void render(Graphics2D g) {
		shipButton.render(g);
	}

}
