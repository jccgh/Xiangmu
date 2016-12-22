package jcc.xiangmu.module.been;

/**
 * Created by Administrator on 2016/11/3.
 */

public class LikeShareBeen {
    private String title;
    private String content;

    public LikeShareBeen(){}
    public LikeShareBeen(String title, String content) {
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
