package game.core;

import game.utility.Direction;
import game.exceptions.BoundaryExceededException;

import static game.GameModel.GAME_HEIGHT;
import static game.GameModel.GAME_WIDTH;

// need to implement this after making exception

public abstract class Controllable extends ObjectWithPosition {

    public Controllable(int x, int y) {
        super(x, y);
    }

    public void move(Direction direction) throws BoundaryExceededException{

        int movedX = x;

        int movedY = y;

        switch (direction){



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

        }else if (movedX < 0) {

            throw new BoundaryExceededException("Cannot move left. Out of bounds!");

        }else if (movedY >= GAME_HEIGHT) {

            throw new BoundaryExceededException("Cannot move down. Out of bounds!");

        }else if (movedY < 0) {

            throw new BoundaryExceededException("Cannot move up. Out of bounds!");

        }else{

            this.x = movedX;

            this.y = movedY;
        }

    }


}
