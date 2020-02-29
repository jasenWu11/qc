package cn.com.qc.yinter;

import java.util.List;

import cn.com.qc.bean.CityBean;

/**
 * Created by lenovo on 2017/12/19.
 */

public interface EditIntroDataListener {

    void onCitySuccess(List<String> citys);

    void onCityError();

    void onUpImageError();

    void updateIntroSuccess(String infoCode,String message);

    void updateIntroError();

}
