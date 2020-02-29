package cn.com.qc.yinter;

import java.util.List;

import cn.com.qc.bean.HomeBean;
import cn.com.qc.bean.HomeReturnDataBean;

/**
 * 首页获取数据的回调接口
 * Created by lenovo on 2017/9/30.
 */

public interface HomeDataListener {

    void onSuccessReturnAllData(List<HomeBean> HomeBean);

    void onErrorReturnAllData();

 //   void onSuccessReturnInfo(List<HomeCompany> homeCompanyList);

 //   void onErrorReturnInfo();

}



