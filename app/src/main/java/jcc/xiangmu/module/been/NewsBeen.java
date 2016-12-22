package jcc.xiangmu.module.been;

import java.util.List;

/**
 * Created by Administrator on 2016/10/20.
 */
public class NewsBeen {
    private String content;
    private String pubDate;
    private String title;
    private List<String> imgurl;
    private String desc;
    private String source;
    private String link;

    public NewsBeen(String content,
                    String pubDate,
                    String title,
                    List<String> imgurl,
                    String desc,
                    String source,
                    String link) {
        this.content = content;
        this.pubDate = pubDate;
        this.title = title;
        this.imgurl = imgurl;
        this.desc = desc;
        this.source = source;
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImgurl() {
        return imgurl;
    }

    public void setImgurl(List<String> imgurl) {
        this.imgurl = imgurl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
}
