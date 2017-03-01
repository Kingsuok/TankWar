package com.su.tankwar;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetClient {
	
	// define the UDP_PORT, ����udp port���ܶ�������Ϊ���������ͬһ̨������������client���ǲ���ʹ��ͬһ��UDP port
	private static int UPD_PORT_START = 2222;
	private int UPD_PORT = 0;
	
	// Ϊ�˿��Ը�Tank���󣬸�ֵassigned ID�� ���Գ��жԷ����ã�
	private TankClient tankClient = null;
	
	
	public NetClient(TankClient tankClient) {
		// Ϊ�˴���һ̨�����Ͽ�������client�� ��UPD_PORTֵ++
		this.UPD_PORT = UPD_PORT_START++;
		this.tankClient = tankClient;
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
			
			// receive the assigned ID from the server
			DataInputStream reader = new DataInputStream(socket.getInputStream());
			int tankID = reader.readInt();
			// assign the ID to a tank
			tankClient.getMyTank().setTankID(tankID);
			
			System.out.println("Connection success:");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			// ����ʹ��udp���д�����Ϣ�����Ե�tcp��client��upd�Ķ˿ںź�ip������server��͹رգ�tcpֻ���������ֵ�����
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
