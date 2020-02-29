package cn.com.qc.ymodel;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.com.qc.bean.EditIntroBean;
import cn.com.qc.bean.ImageMoreUpBean;
import cn.com.qc.help.JsonCallBack;
import rx.Subscriber;

/**
 * Created by lenovo on 2017/11/30.
 */

public class CardCheckModel extends BaseAbtractModel {

    public void upImageMore(final Subscriber subscriber, String url, String frontPath, String backPath){
        List<File> files = addFile(backPath,addFile(frontPath,new ArrayList<File>()));
        OkGo.<ImageMoreUpBean>post(url)
                .tag(this)
                .headers("device","1")
                .addFileParams("files",files)
                .execute(new JsonCallBack<ImageMoreUpBean>() {
                    @Override
                    public void onSuccess(Response<ImageMoreUpBean> response) {
                        subscriber.onNext(response.body());
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Response<ImageMoreUpBean> response) {
                        subscriber.onError(response.getException());
                    }
                });
    }

    private List<File> addFile(String path,List<File> files){
        File file = new File(path);
        if(file.exists()){
            files.add(file);
        }
        return files;
    }

    public void commit(final Subscriber subscriber, String url,String studentId,String idCardImg1,String idCardImg2){
        OkGo.<EditIntroBean>post(url)
                .tag(this)
                .headers("device","1")
                .params("studentId",studentId)
                .params("idCardImg1",idCardImg1)
                .params("idCardImg2",idCardImg2)
                .execute(new JsonCallBack<EditIntroBean>() {
                    @Override
                    public void onSuccess(Response<EditIntroBean> response) {
                        subscriber.onNext(response.body());
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Response<EditIntroBean> response) {
                        subscriber.onError(response.getException());
                    }
                });
    }

}
