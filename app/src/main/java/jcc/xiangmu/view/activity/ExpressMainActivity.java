package jcc.xiangmu.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import jcc.mycustomtopview.MyCustomTopView;
import jcc.mycustomtopview.OnClickListener;
import jcc.xiangmu.R;
import jcc.xiangmu.view.BaseActivity;

public class ExpressMainActivity extends BaseActivity {

    private Button mbt;
    private EditText met;
    private Spinner msp;
    private String company;
    private String simpleName;
    private MyCustomTopView mtop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_main);
        init();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.expresscompany,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        msp.setAdapter(adapter);

        setlistener();
    }

    private void setlistener() {
        mtop.setOnClickListener(new OnClickListener() {
            @Override
            public void OnLeftClick() {
                ExpressMainActivity.this.finish();
            }

            @Override
            public void OncenterClick() {

            }

            @Override
            public void OnRightClick() {

            }
        });
        mbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpressMainActivity.this,ExpressResultActivity.class);
                setSimpleName();
                String no = met.getText().toString();
                intent.putExtra("simpleName",simpleName);
                intent.putExtra("no",no);
                startActivity(intent);
                overridePendingTransition(R.anim.right_enter,R.anim.leftexist);

            }
        });
        msp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                company = ((TextView)view).getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setSimpleName() {
        switch (company){
            case "申通快递":
                simpleName = "shentong";
                break;
            case "圆通速递":
                simpleName = "yuantong";
                break;
            case "韵达快运":
                simpleName = "yunda";
                break;
            case "中通快递":
                simpleName = "zhongtong";
                break;
            case "顺丰速运":
                simpleName = "shunfeng";
                break;
            case "百世快递":
                simpleName = "huitong";
                break;
            case "天天快递":
                simpleName = "tiantian";
                break;
            case "宅急送":
                simpleName = "zhaijisong";
                break;
            case "全峰快递":
                simpleName = "quanfeng";
                break;

            case "德邦物流":
                simpleName = "debang";
                break;
            case "AAE快递":
                simpleName = "aae";
                break;
            case ".......":
                simpleName = null;
                break;
            default:
                simpleName = null;
                break;
        }
    }

    private void init() {
        mbt = (Button)findViewById(R.id.bt_commit);
        met = (EditText)findViewById(R.id.edtitext);
        msp = (Spinner)findViewById(R.id.spinner);
        mtop = (MyCustomTopView)findViewById(R.id.express_TopView);
    }
}
