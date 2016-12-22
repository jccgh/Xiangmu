package jcc.xiangmu.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import jcc.mycustomtopview.MyCustomTopView;
import jcc.mycustomtopview.OnClickListener;
import jcc.xiangmu.R;

public class SetActivity extends AppCompatActivity {

    private TextView mlruchTv;
    private Button mclear;
    private ImageView maboveImg;
    private MyCustomTopView mtop;
    private String mLruchpath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        initview();
        initdata();
        initevent();

    }

    private void initdata() {
        mLruchpath = getExternalFilesDir(null).getAbsolutePath();
        File file = new File(mLruchpath);
        File[] files = file.listFiles();
        if (files != null && files.length > 0) {


            int total = 0;
            try {
                for (int i = 0; i < files.length; i++) {
                    FileInputStream inputStream = new FileInputStream(files[i]);
                    int currentotal = inputStream.available();
                    total +=currentotal;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mlruchTv.setText(total / 1024 + "KB");
        }else{
            mlruchTv.setText("暂无缓存");
        }
    }

    private void initevent() {
        mtop.setOnClickListener(new OnClickListener() {
            @Override
            public void OnLeftClick() {
                SetActivity.this.finish();
            }

            @Override
            public void OncenterClick() {

            }

            @Override
            public void OnRightClick() {

            }
        });
        mclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(mLruchpath);
                File[] files = file.listFiles();
                if(files!=null&&files.length>0){
                    for (File file1:files){
                        file1.delete();
                    }
                    initdata();
                    Toast.makeText(SetActivity.this,"已清除所有缓存",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SetActivity.this,"无缓存",Toast.LENGTH_SHORT).show();

                }
            }
        });
        maboveImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SetActivity.this,AboveActivity.class));
                overridePendingTransition(R.anim.right_enter,R.anim.leftexist);
            }
        });
    }

    private void initview() {
        mlruchTv = (TextView) findViewById(R.id.tv_lruche);
        mclear = (Button) findViewById(R.id.bt_clear);
        maboveImg = (ImageView) findViewById(R.id.about_iv);
        mtop = (MyCustomTopView) findViewById(R.id.set_mycustomtoview);
    }
}
