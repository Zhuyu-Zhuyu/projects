package bree.TankGame.tankGame;

/**
 * 射击子弹
 */
public class Shot implements Runnable{
    private int bulletX;//子弹横坐标
    private int bulletY;//子弹纵坐标
    private int bulletDirect = 0;//子弹方向
    private int bulletSpeed = 10;//子弹速度
    boolean isLive = true;

    public Shot(int bulletX, int bulletY, int bulletDirect) {
        this.bulletX = bulletX;
        this.bulletY = bulletY;
        this.bulletDirect = bulletDirect;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(50);//休眠50毫秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //根据方向调整坐标
            switch (bulletDirect) {
                case 0://上
                    bulletY -= bulletSpeed;
                    break;
                case 1://右
                    bulletX += bulletSpeed;
                    break;
                case 2://下
                    bulletY += bulletSpeed;
                    break;
                case 3://左
                    bulletX -= bulletSpeed;
                    break;
                default:
                    System.out.println("invalid input");

            }
            //System.out.println(bulletX + "-"+ bulletY);//输出子弹的坐标
            //当子弹到达面板的边缘，就应该销毁（终止线程），子弹碰到敌人时，也应该终止
            if( !(bulletX >= 0 && bulletX <= 1000
                    && bulletY >= 0 && bulletY <= 650 && isLive)) {
                isLive = false;
                break;
            }
            }
    }

    public int getBulletX() {
        return bulletX;
    }

    public void setBulletX(int bulletX) {
        this.bulletX = bulletX;
    }

    public int getBulletY() {
        return bulletY;
    }

    public void setBulletY(int bulletY) {
        this.bulletY = bulletY;
    }
}
