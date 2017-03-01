package com.su.tankwar;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TankServer {
	//define TCP_PORT
	public static final int TCP_PORT = 8888;
	
	//store all the clients
	private ArrayList<Client>  netClients = new ArrayList<>();
	
	// assign ID to every client 
	private static int clientIdStart = 100;
	
	private void start(){
		
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(TCP_PORT);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Socket socket = null;
		try {
			while (true) {
				socket = serverSocket.accept();
				// receive the client message
				DataInputStream reader = new DataInputStream(socket.getInputStream());
				String clientIP = socket.getInetAddress().getHostAddress();
				int udpPort = reader.readInt();
				Client client = new Client(clientIP, udpPort);
				netClients.add(client);
				
				// send the assigned ID to the client
				DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
				writer.writeInt(clientIdStart++);
				
				System.out.println("A client connected to the server: address--" + socket.getInetAddress());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
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
