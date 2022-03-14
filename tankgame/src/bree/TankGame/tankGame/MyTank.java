package bree.TankGame.tankGame;

import java.util.Vector;

/*
* 自己的坦克
* */
public class MyTank extends Tank {
    //定义一个shot，表示一个射击（线程）,一颗子弹
    Shot shot = null;
    //发射多颗子弹，用集合
    Vector<Shot> shots = new Vector<>();
    public MyTank(int x, int y) {
        super(x, y);
    }
    public void shotEnemy(){
        //只能发射五颗子弹
        if(shots.size() == 5) return;
        //根据当前myTank的方向，位置来创建
        switch(getDirect()){
            case 0://上
                shot = new Shot(getX() + 20, getY(),0);
                break;
            case 1://右
                shot = new Shot(getX() + 60, getY() + 20,1);
                break;
            case 2://下
                shot = new Shot(getX() + 20, getY() + 60, 2);
                break;
            case 3://左
                shot = new Shot(getX(), getY() + 20, 3);
                break;
        }
        //把新创建的shot放入到shots集合中
        shots.add(shot);
        //启动一个shot线程
       new Thread(shot).start();
    }
}
