package com.rocky.livedatabus.livedata;

import android.support.annotation.NonNull;

/**
 * Description : com.rocky.livedatabus.livedata
 *
 * @author : rocky
 * @Create Time : 2019/4/1 5:04 PM
 * @Modified Time : 2019/4/1 5:04 PM
 */
public abstract class Observer<T> {
    public static final int STATE_ACTIVTY = 1;
    public static final int STATE_ONPAUSE = 2;
    public static final int STATE_ONDESTORY = 3;
    public static final int STATE_INIT = 0;

    private int state = STATE_INIT;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public abstract void onChange(@NonNull T t);
}
