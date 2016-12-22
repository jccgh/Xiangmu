package jcc.xiangmu.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Set;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import jcc.xiangmu.R;
import jcc.xiangmu.module.been.LeftItem;
import jcc.xiangmu.module.been.ResultChannel;
import jcc.xiangmu.view.BaseActivity;
import jcc.xiangmu.view.LoginActivity;
import jcc.xiangmu.view.adapter.LeftListAdapter;
import jcc.xiangmu.view.adapter.NewsPagerAdapter;
import jcc.xiangmu.view.fragment.NewsFragment;

public class MainActivity extends BaseActivity{

    private DrawerLayout mdrawerlayout;
    private ActionBarDrawerToggle tog;
    private Toolbar mtoolbar;
    private TabLayout mtab;
    private ViewPager mpager;
    private Map<String, String> map;
    private NewsPagerAdapter adapter;
    private RelativeLayout mleftLayout;
    private ListView mleftLv;
    private TextView musertv;
    private int leftimgs[] = {R.drawable.menu_like_pressed,R.drawable.myspace_edit_pressed,
            R.drawable.o_lazhu,R.drawable.o_liwu,R.drawable.o_fahongbao};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this,"db5fd068b33b158716cc8c1f427e54c5");
        setContentView(R.layout.activity_main);
        //初始化控件
        init();
        initLeftlayout();
        setToolbar();
        initdata();
        //给控件设置监听事件
        setviewlistener();
        inituser();
    }

    private void inituser() {
        if(BmobUser.getCurrentUser()!=null){
            String user = (String) BmobUser.getObjectByKey("username");
            if (user != null) {
                musertv.setText(user);
            }

        }else{
            musertv.setText("请登录");

        }
    }

    private void initLeftlayout() {
        mleftLv = (ListView)findViewById(R.id.leftlayout_listview);
        View view = LayoutInflater.from(this).inflate(R.layout.leftheader,null,false);
        musertv = (TextView)view.findViewById(R.id.usertv);
        mleftLv.addHeaderView(view);
        List<LeftItem>list = new ArrayList<>();
        list.add(new LeftItem("收藏",leftimgs[0]));
        list.add(new LeftItem("笔记",leftimgs[1]));
        list.add(new LeftItem("我的分享",leftimgs[2]));
        list.add(new LeftItem("趣味功能",leftimgs[3]));
        list.add(new LeftItem("设置",leftimgs[4]));
        LeftListAdapter adapter = new LeftListAdapter(this,list);
        mleftLv.setAdapter(adapter);
        mleftLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1:
                        Intent intent = new Intent(MainActivity.this, LikeShareActivity.class);
                        intent.putExtra("value","我的收藏");
                        startActivity(intent);
                        overridePendingTransition(R.anim.right_enter,R.anim.leftexist);
                        break;
                    case 2:
                       startActivity(new Intent(MainActivity.this,NoteActivity.class));
                        overridePendingTransition(R.anim.right_enter,R.anim.leftexist);
                        break;
                    case 3:
                        Intent intent2 = new Intent(MainActivity.this, LikeShareActivity.class);
                        intent2.putExtra("value","我的分享");
                        startActivity(intent2);
                        overridePendingTransition(R.anim.right_enter,R.anim.leftexist);
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this,AnimtorActivity.class));
                        overridePendingTransition(R.anim.right_enter,R.anim.leftexist);
                        break;
                    case 5:
                        startActivity(new Intent(MainActivity.this,SetActivity.class));
                        overridePendingTransition(R.anim.right_enter,R.anim.leftexist);
                        break;
                }
            }
        });

    }

    private void initdata() {

        localread();
        if (map==null) {
            map = new HashMap<>();
            map.put("国内焦点","5572a108b3cdc86cf39001cd");
            map.put("国际焦点","5572a108b3cdc86cf39001ce");
            map.put("军事焦点","5572a108b3cdc86cf39001cf");
            map.put("财经焦点","5572a108b3cdc86cf39001d0");
            map.put("互联网焦点","5572a108b3cdc86cf39001d1");
            map.put("房产焦点","5572a108b3cdc86cf39001d2");
        }
        Set<String>set =  map.keySet();
        List<String>titles = new ArrayList<>();
        for(String title:set){
            titles.add(title);
        }
        List<Fragment>list = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            NewsFragment newsFragment = new NewsFragment();
            newsFragment.setchannelid(map.get(titles.get(i)));
            list.add(newsFragment);

        }
        adapter = new NewsPagerAdapter(getSupportFragmentManager(),list,titles);
        mtab.setupWithViewPager(mpager);
        mpager.setAdapter(adapter);
    }

    private void localread() {
        File file = new File(getExternalFilesDir(null).getAbsolutePath() + File.separator + "mainchannel.txt");
        if (file.exists()) {
            Log.d("TAG", "init: " + "文件存在");
            FileInputStream in = null;
            ObjectInputStream inputStream = null;
            try {
                in = new FileInputStream(file);
                inputStream = new ObjectInputStream(in);
                map = (Map<String, String>) inputStream.readObject();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void setToolbar() {
        mtoolbar.inflateMenu(R.menu.menu);
        //toolbar代替actionbar
        setSupportActionBar(mtoolbar);
        tog = new ActionBarDrawerToggle(this, mdrawerlayout, R.string.open_drawerlayout, R.string.close_drawerlayout);
        //给drawerlayout设置监听事件
        mdrawerlayout.addDrawerListener(tog);
        //让actionbar与toolbar同步
        tog.syncState();
        //设置actionbar顶部导航按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void setviewlistener() {
        //点击用户名的事件
        musertv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v;
                String text = tv.getText().toString();
                if(text.equals("请登录")){

                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent,2);
                    //第一个参数是即将打开的Activity的入场效果
                    //第二个参数是当前Activity的退出效果
                    overridePendingTransition(R.anim.right_enter,R.anim.leftexist);
                }else{
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("是否退出登录");
                    dialog.setNeutralButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           BmobUser.logOut();
                            inituser();
                        }
                    });
                    dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                }
            }
        });

        //给ToolBar上的菜单设置点击事件
        mtoolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.setting:
                        startActivity(new Intent(MainActivity.this,SetActivity.class));
                        overridePendingTransition(R.anim.right_enter,R.anim.leftexist);
                        break;
                    case R.id.help:
                        Toast.makeText(MainActivity.this, "联系我们", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.channel:
                        Intent intent = new Intent(MainActivity.this, ChannelActivity.class);
                        startActivityForResult(intent,1);
                        break;
                }
                return false;
            }
        });

    }

    private void init() {
        mdrawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        mtab = (TabLayout) findViewById(R.id.tablayout);
        mpager = (ViewPager) findViewById(R.id.mainviewpager);
        mleftLayout = (RelativeLayout)findViewById(R.id.leflayout);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if (mdrawerlayout.isDrawerOpen(Gravity.LEFT)) {
                    mdrawerlayout.closeDrawer(Gravity.LEFT);
                } else {
                    mdrawerlayout.openDrawer(Gravity.LEFT);
                }
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    //必须要重写这个方法，toolbar才能正常设置成功菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (mdrawerlayout.isDrawerOpen(Gravity.LEFT)) {
            mdrawerlayout.closeDrawer(Gravity.LEFT);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        inituser();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_OK){
            ResultChannel resultChannel = (ResultChannel) data.getExtras().getSerializable("result");
            map = resultChannel.getMap();
            localsave();
            initdata();
            Log.d("TAG", "onActivityResult: "+resultChannel.getMap().size());
        }else if(requestCode==2&&resultCode==2){
            String name = data.getStringExtra("user");
//            musertv.setText(name);
            inituser();
        }else if(requestCode==3&&resultCode==3){
            Log.d("TAG", "onActivityResult: "+data.getIntExtra("tag",5));
        }
    }

    private void localsave() {
        FileOutputStream out = null;
        ObjectOutputStream outputStream = null;
        try {
            out = new FileOutputStream(new File(getExternalFilesDir(null), "mainchannel.txt"));
            outputStream = new ObjectOutputStream(out);
            outputStream.writeObject(map);
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
    }

}
