package jcc.xiangmu.module.been;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/27.
 */

public class ResultChannel implements Serializable {
    private Map<String,String>map;

    public ResultChannel(){}
    public ResultChannel(Map<String, String> map) {
        this.map = map;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
