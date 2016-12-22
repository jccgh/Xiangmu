package jcc.xiangmu.module.user;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/10/31.
 */

public class Note extends BmobObject {
    private String title;
    private String content;
    private MyUser user;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
