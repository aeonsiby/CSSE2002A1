package game.core;

public abstract class ObjectWithPosition implements SpaceObject{
    protected int x;
    protected int y;

    public ObjectWithPosition(int x, int y){

        this.x = x;

        this.y = y;
    }

    @Override
    public int getX() {

        return this.x;
    }

    @Override
    public int getY() {

        return this.y;
    }
}
