package com.su.tankwar;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.DatagramSocketImpl;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.Buffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;

public class TankServer {
	//define TCP_PORT
	public static final int TCP_PORT = 8888;
	
	// define UDP_PORT for server
	public static final int UDP_PORT = 6666;
	
	//store all the clients
	private ArrayList<Client>  clients = new ArrayList<>();
	
	// assign ID to every client 
	private static int clientIdStart = 100;
	
	private void start(){
		
	
	   // thread to UDPThread must be the first
		UDPThread uThread = new UDPThread();
		Thread thread = new Thread(uThread);
		thread.start();
		
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(TCP_PORT);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
			while (true) {
				Socket socket = null;
				try {
				socket = serverSocket.accept();
				// receive the client message
				DataInputStream reader = new DataInputStream(socket.getInputStream());
				String clientIP = socket.getInetAddress().getHostAddress();
				int udpPort = reader.readInt();
				Client client = new Client(clientIP, udpPort);
				clients.add(client);
				
				// send the assigned ID to the client
				DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
				writer.writeInt(clientIdStart++);
				
				System.out.println("A client connected to the server: address--" + socket.getInetAddress());
			}
		 catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
					socket = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		   }
     	}
	
	}
	
	public static void main(String[] args){
		TankServer tankServer = new TankServer();
		tankServer.start();
		
	}
	
	// client class
	private class Client{
		public String ip = null;
		public int UDP_PORT = 0;
		public Client(String ip, int udpPort) {
			this.ip = ip;
			UDP_PORT = udpPort;
		}
	}
	
	// thread to deal with listending the message 
	private class UDPThread implements Runnable{
		// receive the data from udp
		byte[] buffer = new byte[1024];
		@Override
		public void run() {
			// TODO Auto-generated method stub
			DatagramSocket datagramSocket = null;
			try {
				datagramSocket = new DatagramSocket(UDP_PORT);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			while (datagramSocket != null){
				DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
				try {
					//receive message
					datagramSocket.receive(datagramPacket);
					
					// transmit to other clients
					for (int i = 0; i < clients.size(); ++i){
						Client  client = clients.get(i);
						String IP = client.ip;
						int udpPort = client.UDP_PORT;
						// set the ip and port
						datagramPacket.setSocketAddress(new InetSocketAddress(IP, udpPort));
						datagramSocket.send(datagramPacket);
					}
					
					System.out.println("receive a packet");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}
}
