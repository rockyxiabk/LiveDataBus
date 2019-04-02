package com.rocky.livedatabus.livedata;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Handler;
import android.os.Looper;

import com.rocky.livedatabus.liferecycer.LifecycleListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description : com.rocky.livedatabus.livedata
 *
 * @author : rocky
 * @Create Time : 2019/4/1 5:03 PM
 * @Modified Time : 2019/4/1 5:03 PM
 */
public class LiveData<T> {
    private HashMap<Integer, Observer<T>> map = new HashMap<>();
    private HashMap<Integer,List<T>> mPendingDelayMap=new HashMap<>();
    Handler handler = new Handler(Looper.getMainLooper());

    public void postValue(final T t) {
        synchronized (this) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    setValue(t);
                }
            });
        }
    }

    public void setValue(T t) {
        List<Observer> observerList=new ArrayList<>();
        for (Map.Entry<Integer, Observer<T>> item : map.entrySet()) {
            Integer activityCode = item.getKey();
            Observer<T> itemValue = item.getValue();
            if (itemValue.getState() == Observer.STATE_ACTIVTY) {
                itemValue.onChange(t);
            }
            if (itemValue.getState()==Observer.STATE_ONPAUSE){
                if (mPendingDelayMap.get(activityCode)==null){
                    mPendingDelayMap.put(activityCode,new ArrayList<T>());
                }
                if (!mPendingDelayMap.get(activityCode).contains(t)){
                    mPendingDelayMap.get(activityCode).add(t);
                }
            }
            if (itemValue.getState()==Observer.STATE_ONDESTORY){
                observerList.add(itemValue);
            }
        }
        for (int i = 0; i < observerList.size(); i++) {
            map.remove(observerList.get(i));
        }
    }

    public void observe(Activity activity, Observer<T> observer) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        HolderFragment fragment = ((HolderFragment) fragmentManager.findFragmentByTag("com.rocky.livedata"));
        if (null == fragment) {
            fragment = new HolderFragment();
            fragmentManager.beginTransaction().add(fragment, "com.rocky.livedata").commitAllowingStateLoss();
        }
        fragment.setLifecycleListener(lifecycleListener);
        map.put(activity.hashCode(), observer);
    }

    private LifecycleListener lifecycleListener = new LifecycleListener() {
        @Override
        public void onCreate(int code) {
            map.get(code).setState(Observer.STATE_INIT);
        }

        @Override
        public void onStart(int code) {
            map.get(code).setState(Observer.STATE_ACTIVTY);
            if (null==mPendingDelayMap.get(code)||mPendingDelayMap.get(code).size()==0){
                return;
            }
            List<T> tList = mPendingDelayMap.get(code);
            for (int i = 0; i < tList.size(); i++) {
                map.get(code).onChange(tList.get(i));
            }
            mPendingDelayMap.get(code).clear();
        }

        @Override
        public void onPause(int code) {
            map.get(code).setState(Observer.STATE_ONPAUSE);
        }

        @Override
        public void onDestroy(int code) {
            map.remove(code);
        }
    };

}
