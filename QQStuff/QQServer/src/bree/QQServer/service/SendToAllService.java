package bree.QQServer.service;

import bree.QQCommon.Message;
import bree.QQCommon.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class SendToAllService implements Runnable {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void run() {
        //为了能够多次推送消息，使用while
        while (true) {
            System.out.println("请输入服务器要推送的新闻/消息[输入exit表示退出推送服务]");
            String news = scanner.next();
            if(news.equals("exit")) break;
            //构建消息，群发消息
            Message message = new Message();
            message.setMesType(MessageType.MESSAGE_ALL_MES);
            message.setSender("服务器");
            message.setSentTime(new java.util.Date().toString());
            message.setContent(news);
            System.out.println("\n服务器推送消息：" + news);
            //遍历所有的线程，得到socket
            HashMap<String, ServerConnectClientThread> hm = ManageClientThread.getHm();
            Iterator<String> iterator = hm.keySet().iterator();
            while (iterator.hasNext()) {
                String onlineUsers = iterator.next().toString();
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(hm.get(onlineUsers).getSocket().getOutputStream());
                    oos.writeObject(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        }
    }
}
