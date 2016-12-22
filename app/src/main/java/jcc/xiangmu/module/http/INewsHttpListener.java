package jcc.xiangmu.module.http;

import java.util.List;

import jcc.xiangmu.module.been.NewsBeen;

/**
 * Created by Administrator on 2016/10/20.
 */
public interface INewsHttpListener {
    void onsuccess(List<NewsBeen>list);
    void onFeild(String msg);
}
