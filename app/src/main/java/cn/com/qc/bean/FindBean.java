package cn.com.qc.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/10/16.
 */

public class FindBean<T> {

    /**
     * 1.标题 2.热点公司 3.热点资讯
     */
    int type;

    T t;

    public FindBean(int type) {
        this.type = type;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public FindBean(int type, T t) {
        this.type = type;
        this.t = t;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
