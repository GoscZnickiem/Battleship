import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class Mouse extends MouseAdapter {

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressedPrev = pressed;
		pressed = e.getButton() == 1;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() != 1) {
			return;
		}
		pressed = false;
		pressedPrev = false;
		dragged = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		if(pressed) {
			dragged = true;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		scrollValue += e.getWheelRotation();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isPressed() {
		return pressed;
	}

	public boolean isClicked() {
		return pressed && !pressedPrev;
	}

	public boolean isDragged() {
		return dragged;
	}

	public int getMouseScroll() {
		int temp = scrollValue;
		scrollValue = 0;
		return temp;
	}

	public Position clickedPosition() {
		if(!isClicked()) return null;
		return new Position(x, y);
	}

	private int x;
	private int y;
	private boolean pressed = false;
	private boolean pressedPrev = false;
	private boolean dragged = false;
	private int scrollValue = 0;
}
