package bree.TankGame.tankGame;

public class Bomb {
    int bombX,bombY;
    int life = 9;
    boolean bombIsLive = true;
    public Bomb(int bombX, int bombY){
        this.bombX = bombX;
        this.bombY = bombY;
    }
    public void lifeDown(){
        if (life > 0) {
            life--;
        } else {
            bombIsLive = false;
        }
    }
}
