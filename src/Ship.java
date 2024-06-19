import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.*;

public class Ship
{
	private Game game;
    public Position position = new Position(0, 0);  
    public int length;
    public Orientation orientation;
    private Button shipButton;
    private ArrayList<Space> spaces;
    private ArrayList<AnimatedSprite> segments;
    private ArrayList<Space> border;
    public boolean onBoard;

    public void make_invisible() {
        this.shipButton.visible = false;
    }

    public Ship(Position pos, Orientation ornt, int length, Game g) {
		game = g;
        this.shipButton = new Button(game, pos.x, pos.y, length * Board.SPACE_SIZE, Board.SPACE_SIZE, null, null);
        this.length = length;
        this.position = pos;
        this.orientation = ornt;
        this.spaces = new ArrayList<>();
        this.segments = new ArrayList<>();
        this.border = new ArrayList<>();
        this.onBoard = false;

		for(int i = 0; i < length; i++) {
			String tex; int x; int y;
			if(ornt == Orientation.HORIZONTAL) {
				x = pos.x + i * Board.SPACE_SIZE - (length - 1) * Board.SPACE_SIZE / 2;
				y = pos.y;
				tex = "ship_" + length + "H_" + i;
			} else {
				x = pos.x;
				y = pos.y + i * Board.SPACE_SIZE - (length - 1) * Board.SPACE_SIZE / 2;
				tex = "ship_" + length + "V_" + i;
			}
			segments.add(new AnimatedSprite(game, x, y, Board.SPACE_SIZE, Board.SPACE_SIZE, tex, 1, 1));
		}
    }

    // default ship to allow using player.getPosOnBoard without offset because offset is (ship.length - 1) / 2 * Board.SPACE_SIZE so in case of ship.length = 1 it is zero
    public Ship() {
        this.orientation = Orientation.HORIZONTAL;
        this.length = 1;
        this.spaces = new ArrayList<>();
        this.segments = new ArrayList<>();
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

	public void shot(Space sp) {
		for(int i = 0; i < length; i++) {
			if(sp == spaces.get(i)) {
				String tex = "shipD_" + length + (orientation == Orientation.VERTICAL ? "V_" : "H_") + i;
				segments.get(i).setAnimation(tex, 1, 1);
			}
		}
	}

    public static ArrayList<Ship> initialize(ArrayList<Ship> arr, Game g)
    {
        int single_length = Board.SPACE_SIZE;
        int lengths[] = {1,1,1,1,2,2,2,3,3,4};
        int sum = (lengths.length - 1) * single_length / 2;
        for (int l : lengths) sum += l * single_length;
        int pos_x = (Game.WIDTH - sum) / 2;
        int pos_y = 660;
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
                return sp;
            }
        }
        return null;
    }

    public void rotate()
    {
        orientation = ((orientation == Orientation.VERTICAL) ? Orientation.HORIZONTAL : Orientation.VERTICAL);
        shipButton.setPos(position);
        shipButton.setSize(shipButton.getHeight(), shipButton.getWidth());

		for(int i = 0; i < length; i++) {
			String tex; int x; int y;
			if(orientation == Orientation.HORIZONTAL) {
				x = position.x + i * Board.SPACE_SIZE - (length - 1) * Board.SPACE_SIZE / 2;
				y = position.y;
				tex = "ship_" + length + "H_" + i;
			} else {
				x = position.x;
				y = position.y + i * Board.SPACE_SIZE - (length - 1) * Board.SPACE_SIZE / 2;
				tex = "ship_" + length + "V_" + i;
			}
			segments.get(i).setPos(x, y);
			segments.get(i).setAnimation(tex, 1, 1);
		}
    }

	public void move(Position pos) {
		int dx = pos.x - position.x;
		int dy = pos.y - position.y;
		position = pos;
		shipButton.setPos(pos);
		for(int i = 0; i < length; i++) {
			AnimatedSprite segment = segments.get(i);;
			segment.setPos(segment.getX() + dx, segment.getY() + dy);
		}
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
		// shipButton.render(g);
		for(int i = 0; i < length; i++) {
			segments.get(i).render(g);
		}
	}

    public void removeShip()
    {
        if (!this.onBoard) return;
        for (Space sp : spaces)
        {
            sp.removeShip();
        }
        this.spaces.clear();
        this.border.clear();
        this.onBoard = false;
    }
}
