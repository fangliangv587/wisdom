package cenco.xz.fangliang.wisdom.weed;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cenco.lib.common.ToastUtil;

import cenco.xz.fangliang.wisdom.App;
import cenco.xz.fangliang.wisdom.weed.coohua.CoohuaActivity;
import cenco.xz.fangliang.wisdom.weed.thumber.TumblerActivity;
import cenco.xz.fangliang.wisdom.weed.txapp2.RegisterActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/25.
 */

public class WeedListActivity extends ListActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> arrayData = getData();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayData);
        setListAdapter(adapter);
    }

    private List<String> getData() {
        List<String> data = new ArrayList<String>();
        data.add("不倒翁");
        data.add("0.5");
        data.add("測試");

        return data;
    }





    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);


        Intent intent = null;
        if (position==0){

            if (App.isTimer){
                ToastUtil.show(this,"请关闭定时器");
                return;
            }

            intent =  new Intent(this, TumblerActivity.class);
            startActivity(intent);
            return;
        }

        if (position==1){
            intent =  new Intent(this, RegisterActivity.class);
            startActivity(intent);
            return;
        }
        if (position==2){
            intent =  new Intent(this, TestMessageActivity.class);
            startActivity(intent);
            return;
        }



    }
}
