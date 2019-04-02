package com.rocky.livedatabus;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.rocky.livedatabus.livedata.Observer;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        LiveDataBus.getInstance().getChannel("event", String.class).observe(this, new Observer<String>() {
            @Override
            public void onChange(@Nullable String s) {
                Log.d("Tag2","------------second activity");
                showToast(s);
            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void sendMsg(View view){
        LiveDataBus.getInstance().getChannel("send",String.class).setValue("haha");
    }
}
