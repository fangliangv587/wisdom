package cenco.xz.fangliang.wisdom.weed;

import android.content.Context;

import com.cenco.lib.common.DateUtil;
import com.cenco.lib.common.TimerHelper;
import com.cenco.lib.common.log.LogUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/5/29.
 */

public abstract class AutoActionHelper implements TimerHelper.TimerListener{

    private Context context;

    public AutoActionHelper() {
        init();
    }

    public AutoActionHelper(Context context) {
        this.context = context;
        init();
    }

    public Context getContext() {
        return context;
    }

    /**
     * 开始时间
     * @return
     */
    protected Date getStartDate(){
        return DateUtil.createDate(2018,1,1,6,0,0);
    }

    /**
     * 结束时间
     * @return
     */
    protected Date getStopDate(){
        return DateUtil.createDate(2018,1,1,23,59,0);
    }

    /**
     * 是否在有效时间内
     * @return
     */
    protected boolean isValidTime(){
        boolean valid = DateUtil.isInPeriodDate(new Date(), getStartDate(), getStopDate(), DateUtil.FORMAT_HMS);
        return valid;
    }

    /**
     * 循环执行的时间间隔，单位秒
     * @return
     */
    protected int getLoopInterval(){
        int interval = 30 * 60;//30min
        return interval;
    }


    @Override
    public void onTimerRunning(int count, int i1, boolean b) {

        if (count % getLoopInterval() != 0){
            return;
        }

        if (!isValidTime()){
            return;
        }

        action();
    }



    protected abstract void init();
    protected abstract void action();


}
