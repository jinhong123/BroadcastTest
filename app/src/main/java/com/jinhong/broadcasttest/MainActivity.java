package com.jinhong.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private IntentFilter intentFilter;
    private NetWorkChangeReceiver networkChangeReceiver;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //注册本地广播监听器
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.jinhong.broadcasttest.LOCAL_BROADCAST");
        localReceiver = new LocalReceiver();
        localBroadcastManager = LocalBroadcastManager.getInstance(this);    //获取实例
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);


        Intent i = new Intent("com.jinhong.broadcasttest.LOCAL_BROADCAST");
        localBroadcastManager.sendBroadcast(i); //发送本地广播

//        intentFilter = new IntentFilter();
//        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        networkChangeReceiver = new NetWorkChangeReceiver();
//        registerReceiver(networkChangeReceiver,intentFilter);

        Button button = (Button) findViewById(R.id.sendBroadcast);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent("com.jinhong.broadcasttest.MY_BROADCAST");
                //sendBroadcast(intent); 发送标准广播
                //接下来发送有序广播
                sendOrderedBroadcast(intent,null);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy(){
        //unregisterReceiver(networkChangeReceiver);
        localBroadcastManager.unregisterReceiver(localReceiver);
        super.onDestroy();

    }

    class NetWorkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo  = connectivityManager.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isAvailable()){
                Toast.makeText(context, "network is available", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "network is unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class LocalReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent){
            Toast.makeText(context, "received local broadcast",Toast.LENGTH_SHORT).show();
        }
    }


}
