package com.lxs.udps;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer implements Runnable{

	private final int PORT = 8080;
	private final DatagramSocket scoket;
	private final DatagramPacket packet;
	private final byte[] buff = new byte[1024];
	private boolean isClose = false;
//	192.168.2.37
	public UDPServer() {
		DatagramSocket temp = null;
		try {
			temp = new DatagramSocket(PORT);
		} catch (SocketException e) {
			e.printStackTrace();
			isClose = true;
			System.out.println("can not bind to 8080...");
		}
		this.scoket = temp;
		this.packet = new DatagramPacket(buff, buff.length);
	}

	public static void main(String[] args) {
		new Thread(new UDPServer()).start();
	}

	@Override
	public void run() {
		System.out.println("start server...");
		while(!isClose){
			try {
				this.scoket.receive(packet);
				System.out.println("receive from client ... ");
				System.out.println(new String(packet.getData(), packet.getOffset(), packet.getLength()));
				System.out.println("ip -> "+packet.getAddress().getHostAddress() +" port -> "+ packet.getPort());
				Thread.sleep(500);
				byte[] data = "hello client ...".getBytes();
				DatagramPacket send = new DatagramPacket(data, data.length, packet.getAddress(), packet.getPort());
				this.scoket.send(send);
				System.out.println("send to client ... ");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
