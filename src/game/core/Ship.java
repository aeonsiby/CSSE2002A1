package game.core;
import game.ui.ObjectGraphic;

public class Ship extends Controllable{

    int health;

    int score;

    public Ship(int x, int y, int health) {

        super(x, y);

        this.health = health;

        this.score = 0;
    }

    public Ship(){

        this(5, 10, 100);

    }

    @Override
    public ObjectGraphic render() {
        return new ObjectGraphic("🚀", "assets/ship.png");
    }

    //was unsure whether hp<0 just means hp = 0 or if damage gets ignored
    public void takeDamage(int damage){

        if ( (this.health - damage) >= 0 ){

            this.health =- damage;

        }

    }
    public void heal(int amount){

        if ( (this.health + amount) <= 100 ){

            this.health += amount;

        }

    }
    public void addScore(int points) {

        this.score += points;
    }

    public int getHealth() {

        return this.health;

    }

    public int getScore() {

        return this.score;

    }

    @Override
    public void tick(int tick) {

    }

}
