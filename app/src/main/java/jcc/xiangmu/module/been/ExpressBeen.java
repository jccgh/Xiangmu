package jcc.xiangmu.module.been;

/**
 * Created by Administrator on 2016/10/15.
 */
public class ExpressBeen {
    String time;
    String context;

    public ExpressBeen(String time, String context) {
        this.time = time;
        this.context = context;
    }

    public String getTime() {
        return time;
    }

    public String getContext() {
        return context;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
