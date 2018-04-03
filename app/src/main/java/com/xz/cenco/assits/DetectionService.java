package com.xz.cenco.assits;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.Window;
import android.view.accessibility.AccessibilityEvent;

import com.cenco.lib.common.DateUtil;
import com.cenco.lib.common.log.LogUtils;
import com.xz.cenco.wisdom.ScreenListener;
import com.xz.cenco.wisdom.activity.App;
import com.xz.cenco.wisdom.util.Util;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Created by shawn
 * Data: 2/3/2016
 * Blog: effmx.com
 */
public class DetectionService extends AccessibilityService {

    final  String TAG = this.getClass().getName();

    private ScreenListener screenListener;

    private String frontPackage;
    private String curPackage;

    @Override
    public void onCreate() {
        super.onCreate();
        initScreenListner();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (screenListener!=null){
            screenListener.stop();
        }
    }



    /**
     * 重载辅助功能事件回调函数，对窗口状态变化事件进行处理
     * @param event
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            /*
             * 如果 与 DetectionService 相同进程，直接比较 foregroundPackageName 的值即可
             * 如果在不同进程，可以利用 Intent 或 bind service 进行通信
             */
            String packageName = event.getPackageName().toString();

            LogUtils.w(TAG,"foregroundPackageName="+packageName);


            if (TextUtils.equals(this.curPackage,packageName)){

            }else {
                this.frontPackage = this.curPackage;
                this.curPackage = packageName;
                insertOrUpdate();
            }


        }
    }


    public void insertOrUpdate(){
        App app = (App) getApplication();
        RecordDao recordDao = app.getDaoSession().getRecordDao();

        //更新旧的
        if (this.frontPackage != null){
            List<Record> list = recordDao.queryBuilder().orderDesc(RecordDao.Properties.Id).limit(1).list();
            if (list!=null && list.size()>0){
                Record record = list.get(0);
                record.setOutTime(DateUtil.getDateString(DateUtil.FORMAT_HMS));
                recordDao.update(record);
            }
        }

        //插入新的
        if (this.curPackage != null){
            Record record = new Record();
            record.setDate(DateUtil.getDateString(DateUtil.FORMAT_YMD));
            record.setInTime(DateUtil.getDateString(DateUtil.FORMAT_HMS));
            record.setPackageName(this.curPackage);
            recordDao.insert(record);
        }


        List<Record> list = recordDao.queryBuilder().orderDesc(RecordDao.Properties.Id).limit(2).list();;
        LogUtils.i("***********************");
        for (int i = 0; i<list.size();i++){
            LogUtils.d(i+":"+list.get(i).toString());
        }

    }


    private void initScreenListner() {
        screenListener = new ScreenListener(this);
        screenListener.begin(new ScreenListener.ScreenStateListener() {

            @Override
            public void onUserPresent() {
                LogUtils.i( TAG,"解锁");

            }

            @Override
            public void onScreenOn() {
                KeyguardManager mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
                boolean flag = mKeyguardManager.inKeyguardRestrictedInputMode();
                LogUtils.i( TAG,"开屏  "+ flag);


            }

            @Override
            public void onScreenOff() {
                LogUtils.i(  TAG,"锁屏");

            }
        });
    }

    public  Activity getActivity() {
        Class activityThreadClass = null;
        try {
            activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map activities = (Map) activitiesField.get(activityThread);
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field[] fields = activityRecordClass.getDeclaredFields();
                LogUtils.d("---"+activityRecordClass.getName());
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                Field activityField = activityRecordClass.getDeclaredField("activity");
                Field windowField = activityRecordClass.getDeclaredField("window");
                LogUtils.d("activity ---"+activityField.getName());

                activityField.setAccessible(true);
                windowField.setAccessible(true);
                Object o = activityField.get(activityRecord);
                Window w = (Window) windowField.get(activityRecord);

                String name = o.getClass().getName();
                String name1 = o.getClass().getPackage().getName();
                LogUtils.d("activity ---"+name);

                int statusBarColor = w.getStatusBarColor();
                String sss = Util.getColor(statusBarColor);
                LogUtils.i("getWindow statusBarColor color  ="+sss);
            }
        }  catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(e.getMessage());
        }
        return null;
    }

    public void getStatusBarColor(String className){
        try {
            Activity foregroundActivity = (Activity) (Class.forName(className).newInstance());
            LogUtils.w("onAccessibilityEvent","activity hashcode"+foregroundActivity.hashCode());
            Field[] fields = Activity.class.getDeclaredFields();

            for (Field f:fields){
                if (f.getName().equals("mTaskDescription")){
                    LogUtils.w(""+f.getName());
                }
            }

            Field field1 = Activity.class.getDeclaredField("mTaskDescription");
            if (!field1.isAccessible()){
                field1.setAccessible(true);
            }
            ActivityManager.TaskDescription td = (ActivityManager.TaskDescription) field1.get(foregroundActivity);
            LogUtils.w("onAccessibilityEvent","mTaskDescription hashcode"+td.hashCode());

            LogUtils.d("************************************************");


            Field f1 = ActivityManager.TaskDescription.class.getDeclaredField("mIcon");
            Field f2 = ActivityManager.TaskDescription.class.getDeclaredField("mLabel");
            Field f3 = ActivityManager.TaskDescription.class.getDeclaredField("mColorPrimary");
            Field f4 = ActivityManager.TaskDescription.class.getDeclaredField("mColorBackground");

            f1.setAccessible(true);
            f2.setAccessible(true);
            f3.setAccessible(true);
            f4.setAccessible(true);

            Bitmap bitmap = (Bitmap) f1.get(td);
            String mLabel = (String) f2.get(td);
            int mColorPrimary = (int) f3.get(td);
            int mColorBackground = (int) f4.get(td);

            LogUtils.w("bitmap="+bitmap+",mLabel="+mLabel+",mColorPrimary="+mColorPrimary+",mColorBackground="+mColorBackground);

        } catch (InstantiationException e) {
            e.printStackTrace();
            LogUtils.e("onAccessibilityEvent","InstantiationException:"+e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            LogUtils.e("onAccessibilityEvent","IllegalAccessException:"+e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            LogUtils.e("onAccessibilityEvent","ClassNotFoundException:"+e.getMessage());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            LogUtils.e("onAccessibilityEvent","NoSuchFieldException:"+e.getMessage());
        }
    }

    @Override
    public void onInterrupt() {
    }

    @Override
    protected  void onServiceConnected() {
        super.onServiceConnected();
    }
}