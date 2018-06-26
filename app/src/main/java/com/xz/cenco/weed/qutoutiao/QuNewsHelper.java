package com.xz.cenco.weed.qutoutiao;

import android.util.Base64;

import com.cenco.lib.common.ThreadManager;
import com.cenco.lib.common.http.HttpUtil;
import com.cenco.lib.common.http.SimpleCallback;
import com.cenco.lib.common.json.GsonUtil;
import com.cenco.lib.common.log.LogUtils;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2018/6/25.
 */

public class QuNewsHelper {

    private static final String TAG = QuNewsHelper.class.getSimpleName();

    List<QuUser> userList;

    boolean isRunning;

    int index;

    final int count = 10;

    public void start(){
        LogUtils.w(TAG,"start");
        userList = QuUser.getUserList();
        action();
    }

    public void stop(){
        isRunning=false;
    }

    private void action() {
        isRunning = true;
        ThreadManager.getPoolProxy().execute(new Runnable() {
            @Override
            public void run() {

                while (isRunning){
                    index++;
                    for (int j=0;j<count;j++){
                        waitInterval();
                        for (int i=0;i<userList.size();i++){
                            QuUser user = userList.get(i);
                            String token = user.getData().get(j);
                            request(user,token);
                        }
                    }

                }

            }

            private void waitInterval() {
                int second = getIntervalSecond();
                LogUtils.d(TAG,"等待时间:"+second+"秒");
                try {
                    Thread.sleep(second*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            private int getIntervalSecond(){
                Random random = new Random();
                int i = random.nextInt(2);
                return 32+i;
            }

        });
    }

    private void request(final QuUser user , String data){
        String url = "https://api.1sapp.com/readtimer/report?qdata="+data;
        HttpUtil.get(url, new SimpleCallback<String>() {
            @Override
            public void onSuccess(String s) {
                try {
                    Result result = GsonUtil.fromJson(s, Result.class);
                    if (result.getData()!=null && result.getData().getCurr_task()!=null){
                        int amount = result.getData().getCurr_task().getAmount();
                        user.setCount(user.getCount()+amount);
                        LogUtils.w(TAG,user.getPhone()+"增加的数量:"+amount+",总增加量:"+user.getCount());
                    }else {
                        LogUtils.d(TAG,s);
                    }
                } catch (JsonIOException e) {
                    LogUtils.e(TAG,e);
                    e.printStackTrace();
                } catch (JsonSyntaxException e) {
                    LogUtils.e(TAG,e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String s) {
                LogUtils.e(TAG,s);
            }
        });
    }



}
