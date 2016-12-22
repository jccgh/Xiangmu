package jcc.xiangmu.module.http;

import android.content.Context;

import java.util.List;

import jcc.xiangmu.module.been.ChannelBeen;

/**
 * Created by Administrator on 2016/10/18.
 */
public interface IHttp {
    void onSuccess(List<ChannelBeen> list);
    void onFailed(Context context,String str);
}
