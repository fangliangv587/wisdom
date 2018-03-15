package hdmi.sample.comkuaifa.discovery.wifi;

import android.net.wifi.WifiConfiguration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import hdmi.sample.comkuaifa.discovery.R;

public class WifiAutoConnectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_auto_connect);

        WifiAdmin wifiAdmin = new WifiAdmin(this);
        wifiAdmin.openWifi();
        String wifiname = "fangliangv58";
        String pass = "xin03531883";
        WifiConfiguration wifiInfo = wifiAdmin.CreateWifiInfo(wifiname, pass, 3);
        wifiAdmin.addNetwork(wifiInfo);
    }
}
