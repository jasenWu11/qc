package cn.com.qc.yinter;

import java.util.List;

import cn.com.qc.bean.FindHotCompany;
import cn.com.qc.bean.FindHotInfo;

/**
 * Created by lenovo on 2017/10/17.
 */

public interface FindDataListener {

    void onSuccessHotCompany(FindHotCompany.FindData datas);

    void onError();

}
