package jcc.xiangmu.view.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import jcc.mycustomtopview.MyCustomTopView;
import jcc.mycustomtopview.OnClickListener;
import jcc.xiangmu.R;
import jcc.xiangmu.module.http.MyOkhttp;
import jcc.xiangmu.module.parsejson.Parsejson;
import jcc.xiangmu.module.been.ExpressBeen;
import jcc.xiangmu.view.BaseActivity;
import jcc.xiangmu.view.adapter.MyExpressAdapter;

public class ExpressResultActivity extends BaseActivity {

    private ListView mlv;
    private ImageView mim;
    private TextView mtv;
    private String simpleName;
    private String no;
    private EventBus eventbus;
    private MyCustomTopView mtop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_result);
        eventbus = EventBus.getDefault();
        eventbus.register(this);
        init();

        simpleName = getIntent().getStringExtra("simpleName");
        no = getIntent().getStringExtra("no");
        new MyOkhttp().json(simpleName, no);
        initevent();
    }

    private void initevent() {
        mtop.setOnClickListener(new OnClickListener() {
            @Override
            public void OnLeftClick() {
                ExpressResultActivity.this.finish();
            }

            @Override
            public void OncenterClick() {

            }

            @Override
            public void OnRightClick() {

            }
        });
    }

    //如果网络访问成功，将发送广播回调此方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initListView(String json) {
        if (json != null) {
            List<ExpressBeen> list = new Parsejson().parse(json);
            if (list != null) {

                if (list.size()>0) {
                    MyExpressAdapter adapter = new MyExpressAdapter(list, this);
                    mlv.setAdapter(adapter);
                }else{
                    Toast.makeText(this, "输入信息错误或者快递尚未发出", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "请检查输入信息是否有误", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "网络出问题了", Toast.LENGTH_SHORT).show();
        }

    }

    private void init() {
        mlv = (ListView) findViewById(R.id.listview_expressresult);
        mim = (ImageView) findViewById(R.id.image_expressresult);
        mtv = (TextView) findViewById(R.id.tv_expressresult);
        mtop = (MyCustomTopView)findViewById(R.id.expressresult_TopView);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventbus.unregister(this);
    }
}
