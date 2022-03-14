package bree.TankGame.tankGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

/*
 * 坦克大战的绘图区域
 * */
@SuppressWarnings("all")
public class MyPanel extends JPanel implements KeyListener, Runnable {
    //定义我的坦克
    MyTank myTank;
    //定义敌人的坦克
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //定一个Vector，存放炸弹.当子弹集中坦克，就加入一个bomb对象到bombs
    Vector<Bomb> bombs = new Vector<Bomb>();
    //定义一个Vector,用于接收敌人坦克恢复的信息
    Vector<Node> nodes = new Vector<>();
    int enemyTankSize = 3;
    //定义三张图片，用于显示炸弹效果
    Image img1 = null;
    Image img2 = null;
    Image img3 = null;

    public MyPanel(String key) {
        //先判断文件是否存在,不存在开新游戏，key = 1;
        File file = new File(Recorder.getRecordFile());
        if(file.exists()){
            nodes = Recorder.getNodesAndEnemyTankRec();
        } else {
            System.out.println("没有历史记录，开始新游戏");
            key = "1";
        }
        //把MyPanel 对象的enemyTanks 赋给Recorder的setEnemyTank，方便记录
        Recorder.setEnemyTanks(enemyTanks);
        myTank = new MyTank(500, 100);//初始化MyTank
        switch(key){
            case "1"://开始新游戏
                //初始化敌人坦克
                for (int i = 0; i < enemyTankSize; i++) {
                    //创建一个敌人的坦克
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
                    //把封装敌人坦克是否碰撞的enemyTanks 集合设置给 enemyTank!!!!!!
                    enemyTank.setEnemyTanks(enemyTanks);
                    enemyTank.setDirect(2);//设置方向
                    new Thread(enemyTank).start();//启动敌人坦克移动的线程
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());//给enemyTank坦克加入一颗子弹
                    enemyTank.shots.add(shot);//加入到shots里
                    new Thread(shot).start();//启动shot
                    enemyTanks.add(enemyTank);
                }
                break;
            case "2"://继续上局游戏
                //初始化敌人坦克
                for (int i = 0; i < nodes.size(); i++) {
                    //创建一个敌人的坦克
                    Node node = nodes.get(i);
                    EnemyTank enemyTank = new EnemyTank(node.getX(),node.getY());
                    //把封装敌人坦克是否碰撞的enemyTanks 集合设置给 enemyTank!!!!!!
                    enemyTank.setEnemyTanks(enemyTanks);
                    enemyTank.setDirect(node.getDirect());//设置方向
                    new Thread(enemyTank).start();//启动敌人坦克移动的线程
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());//给enemyTank坦克加入一颗子弹
                    enemyTank.shots.add(shot);//加入到shots里
                    new Thread(shot).start();//启动shot
                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("输入有误,请重新输入");
        }

        //初始化炸弹
        img1 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/img1.jpeg"));
        img2 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/img2.jpeg"));
        img3 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/img3.jpeg"));
    }
    //编写方法，显示我方击毁敌方坦克的信息
    public void showInfo(Graphics g){
        g.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD, 20);
        g.setFont(font);
        g.drawString("累积击毁敌人数量:",1020,30);
        drawTank(1020,60,g,0,0);//画出一个敌方坦克
        g.setColor(Color.BLACK);
        g.drawString(Recorder.getHitEnemyTankNum() + " ",1080,100);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 650);//填充矩形，默认黑色
        showInfo(g);
        //画出自己的坦克，封装到方法里,首先得确保是否为null，isLive
        if( myTank != null && myTank.tankIsLive) {
            drawTank(myTank.getX(), myTank.getY(), g, myTank.getDirect(), 1);
        }
        //画出射击的子弹.需要一个线程。不停重绘
