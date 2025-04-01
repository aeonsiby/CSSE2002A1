package game.core;

import game.ui.ObjectGraphic;

public class Bullet extends ObjectWithPosition{

    public Bullet(int x, int y){

        super(x,y);
    }
    @Override
    public void tick(int tick){

        this.y--;
    }
    @Override
    public ObjectGraphic render(){

        return new ObjectGraphic("🔺", "assets/bullet.png");
    }
}
