package jcc.xiangmu.view.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import jcc.xiangmu.R;
import jcc.xiangmu.view.BaseActivity;

public class GameActivity extends BaseActivity {

    private SurfaceView msurface;
    private ImageView mplayfeiji;
    private int currentx;
    private int currenty;
    private boolean isruning = true;
    private int mplaywidth;
    private int pinmuwidth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initview();
        setplayermove();
        setzhuti();
    }

    private void setzhuti() {
        SurfaceHolder holder = msurface.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(final SurfaceHolder holder) {
                zidan(holder);
//                diji(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }
    private void zidan(final SurfaceHolder holder) {
        new Thread(new Runnable() {
            int i = 0;

            @Override
            public void run() {
                float pointx=mplayfeiji.getX();
                pinmuwidth = msurface.getMeasuredWidth();
                mplaywidth = mplayfeiji.getMeasuredWidth();
                while (isruning) {
                    Canvas canvas = holder.lockCanvas();

                    try {
                        Paint paint = new Paint();
                        paint.setAntiAlias(true);
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.zidan);
                        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.diji);

                        //获取飞机的位置作为子弹发射的位置
                        float zidanx = mplayfeiji.getX();
                        Log.d("TAG", "onTouch: x"+mplayfeiji.getX());

                        float zidany = mplayfeiji.getY();
                        i++;
                        //绘制子弹
//                        if(pointx!=zidanx){
//                            i=1;
//                        }
                        canvas.drawBitmap(bitmap, zidanx + mplaywidth / 2 - 15, zidany - 10 * i, paint);
                        canvas.drawBitmap(bitmap2, pinmuwidth / 2, 40 * i, paint);

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        holder.unlockCanvasAndPost(canvas);
                    }
                    SystemClock.sleep(100);
                }

            }
        }).start();
    }
    private void diji(final SurfaceHolder holder) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (isruning) {
                    Canvas canvas = holder.lockCanvas();
                    try {
                        Paint paint = new Paint();
                        paint.setAntiAlias(true);
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.diji);

                        canvas.drawBitmap(bitmap, pinmuwidth / 2, 40 * i, paint);
                        i++;

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        holder.unlockCanvasAndPost(canvas);
                    }
                    SystemClock.sleep(1000);
                }
            }
        }).start();
    }


    private void setplayermove() {
        mplayfeiji.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        currentx = (int) event.getX();
                        currenty = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        ((ViewGroup)v.getParent()).scrollBy(currentx - (int) event.getX(),
                                currenty - (int) event.getY());
                        mplayfeiji.setX(event.getX());
                        mplayfeiji.setY(event.getY());
                        break;
                    case MotionEvent.ACTION_UP:
                        mplayfeiji.setX(event.getX());
                        mplayfeiji.setY(event.getY());
                        Log.d("TAG", "onTouch: x"+mplayfeiji.getX());
                        break;

                }
                return true;
            }
        });
    }

    private void initview() {
        msurface = (SurfaceView) findViewById(R.id.surfaceview);
        mplayfeiji = (ImageView) findViewById(R.id.userfeiji);
    }

    @Override
    public void onBackPressed() {
        isruning = false;
        super.onBackPressed();
    }
}
