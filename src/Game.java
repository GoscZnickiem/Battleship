import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

public class Game {
	public Game(boolean server) {
		m_isServer = server;

		if(m_isServer) {
			runServer();
		} else {
			runClient();
		}
	}

	private void runServer() {
		System.out.println("Server IP: " + getPublicIP());
		try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Serwer uruchomiony, oczekiwanie na klienta...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Połączono z klientem!");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Wybierz liczbę od 1 do 10:");
            int number = Integer.parseInt(userInput.readLine());

            while (true) {
                String guess = in.readLine();
                int guessedNumber = Integer.parseInt(guess);

                if (guessedNumber == number) {
                    out.println("Gratulacje! Zgadłeś liczbę.");
                    break;
                } else if (guessedNumber < number) {
                    out.println("Za mało.");
                } else {
                    out.println("Za dużo.");
                }
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	private static String getPublicIP() {
		try {
			URL url = new URL("https://api.ipify.org");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String ip = in.readLine();
			in.close();

			return ip;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "1";
	}


	private void runClient() {
        // Zapytanie użytkownika o podanie adresu IP
		Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj adres IP: ");
        String serverAddress = scanner.nextLine();
		scanner.close();

		try (Socket socket = new Socket(serverAddress, 12345)) {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("Zgadnij liczbę od 1 do 10:");

			while (true) {
				System.out.print("Twoje przypuszczenie: ");
				String guess = userInput.readLine();
				out.println(guess);

				String response = in.readLine();
				System.out.println(response);

				if (response.equals("Gratulacje! Zgadłeś liczbę.")) {
					break;
				}
			}

			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean m_isServer;
}
