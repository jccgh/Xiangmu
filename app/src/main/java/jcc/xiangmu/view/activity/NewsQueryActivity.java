package jcc.xiangmu.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import jcc.mycustomtopview.MyCustomTopView;
import jcc.mycustomtopview.OnClickListener;
import jcc.xiangmu.R;
import jcc.xiangmu.view.BaseActivity;

public class NewsQueryActivity extends BaseActivity {

    private MyCustomTopView mtop;
    private EditText met;
    private TextView mtv;
    private String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_query);
        initview();
        initevent();
    }

    private void initevent() {
        mtop.setOnClickListener(new OnClickListener() {
            @Override
            public void OnLeftClick() {
                NewsQueryActivity.this.finish();
            }

            @Override
            public void OncenterClick() {

            }

            @Override
            public void OnRightClick() {

            }
        });
        mtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword = met.getText().toString();
                if(keyword!=null&&!keyword.equals("")){

                }else {
                    Toast.makeText(NewsQueryActivity.this,"请输入关键词",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initview() {
        mtop = (MyCustomTopView)findViewById(R.id.query_TopView);
        met = (EditText)findViewById(R.id.query_et);
        mtv = (TextView)findViewById(R.id.query_tv);
    }
}
