package cenco.xz.fangliang.wisdom.weed.txapp2;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import com.cenco.lib.common.IOUtils;
import com.cenco.lib.common.http.SimpleCallback;
import com.cenco.lib.common.json.GsonUtil;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cenco.xz.fangliang.wisdom.core.C;
import cenco.xz.fangliang.wisdom.weed.txapp2.bean.Account;
import cenco.xz.fangliang.wisdom.weed.txapp2.bean.WithDrawListResult;

/**
 * Created by Administrator on 2018/7/11.
 */

public class LocalRecordListActivity extends ListActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Account account = (Account) intent.getSerializableExtra("account");
        String indentify = account.getIndentify();
        File file = new File(C.file.txapp_action);
        if (!file.exists()){
            return;
        }

        List<String> list = new ArrayList<>();
        File[] files = file.listFiles();
        for (File f:files){
            String date = f.getName();
            File[] files1 = f.listFiles();
            for (File f1:files1){
                if(f1.getName().contains(indentify)){
                    String str = IOUtils.readFile2String(f1);
                    Account account1 = GsonUtil.fromJson(str, Account.class);
                    list.add(date+"    金额:"+account1.getTxmoney()+"     状态:"+account1.getWithdrawStatus());
                }
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(LocalRecordListActivity.this,android.R.layout.simple_list_item_1,list);
        setListAdapter(adapter);


    }
}
