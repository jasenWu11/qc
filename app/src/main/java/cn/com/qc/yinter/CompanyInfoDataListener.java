package cn.com.qc.yinter;

import cn.com.qc.bean.PositionBean;

/**
 * Created by lenovo on 2017/10/10.
 */

public interface CompanyInfoDataListener {

    void onSuccessCompanyInfo(PositionBean position);

    void onErrorCompanyInfo();

}
