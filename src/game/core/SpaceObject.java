package game.core;

import game.ui.ObjectGraphic;
import game.ui.Tickable;

/**
 * Interface representing any object that exists on the screen.
 * Defines common functions for all game objects.
 */
public interface SpaceObject extends Tickable {

    /**
     * Gets the graphical representation of the object.
     *
     * @return the object's graphic
     */
    ObjectGraphic render();

    /**
     * Gets horizontal position of the object.
     *
     * @return the x coordinate
     */
    int getX();

    /**
     * Gets the vertical position of the object.
     *
     * @return the y coordinate
     */
    int getY();
}
