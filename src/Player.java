import java.util.Scanner;

public class Player 
{
    private String name;
    private Board homeBoard;
    private Board shootingBoard;
    private Player opponent;

    public Player(String name)
    {
        this.name = new String(name);
        this.homeBoard = new Board(0);
        this.shootingBoard = new Board(1);
    }

    public void setOpponent(Player p)
    {
        this.opponent = p;
    }

    public Board getHomeBoard()
    {
        return this.homeBoard;
    }

    public void setNewBoard()
    {
        int counter = 0;
		Scanner scanner = new Scanner(System.in);
        while (true)
        {
            System.out.print("\nWpisz pozycję którą chcesz wybrać w formacie <x: 0-9> <y: 0-9>");
			Position pos = new Position(scanner.nextInt(), scanner.nextInt());
            counter += this.homeBoard.flipSpace(pos);
            if (counter == 19) break;
            // alternatively we can implement submit button and then value of counter can be used as a test if 20 ship units has been selected
            // TODO
            // further improvements will have to be made to ensure that ships are of proper shape and length and that they do not border each other 
        }
        scanner.close();
    }

    public String getName()
    {
        return this.name;
    }

    // returns -1 if shot is invalid, 0 if shot space is empty, 1 if there has been some part of a ship which is yet to be fully eliminated, 2 if the shot had eliminated the whole ship (so that render knows to put dots all around the ship (in the case of 2))
    public int shootShips()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nWpisz pozycję w którą chcesz strzelić");
        Position pos = new Position(scanner.nextInt(), scanner.nextInt());
        scanner.close();
        if (this.shootingBoard.setSpace(pos) == false)
        {
            return -1;
        }
        return opponent.getHomeBoard().getSpace(pos);
    }

    public void shootingResponse()
    {

    }
}