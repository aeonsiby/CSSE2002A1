package game.core;

/**
 * Interface for objects that can apply effects to the player's ship.
 */
public interface PowerUpEffect {
    /**
     * Applies the power-up's effect to the ship.
     *
     * @param playership the ship getting powered up
     */
    void applyEffect(Ship playership);
}