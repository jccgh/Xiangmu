package jcc.xiangmu.module.http;

import android.content.Context;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import jcc.xiangmu.module.parsejson.ChannelParse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/10/18.
 */
public class JsonHttpimpl implements IjsonHttp{

    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(5000, TimeUnit.SECONDS)
            .build();
    @Override
    public void httpUrl(final Context context, final IHttp iHttp){
        Request request = new Request.Builder()
                .url("https://route.showapi.com/109-34?" +
                        "showapi_appid=25516" +
                        "&showapi_sign=93e6f62ffa4f422b9bfb43ebf03e52bf")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                iHttp.onFailed(context,"网络请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                 iHttp.onSuccess(ChannelParse.parsejson(json));

            }
        });

    }
}
