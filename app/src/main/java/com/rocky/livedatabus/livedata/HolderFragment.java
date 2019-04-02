package com.rocky.livedatabus.livedata;

import android.app.Activity;
import android.app.Fragment;

import com.rocky.livedatabus.liferecycer.LifecycleListener;

/**
 * Description : com.rocky.livedatabus.livedata
 *
 * @author : rocky
 * @Create Time : 2019/4/1 5:45 PM
 * @Modified Time : 2019/4/1 5:45 PM
 */
public class HolderFragment extends Fragment {
    private int activityCode;
    private LifecycleListener lifecycleListener;

    public void setLifecycleListener(LifecycleListener lifecycleListener) {
        this.lifecycleListener = lifecycleListener;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityCode=activity.hashCode();
        if (null!=lifecycleListener){
            lifecycleListener.onCreate(activityCode);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (null!=lifecycleListener){
            lifecycleListener.onStart(activityCode);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null!=lifecycleListener){
            lifecycleListener.onPause(activityCode);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (null!=lifecycleListener){
            lifecycleListener.onDestroy(activityCode);
        }
    }
}
