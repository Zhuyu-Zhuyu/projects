package bree.QQServer.service;

import bree.QQCommon.Message;
import bree.QQCommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * 该类的一个对象和某个客户端保持通信
 */
public class ServerConnectClientThread extends Thread {
    private Socket socket;
    private String userID;//连接到服务器的ID

    public ServerConnectClientThread(Socket socket, String userID) {
        this.socket = socket;
        this.userID = userID;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {//线程处于run状态，可以发送/接收消息
        while (true) {
            System.out.println("服务端和客户端" + userID + "保持通信,读取数据");
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                //后面会使用message，根据类型作出相应的处理
                if (message.getMesType().equals(MessageType.MESSAGE_GET_ONLINE_USERS)) {
                    //客户端要在线列表
                    //形式：100 200 Bree
                    System.out.println(message.getSender() + "要在线用户列表");
                    String onlineUser = ManageClientThread.getOnlineUser();
                    //返回message
                    Message msg = new Message();
                    msg.setMesType(MessageType.MESSAGE_RETURN_ONLINE_USERS);
                    msg.setContent(onlineUser);
                    msg.setReceiver(message.getSender());
                    //返回到客户端
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(msg);
                } else if (message.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT)) {
                    //客户端要退出系统,调用方法，把线程从集合中删除
                    System.out.println(message.getSender() + "退出系统");
                    ManageClientThread.removeClientThread(message.getSender());
                    socket.close();
                    //退出线程，socket.close()，只是关闭端口，break退出while循环，退出run，退出线程
                    break;
                } else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
                    //客户端要给另一个客户端发消息,根据message 得到receiver ID 的对应的线程
                    ServerConnectClientThread clientThread = ManageClientThread.getServerConnectClientThread(message.getReceiver());
                    //得到此线程持有的socket的对象输出流
                    ObjectOutputStream oos = new ObjectOutputStream(clientThread.getSocket().getOutputStream());
                    oos.writeObject(message);
                } else if (message.getMesType().equals(MessageType.MESSAGE_ALL_MES)) {
                    //遍历集合中所有线程（除了自己），得到socket进行转发
                    HashMap<String, ServerConnectClientThread> hm = ManageClientThread.getHm();

                    Iterator<String> iterator = hm.keySet().iterator();
                    while (iterator.hasNext()) {
                        //取出在线用户的id
                        String onlineUserID = iterator.next().toString();
                        if ( !(onlineUserID.equals(message.getSender()))) {
                            //进行转发
                            ObjectOutputStream oos = new ObjectOutputStream(hm.get(onlineUserID).getSocket().getOutputStream());
                            oos.writeObject(message);
                        }
                    }
                } else if(message.getMesType().equals(MessageType.MESSAGE_SENDFILE_MES)){
                    //拿到receiver 的ID，转发
                    ObjectOutputStream oos =
                            new ObjectOutputStream(ManageClientThread.getServerConnectClientThread(message.getReceiver()).getSocket().getOutputStream());
                    oos.writeObject(message);
                }
                else {
                    System.out.println("其他类型的message，暂不处理");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
