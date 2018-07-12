package cenco.xz.fangliang.wisdom.weed.txapp2;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import com.cenco.lib.common.http.SimpleCallback;

import java.util.ArrayList;
import java.util.List;

import cenco.xz.fangliang.wisdom.weed.txapp2.bean.WithDrawListResult;

/**
 * Created by Administrator on 2018/7/11.
 */

public class RecordListActivity extends ListActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        String token = intent.getStringExtra("token");

        ApiService.withdrawList(id, token, new SimpleCallback<WithDrawListResult>() {
            @Override
            public void onSuccess(WithDrawListResult o) {

                List<WithDrawListResult.DataBean> data = o.getData();
                List<String> list = new ArrayList<>();
                for (int i=0;i<data.size();i++){
                    WithDrawListResult.DataBean bean = data.get(i);
                    list.add(bean.getWithdrawDate()+"        提现:"+bean.getWithdrawNumber()+"元          状态:" +bean.getBillStatus());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(RecordListActivity.this,android.R.layout.simple_list_item_1,list);
                setListAdapter(adapter);
            }

            @Override
            public void onError(String s) {

            }
        });
    }
}
