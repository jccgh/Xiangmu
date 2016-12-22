package jcc.xiangmu.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import jcc.mycustomtopview.MyCustomTopView;
import jcc.mycustomtopview.OnClickListener;
import jcc.xiangmu.R;
import jcc.xiangmu.module.been.NewsItemBeen;
import jcc.xiangmu.module.user.Like;
import jcc.xiangmu.module.user.MyUser;
import jcc.xiangmu.module.user.Share;

import static cn.bmob.v3.BmobUser.getCurrentUser;

public class NewsItemActivity extends AppCompatActivity {

    private TextView mtv;
    private String newsconten;
    private ImageView mshareimg;
    private ImageView mlikeimg;
    private ImageView minterentimg;
    private String newslink;
    private String source;
    private String title;
    private TextView msourcetv;
    private MyCustomTopView mtopview;
    private Handler mhandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_item);
        NewsItemBeen newsItemBeen = (NewsItemBeen) getIntent().getExtras().getSerializable("content");
        newsconten = newsItemBeen.getContent();
        newslink = newsItemBeen.getLink();
        source = newsItemBeen.getSource();
        title = newsItemBeen.getTitle();
        initview();
        initdata();
        initevent();
    }

    private void initevent() {
        mtopview.setOnClickListener(new OnClickListener() {
            @Override
            public void OnLeftClick() {
                NewsItemActivity.this.finish();
            }

            @Override
            public void OncenterClick() {

            }

            @Override
            public void OnRightClick() {

            }
        });
        mshareimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser user = BmobUser.getCurrentUser();
                if (user != null) {
                    showShare();
                    saveShare();
                } else {
                    Toast.makeText(NewsItemActivity.this, "请登录后再执行此操作", Toast.LENGTH_SHORT).show();
                }
            }
        });
        minterentimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsItemActivity.this, NewsInterLinkage.class);
                intent.putExtra("link", newslink);
                startActivity(intent);
                overridePendingTransition(R.anim.right_enter, R.anim.leftexist);
            }
        });
        mlikeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser user = BmobUser.getCurrentUser(MyUser.class);
                if (user != null) {
                    saveLike();
                } else {
                    Toast.makeText(NewsItemActivity.this, "请登录后再执行此操作", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveShare() {
        MyUser myUser = getCurrentUser(MyUser.class);
        Share share = new Share();
        share.setTitle(title);
        share.setContent(newsconten);
        share.setUser(myUser);
        share.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(NewsItemActivity.this, "已保存至我的分享", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NewsItemActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void saveLike() {
        MyUser myUser = getCurrentUser(MyUser.class);
        Like like = new Like();
        like.setTitle(title);
        like.setContent(newsconten);
        like.setUser(myUser);
        like.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    mlikeimg.setImageResource(R.drawable.menu_like_pressed);
                    Toast.makeText(NewsItemActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NewsItemActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showShare() {
        ShareSDK.initSDK(NewsItemActivity.this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("CC新闻");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(newslink);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(title);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(newslink);

// 启动分享GUI
        oks.show(NewsItemActivity.this);
    }

    private void initdata() {
        mtv.setText(newsconten);
        msourcetv.setText(source);


    }

    private void initview() {
        mtopview = (MyCustomTopView) findViewById(R.id.newsitem_TopView);
        mtv = (TextView) findViewById(R.id.news_item_tv);
        mlikeimg = (ImageView) findViewById(R.id.likeimg);
        mshareimg = (ImageView) findViewById(R.id.shareimg);
        minterentimg = (ImageView) findViewById(R.id.interentimg);
        msourcetv = (TextView) findViewById(R.id.newssourcetv);
        initmlikeimg();
    }

    private void initmlikeimg() {
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        if (myUser != null) {
            BmobQuery<Like> query = new BmobQuery<>();
            query.addWhereEqualTo("user", myUser);
            query.findObjects(new FindListener<Like>() {
                @Override
                public void done(List<Like> list, BmobException e) {
                    if (e == null) {
                        for (Like like : list) {
                            if (like.getTitle().equals(title)) {
                                mhandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mlikeimg.setImageResource(R.drawable.menu_like_pressed);

                                    }
                                });
                            }
                        }
                    } else {

                    }
                }
            });
        }
    }
}
