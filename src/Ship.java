import java.util.ArrayList;


public class Ship
{
    public Position position = new Position(0, 0);  
    private int length;
    private Orientation orientation;
    private ArrayList<Space> spaces;
    private ArrayList<Space> border;

    public Ship(Position pos, Orientation ornt, int length)
    {
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

}