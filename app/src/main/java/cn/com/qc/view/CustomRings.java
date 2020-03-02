package cn.com.qc.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by lenovo on 2017/4/7.
 */

public class CustomRings extends View {

    Paint paint;

    Context context;

    int width;

    int[] colorArray;

    public CustomRings(Context context) {
        this(context, null);
    }

    public CustomRings(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRings(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        colorArray = new int[]{0xffeeeeee,0xffcccccc,0xffaaaaaa,0xff888888
                ,0xff666666,0xff444444,0xff222222,0xff000000};

        paint = new Paint();
        width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,6,getResources().getDisplayMetrics());
        setS();
    }

    private int getColor(int colorId){
        Resources res = context.getResources();
        return res.getColor(colorId);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setAntiAlias(true);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(width);

        int center = getWidth() / 2;
        int radius = center - width / 2;
        drawOval(canvas, radius, center);
    }

    public void drawOval(Canvas canvas, int radius, int center) {
        //8
        RectF bottemOval = new RectF(center - radius, center - radius, radius + center, center + radius);
        //
        float speed1 = 10;
        //
        float speed2 = 35;

        RectF oval = new RectF(center - radius, center - radius, radius + center, center + radius);
        for (int i = 7; i >= 0; i--) {
            paint.setColor(colorArray[i]);
            canvas.drawArc(oval, i * (speed1 + speed2) + current * (speed1 + speed2), speed1, false, paint);
        }

    }

    Timer timer = new Timer();
    int current = 0;

    public void setS() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(isStop){
                    return;
                }
                current++;
                postInvalidate();
            }
        }, 80, 80);
    }

    boolean isStop = false;

    public void stop(){
        isStop = true;
    }

}

