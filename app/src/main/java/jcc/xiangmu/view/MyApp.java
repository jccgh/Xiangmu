package jcc.xiangmu.view;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

/**
 * Created by Administrator on 2016/10/28.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //加载大图片的时候，setDownsampleEnabled()设置为true,可以避免OOM
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this,config);
    }
}
