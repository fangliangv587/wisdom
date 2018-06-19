package com.xz.cenco.weed.txapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xz.cenco.wisdom.R;

import java.util.List;

/**
 * Created by Administrator on 2018/6/19.
 */

public class UserAdapter extends BaseAdapter {

    private Context context;
    private List<User> data;

    public UserAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        if (data!=null){
            return data.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_user, null);
            holder.userTv = convertView.findViewById(R.id.userTv);
            holder.infoTv = convertView.findViewById(R.id.userInfoTv);
            holder.txTv = convertView.findViewById(R.id.txRecordTv);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        User user = data.get(position);
        holder.userTv.setText((position+1)+"  "+user.user);
        holder.infoTv.setText(user.toString());
        String str;
        if(user.txRecord==null){
            str="暂无体现成功记录";
        }else {
            str=user.txRecord.getInfo();
        }
        holder.txTv.setText(str);

        return convertView;
    }

    public void setData(List<User> data) {
        this.data = data;
    }


    public static class ViewHolder{
        public TextView userTv;
        public TextView infoTv;
        public TextView txTv;

    }
}
