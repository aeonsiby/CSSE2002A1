package game;

import game.core.*;
import game.utility.Logger;
import game.core.SpaceObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Represents the game information and state. Stores and manipulates the game state.
 * Handles game objects, collisions, spawning, and level progression.
 */
public class GameModel {
    public static final int GAME_HEIGHT = 20;
    public static final int GAME_WIDTH = 10;
    public static final int START_SPAWN_RATE = 2; // spawn rate (percentage chance per tick)
    public static final int SPAWN_RATE_INCREASE = 5; // Increase spawn rate by 5% per level
    public static final int START_LEVEL = 1; // Starting level value
    public static final int SCORE_THRESHOLD = 100; // Score threshold for leveling
    public static final int ASTEROID_DAMAGE = 10; // The amount of damage an asteroid deals
    public static final int ENEMY_DAMAGE = 20; // The amount of damage an enemy deals
    public static final double ENEMY_SPAWN_RATE = 0.5; // Percentage of asteroid spawn chance
    public static final double POWER_UP_SPAWN_RATE = 0.25; // Percentage of asteroid spawn chance

    private final Random random = new Random(); // ONLY USED IN this.spawnObjects()
    private final List<SpaceObject> allSpaceObjects;
    private final Ship playerShip;
    private final Logger logger;

    private int currentLevel;
    private double currentSpawnRate;

    /**
     * Models a game, storing and modifying data relevant to the game.
     * Logger argument should be a method reference to a .log method such as the UI.log method.
     * Example: Model gameModel = new GameModel(ui::log)
     * - Instantiates an empty list for storing all SpaceObjects the model needs to track.
     * - Instantiates the game level with the starting level value.
     * - Instantiates the game spawn rate with the starting spawn rate.
     * - Instantiates a new ship.
     * - Stores reference to the given logger.
     *
     * @param logger a functional interface for passing information between classes.
     */
    public GameModel(Logger logger) {
        this.logger = logger;
        this.playerShip = new Ship();
        this.allSpaceObjects = new ArrayList<>();
        this.currentLevel = START_LEVEL;
        this.currentSpawnRate = START_SPAWN_RATE;
    }

    /**
     * Sets the seed of the Random instance created in the constructor using .setSeed().
     *
     * This method should NEVER be called.
     *
     * @param seed to be set for the Random instance
     * @provided
     */
    public void setRandomSeed(int seed) {
        this.random.setSeed(seed);
    }

    /**
     * Adds a space object to the screen.
     *
     * @param object the space object to add (cannot be null)
     * @throws NullPointerException if the object is null
     */
    public void addObject(SpaceObject object) {
        Objects.requireNonNull(object, "SpaceObject cannot be null");
        allSpaceObjects.add(object);
    }

    /**
     * Gets a copy of all space objects currently in the game.
     *
     * @return an immutable list of space objects
     */
    public List<SpaceObject> getSpaceObjects() {
        return List.copyOf(allSpaceObjects);
    }

    /**
     * Updates all game objects for the current tick.
     * Removes objects that are off-screen.
     *
     * @param tick the current game tick
     */
    public void updateGame(int tick) {
        List<SpaceObject> offScreenObject = new ArrayList<>();

        for (SpaceObject spaceObject : allSpaceObjects) {
            spaceObject.tick(tick);

            if (spaceObject.getY() > GAME_HEIGHT) {
                offScreenObject.add(spaceObject);
            }
        }

        allSpaceObjects.removeAll(offScreenObject);
    }

    /**
     * Handles collision effects between two space objects.
     *
     * @param collided the object being collided with
     * @param collider the object doing the colliding
     */
    private void collision(SpaceObject collided, SpaceObject collider) {
        if (collided instanceof Ship playerShip) {

            if (collider instanceof HealthPowerUp) {
                ((HealthPowerUp) collider).applyEffect(playerShip);
            } else if (collider instanceof ShieldPowerUp) {
                ((ShieldPowerUp) collider).applyEffect(playerShip);
            } else if (collider instanceof Asteroid) {
                playerShip.takeDamage(ASTEROID_DAMAGE);
            } else if (collider instanceof Enemy) {
                playerShip.takeDamage(ENEMY_DAMAGE);
            }
        }

        if (collided instanceof Bullet) {
            if (collider instanceof Enemy) {
                allSpaceObjects.remove(collided);
                allSpaceObjects.remove(collider);
            }
        }
    }

