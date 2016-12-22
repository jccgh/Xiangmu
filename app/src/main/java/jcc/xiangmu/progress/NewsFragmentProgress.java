package jcc.xiangmu.progress;

import android.content.Context;

import java.util.List;

import jcc.xiangmu.module.been.NewsBeen;
import jcc.xiangmu.module.http.INewsHttp;
import jcc.xiangmu.module.http.INewsHttpListener;
import jcc.xiangmu.module.http.NewsHttpimpl;
import jcc.xiangmu.view.fragment.INewsFragment;

/**
 * Created by Administrator on 2016/10/20.
 */
public class NewsFragmentProgress {
    private INewsHttp iNewsHttp;
    private INewsFragment iNewsFragment;
    private String channelid;
    private Context context;


    public NewsFragmentProgress(Context context,String channelid,INewsFragment iNewsFragment) {
        this.iNewsFragment = iNewsFragment;
        iNewsHttp = new NewsHttpimpl();
        this.channelid = channelid;
        this.context = context;

    }
    public void start(int page){
        iNewsHttp.getlist(context,page,channelid, new INewsHttpListener() {
            @Override
            public void onsuccess(List<NewsBeen> list) {
                iNewsFragment.updateView(list);
            }

            @Override
            public void onFeild(String msg) {
                iNewsFragment.toastFailed();
            }
        });
    };
}
