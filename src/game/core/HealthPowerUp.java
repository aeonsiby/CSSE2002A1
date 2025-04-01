package game.core;

import game.ui.ObjectGraphic;


public class HealthPowerUp extends PowerUp {

    private static final int HEALING_EFFECT = 20;

    public HealthPowerUp(int x, int y) {

        super(x, y);

    }

    @Override
    public ObjectGraphic render() {

        return new ObjectGraphic("❤️", "assets/health.png");

    }

    @Override
    public void applyEffect(Ship ship) {

        ship.heal(HEALING_EFFECT);

        System.out.println("Health restored by 20!");

    }
}