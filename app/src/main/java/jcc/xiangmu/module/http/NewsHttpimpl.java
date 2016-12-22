package jcc.xiangmu.module.http;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import jcc.xiangmu.module.been.NewsBeen;
import jcc.xiangmu.module.parsejson.NewsParse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/10/20.
 */
public  class NewsHttpimpl implements INewsHttp{
    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(5000, TimeUnit.SECONDS)
            .build();
    @Override
    public  void getlist(final Context context, final int page, final String channelid, final INewsHttpListener iNewsHttpListener){
        Request request = new Request.Builder()
                .url("https://route.showapi.com/109-35?" +
                        "channelId=" +channelid+
                        "&channelName=" +
                        "&maxResult=20" +
                        "&needAllList=0" +
                        "&needContent=1" +
                        "&needHtml=0" +
                        "&page=" + page +
                        "&showapi_appid=25516" +
                        "&title=" +
                        "&showapi_sign=93e6f62ffa4f422b9bfb43ebf03e52bf")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                iNewsHttpListener.onFeild(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String json = response.body().string();
//                InputStream inputStream = response.body().byteStream();
                if(page==1){
                    File file = new File(context.getExternalFilesDir(null),channelid+".txt");
                    Log.d("TAG", "onResponse: *"+file.getAbsolutePath());
                    FileOutputStream outputStream = new FileOutputStream(file);
                    outputStream.write(json.getBytes());
                }
                List<NewsBeen> list = new NewsParse().getList(json);
                iNewsHttpListener.onsuccess(list);
            }
        });
    }
}
