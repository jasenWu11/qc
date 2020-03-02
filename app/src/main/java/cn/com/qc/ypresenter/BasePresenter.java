package cn.com.qc.ypresenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import cn.com.qc.R;
import cn.com.qc.app.App;
import cn.com.qc.help.NetUrl;
import cn.com.qc.view.CropCircleTransformation;
import cn.com.qc.yinter.BaseInter;
import cn.com.qc.ymodel.BaseAbtractModel;
import cn.com.qc.ymodel.BaseModelInter;
import rx.Subscription;

/**
 * Created by lenovo on 2017/9/25.
 */

public abstract class BasePresenter<V extends BaseInter, M extends BaseAbtractModel> {

    public M m;

    V v;

    List<Subscription> list = new ArrayList<>();

    public abstract M getModel();

    public void attach(V v) {
        this.v = v;
        m = getModel();
    }

    public void deAttach() {
        if (v != null) {
            v = null;
        }
        OkGo.getInstance().cancelTag(m);
        for (Subscription s : list) {
            s.unsubscribe();
        }
    }


    /**
     * 加载图片，先判断是否获取到图片的前半段，若没有获取到，则开请求获取，否则直接获取
     *
     * @param context
     * @param url
     * @param imageView
     */
    public void intoImageView(Context context, String url, ImageView imageView, int id) {
        if (!App.isCompleteLoad) {
            getModel().getImgUrlHeader(NetUrl.ImgHeader, imageHeaderInfer, context, url, imageView, id);
        } else {
            into(context, url, imageView, id);
        }
    }

    ImageHeaderInfer imageHeaderInfer = new ImageHeaderInfer() {
        @Override
        public void success(Context context, String url, ImageView imageView, int id) {
            into(context, url, imageView, id);
        }

        @Override
        public void error() {

        }
    };

    public void into(Context context, String url, ImageView imageView, int id) {
        if(imageView == null){
            return;
        }
        Glide.with(context)
                .load(App.imgHeader + url).placeholder(id)
                .into(imageView);
    }

    public void intoCircle(Context context, String url, ImageView imageView, int id,int width,int height) {
        if(imageView == null){
            return;
        }
        Glide.with(context)
                .load(App.imgHeader + url).placeholder(id).bitmapTransform(new CropCircleTransformation(context))
                .override(width,height)
                .into(imageView);
    }

    public void intoLocal(Context context, String url, ImageView imageView, int id) {
        if(imageView == null){
            return;
        }
        Glide.with(context)
                .load(url).placeholder(id)
                .into(imageView);
    }

    public void intoLocalCicle(Context context, String url, ImageView imageView, int id,int width) {
        if(imageView == null){
            return;
        }
        Glide.with(context)
                .load(url).placeholder(id).bitmapTransform(new CropCircleTransformation(context))
                .override(width,width)
                .into(imageView);
    }

    public interface ImageHeaderInfer {
        void success(Context context, String url, ImageView imageView, int id);
        void error();
    }

    /**
     * 从SD卡中读取图片并压缩
     * @param path
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public Bitmap decodeBitmapFromSDCard(String path,int reqWidth,int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,options);
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;
        if(width > reqWidth||height > reqHeight){
            int widthRadio = Math.round((float)width/(float)reqWidth);
            int heightRadio = Math.round((float)height/(float)reqHeight);
            inSampleSize = widthRadio > heightRadio?widthRadio:heightRadio;
        }

        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path,options);
        return bitmap;
    }

    /**
     * 压缩图片到本地
     * @param bitmap
     * @param fileName
     * @param context
     */
    public String compressBitmapToLocal(Bitmap bitmap,String fileName,Context context){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG,quality,os);
        while (os.toByteArray().length/1024 > 1024){
            os.reset();
            quality -= 10;
            if(quality <= 0){
                break;
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG,quality,os);
        }
        String outPath = getOutPath(context,fileName);
        delete(outPath);
        FileOutputStream fs = null;
        try {
            fs = new FileOutputStream(outPath);
            fs.write(os.toByteArray());
            fs.flush();
            return outPath;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
                if(fs != null){
                    fs.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getOutPath(Context context,String fileName){
        String outPath;
        if(!Environment.isExternalStorageRemovable()||Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            outPath = context.getExternalCacheDir().getPath() + File.separator + fileName;
        }else{
            outPath = context.getCacheDir().getPath() + File.separator + fileName;
        }
        return outPath;
    }

    public void delete(String path){
        if(path == null){
            return;
        }
        File file = new File(path);
        if(file.exists()){
            file.delete();
        }
    }

}
