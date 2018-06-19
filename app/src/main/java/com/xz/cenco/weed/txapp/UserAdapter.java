package com.xz.cenco.weed.txapp;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
            holder.recordBtn = convertView.findViewById(R.id.recordBtn);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final User user = data.get(position);
        holder.userTv.setText((position+1)+"==>"+user.user);
        holder.infoTv.setText(user.toString());
        String str;
        if(user.txRecord==null){
            str="暂无体现成功记录";
        }else {
            str=user.txRecord.getInfo();
        }
        holder.txTv.setText(str);
        holder.recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecords(user);
            }
        });

        return convertView;
    }

    private void showRecords(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        TextView textView = new TextView(context);
        textView.setPadding(20,20,20,20);
        if (user==null){
            textView.setText("用户为空");
        }else if(user.recordList==null){
            textView.setText("记录为空");
        }else {
            StringBuffer sb = new StringBuffer();
            sb.append("\n\n");
            for (int i =0;i<user.recordList.size();i++){
                TxRecord record = user.recordList.get(i);
                sb.append(record.toString()+"\n\n");
            }
            textView.setText(sb.toString());
        }
        builder.setTitle("提现记录");
        builder.setView(textView);
        builder.create().show();
    }

    public void setData(List<User> data) {
        this.data = data;
    }


    public static class ViewHolder{
        public TextView userTv;
        public TextView infoTv;
        public TextView txTv;
        public Button recordBtn;

    }
}
