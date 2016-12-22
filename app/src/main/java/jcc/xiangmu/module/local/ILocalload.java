package jcc.xiangmu.module.local;

import java.util.List;

import jcc.xiangmu.module.been.NewsBeen;

/**
 * Created by Administrator on 2016/10/31.
 */

public interface ILocalload {
    public List<NewsBeen> getList(String json);
}
