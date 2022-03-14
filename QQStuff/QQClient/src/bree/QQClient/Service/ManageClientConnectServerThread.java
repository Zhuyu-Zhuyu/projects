package bree.QQClient.Service;

import java.util.HashMap;

/**
 * 该类管理客户端连接到服务器端的线程
 */
public class ManageClientConnectServerThread {
    //把多个线程放入HashMap中，key 是 ID，value 是 线程
    private static HashMap<String,ClientConnectServerThread> hm = new HashMap<>();
    //放入线程到集合中
    public static void addClientConnectServerThread(String userID,ClientConnectServerThread clientConnectServerThread){
        hm.put(userID,clientConnectServerThread);
    }
    //通过UserID可以得到对应的线程
    public static ClientConnectServerThread getClientConnectServerThread(String userID){
       return  hm.get(userID);
    }
}
