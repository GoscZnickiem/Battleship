
public class Board {
    public static final int SIZE = 10;
    private int type; // 0 if HomeBoard containing ships, 1 if shootingBoard
    private Space spaces[][];

    public Board(int type)
    {
        this.type = type;
        this.spaces = new Space[10][10];
        for (int i = 0; i < SIZE; i++)
        {
            for (int j = 0; j < SIZE; j++)
            {
                spaces[i][j] = new Space();
            }
        }
    }

    public boolean setSpace(Position a)
    {
        if (spaces[a.x][a.y].get() == true) return false;
        spaces[a.x][a.y].set();
        return true;
    }

    public int flipSpace(Position a)
    {
        spaces[a.x][a.y].flip();
        if (spaces[a.x][a.y].get() == false) return -1;
        else return 1;
    }

    // if type = 0 then returns 0 if shot space is empty, 1 if there has been some part of a ship which is yet to be fully eliminated, 2 if the shot had eliminated the whole ship
    // if type = 1 then returns 0 if space is able to be shot and 1 if it has already been shot
    public int getSpace(Position a)
    {
        if (spaces[a.x][a.y].get() == false) return 0;
        if (this.type == 1) return 1;
        for (int i = 1; i < 4; i++)
        {
            if ((a.x - i >= 0 && spaces[a.x - i][a.y].get() == true) || (a.x + i < Board.SIZE && spaces[a.x + i][a.y].get() == true) 
                || ((a.y - i) >= 0 && spaces[a.x][a.y - i].get() == true) || (a.y + i < Board.SIZE && spaces[a.x][a.y + i].get() == true))
                return 1;
        }
        return 2;
    }
}

class Space
{
    private boolean value;
    public Space() {}
    public void set() { this.value = true; }
    public void flip() { this.value = !this.value; }
    public boolean get() { return this.value; }
}