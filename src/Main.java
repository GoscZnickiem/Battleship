import javax.swing.*;


public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame();
				Game game = new Game(frame);
				frame.setTitle("2D Physics - Box vs Box / Impulse resolution test (with rotation)");
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
