package game.core;

public abstract class DescendingEnemy extends ObjectWithPosition {

    public DescendingEnemy(int x, int y) {

        super(x, y);

    }

    @Override
    public void tick(int tick) {

        if (tick % 10 == 0) {

            this.y++;

        }
    }

}



