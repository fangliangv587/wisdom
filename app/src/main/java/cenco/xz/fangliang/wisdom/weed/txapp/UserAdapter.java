package cenco.xz.fangliang.wisdom.weed.txapp;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
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
    private UserAdapterListener listener;

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

    public void setListener(UserAdapterListener listener) {
        this.listener = listener;
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
            holder.itemLayout = convertView.findViewById(R.id.itemLayout);
            holder.firePersonTv = convertView.findViewById(R.id.firePersonTv);
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
            holder.itemLayout.setBackgroundColor(Color.WHITE);
        }else {
            str=user.txRecord.getInfo();
            if (user.txRecord.standminute>user.txRecord.disminute){
                holder.itemLayout.setBackgroundColor(Color.GRAY);
            }else {
                holder.itemLayout.setBackgroundColor(Color.WHITE);
            }
        }


        if (user.names.size()>0){
            holder.firePersonTv.setTextColor(Color.DKGRAY);
            holder.firePersonTv.setText("此账号提现成功过====>"+user.getName());

            if (user.txRecord.standminute>user.txRecord.disminute){
                holder.itemLayout.setBackgroundColor(Color.GRAY);
            }else {
                holder.itemLayout.setBackgroundColor(Color.parseColor("#DCA8F3"));
            }

        }else {
            holder.firePersonTv.setText("");
        }

        holder.txTv.setText(str);
        holder.recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onUserRecord(user);
                }
            }
        });

        return convertView;
    }



    public void setData(List<User> data) {
        this.data = data;
    }


    public static class ViewHolder{
        public View itemLayout;
        public TextView userTv;
        public TextView firePersonTv;
        public TextView infoTv;
        public TextView txTv;
        public Button recordBtn;

    }

    public interface UserAdapterListener{
        void onUserRecord(User user);
    }
}
