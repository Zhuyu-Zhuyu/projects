package bree.QQServer.service;

import java.util.HashMap;
import java.util.Iterator;

/**
 * 管理和客户通讯的线程
 */
public  class ManageClientThread {

   private static HashMap<String, ServerConnectClientThread> hm = new HashMap<>();
   //返回hm

   public static HashMap<String, ServerConnectClientThread> getHm(){
    return hm;
   }

    //添加线程对象到集合中
    public static void addClientThread(String userID, ServerConnectClientThread serverConnectClientThread) {
        hm.put(userID, serverConnectClientThread);
    }
    //把某个客户端的线程从集合中删除
    public static void removeClientThread(String userID){
        hm.remove(userID);
    }

    //根据userID返回线程
    public static ServerConnectClientThread getServerConnectClientThread(String userID) {
        return hm.get(userID);
    }
    //返回在线用户列表
    public static String getOnlineUser(){
        //遍历集合，返回hashmap的key
        Iterator<String> iterator = hm.keySet().iterator();
        String onlineUserList = "";
        while(iterator.hasNext()){
            onlineUserList += iterator.next().toString() + " ";
        }
        return onlineUserList;
    }

}
