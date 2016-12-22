package jcc.xiangmu.progress;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import jcc.xiangmu.module.been.ChannelBeen;
import jcc.xiangmu.module.http.IHttp;
import jcc.xiangmu.module.http.IjsonHttp;
import jcc.xiangmu.module.http.JsonHttpimpl;
import jcc.xiangmu.view.activity.minterface.Iupdataview;

/**
 * Created by Administrator on 2016/10/19.
 */
public class ChannelProgress {
    private Iupdataview iupdataview;
    private IjsonHttp ijsonHttp;

    public ChannelProgress(Iupdataview iupdataview,Context context) {
        this.iupdataview = iupdataview;
       ijsonHttp = new JsonHttpimpl();

    }

    public void start (Context context){
        ijsonHttp.httpUrl(context, new IHttp() {
            @Override
            public void onSuccess(List<ChannelBeen> list) {
                iupdataview.updataview(list);
            }

            @Override
            public void onFailed(Context context, String str) {
                Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
            }
        });
    }

}
