package game;

import game.core.*;
import game.GameModel;
import game.exceptions.BoundaryExceededException;
import game.ui.UI;
import game.utility.Direction;
import game.utility.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The Controller handling the game flow and interactions.
 *
 * Holds references to the UI and the Model, so it can pass information and references back and forth as necessary.
 * Manages changes to the game, which are stored in the Model, and displayed by the UI.
 */
public class GameController {
    private long startTime;
    private UI ui;
    private GameModel model;
    private boolean gameRunning = true;
    private final Logger logger;

    /**
     * Initializes the game controller with the given UI and Model.
     * Stores the ui, model and start time.
     * The start time System.currentTimeMillis() should be stored as a long.
     *
     * @param ui the UI used to draw the Game
     * @param model the model used to maintain game information
     * @provided
     */
    public GameController(UI ui, GameModel model) {
        this.ui = ui;
        this.model = model;
        this.startTime = System.currentTimeMillis(); // Start the timer
        this.logger = ui::log;
    }

    /**
     * Initializes the game controller with the given UI and a new GameModel (taking ui::log as the logger).
     * This constructor should call the other constructor using the "this()" keyword.
     *
     * @param ui the UI used to draw the Game
     * @provided
     */
    public GameController(UI ui) {
        this(ui, new GameModel(ui::log));
    }

    /**
     * Starts the main game loop.
     *
     * Passes onTick and handlePlayerInput to ui.onStep and ui.onKey respectively.
     * add ship to space objects when initialising game to ensure empty spaceobject list
     * on creation.
     * @provided
     */
    public void startGame() {
        model.addObject(model.getShip());

        ui.onStep(this::onTick);
        ui.onKey(this::handlePlayerInput);
    }

    /**
     * Uses the provided tick to call and advance the following:
     *      - A call to renderGame() to draw the current state of the game.
     *      - A call to model.updateGame(tick) to advance the game by the given tick.
     *      - A call to model.checkCollisions() to handle game interactions.
     *      - A call to model.spawnObjects() to handle object creation.
     *      - A call to model.levelUp() to check and handle leveling.
     *
     * @param tick the provided tick
     * @provided
     */
    public void onTick(int tick) {
        if (gameRunning) {
            renderGame(); // Update Visual
            model.updateGame(tick); // Update GameObjects
            model.checkCollisions(); // Check for Collisions
            model.spawnObjects(); // Handles new spawns
            model.levelUp(); // Level up when score threshold is met
        }
    }

    /**
     * Renders the current game state by updating UI stats
     * and drawing all game objects.
     */
    public void renderGame() {
        Ship playerShip = model.getShip();

        ui.setStat("Score", String.valueOf(playerShip.getScore()));
        ui.setStat("Health", String.valueOf(playerShip.getHealth()));
        ui.setStat("Level", String.valueOf(model.getLevel()));
        ui.setStat("Time Survived",
                (System.currentTimeMillis() - startTime) / 1000 + " seconds");

        List<SpaceObject> objects = model.getSpaceObjects();
        ui.render(objects);
    }

    /**
     * Processes player input and makes it into game actions.
     * Handles moving, shooting, and pausing the game.
     *
     * @param input the player's input string
     */
    public void handlePlayerInput(String input) {
        Ship playerShip = model.getShip();

        if (input == null || input.isEmpty()) {
            return;
        }

        String upperInput = input.toUpperCase();

        // try movements first to prevent boundary obstruction
        try {
            switch (upperInput) {
                case "W":
                    playerShip.move(Direction.UP);
                    logMovement();
                    break;
                case "A":
                    playerShip.move(Direction.LEFT);
                    logMovement();
                    break;
                case "S":
                    playerShip.move(Direction.DOWN);
                    logMovement();
                    break;
                case "D":
                    playerShip.move(Direction.RIGHT);
                    logMovement();
                    break;
                case "F":
                    model.fireBullet();
                    break;
                case "P":
                    pauseGame();
                    break;
                default:
                    logger.log("Invalid input. Use W, A, S, D, F, or P.");

            }
        } catch (BoundaryExceededException e) {
            // Handle movement beyond game boundaries
        }
    }

    /**
     * Switches the game between being paused and running, basically a play button.
     * Updates the UI and logs the state change.
     */
    public void pauseGame() {
        gameRunning = !gameRunning;
        ui.pause();
        logger.log("Game " + (gameRunning ? "resumed." : "paused."));
    }

    /**
     * Logs the player's ship's current position.
     */
    private void logMovement() {
        Ship playerShip = model.getShip();
        logger.log("Core.Ship moved to ("
                + playerShip.getX() + ", " + playerShip.getY() + ")");
    }

    /**
     * Gets the game model being used.
     *
     * @return the current game model
     */
    public GameModel getModel() {
        return this.model;
    }
}

