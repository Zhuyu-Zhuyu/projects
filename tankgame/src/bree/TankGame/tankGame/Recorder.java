package bree.TankGame.tankGame;

import java.io.*;
import java.util.Vector;

/*
 * 记录相关信息，和文件交互
 * */
@SuppressWarnings({"all"})
public class Recorder {
    //定义变量，记录我方击毁敌方坦克数量
    private static int hitEnemyTankNum = 0;
    //定义IO对象，准备写数据到文件中
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    private static String recordFile = "myRecord.txt";
    //定义一个Node 的Vector，用于保存敌人信息的Node
    private static Vector<Node> nodes = new Vector<>();

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    public static String getRecordFile() {
        return recordFile;
    }

    //增加一个方法，用于读取recordFile，恢复相关信息
    public static Vector<Node> getNodesAndEnemyTankRec() {
        try {
            br = new BufferedReader(new FileReader(recordFile));
            hitEnemyTankNum = Integer.parseInt(br.readLine());
            //循环读取文件，生成一个nodes集合
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] xyd = line.split(" ");
                Node node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]),
                                     Integer.parseInt(xyd[2]));
                nodes.add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return nodes;
    }

    //定义Vector，指向MyPanel 对象，敌人坦克的 Vector；
    private static Vector<EnemyTank> enemyTanks = null;

    //增加一个功能，当游戏退出时，将hitEnemyTankNum 保存到 recordFile；
    //升级，保存游戏退出时的所有敌人坦克（还存活的）的坐标和方向
    public static void keepRecord() {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(getHitEnemyTankNum() + "\r\n");
            //OOP,定义一个属性，然后通过setXXXX得到敌人坦克的Vector
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                if (enemyTank.enemyIsLive) {
                    String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect();
                    bw.write(record + "\r\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int getHitEnemyTankNum() {
        return hitEnemyTankNum;
    }

    public static void addHitEnemyTank() {
        hitEnemyTankNum++;
    }
}
