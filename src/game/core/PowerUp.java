package game.core;

/**
 * Abstract parent class for power-up objects in the game.
 */
public abstract class PowerUp extends ObjectWithPosition implements PowerUpEffect {

    /**
     * Creates a power-up at specified position.
     *
     * @param x the horizontal position(postive to the right)
     * @param y the vertical position(positive downwards)
     */
    public PowerUp(int x, int y) {
        super(x, y);
    }

    @Override
    public void tick(int tick) {
        // Power-ups don't move automatically, which seems like a bug but seems to be intended
    }
}
