package jcc.xiangmu.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jcc.mycustomtopview.MyCustomTopView;
import jcc.mycustomtopview.OnClickListener;
import jcc.xiangmu.R;
import jcc.xiangmu.module.been.ChannelBeen;
import jcc.xiangmu.module.been.ResultChannel;
import jcc.xiangmu.progress.ChannelProgress;
import jcc.xiangmu.view.BaseActivity;
import jcc.xiangmu.view.activity.minterface.Iupdataview;

public class ChannelActivity extends BaseActivity implements Iupdataview {

    private FlexboxLayout mfbox1;
    private FlexboxLayout mfbox2;
    private ChannelProgress progress;
    private Handler mhandler = new Handler();
    private List<ChannelBeen> mlist = new ArrayList<>();
    private MyCustomTopView mtopView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        progress = new ChannelProgress(this, this);
        init();
        initevent();
    }

    private void initevent() {
        mtopView.setOnClickListener(new OnClickListener() {
            @Override
            public void OnLeftClick() {
                resultData();
                ChannelActivity.this.finish();
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
        mtopView = (MyCustomTopView) findViewById(R.id.channel_TopView);
        mfbox1 = (FlexboxLayout) findViewById(R.id.flexboxlayout1);
        mfbox2 = (FlexboxLayout) findViewById(R.id.flexboxlayout2);
        File file = new File(getExternalFilesDir(null).getAbsolutePath() + File.separator + "channel.txt");
        if (file.exists()) {
            Log.d("TAG", "init: "+"文件存在");
            FileInputStream in = null;
            ObjectInputStream inputStream = null;
            try {
                in = new FileInputStream(file);
                inputStream = new ObjectInputStream(in);
                mlist = (List<ChannelBeen>) inputStream.readObject();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }finally {
                if(inputStream!=null){
                    try {
                        inputStream.close();
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            setdata(mlist);

        }else {
            progress.start(ChannelActivity.this);

        }

    }

    @Override
    public void updataview(final List<ChannelBeen> list) {
        for (int i = 0; i < list.size(); i++) {

            ChannelBeen been = list.get(i);
            if (i < 6) {
                been.setState(true);
            } else {
                been.setState(false);
            }
        }
        mhandler.post(new Runnable() {
            @Override
            public void run() {

                setdata(list);
            }
        });


    }

    private void setdata(final List<ChannelBeen> list) {
        for (int i = 0; i < list.size(); i++) {
            final ChannelBeen been = list.get(i);
            TextView tv = new TextView(ChannelActivity.this);
            tv.setText(been.getName());
            tv.setPadding(4, 8, 4, 8);
            tv.setBackgroundColor(Color.WHITE);
            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 4, 8, 4);
            tv.setLayoutParams(params);
            if (been.getState()) {
                tv.setTextColor(Color.RED);
                mfbox1.addView(tv);
            } else {
                mfbox2.addView(tv);
                tv.setTextColor(Color.BLACK);
            }
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView textview = (TextView) v;
                    for (int i = 0; i < list.size(); i++) {
                        ChannelBeen channelBeen = list.get(i);
                        if (channelBeen.getName().equals(textview.getText().toString())) {
                            if (i >= 6) {
                                if (channelBeen.getState()) {
                                    channelBeen.setState(false);
                                    mfbox1.removeView(textview);
                                    mfbox2.addView(textview);
                                    textview.setTextColor(Color.BLACK);
                                } else {
                                    channelBeen.setState(true);
                                    mfbox2.removeView(textview);
                                    mfbox1.addView(textview);
                                    textview.setTextColor(Color.RED);
                                }
                            } else {
                                Toast.makeText(ChannelActivity.this, "重要新闻不能删除", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        }
                    }

                }
            });
        }
        mlist = list;
    }

    @Override
    public void onBackPressed() {
        resultData();
        super.onBackPressed();
        this.finish();
    }

    private void resultData() {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < mlist.size(); i++) {
            ChannelBeen channelBeen = mlist.get(i);
            if (channelBeen.getState()) {
                map.put(channelBeen.getName(), channelBeen.getChannelId());
            }
        }
        FileOutputStream out = null;
        ObjectOutputStream outputStream = null;
        try {
            out = new FileOutputStream(new File(getExternalFilesDir(null), "channel.txt"));
            outputStream = new ObjectOutputStream(out);
            outputStream.writeObject(mlist);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        ResultChannel resultChannel = new ResultChannel(map);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("result", resultChannel);
        intent.putExtras(bundle);
        this.setResult(RESULT_OK, intent);
    }

}

