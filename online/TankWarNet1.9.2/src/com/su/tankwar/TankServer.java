package com.su.tankwar;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TankServer {
	//define TCP_PORT
	public static final int TCP_PORT = 8888;
	
	//store all the clients
	private ArrayList<Client>  netClients = new ArrayList<>();
	
	private void start(){
		Socket socket = null;
		try {
			ServerSocket serverSocket = new ServerSocket(TCP_PORT);
			
			while (true) {
				socket = serverSocket.accept();
				// receive the client message
				DataInputStream reader = new DataInputStream(socket.getInputStream());
				String clientIP = socket.getInetAddress().getHostAddress();
				int udpPort = reader.readInt();
				Client client = new Client(clientIP, udpPort);
				netClients.add(client);
				System.out.println("A client connected to the server: address--" + socket.getInetAddress());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
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
	
	public static void main(String[] args){
		TankServer tankServer = new TankServer();
		tankServer.start();
		
	}
	
	// client class
	private class Client{
		public String ip = null;
		public int UDP_PORT = 0;
		public Client(String ip, int udpPort) {
			super();
			this.ip = ip;
			UDP_PORT = udpPort;
		}
	}
}
