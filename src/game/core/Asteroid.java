package game.core;

import game.ui.ObjectGraphic;

/**
 * Represents an asteroid enemy in the game.
 */
public class Asteroid extends DescendingEnemy {

    /**
     * Creates an asteroid at the specified position.
     *
     * @param x the horizontal position(postive to the right)
     * @param y the vertical position(positive downwards)
     */
    public Asteroid(int x, int y) {
        super(x, y);
    }

    @Override
    public ObjectGraphic render() {
        return new ObjectGraphic("🌑", "assets/asteroid.png");
    }
}