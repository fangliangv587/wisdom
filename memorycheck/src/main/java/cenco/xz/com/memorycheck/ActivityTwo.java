package cenco.xz.com.memorycheck;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class ActivityTwo extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.activity=this;
    }
}