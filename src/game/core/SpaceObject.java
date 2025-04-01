package game.core;

import game.ui.ObjectGraphic;
import game.ui.Tickable;

public interface SpaceObject extends Tickable {

    ObjectGraphic render();

    int getX();

    int getY();


}
