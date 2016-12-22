package jcc.xiangmu.module.http;

import android.content.Context;

/**
 * Created by Administrator on 2016/10/20.
 */
public interface INewsHttp {
    void getlist(Context context,int page, String channelid, INewsHttpListener iNewsHttpListener);
}
