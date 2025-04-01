package game.core;
import game.ui.ObjectGraphic;

/**
 * Represents the player's ship in the game.
 */
public class Ship extends Controllable {
    private int health;
    private int score;

    /**
     * Creates a ship with specified position and health.
     *
     * @param x the horizontal position(postive to the right)
     * @param y the vertical position(positive downwards)
     * @param health  hoe much damage ship can sustain
     */
    public Ship(int x, int y, int health) {
        super(x, y);
        this.health = health;
        this.score = 0;
    }

    /**
     * Creates a default ship at position (5,10) with 100 health.
     */
    public Ship() {
        this(5, 10, 100);
    }

    @Override
    public ObjectGraphic render() {
        return new ObjectGraphic("🚀", "assets/ship.png");
    }

    /**
     * Reduces the ship's health by specified damage.
     * note, no specifications on what occurs at 0 hp,
     * @param damage the amount of damage to take
     */
    public void takeDamage(int damage) {

        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
        }

    }

    /**
     * Increases the ship's health by specified amount.
     *
     * @param amount the amount to heal
     */
    public void heal(int amount) {
        this.health += amount;
        if (this.health >= 100) {
            this.health = 100;
        }
    }

    /**
     * Adds points to the ship's score.
     *
     * @param points the points to add
     */
    public void addScore(int points) {
        this.score += points;
    }

    /**
     * Gets the ship's current health.
     *
     * @return the current health
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Gets the ship's current score.
     *
     * @return the current score
     */
    public int getScore() {
        return this.score;
    }

    @Override
    public void tick(int tick) {
        // Ship doesn't move automatically
    }
}