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

    int index;

    public void start(){
        userList = QuUser.getUserList();
        action();
    }

    private void action() {
        ThreadManager.getPoolProxy().execute(new Runnable() {
            @Override
            public void run() {

                while (true){
                    index++;
                    LogUtils.w(TAG,"第"+index+"次=====>");
                    for (int i=0;i<userList.size();i++){
                        LogUtils.i(TAG,"第"+i+"个用户=====>");
                        QuUser user = userList.get(i);
                        List<String> data = user.getData();
                        for (int j=0;j<data.size();j++){
                            LogUtils.d(TAG,"进度:"+(j+1)+"/"+data.size());
                            waitInterval();
                            String token = data.get(j);
                            request(token);
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
                int i = random.nextInt(5);
                return 30+i;
            }

        });
    }

    private void request(String data){
        String url = "https://api.1sapp.com/readtimer/report?qdata="+data;
        HttpUtil.get(url, new SimpleCallback<String>() {
            @Override
            public void onSuccess(String s) {
                try {
                    Result result = GsonUtil.fromJson(s, Result.class);
                    if (result.getData()!=null && result.getData().getCurr_task()!=null){
                        int amount = result.getData().getCurr_task().getAmount();
                        LogUtils.i(TAG,"增加的数量:"+amount);
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
