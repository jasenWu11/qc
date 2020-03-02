package cn.com.qc.ypresenter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.com.qc.bean.EditIntroBean;
import cn.com.qc.bean.MyIntroductionBean;
import cn.com.qc.fragment.PersonFragment;
import cn.com.qc.help.ServerApi;
import cn.com.qc.view.CropCircleTransformation;
import cn.com.qc.ymodel.PersonModel;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by lenovo on 2017/12/22.
 */

public class PersonPresener extends BasePresenter<PersonFragment,PersonModel> {

    @Override
    public PersonModel getModel() {
        return new PersonModel();
    }

    public void logout(final String url){
        Subscription subscription = ServerApi.subscribe(new Observable.OnSubscribe<EditIntroBean>() {
            @Override
            public void call(Subscriber<? super EditIntroBean> subscriber) {
                m.logout(subscriber,url);
            }
        }, new Observer<EditIntroBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                v.onLogoutError();
            }

            @Override
            public void onNext(EditIntroBean introductionBean) {
                v.onLogoutSuccess(introductionBean.getStatus(),introductionBean.getMsg());
            }
        });
        list.add(subscription);
    }

    public void intoCircle(Context context, String url, ImageView imageView, int id, int width, int height) {
        if(imageView == null){
            return;
        }
        Glide.with(context)
                .load(url).placeholder(id).bitmapTransform(new CropCircleTransformation(context))
                .override(width,height)
                .into(imageView);
    }

}
