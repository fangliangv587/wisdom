package com.xz.cenco.weed.txapp;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
import java.util.Set;

//自定义adapter
public class Myadapter extends BaseExpandableListAdapter {

    Map<String, List<AliRecord>> maps;

    Context context;

    ExpandableListView listView;

    public Myadapter(Map<String, List<AliRecord>> maps, Context context, ExpandableListView listView) {
        this.maps = maps;
        this.context = context;
        this.listView = listView;
    }

    @Override
    //组数
    public int getGroupCount() {

        return maps.keySet().size();
    }

    @Override
    //每组的子数
    public int getChildrenCount(int groupPosition) {
        Set<String> set = maps.keySet();
        String[] strings = set.toArray(new String[set.size()]);
        List<AliRecord> aliRecords = maps.get(strings[groupPosition]);
        return aliRecords.size();
    }

    @Override
    //返回对应的组
    public String getGroup(int groupPosition) {
        Set<String> set = maps.keySet();
//        String[] objects = (String[])set.toArray();
        String[] strings = set.toArray(new String[set.size()]);
        String a = strings[groupPosition];
        return a;
    }

    public double getGroupMoney(int groupPosition) {
        Set<String> set = maps.keySet();
//        String[] objects = (String[])set.toArray();
        String[] strings = set.toArray(new String[set.size()]);
        List<AliRecord> aliRecords = maps.get(strings[groupPosition]);
        double money =0;
        for (AliRecord aliRecord : aliRecords){
            money+=aliRecord.getMoney();
        }
        return money;
    }

    @Override
    //返回对应的子
    public AliRecord getChild(int groupPosition, int childPosition) {
        Set<String> set = maps.keySet();
        String[] strings = set.toArray(new String[set.size()]);
        List<AliRecord> aliRecords = maps.get(strings[groupPosition]);
        AliRecord aliRecord = aliRecords.get(childPosition);
        return aliRecord;
    }

    @Override
    //返回组id
    public long getGroupId(int groupPosition) {

        return groupPosition;
    }

    @Override
    //返回子id
    public long getChildId(int groupPosition, int childPosition) {

        return childPosition;
    }

    @Override
    public boolean hasStableIds() {

        return true;
    }

    @Override
    //返回组试图
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        TextView view;
        if (convertView != null) {
            view = (TextView) convertView;
        } else {
            view = new TextView(context);
        }

        double groupMoney = getGroupMoney(groupPosition);
        view.setText(getGroup(groupPosition)+"==>"+groupMoney+"元");
        view.setTextColor(Color.WHITE);
        view.setBackgroundColor(Color.DKGRAY);
        return view;
    }

    @Override
    //返回子试图
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        TextView view;
        if (convertView != null) {
            view = (TextView) convertView;
        } else {
            view = new TextView(context);
        }
        AliRecord record = getChild(groupPosition, childPosition);
        view.setText(record.getDate() + "  " + record.getMoney());
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }

    @Override
    public void notifyDataSetChanged() {
        int groupCount = listView.getCount();
        super.notifyDataSetChanged();
        for (int i = 0; i < groupCount; i++) {
            listView.expandGroup(i);
        }
    }


}