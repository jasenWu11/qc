package cn.com.qc.yinter;

/**
 * Created by lenovo on 2017/9/28.
 */

public interface SwipeTrigger {
    void onPrepare();

    void onMove(int var1, boolean var2, boolean var3);

    void onRelease();

    void onComplete();

    void onReset();
}
