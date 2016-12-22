package jcc.xiangmu.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import jcc.mycustomtopview.MyCustomTopView;
import jcc.mycustomtopview.OnClickListener;
import jcc.xiangmu.R;

public class AboveActivity extends AppCompatActivity {

    private MyCustomTopView mtop;
    private TextView mtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_above);
        initview();
        initdata();
        initevent();
    }

    private void initevent() {
        mtop.setOnClickListener(new OnClickListener() {
            @Override
            public void OnLeftClick() {
                finish();
            }

            @Override
            public void OncenterClick() {

            }

            @Override
            public void OnRightClick() {

            }
        });
    }

    private void initdata() {
        mtv.setText("生命不息，奋斗不息");
    }

    private void initview() {
        mtop = (MyCustomTopView)findViewById(R.id.above_mycustomtoview);
        mtv = (TextView)findViewById(R.id.above_tv);
    }
}
