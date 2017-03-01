package com.su.tankwar;

import java.awt.TrayIcon.MessageType;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.omg.CORBA.PRIVATE_MEMBER;


public class NetClient {
	
	// define the UDP_PORT, ����udp port���ܶ�������Ϊ���������ͬһ̨������������client���ǲ���ʹ��ͬһ��UDP port
	private int UPD_PORT;
	
	public void setUPD_PORT(int uPD_PORT) {
		UPD_PORT = uPD_PORT;
	}

	// server's information
	private String serverIP;
	private int serverUDP_PORT;
	
	// Ϊ�˿��Ը�Tank���󣬸�ֵassigned ID�� ���Գ��жԷ����ã�
	private TankClient tankClient = null;
	
	private DatagramSocket datagramSocket = null;
	
	public NetClient(TankClient tankClient, String serverIP, int servertUDP_PORT) {
		// Ϊ�˴���һ̨�����Ͽ�������client�� ��UPD_PORTֵ++
		this.tankClient = tankClient;
		this.serverIP = serverIP;
		this.serverUDP_PORT = servertUDP_PORT;
		
	}


	// connect to the server
	public void connect(String serverIP, int TCP_PORT){
		try {
			datagramSocket = new DatagramSocket(UPD_PORT);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Socket socket = null;
		//apply to connect to the server
		try {
			socket = new Socket(serverIP, TCP_PORT);
			// send UDP_PORT to the server
			DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
			writer.writeInt(UPD_PORT);
			
			// receive the assigned ID from the server
			DataInputStream reader = new DataInputStream(socket.getInputStream());
			int tankID = reader.readInt();
			// assign the ID to a tank
			tankClient.getMyTank().setTankID(tankID);
			
			System.out.println("Connection success:+++++");
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
		
		// send tank's information through udp
		TankNewMessage tankNewMessage = new TankNewMessage(tankClient.getMyTank());
		send(tankNewMessage);
		
		UDPReceiveThread uReceiveThread = new UDPReceiveThread();
		Thread thread = new Thread(uReceiveThread);
		thread.start();
	}
	public void send(Message message){
		
		message.send(datagramSocket,serverIP, serverUDP_PORT);
	}
	
	//thread to receive the message from server
	private class UDPReceiveThread implements Runnable{


		// receive the data from udp
		byte[] buffer = new byte[1024];
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			while (datagramSocket != null){
				DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
				try {
					//receive message
					datagramSocket.receive(datagramPacket);
					parse(datagramPacket);
					System.out.println("receive a packet from the server");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		private void parse(DatagramPacket datagramPacket) {
			// TODO Auto-generated method stub
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer, 0, datagramPacket.getLength());
			DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
			try {
				Message message = null;
				int messageType = dataInputStream.readInt();
				switch (messageType) {
				case Message.TANK_NEW_MESSAGE:
					message = new TankNewMessage(tankClient);
					message.parse(dataInputStream);
					break;
				case Message.TANK_MOVE_MESSAGE:
					message = new TankMoveMessage(tankClient);
					message.parse(dataInputStream);
					break;
				default:
					break;
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
	}
}
