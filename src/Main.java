import javax.swing.*;


public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame();
				Game game = new Game(frame);
				frame.setTitle("BattleShip");
				frame.getContentPane().add(game);
				frame.setSize(Game.WIDTH, Game.HEIGHT);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(false);
				frame.setVisible(true);
				game.requestFocus();
			}
		});
	}
}
