package cn.com.qc.yinter;

import java.util.List;

import cn.com.qc.bean.SearchCompanyBean;
import cn.com.qc.bean.SearchConditionBean;
import cn.com.qc.bean.SearchPositionBean;

/**
 * Created by lenovo on 2017/12/13.
 */

public interface SearchDataListener extends BaseInter {

    void onSuccess(List<SearchCompanyBean.SearchData> datas);

    void onError();

    void onPosSuccess(List<SearchPositionBean.SearchPositionData> datas);

    void onConditionSuccess(List<SearchConditionBean.SearchCoditionData> datas);

}
