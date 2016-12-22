package jcc.xiangmu.module.been;

/**
 * Created by Administrator on 2016/10/29.
 */

public class LeftItem {

    private String title;
    private int imgid;

    public LeftItem(){}
    public LeftItem(String title, int imgid) {
        this.title = title;
        this.imgid = imgid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }
}
