import java.awt.Graphics2D;

public class Space {
    public static enum HitValue {
        HIT,
        MISS,
        NONE
    };

    // public static final String hidden_water = "water";
    // public static final String empty_water = "empty_water";
    // public static final String exploded_water = "exploded_water";
    // public static final String ship_space = "ship_space";
    public static final String highlight = "highlight";
    public static final String empty = "border";

    public boolean hasShip;
    private HitValue value;
    private Ship ship;
    private Position pos;
    private Position boardPos;
	private Button button;
	private AnimatedSprite texture;

    public void removeShip()
    {
        this.hasShip = false;
        this.ship = null;
        this.button.setTex(empty, highlight);
    }

    public Position boardPosition() {
        return boardPos;
    }

    public boolean isClicked() {
        return button.isClicked();
    }

    public Ship getShip() {
        return ship;
    }

    public void addShip(Ship ship) {
        this.hasShip = true;
        this.ship = ship;
        this.button.setTex(highlight);
    }

    public Space(Game g, Position boardPos, Position pos, int size) {
        this.boardPos = boardPos;
        this.pos = pos;
        hasShip = false;
        value = HitValue.NONE;
		button = new Button(g, pos.x, pos.y, size, size, empty, highlight);
		texture = new AnimatedSprite(g, pos.x, pos.y, size, size, "water", 60, 10);
    }

    public Position position() {
        return new Position(pos.x, pos.y);
    }

    public void setHit(HitValue value) {
        switch (value) {
            case NONE:
                if (this.hasShip) {
                    // this.button.setTex(ship_space, ship_space + 'A');
                } else {
                    // this.button.setTex(hidden_water, hidden_water + 'A');
                }
                break;

            case MISS:
                // this.button.setTex(empty_water, empty_water + 'A');
				texture.setForeground(highlight);
                break;

            case HIT:
                // this.button.setTex(exploded_water, exploded_water + 'A');
				texture.setForeground("explode");
				if(ship != null) this.ship.shot(this);
                break;
        }
        this.value = value;
    }

    public HitValue getHitVal() {
        return this.value;
    }

    public boolean hasShip() {
        return hasShip;
    }

	public void render(Graphics2D g) {
		texture.render(g);
		button.render(g);
	}
}
