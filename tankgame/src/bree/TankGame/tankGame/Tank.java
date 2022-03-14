package bree.TankGame.tankGame;

public class Tank {
    private int x;
    private int y;
    private int direct;//0up 1right 2down 3left
    private int speed = 2;
    boolean tankIsLive = true;
    //坦克移动,判断x，y，是否出界了
    public void moveUp(){
        y -= speed;
    }
    public void moveRight(){
        x += speed;
    }
    public void moveDown(){
        y += speed;
    }
    public void moveLeft(){
        x -= speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public Tank(int x, int y){
          this.x = x;
          this.y = y;
      }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
