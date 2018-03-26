package com.xz.cenco.wisdom.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.cenco.lib.common.ToastUtil;
import com.xz.cenco.wisdom.R;
import com.xz.cenco.wisdom.entity.WisdomType;
import com.xz.cenco.wisdom.entity.WisdomTypeDao;

import java.util.Date;

public class TypeAddActivity extends BaseActivity {

    EditText typeNameEt;
    WisdomTypeDao typeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_add);
        typeDao = getApp().getDaoSession().getWisdomTypeDao();
        typeNameEt =(EditText) findViewById(R.id.typeName);
    }

    public void addClick(View view){
        String typeName = typeNameEt.getText().toString().trim();
        if (TextUtils.isEmpty(typeName)){
            ToastUtil.show(this,"不能为空");
            return;
        }
        typeNameEt.setText("");
        WisdomType type = new WisdomType();
        type.setName(typeName);
        type.setDate(new Date());
        typeDao.insert(type);

        setResult(RESULT_OK);
        finish();
    }
}
