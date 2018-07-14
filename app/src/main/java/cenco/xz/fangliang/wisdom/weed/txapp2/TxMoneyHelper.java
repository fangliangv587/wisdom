package cenco.xz.fangliang.wisdom.weed.txapp2;

import android.content.Context;

import com.cenco.lib.common.DateUtil;
import com.cenco.lib.common.IOUtils;
import com.cenco.lib.common.SPUtil;
import com.cenco.lib.common.ThreadManager;
import com.cenco.lib.common.TimerHelper;
import com.cenco.lib.common.json.GsonUtil;
import com.cenco.lib.common.log.LogUtils;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import cenco.xz.fangliang.wisdom.weed.txapp2.bean.Account;
import cenco.xz.fangliang.wisdom.weed.txapp2.bean.IncomeResult;
import cenco.xz.fangliang.wisdom.weed.txapp2.bean.LoginResult;
import cenco.xz.fangliang.wisdom.weed.txapp2.bean.WithdrawResult;

/**
 * Created by Administrator on 2018/7/12.
 */

public class TxMoneyHelper  implements TimerHelper.TimerListener {

    private static final String TAG = TxMoneyHelper.class.getSimpleName();

    private List<Account> userlist;
    private Context context;

//    private final int  interval = 30 * 60;//30分钟
    private final int interval = 24 * 60 * 60;//24小时

    private Date upDate;
    private Date downDate;

    public TxMoneyHelper(Context context) {
        this.context = context;
        userlist = Utils.getAccount();
        upDate = DateUtil.createDate(2018,1,1,23,59,0);
        downDate = DateUtil.createDate(2018,1,1,9,0,0);

    }




    public void action(){

        ThreadManager.getPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<userlist.size();i++){
                    Account account = userlist.get(i);

                    String path = Utils.getFilePath(account);
                    File file = new File(path);
                    if (file.exists()){
                        LogUtils.w(TAG,account.getIndentify()+" 已经提现过了！");
                        continue;
                    }

                    //登录
                    String s = ApiService.syncLogin(account.getPhone(), account.getPass());
                    LoginResult result = GsonUtil.fromJson(s, LoginResult.class);
                    LoginResult.DataBean user = result.getData();
                    int id = user.getId();
                    String token = result.getToken();
                    account.setId(id);
                    account.setToken(token);


                    //增加余额
                    addBalance(id,token,i,account);


                    try {
                        Thread.sleep(4*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    String money = "0.5";
                    String s1 = ApiService.syncWithdraw(id, token, money, "1");
                    WithdrawResult result1 = GsonUtil.fromJson(s1, WithdrawResult.class);
                    String message = result1.getSuccessMessage();
                    account.setWithdrawStatus(result1.getState());
                    account.setTxmoney(money);
                    account.setTxtime(DateUtil.getDateString());

                    LogUtils.w(TAG,account.getIndentify()+message);


                    if (!file.getParentFile().exists()){
                        file.getParentFile().mkdirs();
                    }
                    String json = GsonUtil.toJson(account);
                    IOUtils.writeFileFromString(file,json);

                    try {
                        Thread.sleep((long) ((Math.random()*10+10) * 1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

            private void addBalance(int id,String token,int index,Account account) {

                int times = index % 4 + 1;

                LogUtils.d(TAG,account.getIndentify()+"增加金额次数:"+times);

                for (int i=0;i<times;i++){
                    double random = Math.random()/3+0.5;
                    DecimalFormat df = new DecimalFormat("0.00");
                    String format = df.format(random);

                    LogUtils.d(TAG,"随机数:"+format);

                    String s1 = ApiService.syncIncome(id, token, format);
                    IncomeResult result1 = GsonUtil.fromJson(s1, IncomeResult.class);
                    LogUtils.d(TAG,result1.getSuccessMessage());
                    String s2 = ApiService.syncReward(id, token, format);
                    IncomeResult result2 = GsonUtil.fromJson(s2, IncomeResult.class);
                    LogUtils.i(TAG,result2.getSuccessMessage());

                    try {
                        Thread.sleep(10 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }
        });
    }

    @Override
    public void onTimerRunning(int current, int n, boolean b) {

        if ( isValidTime() ){
            SPUtil.put(context,"txmoney_action_time",new Date().getTime());
            action();
        }
    }

    private boolean isValidTime(){

//        boolean valid = DateUtil.isInPeriodDate(new Date(), downDate, upDate, DateUtil.FORMAT_HMS);
//        LogUtils.i(TAG,"时间段检查："+valid);

        //2018-07-13 15:26:22
        long actionTime = (long) SPUtil.get(context, "txmoney_action_time", 1531466782000L);
        long currentTime = new Date().getTime();

        int difsecond = (int) ((currentTime - actionTime)/1000);

        int h = difsecond / (60 * 60);
        int m = difsecond % (60 * 60) / 60;
        int s = difsecond % (60 * 60) % 60;
//        LogUtils.d(TAG,"间隔时间:"+h+"时"+m+"分"+s+"秒");

        int delaysecond = 8 * 60 +47;

        if (difsecond >= (interval+delaysecond)){
            return true;
        }

        return false;
    }


    public int getDelayInterval(){
        Random random = new Random();
        int m = random.nextInt(12)+10;
        int s = random.nextInt(60)+6;
        int time = interval + m * 60 + s;
        return time;
    }
}
