package bree.QQClient.Service;

import bree.QQCommon.Message;
import bree.QQCommon.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * 消息相关的功能
 */
public class MessageClientService {
    /**
     * @param senderId
     * @param chatContent
     * @param receiverId
     */
    public void sendMessageTo(String senderId, String chatContent, String receiverId) {
        //构建message
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_COMM_MES);
        message.setSender(senderId);
        message.setReceiver(receiverId);
        message.setContent(chatContent);
        message.setSentTime(new java.util.Date().toString());
        System.out.println("\n"+senderId + " to " + receiverId + ": " + chatContent);
        //将message传给服务器端
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.
                    getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param userID
     * @param content
     */
    public void sentMessageToAll(String userID, String content) {
        //构建message
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_ALL_MES);
        message.setSender(userID);
        message.setContent(content);
        message.setSentTime(new java.util.Date().toString());

        System.out.println("\n"+userID + " to ALL: " + content);
        //将message传给服务器端
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.
                    getClientConnectServerThread(userID).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

