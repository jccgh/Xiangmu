package jcc.xiangmu.view.activity.minterface;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import jcc.mycustomtopview.MyCustomTopView;
import jcc.mycustomtopview.OnClickListener;
import jcc.xiangmu.R;

public class LikeShareItemActivity extends AppCompatActivity {

    private MyCustomTopView mtop;
    private TextView mtv;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_share_item);
        content = getIntent().getStringExtra("content");
        init();
        initevent();
    }

    private void initevent() {
        mtop.setOnClickListener(new OnClickListener() {
            @Override
            public void OnLeftClick() {
                LikeShareItemActivity.this.finish();
            }

            @Override
            public void OncenterClick() {

            }

            @Override
            public void OnRightClick() {

            }
        });

    }

    private void init() {
        mtop = (MyCustomTopView)findViewById(R.id.likeshareitem_TopView);
        mtv = (TextView)findViewById(R.id.likeshareitem_tv);
        mtv.setText(content);
    }
}
