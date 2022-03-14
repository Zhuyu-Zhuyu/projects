package bree.QQServer.service;

import bree.QQCommon.Message;
import bree.QQCommon.MessageType;
import bree.QQCommon.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * 服务器，监听9999端口，等待连接，并保持通讯
 */
public class QQServer {

    private ServerSocket serverSocket = null;
    //创建一个集合，存放多人的数据，如果是这些人登录，就认为是合法的
    private static HashMap<String,User> validUsers = new HashMap<>();
    static {//静态代码块，类加载的时候会加载一次
        validUsers.put("100",new User("100","1234"));
        validUsers.put("200",new User("200","1234"));
        validUsers.put("Gabrielle",new User("Gabrielle","1234"));
        validUsers.put("400",new User("400","1234"));
        validUsers.put("Bree",new User("Bree","1234"));
        validUsers.put("Eddi",new User("Eddi","1234"));
    }
    //验证用户方法
    private boolean checkUser(String userID,String password){
        User userid = validUsers.get(userID);
        if(userid == null){//说明validUsers中根本不存在userID
            return false;
        }
        if( !userid.getPassword().equals(password)){//id 正确，密码错误
            return false;
        }
        return true;
    }
    public QQServer() {
        System.out.println("服务端在9999端口监听");
        try {
            //启动推送消息的线程
            new Thread(new SendToAllService()).start();
            serverSocket = new ServerSocket(9999);
            while (true) {//和某个客户端连接后，会一直监听,直到连接下一个客户端的端口
                Socket socket = serverSocket.accept();
                //得到socket关联的对象输入流
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                //得到socket关联的对象输出流
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                User user = (User) ois.readObject();
                //创建一个Message对象，准备回复客户端
                Message message = new Message();

                //验证用户方法
                if (checkUser(user.getUserID(),user.getPassword())) {//登录成功
                    message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    //将message对象回复给客户端
                    oos.writeObject(message);
                    //创建一个线程，和客户端保持通信，并且持有socket对象
                    ServerConnectClientThread serverConnectClientThread =
                            new ServerConnectClientThread(socket, user.getUserID());
                    //启动该线程
                    serverConnectClientThread.start();
                    //把该线程对象放入集合中，进行管理
                    ManageClientThread.addClientThread(user.getUserID(),serverConnectClientThread);

                } else {//登录失败
                    System.out.println("用户 ID:"+user.getUserID()+ " pwd: "+user.getPassword()+ "登录失败");
                    message.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
                    oos.writeObject(message);
                    //关闭socket
                    socket.close();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        //如果服务端退出while，说明服务端不再监听，需要关闭serverSocket
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
