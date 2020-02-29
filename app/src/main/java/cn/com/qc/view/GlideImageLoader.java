package cn.com.qc.view;

import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.imagepicker.loader.ImageLoader;

import java.io.File;

import cn.com.qc.R;

/**
 * Created by lenovo on 2017/4/14.
 */

public class GlideImageLoader implements ImageLoader{

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity).load(new File(path)).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESULT).placeholder(R.color.black).error(R.color.black).into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }

}
