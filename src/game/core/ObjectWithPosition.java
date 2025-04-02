package game.core;

/**
 * Abstract parent class for objects that have a position on the screen.
 */
public abstract class ObjectWithPosition implements SpaceObject {
    protected int x;
    protected int y;

    /**
     * Creates an object at the specific coordinates.
     *
     * @param x the horizontal position(postive to the right)
     * @param y the vertical position(positive downwards)
     */
    public ObjectWithPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }
}
