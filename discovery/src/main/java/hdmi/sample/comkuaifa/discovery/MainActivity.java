package hdmi.sample.comkuaifa.discovery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Object service = getSystemService("statusbar");
            Class<?> statusbarManager = Class.forName("android.app.StatusBarManager");
            Method[] methods = statusbarManager.getMethods();
            int a =0;

        }  catch (Exception e) {
            e.printStackTrace();
            Log.e("xin",e.getMessage());
        }
    }
}
