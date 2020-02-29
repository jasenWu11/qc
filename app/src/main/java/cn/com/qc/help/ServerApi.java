package cn.com.qc.help;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2017/10/9.
 */

public class ServerApi {

    public static  <T> Observable<T> getObservable(Observable.OnSubscribe<T> onSubscribe){
        return Observable.create(onSubscribe).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static  <T> Subscription subscribe(Observable.OnSubscribe<T> onSubscribe, Observer<T> observer){
        return getObservable(onSubscribe).subscribe(observer);
    }

}



