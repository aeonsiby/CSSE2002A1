package game.core;

import game.utility.Direction;
import game.exceptions.BoundaryExceededException;

import static game.GameModel.GAME_HEIGHT;
import static game.GameModel.GAME_WIDTH;

/**
 * Abstract parent class for objects that can be controlled and moved.
 * Handles movement logic and checking if it is in bounds.
 */
public abstract class Controllable extends ObjectWithPosition {

    /**
     * Creates a controllable object at the specified position.
     *
     * @param x the horizontal position(postive to the right)
     * @param y the vertical position(positive downwards)
     */
    public Controllable(int x, int y) {
        super(x, y);
    }

    /**
     * Moves the object in the given direction.
     *
     * @param direction the direction to move
     * @throws BoundaryExceededException if movement would go out of bounds
     */
    public void move(Direction direction) throws BoundaryExceededException {
        int movedX = x;
        int movedY = y;

        switch (direction) {
            case UP:
                movedY--;
                break;
            case DOWN:
                movedY++;
                break;
            case LEFT:
                movedX--;
                break;
            case RIGHT:
                movedX++;
                break;
        }

        if (movedX >= GAME_WIDTH) {
            throw new BoundaryExceededException("Cannot move right. Out of bounds!");
        } else if (movedX < 0) {
            throw new BoundaryExceededException("Cannot move left. Out of bounds!");
        } else if (movedY >= GAME_HEIGHT) {
            throw new BoundaryExceededException("Cannot move down. Out of bounds!");
        } else if (movedY < 0) {
            throw new BoundaryExceededException("Cannot move up. Out of bounds!");
        } else {
            this.x = movedX;
            this.y = movedY;
        }
    }
}