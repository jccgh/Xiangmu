package jcc.xiangmu.module.local;

import java.util.List;

import jcc.xiangmu.module.been.NewsBeen;
import jcc.xiangmu.module.parsejson.NewsParse;

/**
 * Created by Administrator on 2016/10/31.
 */

public class LocalloadImpl implements ILocalload{

    @Override
    public List<NewsBeen> getList(String json) {
        List<NewsBeen> localList = new NewsParse().getList(json);
        return localList;
    }
}