//        if (myTank.shot != null && myTank.shot.isLive == true) {
//            g.draw3DRect(myTank.shot.getBulletX(), myTank.shot.getBulletY(), 2, 2, false);
//        }
        //画出myTank 射击的子弹，从shots集合中遍历取出
        for (int i = 0; i < myTank.shots.size(); i++) {
            Shot shot = myTank.shots.get(i);
            if (shot != null && shot.isLive == true) {
            g.draw3DRect(shot.getBulletX(), shot.getBulletY(), 2, 2, false);
        }else{//该对象无效，就从集合中拿掉
                myTank.shots.remove(shot);
            }
        }
        //如果bombs vector 中有对象，就画出
        for (int i = 0; i < bombs.size(); i++) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Bomb bomb = bombs.get(i);
            //根据当前bomb的life值，画出对应的图片
            if (bomb.life > 6) {
                g.drawImage(img3, bomb.bombX, bomb.bombY, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(img2, bomb.bombX, bomb.bombY, 60, 60, this);
            } else {
                g.drawImage(img1, bomb.bombX, bomb.bombY, 60, 60, this);
            }
            //减少life,为零就从集合中删除
            bomb.lifeDown();
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }
        }
        //画出敌人的坦克,重绘子弹,
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);//从Vector中取出
            //判断当前坦克是否还活着,死了就remove
            if (enemyTank.enemyIsLive) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
                //画出enemyTank所有的子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    Shot shot = enemyTank.shots.get(j);//取出子弹
                    if (shot.isLive) {//isLive == true
                        g.draw3DRect(shot.getBulletX(), shot.getBulletY(), 2, 2, false);
                    } else {
                        enemyTank.shots.remove(shot);//移除
                    }
                }
            }
        }

    }


    /**
     * @param x         坦克左上角横坐标
     * @param y         坦克左上角的纵坐标
     * @param g         画笔
     * @param direction 移动方向（上下左右）
     * @param type      坦克类型
     */
    public void drawTank(int x, int y, Graphics g, int direction, int type) {
        //根据类型设置不同的颜色区分敌方我方
        switch (type) {
            case 0://敌方坦克
                g.setColor(Color.cyan);
                break;
            case 1://己方坦克
                g.setColor(Color.yellow);
                break;
        }
        //根据坦克方向，绘制坦克0:up;1:right;2:down;3:left
        switch (direction) {
            case 0://up
                g.fill3DRect(x, y, 10, 60, false);//左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//机体
                g.drawOval(x + 10, y + 20, 20, 20);//盖子
                g.drawLine(x + 20, y + 30, x + 20, y);//炮筒
                break;
            case 1://right
                g.fill3DRect(x, y, 60, 10, false);//上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//下边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//机体
                g.drawOval(x + 20, y + 10, 20, 20);//盖子
                g.drawLine(x + 30, y + 20, x + 60, y + 20);//炮筒
                break;
            case 2://down
                g.fill3DRect(x, y, 10, 60, false);//左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//机体
                g.drawOval(x + 10, y + 20, 20, 20);//盖子
                g.drawLine(x + 20, y + 30, x + 20, y + 60);//炮筒
                break;
            case 3://left
                g.fill3DRect(x, y, 60, 10, false);//上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//下边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//机体
                g.drawOval(x + 20, y + 10, 20, 20);//盖子
                g.drawLine(x + 30, y + 20, x, y + 20);//炮筒
                break;
            default:
                System.out.println("暂时未处理");
        }
    }
    //编写方法，判断敌人坦克的子弹是否击中我方坦克
    public void  hitMyTank(){
        //遍历敌人的所有坦克，并取出
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            //遍历取出所有坦克的所有子弹
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                Shot shot = enemyTank.shots.get(j);
                //判断shot是否击中我的坦克
                if(enemyTank.tankIsLive && shot.isLive){
                    hitTank(shot,myTank);
                }
            }
        }
    }
    //编写方法，判断我方子弹是否击中敌方坦克
    //如果我方的子弹不止一颗，就需要将集合中的每一颗子弹取出，和敌人坦克集合中的每一辆进行比较
    public void hitEnemyTank(){
        for (int i = 0; i < myTank.shots.size(); i++) {
           Shot shot =  myTank.shots.get(i);
            //判断我方的子弹是否还继续存在
            if (shot != null && shot.isLive) {
                //遍历敌方坦克
                for (int j = 0; j < enemyTanks.size(); j++) {
                    EnemyTank enemyTank = enemyTanks.get(j);
                    hitTank(shot, enemyTank);
                }
            }
        }
    }
    public void hitTank(Shot s, Tank tank) {
        switch (tank.getDirect()) {
            case 0://上
            case 2://下
                if (s.getBulletX() > tank.getX() && s.getBulletX() < tank.getX() + 40
                        && s.getBulletY() > tank.getY() && s.getBulletY() < tank.getY() + 60) {
                    s.isLive = false;
                    tank.tankIsLive = false;
                    //击中坦克后，就从集合中remove
                    enemyTanks.remove(tank);
                    //击中坦克后，就把Recorder.hitEnemyTankNum++，但是这个坦克可能是自己的，所以要先判断
                    if( tank instanceof EnemyTank ) Recorder.addHitEnemyTank();
                    //创建bomb对象加入到bombs
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1://右
            case 3://左
                if (s.getBulletX() > tank.getX() && s.getBulletX() < tank.getX() + 60
                        && s.getBulletY() > tank.getY() && s.getBulletY() < tank.getY() + 40) {
                    s.isLive = false;
                    tank.tankIsLive = false;
                    //击中坦克后，就从集合中remove
                    enemyTanks.remove(tank);
                    //创建bomb对象加入到bombs
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {//up
            myTank.setDirect(0);
            if (myTank.getY() > 0) myTank.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {//right
            myTank.setDirect(1);
            if (myTank.getX() + 60 < 1000) myTank.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {//down
            myTank.setDirect(2);
            if (myTank.getY() + 60 < 650) myTank.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {//left
            myTank.setDirect(3);
            if (myTank.getX() > 0) myTank.moveLeft();
        }
        //键盘点击J，就发射一颗子弹
        if (e.getKeyCode() == KeyEvent.VK_J) {
            //判断myTank.的子弹是否为null,仅针对一颗子弹
           // if(myTank.shot == null || !myTank.shot.isLive ) myTank.shotEnemy();
        //发射多颗子弹，不必判断，直接调用方法发射
            myTank.shotEnemy();
        }
        this.repaint();//面板重绘
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100); //每隔100ms，repaint
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //判断我方的子弹是否击中敌人的坦克
            hitEnemyTank();
            //判断敌人的所有的坦克的所有子弹是否击中我方坦克
            hitMyTank();
            this.repaint();
        }
    }
}

