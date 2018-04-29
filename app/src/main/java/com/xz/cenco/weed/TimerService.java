package com.xz.cenco.weed;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.cenco.lib.common.DateUtil;
import com.cenco.lib.common.TimerHelper;
import com.cenco.lib.common.log.LogUtils;
import com.xz.cenco.weed.thumber.ThumberHelper;
import com.xz.cenco.wisdom.service.WisdomHelper;
import com.xz.cenco.wisdom.service.WisdomService;

/**
 * Created by Administrator on 2018/4/27.
 */

public class TimerService extends Service implements TimerHelper.TimerListener{

    private TimerHelper timerHelper;
    private WisdomHelper wisdomHelper;
    private ThumberHelper thumberHelper;

    private MyBinder binder = new MyBinder();



    @Override
    public void onCreate() {
        super.onCreate();

        wisdomHelper = new WisdomHelper(this);
        wisdomHelper.start();

//        thumberHelper = new ThumberHelper();
//        thumberHelper.start();


        timerHelper = new TimerHelper(this);
        timerHelper.setInterval(1);
        timerHelper.start();
        timerHelper.addListener(wisdomHelper);
//        timerHelper.addListener(thumberHelper);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timerHelper!=null){
            timerHelper.stop();
        }

    }

    public void updateWisdom(){
        if (wisdomHelper!=null){
            wisdomHelper.resetFloatView();
        }
    }


    @Override
    public void onTimerRunning(int current, int i1, boolean b) {

    }



    public class MyBinder extends Binder {

        public TimerService getService(){
            return TimerService.this;
        }
    }

}
