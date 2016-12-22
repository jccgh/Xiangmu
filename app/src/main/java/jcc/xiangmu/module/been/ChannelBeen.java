package jcc.xiangmu.module.been;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/18.
 */
public class ChannelBeen implements Serializable{
    private String channelId;
    private String name;
    private boolean state;

    public ChannelBeen(String channelId, String name) {
        this.channelId = channelId;
        this.name = name;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
