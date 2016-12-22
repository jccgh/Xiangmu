package jcc.xiangmu.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import jcc.xiangmu.R;

public class WelcomActivity extends AppCompatActivity {

    private ImageView mimg;
    private TextView mtv;
//    private int[] imgs = {R.drawable.p95,R.drawable.p96,R.drawable.p97};
    private int[] imgs = {R.drawable.welcomenes2,R.drawable.welcomenew3,R.drawable.welcomenews1};
    private int point =0;
    private Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ++point;
            if(point<4) {
                mhandler.sendEmptyMessageDelayed(point, 1500);
            }
            switch (msg.what){
                case 0:
                    mimg.setImageResource(imgs[0]);
                    break;
                case 1:
                    mimg.setImageResource(imgs[1]);
                    break;
                case 2:
                    mimg.setImageResource(imgs[2]);
                    break;
                case 3:
                    startActivity(new Intent(WelcomActivity.this,MainActivity.class));
                    overridePendingTransition(R.anim.right_enter,R.anim.leftexist);
                    WelcomActivity.this.finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
        initview();
        setimg();
    }

    private void setimg() {
        mhandler.sendEmptyMessage(point);
    }

    private void initview() {
        mimg = (ImageView)findViewById(R.id.welcom_img);
        mtv = (TextView)findViewById(R.id.advertisement_text);
        mtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomActivity.this,MainActivity.class));
                overridePendingTransition(R.anim.right_enter,R.anim.leftexist);
                point=4;
                WelcomActivity.this.finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        point=4;
    }
}
