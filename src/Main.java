import javax.swing.*;
import java.awt.*;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame();
				Game game = new Game(frame);
				frame.setTitle("BattleShip");
				frame.getContentPane().add(game);
				game.setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setResizable(false);
				game.requestFocus();
			}
		});
	}
}
