package bree.QQClient.Service;

import bree.QQCommon.Message;
import bree.QQCommon.MessageType;
import bree.QQCommon.User;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 完成用户登录和用户注册等功能
 */
public class UserClientService {
    //其他地方可能使用User信息，因此作出成员属性
    private User u = new User();
    //其他地方可能使用Socket信息，因此作出成员属性
    private Socket socket;

    //根据userID和pwd到服务器验证是否合理
    public boolean checkUser(String userID, String pwd) {
        boolean b = false;
        u.setUserID(userID);
        u.setPassword(pwd);

        try {
            //连接socket,发送u
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
            //得到ObjectOutputStream对象
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(u);//发送user对象

            //  读取从服务端回复的message对象
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message message = (Message)ois.readObject();
            if(message.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)){//登录成功

                //创建一个和服务器端保持通讯的线程，类：ClientConnectServerThread
                ClientConnectServerThread clientConnectServerThread =
                        new ClientConnectServerThread(socket);
                //启动客户端线程
                clientConnectServerThread.start();
                //为了方便管理线程的扩展，将线程放入集合中管理
                ManageClientConnectServerThread.addClientConnectServerThread(userID,clientConnectServerThread);
                b = true;
            } else{
                //如果启动失败，就没有启动客户端和服务器的线程，需要关闭socket
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }
    //向服务器端获取在线用户列表
    public void onlineUsersList(){
        //发送一个message，类型是MESSAGE_GET_ONLINE_USERS,还有setSender
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_GET_ONLINE_USERS);
        message.setSender(u.getUserID());

        try {
            //得到当前线程的socket 对应的ObjectOutputStream，发送给服务器
            ObjectOutputStream oos = new ObjectOutputStream(
                    ManageClientConnectServerThread.getClientConnectServerThread(u.getUserID()).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //向服务器发送关闭线程的message
    public void logout(){
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(u.getUserID());//一定要指明是哪个客户端需要关闭线程
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.
                            getClientConnectServerThread(u.getUserID()).getSocket().getOutputStream());
            oos.writeObject(message);
            System.out.println(u.getUserID() + " 退出系统");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
