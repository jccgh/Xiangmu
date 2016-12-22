package jcc.mycutomedittext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.EditText;

/**
 * Created by Administrator on 2016/11/2.
 */

public class MyCustomEditText extends EditText {

    private Paint paint;
    private int padding=30;

    public MyCustomEditText(Context context) {
        this(context, null);
    }

    public MyCustomEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.GRAY);
        setFocusableInTouchMode(true);
        setBackgroundResource(R.drawable.background);
        setTextSize(18);
        setTextColor(Color.BLACK);
        setGravity(Gravity.TOP);
        setPadding(padding,0,padding,0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int lineheight = getLineHeight();
        int pagcount = height/lineheight;
        //画版设置的颜色将覆盖掉构造方法中所设置的背景
//        canvas.drawColor(Color.WHITE);
        for (int i = 1; i <= pagcount; i++) {

            canvas.drawLine(padding, lineheight*i+1, width-padding, lineheight*i+1, paint);

        }
        int linecount = getLineCount();
        if(linecount>pagcount){
            for(int i = pagcount+1;i<=linecount;i++){
                canvas.drawLine(padding, lineheight*i+1, width-padding, lineheight*i+1, paint);

            }
        }
    }
}
