package com.jinhong.broadcastbestpractice;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by jinhong on 2/6/16.
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
