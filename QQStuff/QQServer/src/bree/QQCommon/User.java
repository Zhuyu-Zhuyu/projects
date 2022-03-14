package bree.QQCommon;

import java.io.Serializable;

/**
 * 表示一个用户信息
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userID;//用户ID/用户名
    private String password;//用户密码

    public User(String userID, String password) {
        this.userID = userID;
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
