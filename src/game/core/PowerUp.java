package game.core;

public abstract class PowerUp extends ObjectWithPosition implements PowerUpEffect{

    public PowerUp(int x, int y) {

        super(x,y);

    }

    @Override
    public void tick(int tick) {

    }

}
