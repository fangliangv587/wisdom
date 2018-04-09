package com.xz.cenco.assits;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xz.cenco.common.widget.TreeView;
import com.xz.cenco.wisdom.R;
import com.xz.cenco.wisdom.activity.BaseActivity;
import com.xz.cenco.wisdom.util.C;
import com.xz.cenco.wisdom.util.Util;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ezy.assist.compat.RomUtil;

public class AppRecordActivity extends BaseActivity {

    private RecordDao recordDao;
    private String date;
    private TextView infoTv;
    private ListView listView;
    private TreeView treeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_record);
        date = getIntent().getStringExtra(C.extra.date);
        recordDao = getApp().getDaoSession().getRecordDao();
        infoTv = findViewById(R.id.infoTv);
        infoTv.setText(date);

        treeView = (TreeView) findViewById(R.id.tree_view);
        treeView.setHeaderView(getLayoutInflater().inflate(R.layout.list_head_view, treeView,
                false));
        TreeViewAdapter adapter = new TreeViewAdapter(this, treeView);
        List<Track> appTrackList = getAppTrack();
        adapter.setData(appTrackList);
        treeView.setAdapter(adapter);

//        List<String> appRecords = getAppRecords();
//        listView = findViewById(R.id.listView);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, appRecords);
//        listView.setAdapter(adapter);



    }

    public List<Track> getAppTrack(){
        if (date == null){
            return null;
        }

        String romName = RomUtil.getName().toLowerCase();
        String systemName = "android";

        List<Record> list = recordDao.queryBuilder().where(RecordDao.Properties.Date.eq(date)).list();
        List<Track> trackList = new ArrayList<>();

        PackageManager pm = getApplication().getPackageManager();

        for (Record r:list){
            String packageName = r.getPackageName();
            if (TextUtils.isEmpty(packageName)){
                continue;
            }
            //跳过系统级应用
//            if (packageName.contains(romName) || packageName.contains(systemName)){
//                continue;
//            }

            Track track = containTrack(trackList, packageName);
            if (track == null){
                List<Record> recordList = new ArrayList<>();
                recordList.add(r);
                track = new Track(packageName,r.getStayTime(),recordList);
                trackList.add(track);
            }else {
                int second = track.getSeconds()+r.getStayTime();
                track.setSeconds(second);

                track.getRecords().add(r);

                try {
                    ApplicationInfo info = pm.getPackageInfo(packageName, 0).applicationInfo;
                    Drawable drawable = info.loadIcon(pm);
                    track.setDrawable(drawable);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }

        Collections.sort(trackList, new Comparator<Track>() {
            @Override
            public int compare(Track o1, Track o2) {
                int i ;
                if (o1.getSeconds()>o2.getSeconds()){
                    i = -1;
                }else {
                    i = 1;
                }
                return i;
            }
        });



        return trackList;
    }


    public Track containTrack(List<Track> list,String packageName){
        if (list == null || TextUtils.isEmpty(packageName)){
            return null;
        }

        for (Track t: list){
            if (t.getPackageName().equalsIgnoreCase(packageName)){
                return t;
            }
        }

        return null;
    }

    public String getFormatString(Record r){
        int stayTime = r.getStayTime();
        String str = Util.getProgramNameByPackageName(this,r.getPackageName())+"  "+ r.getInTime() + "-"+ r.getOutTime()+"    "+Util.getStayTimeString(stayTime);
        str = str +"\r\n"+r.getPackageName();
        return str;
    }
}
