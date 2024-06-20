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
		pressed = e.getButton() == 1;
		pressed2 = e.getButton() == 3;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == 1) {
			pressed = false;
			dragged = false;
		} else if(e.getButton() == 3) {
			pressed2 = false;
			dragged2 = false;
		}
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
		if(pressed2) {
			dragged2 = true;
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

	public Position getPos() {
		return new Position(x, y);
	}

	public boolean isPressed() {
		return pressed;
	}

	public boolean isClicked() {
		return pressed && !pressedPrev;
	}

	public boolean isRightDragged() {
		return dragged2;
	}

	public boolean isRightPressed() {
		return pressed2;
	}

	public boolean isRightClicked() {
		return pressed2 && !pressedPrev2;
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

	public void update() {
		pressedPrev = pressed;
		pressedPrev2 = pressed2;
	}

	private int x;
	private int y;
	private boolean pressed = false;
	private boolean pressedPrev = false;
	private boolean dragged = false;
	private boolean pressed2 = false;
	private boolean pressedPrev2 = false;
	private boolean dragged2 = false;
	private int scrollValue = 0;
}
