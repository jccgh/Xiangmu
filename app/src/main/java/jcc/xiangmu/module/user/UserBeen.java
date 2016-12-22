package jcc.xiangmu.module.user;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/10/28.
 */

public class UserBeen extends BmobObject {
    private String username;
    private String userpassword;
    private String usercall;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getUsercall() {
        return usercall;
    }

    public void setUsercall(String usercall) {
        this.usercall = usercall;
    }
}