    /**
     * Checks for and handles collisions between all game objects.
     */
    public void checkCollisions() {
        for (int i = 0; i < allSpaceObjects.size(); i++) {
            SpaceObject collidedObject = allSpaceObjects.get(i);

            for (int j = i + 1; j < allSpaceObjects.size(); j++) {
                SpaceObject collidingObject = allSpaceObjects.get(j);

                if (collidedObject.getX() == collidingObject.getX()
                        && collidedObject.getY() == collidingObject.getY()) {

                    if (collidedObject instanceof Ship || collidedObject instanceof Bullet) {
                        collision(collidedObject, collidingObject);
                    } else if (collidingObject instanceof Ship
                            || collidingObject instanceof Bullet) {
                        collision(collidingObject, collidedObject);
                    }
                }
            }
        }
    }

    /**
     * Fires a bullet from the player's ship.
     */
    public void fireBullet() {
        addObject(new Bullet(playerShip.getX(), playerShip.getY()));
        logger.log("Core.Bullet fired!");
    }

    /**
     * Checks if the player has reached the score threshold to level up.
     * Increases level and spawn rate if threshold is met.
     */
    public void levelUp() {
        int requiredScore = currentLevel * SCORE_THRESHOLD;

        if (playerShip.getScore() >= requiredScore) {
            currentLevel++;
            currentSpawnRate += SPAWN_RATE_INCREASE;

            logger.log(String.format(
                    "Level Up! Welcome to Level %d. Spawn rate increased to %1f%%.",
                    currentLevel,
                    currentSpawnRate
            ));
        }
    }

    /**
     * Gets the current game level.
     *
     * @return the current level
     */
    public int getLevel() {
        return currentLevel;
    }

    /**
     * Spawns new game objects (asteroids, enemies, power-ups) based on current spawn rates.
     */
    public void spawnObjects() {
        // 1. Check asteroid spawn
        boolean spawnAsteroid = random.nextInt(100) < currentSpawnRate;
        if (spawnAsteroid) {
            int asteroidX = random.nextInt(GAME_WIDTH);
            if (!isShipAtPosition(asteroidX, 0)) {
                addObject(new Asteroid(asteroidX, 0));
            }
        }

        // 2. Check enemy spawn
        boolean spawnEnemy = random.nextInt(100) < currentSpawnRate * ENEMY_SPAWN_RATE;
        if (spawnEnemy) {
            //randomizes spawn location
            int enemyX = random.nextInt(GAME_WIDTH);
            // checks that enemy doesn't appear in player
            if (!isShipAtPosition(enemyX, 0)) {
                addObject(new Enemy(enemyX, 0));
            }
        }

        // 3. Check power-up spawn
        boolean spawnPowerUp = random.nextInt(100) < currentSpawnRate * POWER_UP_SPAWN_RATE;
        if (spawnPowerUp) {
            //randomizes spawn location
            int powerUpX = random.nextInt(GAME_WIDTH);
            //prevents power ups from spawning in ship
            if (!isShipAtPosition(powerUpX, 0)) {
                if (random.nextBoolean()) {
                    addObject(new ShieldPowerUp(powerUpX, 0));
                } else {
                    addObject(new HealthPowerUp(powerUpX, 0));
                }
            }
        }
    }

    /**
     * Checks if the player ship is at the specified position.
     *
     * @param x the x coordinate to check
     * @param y the y coordinate to check
     * @return true if the ship is at the position, false otherwise
     */
    private boolean isShipAtPosition(int x, int y) {
        return playerShip.getX() == x && playerShip.getY() == y;
    }

    /**
     * Gets the player's ship.
     *
     * @return the player ship
     */
    public Ship getShip() {
        return this.playerShip;
    }
}