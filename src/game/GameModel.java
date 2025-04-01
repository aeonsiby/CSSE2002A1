package game;


import game.core.*;
import game.utility.Logger;
import game.core.SpaceObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static java.rmi.server.LogStream.log;

/**
 * Represents the game information and state. Stores and manipulates the game state.
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
    // ALL_SPACE_OBJECTS is final because it can still be added without being reassigned.
    private final List<SpaceObject> ALL_SPACE_OBJECTS;

    private final Ship playerShip;

    private final Logger logger;

    int currentLevel;

    double currentSpawnRate;



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

        this.ALL_SPACE_OBJECTS = new ArrayList<>();

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



    public void addObject(SpaceObject object){

        Objects.requireNonNull(object, "SpaceObject cannot be null");
        ALL_SPACE_OBJECTS.add(object);

    }

    public List<SpaceObject> getSpaceObjects(){

        return List.copyOf(ALL_SPACE_OBJECTS);
    }

    public void updateGame(int tick) {

        List <SpaceObject> offScreenObject = new ArrayList<>();

        for (SpaceObject spaceObject : ALL_SPACE_OBJECTS) {

            spaceObject.tick(tick);

            if (spaceObject.getY() > GAME_HEIGHT){

                offScreenObject.add(spaceObject);

            }
        }

        ALL_SPACE_OBJECTS.removeAll(offScreenObject);

    }
    private void collision(SpaceObject collided, SpaceObject collider){
        // makes more sense to implement this after creating other classes need Logs as well

        if (collided instanceof Ship playerShip){

            if (collider instanceof HealthPowerUp) {

                ((HealthPowerUp)collider).applyEffect(playerShip);

            }else if (collider instanceof ShieldPowerUp) {

                ((ShieldPowerUp)collider).applyEffect(playerShip);

            }else if (collider instanceof Asteroid){

                playerShip.takeDamage(ASTEROID_DAMAGE);

            }else if (collider instanceof Enemy){

                playerShip.takeDamage(ENEMY_DAMAGE);

            }
        }
        if (collided instanceof Bullet){

            if (collider instanceof Enemy){

                ALL_SPACE_OBJECTS.remove(collided);

                ALL_SPACE_OBJECTS.remove(collider);
            }
        }

    }
    public void checkCollisions(){

        for (int i = 0; i < ALL_SPACE_OBJECTS.size(); i++){

            SpaceObject collidedObject = ALL_SPACE_OBJECTS.get(i);

            for (int j = i+1; j < ALL_SPACE_OBJECTS.size(); j++){

                SpaceObject collidingObject = ALL_SPACE_OBJECTS.get(j);

                if (collidedObject.getX() == collidingObject.getX() &&
                        collidedObject.getY() == collidingObject.getY()){

                    if (collidedObject instanceof Ship || collidedObject instanceof Bullet){

                        collision(collidedObject, collidingObject);

                    } else if (collidingObject instanceof Ship || collidingObject instanceof
                              Bullet) {

                        collision(collidingObject, collidedObject);

                    }


                }

            }


        }

    }

    public void fireBullet(){

        addObject(new Bullet(playerShip.getX(), playerShip.getY()));

        logger.log("Core.Bullet fired!");;

    }

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

    // Add getter for current level (needed for rendering)
    public int getLevel() {
        return currentLevel;
    }
    public void spawnObjects() {
        // 1. Check asteroid spawn
        boolean spawnAsteroid = random.nextInt(100) < currentSpawnRate;
        if (spawnAsteroid) {
            // 2. Asteroid x-coordinate
            int asteroidX = random.nextInt(GAME_WIDTH);
            if (!isShipAtPosition(asteroidX, 0)) {
                addObject(new Asteroid(asteroidX, 0));
            }
        }

        // 3. Check enemy spawn
        boolean spawnEnemy = random.nextInt(100) < currentSpawnRate * ENEMY_SPAWN_RATE;
        if (spawnEnemy) {
            // 4. Enemy x-coordinate
            int enemyX = random.nextInt(GAME_WIDTH);
            if (!isShipAtPosition(enemyX, 0)) {
                addObject(new Enemy(enemyX, 0));
            }
        }

        // 5. Check power-up spawn
        boolean spawnPowerUp = random.nextInt(100) < currentSpawnRate * POWER_UP_SPAWN_RATE;
        if (spawnPowerUp) {
            // 6. Power-up x-coordinate
            int powerUpX = random.nextInt(GAME_WIDTH);
            if (!isShipAtPosition(powerUpX, 0)) {
                // 7. Determine power-up type
                if (random.nextBoolean()) {
                    addObject(new ShieldPowerUp(powerUpX, 0));
                } else {
                    addObject(new HealthPowerUp(powerUpX, 0));
                }
            }
        }
    }

    private boolean isShipAtPosition(int x, int y) {
        return playerShip.getX() == x && playerShip.getY() == y;
    }

    public Ship getShip() {
        return this.playerShip;
    }

}
