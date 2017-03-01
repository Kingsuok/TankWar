package com.su.tankwar;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetClient {
	
	// define the UDP_PORT, 由于udp port不能定死，因为如果定死，同一台电脑上起两个client，是不能使用同一个UDP port
	private static int UPD_PORT_START = 2222;
	private int UPD_PORT = 0;
	
	
	public NetClient() {
		// 为了处理一台电脑上开启连个client， 让UPD_PORT值++
		this.UPD_PORT = UPD_PORT_START++;
	}


	// connect to the server
	public void connect(String ip, int port){
		Socket socket = null;
		//apply to connect to the server
		try {
			socket = new Socket(ip, port);
			// send UDP_PORT to the server
			DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
			writer.writeInt(UPD_PORT);
			System.out.println("Connection success:");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			// 这里使用udp进行传输信息，所以当tcp把client的upd的端口号和ip告诉了server后就关闭，tcp只是起到了握手的作用
			if (socket != null){
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				socket = null;
			}
		}
	}
}
