package cn.com.qc.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by lenovo on 2017/12/26.
 */

public class CardImageView extends ImageView {

    public CardImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setScaleType(ScaleType.CENTER);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
    }

    public void setBitmap(String path,int reqWidth,int reqHeight){
        this.path = path;
        this.reqWidth = reqWidth;
        this.reqHeight = reqHeight;
    //    invalidate();//不会调用
        getBitmap();
    }

    String path;

    int reqWidth;

    int reqHeight;

    Bitmap bitmap;

    public void getBitmap(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                paint = new Paint();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(path,options);
                int width = options.outWidth;
                int height = options.outHeight;
                int radio = 1;
                if(width < height){
                    int c = width;
                    width = height;
                    height = c;
                    if(width > reqWidth||height > reqHeight){
                        float wRadio = (float)width/(float)reqWidth;
                        float hRadio = (float)height/(float)reqHeight;
                        radio = (int) (wRadio > hRadio?wRadio:hRadio);
                    }
                    options.inJustDecodeBounds = false;
                    options.inSampleSize = radio;
                    bitmap = BitmapFactory.decodeFile(path,options);
                    Matrix matrix = new Matrix();
                    if(options.outWidth < options.outHeight){
                        matrix.postRotate(90);
                    }
                    if(width < reqWidth&&height < reqHeight){
                        float wRadio = (float)reqWidth/(float)width;
                        float hRadio = (float)reqHeight/(float)height;
                        float sRadio = wRadio > hRadio?hRadio:wRadio;
                        matrix.postScale(sRadio,sRadio);
                        bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix, true);
                    }else{
                        bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix, true);
                    }
                }else{
                    if(width > reqWidth||height > reqHeight){
                        float wRadio = (float)width/(float)reqWidth;
                        float hRadio = (float)height/(float)reqHeight;
                        radio = (int) (wRadio > hRadio?wRadio:hRadio);
                    }
                    options.inJustDecodeBounds = false;
                    options.inSampleSize = radio;
                    bitmap = BitmapFactory.decodeFile(path,options);

                    if(width < reqWidth&&height < reqHeight){
                        float wRadio = (float)reqWidth/(float)width;
                        float hRadio = (float)reqHeight/(float)height;
                        float sRadio = wRadio > hRadio?hRadio:wRadio;
                        Matrix matrix = new Matrix();
                        matrix.postScale(sRadio,sRadio);
                        bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix, true);
                    }
                }
                paint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

                post(new Runnable() {
                    @Override
                    public void run() {
                        setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
    }

    Paint paint;

    @Override
    protected void onDraw(Canvas canvas) {
    //    super.onDraw(canvas);
        if(path == null){
            return;
        }
        drawF(canvas);
    }

    private void drawF(Canvas canvas){
        RectF rectF = new RectF(getWidth()/2 - reqWidth/2,getHeight()/2 - reqHeight/2,getWidth()/2 + reqWidth/2,
                getHeight()/2 + reqHeight/2);
        canvas.drawRect(rectF,paint);
    }

}
