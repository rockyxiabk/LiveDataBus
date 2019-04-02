package com.rocky.livedatabus;

import com.rocky.livedatabus.livedata.LiveData;

import java.util.HashMap;
import java.util.Map;

/**
 * Description : com.rocky.livedatabus
 *
 * @author : rocky
 * @Create Time : 2019/4/1 4:43 PM
 * @Modified Time : 2019/4/1 4:43 PM
 */
public class LiveDataBus {

    private Map<String, LiveData<Object>> bus;
    private static LiveDataBus instance =new LiveDataBus();

    private LiveDataBus(){
        bus=new HashMap<>();
    }

    public static LiveDataBus getInstance(){
        return instance;
    }

    public <T> LiveData<T>  getChannel(String target,Class<T> tClass){
        if (!bus.containsKey(target)){
            bus.put(target,new LiveData<Object>());
        }
        return ((LiveData<T>) bus.get(target));
    }
    public LiveData<Object> getChannel(String target){
        return getChannel(target,Object.class);
    }
}
