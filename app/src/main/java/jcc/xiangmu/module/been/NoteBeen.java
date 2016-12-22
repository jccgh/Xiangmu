package jcc.xiangmu.module.been;

/**
 * Created by Administrator on 2016/11/2.
 */

public class NoteBeen {
    private String title;
    private String content;

    public NoteBeen(){}
    public NoteBeen(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

