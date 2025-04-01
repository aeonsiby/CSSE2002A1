package game.core;

import game.ui.ObjectGraphic;

public class Asteroid extends DescendingEnemy{

    public Asteroid(int x, int y){

        super(x,y);

    }
    @Override
    public ObjectGraphic render() {

        return new ObjectGraphic("🌑", "assets/asteroid.png");

    }
}
