package bree.TankGame.tankGame;

import java.util.Vector;

@SuppressWarnings({"all"})
public class EnemyTank extends Tank implements Runnable {//敌人的坦克
    boolean enemyIsLive = true;
    Vector<Shot> shots = new Vector<>();//敌人坦克，使用Vector保存多个shot，

    public EnemyTank(int x, int y) {
        super(x, y);
    }

    //使用Vector<enmeyTank>
    Vector<EnemyTank> enemyTanks = new Vector<>();

    //敌人坦克集合其实是在myPanel里面创建的，用set方法把myPanel的作为参数
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    //编写方法，判断当前的这个敌人坦克，是否和其余的敌人坦克发生碰撞
    public boolean isTouchEnemyTank() {
        switch (this.getDirect()) {
            case 0://上
                //当前坦克和除自己以外的所有坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {
                        //敌人坦克方向上下:x的范围enemyTank.getX(),enemyTank.getX() + 40;
                        //              y的范围:enemyTank.getY(),enemyTank.getY() + 60
                        //当前坦克的左上：this.getX(),this.getY()
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60)
                                return true;
                            //当前坦克的右上：this.getX() + 40,this.getY()
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60)
                                return true;
                        }
                        //敌人坦克方向右左:x的范围enemyTank.getX(),enemyTank.getX() + 60;
                        //              y的范围:enemyTank.getY(),enemyTank.getY() + 40
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //当前坦克的左上：this.getX(),this.getY()
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40)
                                return true;
                            //当前坦克的右上：this.getX() + 40,this.getY()
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40)
                                return true;
                        }
                    }
                }
                break;
            case 1://右
                //当前坦克和除自己以外的所有坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {
                        //敌人坦克方向上下:x的范围enemyTank.getX(),enemyTank.getX() + 40;
                        //              y的范围:enemyTank.getY(),enemyTank.getY() + 60
                        //当前坦克的右上：this.getX() + 60,this.getY()
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60<= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60)
                                return true;
                            //当前坦克的右下：this.getX() + 60,this.getY() + 40；
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY()  + 40 <= enemyTank.getY() + 60)
                                return true;
                        }
                        //敌人坦克方向右左:x的范围enemyTank.getX(),enemyTank.getX() + 60;
                        //              y的范围:enemyTank.getY(),enemyTank.getY() + 40
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //当前坦克的右上：this.getX() + 60,this.getY()
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40)
                                return true;
                            //当前坦克的右下：this.getX() + 60,this.getY() + 40
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40)
                                return true;
                        }
                    }
                }
                break;
            case 2://下
                //当前坦克和除自己以外的所有坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {
                        //敌人坦克方向上下:x的范围enemyTank.getX(),enemyTank.getX() + 40;
                        //              y的范围:enemyTank.getY(),enemyTank.getY() + 60
                        //当前坦克的左下：this.getX(),this.getY() + 60
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60)
                                return true;
                            //当前坦克的右下：this.getX() + 40,this.getY() + 60；
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY()  + 60 <= enemyTank.getY() + 60)
                                return true;
                        }
                        //敌人坦克方向右左:x的范围enemyTank.getX(),enemyTank.getX() + 60;
                        //              y的范围:enemyTank.getY(),enemyTank.getY() + 40
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //当前坦克的左下：this.getX(),this.getY() + 60
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40)
                                return true;
                            //当前坦克的右下：this.getX() + 40,this.getY() + 60；
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40)
                                return true;
                        }
                    }
                }
                break;
            case 3://左
                //当前坦克和除自己以外的所有坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {
                        //敌人坦克方向上下:x的范围enemyTank.getX(),enemyTank.getX() + 40;
                        //              y的范围:enemyTank.getY(),enemyTank.getY() + 60
                        //当前坦克的左上：this.getX(),this.getY()
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60)
                                return true;
                            //当前坦克的左下：this.getX(),this.getY() + 40；
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY()  + 40 <= enemyTank.getY() + 60)
                                return true;
                        }
                        //敌人坦克方向右左:x的范围enemyTank.getX(),enemyTank.getX() + 60;
                        //              y的范围:enemyTank.getY(),enemyTank.getY() + 40
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //当前坦克的左上：this.getX(),this.getY()
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40)
                                return true;
                            //当前坦克的左下：this.getX(),this.getY() + 40；
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40)
                                return true;
                        }
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public void run() {
        while (true) {
            //坦克存活，并且当shots里面null时，就放入一颗子弹，并启动
            if (enemyIsLive && shots.size() < 3) {
                Shot s = null;
                switch (getDirect()) {
                    case 0:
                        s = new Shot(getX() + 20, getY(), 0);
                        break;
                    case 1:
                        s = new Shot(getX() + 60, getY() + 20, 1);
                        break;
                    case 2:
                        s = new Shot(getX() + 20, getY() + 60, 2);
                        break;
                    case 3:
                        s = new Shot(getX(), getY() + 20, 3);
                        break;
                }
                shots.add(s);
                new Thread(s).start();
            }
            switch (getDirect()) {//根据坦克的方向继续移动
                case 0:
                    for (int i = 0; i < 30; i++) {
                        if (getY() > 0 && !isTouchEnemyTank()) moveUp();
                        try {
                            Thread.sleep(50);//休眠50ms
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1:
                    for (int i = 0; i < 30; i++) {
                        if (getX() + 60 < 1000 && !isTouchEnemyTank()) moveRight();
                        try {
                            Thread.sleep(50);//休眠50ms
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < 30; i++) {
                        if (getY() + 60 < 650 && !isTouchEnemyTank()) moveDown();
                        try {
                            Thread.sleep(50);//休眠50ms
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < 30; i++) {
                        if (getX() > 0 && !isTouchEnemyTank()) moveLeft();
                        try {
                            Thread.sleep(50);//休眠50ms
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }

            setDirect((int) (Math.random() * 4));//随机改变方向0-3
            if (!enemyIsLive) break;//判断敌人坦克是否活着，死了就停止该坦克的线程
        }
    }
}
