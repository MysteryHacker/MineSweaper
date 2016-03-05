package blattneun.hausafugabe;

/**
 * Model
 *
 * Immutable
 *
 * Created by jost on 24.11.15.
 */
public class Position {

    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Position up() {
       return new Position(x,y-1);
    }

    public Position down() {
        return new Position(x,y+1);
    }
    public Position left() {
        return new Position(x-1,y);
    }
    public Position right() {
        return new Position(x+1,y);
    }

}
