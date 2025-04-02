package game.core;

/**
 * Abstract parent class for enemies that move down the screen.
 */
public abstract class DescendingEnemy extends ObjectWithPosition {

    /**
     * Creates a descending enemy at the specified position.
     *
     * @param x the horizontal position(postive to the right)
     * @param y the vertical position(positive downwards)
     */
    public DescendingEnemy(int x, int y) {
        super(x, y);
    }

    @Override
    public void tick(int tick) {
        if (tick % 10 == 0) {
            this.y++;
        }
    }
}



