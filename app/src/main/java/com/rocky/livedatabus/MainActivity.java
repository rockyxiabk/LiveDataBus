package com.rocky.livedatabus;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.rocky.livedatabus.livedata.Observer;

public class MainActivity extends Activity {

    Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LiveDataBus.getInstance().getChannel("send", String.class).observe(this, new Observer<String>() {
            @Override
            public void onChange(@Nullable String s) {
                Log.d("Tag1","------------second activity");
                showToast(s);
            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void jump(View view) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LiveDataBus.getInstance().getChannel("event").setValue("second event lick");
            }
        },3000);
        LiveDataBus.getInstance().getChannel("event").setValue("event lick");
        startActivity(new Intent(this,SecondActivity.class));
    }
}
