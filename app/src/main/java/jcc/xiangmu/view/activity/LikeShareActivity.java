package jcc.xiangmu.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import jcc.mycustomtopview.MyCustomTopView;
import jcc.mycustomtopview.OnClickListener;
import jcc.xiangmu.R;
import jcc.xiangmu.module.been.LikeShareBeen;
import jcc.xiangmu.module.user.Like;
import jcc.xiangmu.module.user.MyUser;
import jcc.xiangmu.module.user.Share;
import jcc.xiangmu.view.activity.minterface.IsetOnLongClick;
import jcc.xiangmu.view.adapter.LikeAdapter;

public class LikeShareActivity extends AppCompatActivity implements IsetOnLongClick {

    private RecyclerView mrv;
    private MyCustomTopView mtop;
    private String toptitle;
    private List<LikeShareBeen> mlist;
    private LikeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        toptitle = getIntent().getStringExtra("value");
        initview();
        initdata();
        initevent();
    }

    private void initdata() {
        mlist = new ArrayList<>();

        mtop.setcenterTextTitle(toptitle);
        if (toptitle.equals("我的收藏")) {
            querylike();

        } else if (toptitle.equals("我的分享")) {
            queryshare();
        }
        adapter = new LikeAdapter(this, mlist, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mrv.setLayoutManager(manager);
        mrv.setAdapter(adapter);
    }

    private void queryshare() {
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        if(myUser==null){
            return;
        }
        BmobQuery<Share> query = new BmobQuery<>();
        query.addWhereEqualTo("user", myUser);
        query.order("-updatedAt");
        query.findObjects(new FindListener<Share>() {
            @Override
            public void done(List<Share> list, BmobException e) {
                if (e == null) {
                    for (Share share : list) {
                        String title = share.getTitle();
                        String content = share.getContent();
                        mlist.add(new LikeShareBeen(title,content));
                        adapter.notifyDataSetChanged();
                    }
                } else {

                }
            }
        });
    }

    private void querylike() {
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        if(myUser==null){
            return;
        }
        BmobQuery<Like> query = new BmobQuery<>();
        query.addWhereEqualTo("user", myUser);
        query.order("-updatedAt");
        query.findObjects(new FindListener<Like>() {
            @Override
            public void done(List<Like> list, BmobException e) {
                if (e == null) {
                    for (Like like : list) {
                        String title = like.getTitle();
                        String content = like.getContent();
                        mlist.add(new LikeShareBeen(title,content));
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Log.d("TAG", "done: " + "查询失败");
                }
            }
        });
    }

    private void initevent() {
        mtop.setOnClickListener(new OnClickListener() {
            @Override
            public void OnLeftClick() {
                LikeShareActivity.this.finish();
            }

            @Override
            public void OncenterClick() {

            }

            @Override
            public void OnRightClick() {

            }
        });
    }

    private void initview() {
        mrv = (RecyclerView) findViewById(R.id.like_recyclerview);
        mtop = (MyCustomTopView) findViewById(R.id.like_TopView);

    }

    @Override
    public void onLongclick(final int position) {
        Log.d("TAG", "activity*onLongclick: *********"+position);
        if (toptitle.equals("我的收藏")) {
            MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
            BmobQuery<Like> query = new BmobQuery<>();
            query.addWhereEqualTo("user", myUser);
            query.findObjects(new FindListener<Like>() {
                @Override
                public void done(List<Like> list, BmobException e) {
                    if (e == null) {
                        Log.d("TAG", "done: ");
                        for (Like like : list) {
                            if (like.getTitle().equals(mlist.get(position).getTitle())) {
                                Log.d("TAG", "like*done: "+like.getTitle());
                                Log.d("TAG", "done: mlisst"+mlist.get(position).getTitle());
                                String objectId = like.getObjectId();
                                deletelike(objectId,position);
                            }
                        }
                    } else {
                        Toast.makeText(LikeShareActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();

                    }
                }
            });

        } else if (toptitle.equals("我的分享")) {
            Log.d("TAG", "onLongclick: **********");
            MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
            BmobQuery<Share>query = new BmobQuery<>();
            query.addWhereEqualTo("user",myUser);
            query.findObjects(new FindListener<Share>() {
                @Override
                public void done(List<Share> list, BmobException e) {
                    if(e==null){
                        for(Share share:list){
                            if(share.getTitle().equals(mlist.get(position).getTitle())){
                                String objectId = share.getObjectId();
                                deleteshare(objectId,position);
                            }
                        }
                    }else {
                        Toast.makeText(LikeShareActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

    }

    private void deleteshare(String objectId, final int position) {
        Share share = new Share();
        share.setObjectId(objectId);
        share.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    mlist.remove(position);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(LikeShareActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LikeShareActivity.this,"删除失败",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void deletelike(String objectId, final int position) {
        Log.d("TAG", "deletelike: ");
        Like like = new Like();
        like.setObjectId(objectId);
        like.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    mlist.remove(position);
                    Log.d("TAG", "done: remove");
                    adapter.notifyDataSetChanged();
                    Toast.makeText(LikeShareActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                }else{

                    Toast.makeText(LikeShareActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
