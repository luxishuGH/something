package com.lxs.client;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class WIFIInfoActivity extends Activity {

	WifiManager manager;
	WifiConfiguration config;
	WifiInfo info;
	WifiLock lock;
	List<ScanResult> results;
	
	List<WifiConfiguration> configs;
	
	TextView tvMsg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tvMsg = (TextView) findViewById(R.id.tv_msg);
		tvMsg.setMovementMethod(ScrollingMovementMethod.getInstance());
		
		manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		tvMsg.append("\n--" + manager.getWifiState());
		tvMsg.append("\n--" + manager.getConnectionInfo().toString());
//		results = manager.getScanResults();
//		
//		for (ScanResult re : results) {
//			tvMsg.append("\n--" + re.SSID);
//		}
		configs = manager.getConfiguredNetworks();
		for(WifiConfiguration config : configs) {
			tvMsg.append("\n--" + config.toString());
		}
		
	}
}
