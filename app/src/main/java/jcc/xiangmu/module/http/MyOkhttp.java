package jcc.xiangmu.module.http;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/10/15.
 */
public class MyOkhttp{
    private static OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .build();

    public void json(String simpleName,String no){

        Request request = new Request.Builder()
                .url("https://route.showapi.com/64-19?" +
                        "com="+simpleName+
                        "&nu="+no+
                        "&showapi_appid=25516" +
                        "&showapi_sign=93e6f62ffa4f422b9bfb43ebf03e52bf")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                    if(response.isSuccessful()){
                       String json = response.body().string();
                        EventBus.getDefault().post(json);

                    }
            }
        });

    }
}
