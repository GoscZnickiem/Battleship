import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class Mouse extends MouseAdapter {

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();

		System.out.println("pos: " + x + " " + y);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println(e.getButton());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println(e.getButton());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(e.getButton());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();

		System.out.println(e.getButton() + " pos: " + x + " " + y);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		x = e.getX();
		y = e.getY();

		System.out.println("pos: " + x + " " + y);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		x = e.getX();
		y = e.getY();

		System.out.println("pos: " + x + " " + y);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		System.out.println("scroll: " + e.getWheelRotation());
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	private int x;
	private int y;
}

