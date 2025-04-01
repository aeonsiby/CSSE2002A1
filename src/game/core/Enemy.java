package game.core;

import game.ui.ObjectGraphic;

/**
 * Represents an enemy spacecraft in the game that moves down the screen.
 */
public class Enemy extends DescendingEnemy {

    /**
     * Creates an enemy at the specified position.
     *
     * @param x the horizontal position(postive to the right)
     * @param y the vertical position(positive downwards)
     */
    public Enemy(int x, int y) {
        super(x, y);
    }

    @Override
    public ObjectGraphic render() {
        return new ObjectGraphic("👾", "assets/enemy.png");
    }
}