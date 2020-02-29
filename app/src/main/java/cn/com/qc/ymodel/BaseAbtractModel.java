package cn.com.qc.ymodel;

import android.content.Context;
import android.support.annotation.IdRes;
import android.widget.ImageView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import cn.com.qc.app.App;
import cn.com.qc.bean.ImageUrlHeaderBean;
import cn.com.qc.help.JsonCallBack;
import cn.com.qc.ypresenter.BasePresenter;

/**
 * Created by lenovo on 2017/9/25.
 */

public abstract class BaseAbtractModel implements BaseModelInter {

    /**
     * 获取图片的前半部分URL
     *
     * @param imgUrlHeader
     * @param url
     */
    public void getImgUrlHeader(String imgUrlHeader, final BasePresenter.ImageHeaderInfer imageHeaderInfer, final Context context, final String url, final ImageView imageView, @IdRes final int id) {
        OkGo.<ImageUrlHeaderBean>post(imgUrlHeader)
                .headers("device", "1")
                .execute(new JsonCallBack<ImageUrlHeaderBean>() {
                    @Override
                    public void onSuccess(Response<ImageUrlHeaderBean> response) {
                        ImageUrlHeaderBean bean = response.body();
                        if (bean != null) {
                            String path = bean.getBasePath();
                            App.isCompleteLoad = true;
                            App.imgHeader = path;
                            if(imageHeaderInfer != null&&imageView != null){
                                imageHeaderInfer.success(context, url, imageView,id);
                            }
                        }
                    }

                    @Override
                    public void onError(Response<ImageUrlHeaderBean> response) {
                        if(imageHeaderInfer != null){
                            imageHeaderInfer.error();
                        }
                    }
                });
    }

}
