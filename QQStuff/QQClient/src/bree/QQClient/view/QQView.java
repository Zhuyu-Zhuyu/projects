package bree.QQClient.view;

import bree.QQClient.Service.FileClientService;
import bree.QQClient.Service.MessageClientService;
import bree.QQClient.Service.UserClientService;

import java.io.File;
import java.util.Scanner;

/**
 * 客户端的菜单界面
 */
public class QQView {
    public static void main(String[] args) {
        new QQView().mainMenu();
        System.out.println("客户端退出系统");
    }
    private String userID;
    private boolean loop = true;//用来控制是否显示界面
    private UserClientService userClientService = new UserClientService();//用于登录服务器/注册用户
    private MessageClientService messageClientService = new MessageClientService();//用户聊天
    private FileClientService fileClientService = new FileClientService();//用于传输文件

    private void mainMenu() {
        while (loop) {
            System.out.println("===========欢迎登录网络通信系统=========");
            System.out.println("\t\t 1 登录系统");
            System.out.println("\t\t 9 退出登录");
            System.out.print("请输入你的选择: ");
            Scanner scanner = new Scanner(System.in);
            String key = scanner.next();
            //根据用户输入，处理不同逻辑
            switch (key) {
                case "1":
                    System.out.print("请输入用户名/ID：");
                    userID = scanner.next();
                    System.out.print("请输入密码：");
                    String password = scanner.next();
                    //到服务端验证该用户是否合法，代码比较多，编写一个类UserClientService
                    if (userClientService.checkUser(userID,password)) {
                        System.out.println("===========用户 " + userID + " 登录成功=========");
                        //进入到二级菜单
                        while (loop) {
                            System.out.println("===========网络通信系统二级菜单(用户" + userID + ")=========");
                            System.out.println("\t\t 1 在线用户");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.print("请输入你的选择");
                            String key2 = scanner.next();;
                            switch (key2) {
                                case "1":
                                    //写一个方法，获取在线用户列表
                                    userClientService.onlineUsersList();
                                    break;
                                case "2":
                                    System.out.println("请输入内容");
                                    String content = scanner.next();
                                    //调用一个方法进行群发
                                    messageClientService.sentMessageToAll(userID,content);
                                    break;
                                case "3":
                                    System.out.print("请输入想要私聊的用户号(在线): ");
                                    String receiverID = scanner.next();
                                    System.out.println("请输入内容");
                                    content = scanner.next();

                                    //编写方法，将消息发送给服务器端
                                    messageClientService.sendMessageTo(userID,content,receiverID);
                                    break;
                                case "4":
                                    System.out.println("请输入用户");
                                    receiverID = scanner.next();
                                    System.out.println("请输入文件的源路径");
                                    String srcFilePath = scanner.next();
                                    System.out.println("请输入目标路径");
                                    String destFilePath = scanner.next();
                                    fileClientService.sendFileToOne(userID,srcFilePath,receiverID,destFilePath);
                                    break;
                                case "9":
                                    //调用方法，给服务器发送退出线程的message
                                    userClientService.logout();
                                    loop = false;
                                    break;
                            }
                        }
                    } else {
                        System.out.println("输入无效,请重试");
                    }
                    break;
                case "9":
                    System.out.println("退出登录");
                    loop = false;
                    break;
            }
        }

    }
}
