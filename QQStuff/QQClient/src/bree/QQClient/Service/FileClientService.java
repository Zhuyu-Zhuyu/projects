package bree.QQClient.Service;

import bree.QQCommon.Message;
import bree.QQCommon.MessageType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * 该类/对象完成 文件传输服务
 */
public class FileClientService {
    /**
     *
     * @param senderID 发送者的id
     * @param srcFilePath 文件保存的源目录
     * @param receiverID 接受者的id
     * @param destFilePath 文件发送到哪个目录（接受者）
     */
    public void sendFileToOne(String senderID,String srcFilePath,String receiverID,String destFilePath){
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_SENDFILE_MES);
        message.setSrcFilePath(srcFilePath);
        message.setSender(senderID);
        message.setDestFilePath(destFilePath);
        message.setReceiver(receiverID);
        //读取文件
        FileInputStream fileInputStream = null;
        byte[] fileBytes = new byte[(int) new File(srcFilePath).length()];

        try {
            fileInputStream = new FileInputStream(srcFilePath);
            fileInputStream.read(fileBytes);//srcFilePath的文件IO到程序的字节数组
            //将文件对应的字节数组放入到message里
            message.setFileBytes(fileBytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(fileBytes != null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //提示信息
        System.out.println("\n" + senderID + "sends file to "+ receiverID +
                "\nfrom " + srcFilePath + " to " + destFilePath) ;

        //发送
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.
                    getClientConnectServerThread(senderID).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
