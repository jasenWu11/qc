package cn.com.qc.ypresenter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import cn.com.qc.bean.EditIntroBean;
import cn.com.qc.bean.ImageMoreUpBean;
import cn.com.qc.help.ServerApi;
import cn.com.qc.main.CardCheckActivity;
import cn.com.qc.view.CropRetangleTransformation;
import cn.com.qc.ymodel.CardCheckModel;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by lenovo on 2017/11/30.
 */

public class CardCheckPresenter extends BasePresenter<CardCheckActivity,CardCheckModel> {

    @Override
    public CardCheckModel getModel() {
        return new CardCheckModel();
    }

    public void intoLocalRetangle(ImageView imageView, String path,int width,int height){
        if(imageView == null){
            return;
        }
        Glide.with(v)
                .load(path)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .bitmapTransform(new CropRetangleTransformation(v))
                .override(width,height)
                .into(imageView);
    }

    public void upImageMore(final String url, final String frontPath, final String backPath){
        list.add(ServerApi.subscribe(new Observable.OnSubscribe<ImageMoreUpBean>() {
            @Override
            public void call(Subscriber<? super ImageMoreUpBean> subscriber) {
                m.upImageMore(subscriber,url,frontPath,backPath);
            }
        }, new Observer<ImageMoreUpBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                v.onError();
            }

            @Override
            public void onNext(ImageMoreUpBean data) {
                v.onUpImageMoreSuccess(data);
            }
        }));
    }

    public void commit(final String url, final String studentId, final String idCardImg1, final String idCardImg2){
        list.add(ServerApi.subscribe(new Observable.OnSubscribe<EditIntroBean>() {
            @Override
            public void call(Subscriber<? super EditIntroBean> subscriber) {
                m.commit(subscriber,url,studentId,idCardImg1,idCardImg2);
            }
        }, new Observer<EditIntroBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                v.onError();
            }

            @Override
            public void onNext(EditIntroBean data) {
                v.onCommitSuccess(data.getStatus(),data.getMsg());
            }
        }));
    }

}
