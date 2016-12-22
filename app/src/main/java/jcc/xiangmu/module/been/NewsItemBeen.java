package jcc.xiangmu.module.been;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/29.
 */

public class NewsItemBeen implements Serializable{
    private String title;
    private String content;
    private String source;
    private String link;

    public NewsItemBeen(){}
    public NewsItemBeen(String title,String content, String source, String link) {
        this.content = content;
        this.source = source;
        this.link = link;
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
