package cn.com.qc.ypresenter;

import cn.com.qc.main.InformationActivity;
import cn.com.qc.ymodel.InformationModel;

/**
 * Created by lenovo on 2017/10/13.
 */

public class InformationPresenter extends BasePresenter<InformationActivity,InformationModel> {

    @Override
    public InformationModel getModel() {
        return new InformationModel();
    }

}
