package bree.QQCommon;

/**
 * 表示消息类型
 */
public interface MessageType {
    String MESSAGE_LOGIN_SUCCEED = "1";//登陆成功
    String MESSAGE_LOGIN_FAIL = "2";//登陆失败
    String MESSAGE_COMM_MES = "3";//普通信息包
    String MESSAGE_GET_ONLINE_USERS = "4";//获取在线用户列表
    String MESSAGE_RETURN_ONLINE_USERS = "5";//返回在线用户
    String MESSAGE_CLIENT_EXIT = "6";//用户退出
    String MESSAGE_ALL_MES = "7";//群发信息包
    String MESSAGE_SENDFILE_MES = "8";//群发信息包
}
