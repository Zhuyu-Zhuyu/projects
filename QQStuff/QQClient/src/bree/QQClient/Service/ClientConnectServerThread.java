package bree.QQClient.Service;

import bree.QQCommon.Message;
import bree.QQCommon.MessageType;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnectServerThread extends Thread {
    //该线程需要持有socket
    private Socket socket;

    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        //在后台Thread需要和服务器通信，因此做成一个while循环
        while(true){

            try {
                System.out.println("客户端的线程，等待读取从服务器端发送的消息");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                //如果服务器没有消息发送，就会阻塞在这里，后面会用到message
                Message message = (Message)ois.readObject();
                //判断message的类型，然后回复相应的内容
                if (message.getMesType().equals(MessageType.MESSAGE_RETURN_ONLINE_USERS)){
                    //取出在线用户列表，并显示
                    String[] onlineUsers = message.getContent().split(" ");
                    System.out.println("\n=========当前在线用户列表==========");
                    for (int i = 0; i < onlineUsers.length; i++) {
                        System.out.println("用户：" + onlineUsers[i]);
                    }
                } else if(message.getMesType().equals(MessageType.MESSAGE_COMM_MES)){
                    //私发消息显示在对应客户端的控制台
                    System.out.println("\n" + message.getSentTime() + "\n" + message.getSender() +
                            " 给你发消息： " + message.getContent() );
                }else if(message.getMesType().equals(MessageType.MESSAGE_ALL_MES)){
                    //群发消息显示在客户端的控制台即可
                    System.out.println("\n" + message.getSentTime() +"\n"+ message.getSender() +
                            " to ALL:" + message.getContent());
                }else if(message.getMesType().equals(MessageType.MESSAGE_SENDFILE_MES)){
                    System.out.println(message.getSender() + "给" + message.getReceiver()
                    + "发送文件，从对方的" + message.getSrcFilePath() + "目录，到我的" + message.getDestFilePath());

                    //读取文件数组，FileOutputStream
                    FileOutputStream fileOutputStream = new FileOutputStream(message.getDestFilePath());
                    fileOutputStream.write(message.getFileBytes());
                    fileOutputStream.close();
                    System.out.println("\n保存文件成功");
                }
                else{
                    System.out.println("其他类型的message，暂时不处理");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
