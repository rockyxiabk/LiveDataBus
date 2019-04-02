package com.rocky.livedatabus.liferecycer;

/**
 * Description : com.rocky.livedatabus.liferecycer
 *
 * @author : rocky
 * @Create Time : 2019/4/1 5:47 PM
 * @Modified Time : 2019/4/1 5:47 PM
 */
public interface LifecycleListener {
    public void onCreate(int code);

    public void onStart(int code);

    public void onPause(int code);

    public void onDestroy(int code);
}
