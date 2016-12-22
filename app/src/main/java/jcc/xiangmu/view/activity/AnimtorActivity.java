package jcc.xiangmu.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import jcc.mycustomtopview.MyCustomTopView;
import jcc.mycustomtopview.OnClickListener;
import jcc.xiangmu.R;
import jcc.xiangmu.view.BaseActivity;

public class AnimtorActivity extends BaseActivity {

    private ImageView mimgcenter;
    private RelativeLayout mRlayout;
    private ImageView mexpress;
    private float currenpointY;
    private float currenpoingX;
    private MyCustomTopView mtop;
    private ImageView mgame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animtor);


        initView();

        ObjectAnimator animator = ObjectAnimator.ofFloat(mRlayout,"rotation",0f,360f);
        animator.setDuration(25000);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
        initevent();
    }

    private void initevent() {
        mtop.setOnClickListener(new OnClickListener() {
            @Override
            public void OnLeftClick() {
                AnimtorActivity.this.finish();
            }

            @Override
            public void OncenterClick() {

            }

            @Override
            public void OnRightClick() {

            }
        });
    }

    private void initView() {
        mimgcenter = (ImageView) findViewById(R.id.imageviewCenter);
        mRlayout = (RelativeLayout) findViewById(R.id.centerRelativelayout);
        mexpress = (ImageView) findViewById(R.id.express);
        mgame = (ImageView)findViewById(R.id.game);
        mtop = (MyCustomTopView)findViewById(R.id.enjoy_TopView);
        mimgcenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AnimtorActivity.this,"中心图片",Toast.LENGTH_SHORT).show();
            }
        });
        mexpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mexpress.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
                addAnimaltor(mexpress, ExpressMainActivity.class,71,71);

            }

        });
        mgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mgame.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
//                addAnimaltor(mgame,GameActivity.class,-71,-71);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mexpress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        mgame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

    //设置动画
    private void addAnimaltor(final ImageView img, final Class<?> cls,float x,float y) {
        currenpoingX = img.getTranslationX();
        currenpointY = img.getTranslationY();
        //原本是71，加了18
        ObjectAnimator animator_test_translationx = ObjectAnimator.ofFloat(img,"translationX", currenpoingX,x);
        ObjectAnimator anitator_test_translationy = ObjectAnimator.ofFloat(img,"translationY", currenpointY,y);
        ObjectAnimator animator_test_scaleY = ObjectAnimator.ofFloat(img,"scaleY",1f,2f);
        ObjectAnimator animator_test_scaleX = ObjectAnimator.ofFloat(img,"scaleX",1f,2f);
        AnimatorSet set = new AnimatorSet();
        set.play(animator_test_scaleX).with(animator_test_scaleY).after(animator_test_translationx).after(anitator_test_translationy);
        set.setDuration(1000);
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                img.setTranslationX(currenpoingX);
                img.setTranslationY(currenpointY);
                img.setScaleX(1f);
                img.setScaleY(1f);
                startActivity(new Intent(AnimtorActivity.this,cls));

            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });

    }
}
