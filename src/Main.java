import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Wpisz 1 aby wybrać serwer lub 0 aby wybrać klienta\n");
        boolean s = scanner.nextInt() == 1 ? true : false;

		Game game = new Game(s);

		scanner.close();
	}
}
