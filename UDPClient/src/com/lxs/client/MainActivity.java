package com.lxs.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity implements Runnable {

	final String SERVER_IP = "192.168.2.37";
	final int PORT = 8080;

	DatagramSocket scoket;
	DatagramPacket packet;

	boolean disConnect = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		DatagramSocket temp = null;
		try {
			temp = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		scoket = temp;
		temp = null;
//		new Thread(this).start();
		startActivity(new Intent(this, WIFIInfoActivity.class));
		finish();
	}

	@Override
	protected void onDestroy() {
		disConnect = true;
		super.onDestroy();
	}
	@Override
	public void run() {
		System.out.println("start client ... ");
		while (!disConnect) {
			try {
				byte[] buff = "hello server ... ".getBytes();
				packet = new DatagramPacket(buff, buff.length,
						InetAddress.getByName(SERVER_IP), PORT);
				scoket.send(packet);
				System.out.println("send to server ... ");
				buff = new byte[1024];
				packet = new DatagramPacket(buff, buff.length);
				scoket.receive(packet);
				System.out.println("receiver from server ... ");
				System.out.println("server msg -> " +new String(packet.getData(), packet.getOffset(), packet.getLength()));
				Thread.sleep(10000);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
