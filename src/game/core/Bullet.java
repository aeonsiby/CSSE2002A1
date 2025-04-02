package game.core;

import game.ui.ObjectGraphic;

/**
 * Represents a bullet fired from player's ship.
 */
public class Bullet extends ObjectWithPosition {

    /**
     * Creates a bullet at the specified position.
     *
     * @param x the horizontal position(postive to the right)
     * @param y the vertical position(positive downwards)
     */
    public Bullet(int x, int y) {
        super(x, y);
    }

    @Override
    public void tick(int tick) {
        this.y--;
    }

    @Override
    public ObjectGraphic render() {
        return new ObjectGraphic("🔺", "assets/bullet.png");
    }
}