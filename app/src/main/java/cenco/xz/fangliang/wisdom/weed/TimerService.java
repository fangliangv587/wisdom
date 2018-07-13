package cenco.xz.fangliang.wisdom.weed;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;

import com.cenco.lib.common.TimerHelper;
import com.cenco.lib.common.log.LogUtils;
import cenco.xz.fangliang.wisdom.core.WisdomHelper;

import cenco.xz.fangliang.wisdom.weed.coohua.CoohuaHelper;
import cenco.xz.fangliang.wisdom.weed.qutoutiao.QuNewsHelper;
import cenco.xz.fangliang.wisdom.weed.thumber.ThumberHelper;
import cenco.xz.fangliang.wisdom.weed.txapp2.TxMoneyHelper;

/**
 * Created by Administrator on 2018/4/27.
 */

public class TimerService extends Service implements TimerHelper.TimerListener{

    private TimerHelper timerHelper;
    private WisdomHelper wisdomHelper;
    private ThumberHelper thumberHelper;
    private QuNewsHelper quNewsHelper;
    private CoohuaHelper coohuaHelper;
    private PowerManager.WakeLock mWakeLock;

    private MyBinder binder = new MyBinder();



    @Override
    public void onCreate() {
        super.onCreate();

        LogUtils.w("计时器服务开启");

        //防止休眠
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager. PARTIAL_WAKE_LOCK  ,"TimerService");
        mWakeLock.acquire();

        //智者提示
        wisdomHelper = new WisdomHelper(this);
        wisdomHelper.start();

        //不倒翁
        thumberHelper = new ThumberHelper(this);
        thumberHelper.start();

        //0.5
        TxMoneyHelper moneyHelper = new TxMoneyHelper();




//        //酷划
//        coohuaHelper = new CoohuaHelper(this);
//        coohuaHelper.start();
//
//        //国证投资
//        aiaixgHelper = new AiaixgHelper(this);
//        aiaixgHelper.start();
//
//        //鑫茂国荣---国证投资2
//        xmgrHelper = new XmgrHelper(this);
//        xmgrHelper.start();


        timerHelper = new TimerHelper(this);
        timerHelper.setInterval(60);
        timerHelper.start();
        timerHelper.addListener(wisdomHelper);
        timerHelper.addListener(thumberHelper);
        timerHelper.addListener(moneyHelper);
//        timerHelper.addListener(coohuaHelper);
//        timerHelper.addListener(aiaixgHelper);
//        timerHelper.addListener(xmgrHelper);


        //趣头条
        quNewsHelper = new QuNewsHelper();
        quNewsHelper.start();

    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mWakeLock!=null){
            mWakeLock.release();
        }

        if (quNewsHelper!=null){
            quNewsHelper.stop();
        }
        if (timerHelper!=null){
            timerHelper.stop();
        }
        if (coohuaHelper!=null){
            coohuaHelper.stop();
        }
        if (thumberHelper!=null){
            thumberHelper.stop();
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
