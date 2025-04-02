package game.core;

import game.ui.ObjectGraphic;

/**
 * Represents a health power-up that heals the player when picked up.
 */
public class HealthPowerUp extends PowerUp {
    private static final int HEALING_EFFECT = 20;

    /**
     * Creates a health power-up at specified position.
     *
     * @param x the horizontal position(postive to the right)
     * @param y the vertical position(positive downwards)
     */
    public HealthPowerUp(int x, int y) {
        super(x, y);
    }

    @Override
    public ObjectGraphic render() {
        return new ObjectGraphic("❤️", "assets/health.png");
    }

    /*
    During testing of game found that each tick causes 20 heal and item doesn't despawn. checked
    javadoc, couldn't see any relevant methods not sure if this is an intended bug
     */
    @Override
    public void applyEffect(Ship ship) {
        ship.heal(HEALING_EFFECT);
        System.out.println("Health restored by 20!");
    }
}