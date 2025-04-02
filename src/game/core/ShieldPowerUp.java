package game.core;

import game.ui.ObjectGraphic;

/**
 * Represents a shield power-up that increases the player's score when collected.
 */
public class ShieldPowerUp extends PowerUp {
    private static final int SHIELD_EFFECT = 50;

    /**
     * Creates a shield power-up at specified position.
     *
     * @param x the horizontal position(postive to the right)
     * @param y the vertical position(positive downwards)
     */
    public ShieldPowerUp(int x, int y) {
        super(x, y);
    }

    @Override
    public ObjectGraphic render() {
        return new ObjectGraphic("💠", "assets/shield.png");
    }

    /*This effect triggeres every tick that space ship is colliding which
    seems like a bug, couldn't find relevant methods though.
     */
    @Override
    public void applyEffect(Ship ship) {
        ship.addScore(SHIELD_EFFECT);
        System.out.println("Shield activated! Score increased by 50.");
    }
}