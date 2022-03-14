package bree.TankGame.tankGame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

public class MyTankGame extends JFrame {
    MyPanel mp;//定义MyPanel
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        MyTankGame myTankGame = new MyTankGame();

    }

    public MyTankGame() {
        System.out.println("请输入：1（进行新游戏），2（继续上局）");
        String key = scanner.next();
        mp = new MyPanel(key);
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);//游戏的绘图区域
        this.setSize(1250, 650);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(mp);//监听键盘输入
        //在JFrame中，增加相应关闭窗口的处理
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();
                System.exit(0);
            }
        });
    }

}
