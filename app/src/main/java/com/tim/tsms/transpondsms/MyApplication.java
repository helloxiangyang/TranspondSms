package com.tim.tsms.transpondsms;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tim.tsms.transpondsms.utils.DingdingMsg;
import com.tim.tsms.transpondsms.utils.SendHistory;
import com.tim.tsms.transpondsms.utils.SendUtil;

/**
 * @author alive on 2017/10/30.
 */
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    private static Context context;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public synchronized static Context getContext(){
        return context;
    }

    @Override
    public void onCreate() {
        Log.d(TAG,"onCreate");
        super.onCreate();
        Intent intent = new Intent(this,FrontService.class);
        startService(intent);
        SendHistory.init(this);
        SendUtil.init(this);
    }
}
