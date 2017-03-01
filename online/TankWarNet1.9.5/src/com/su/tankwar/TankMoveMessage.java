package com.su.tankwar;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class TankMoveMessage implements Message {

	private int tankID;
	private Direction direction;
	
	private int messageType = Message.TANK_MOVE_MESSAGE;
	
	private TankClient tankClient = null;
	
	public TankMoveMessage(int tankID, Direction direction) {
		super();
		this.tankID = tankID;
		this.direction = direction;
	}
    public TankMoveMessage(TankClient tankClient) {
		// TODO Auto-generated constructor stub
    	this.tankClient = tankClient;
	}
    
    public void send(DatagramSocket datagramSocket, String ip, int udpPort) {
		// TODO Auto-generated method stub
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
		try {
			dataOutputStream.writeInt(messageType);
			dataOutputStream.writeInt(tankID);
			dataOutputStream.writeInt(direction.ordinal());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] buffer = byteArrayOutputStream.toByteArray();
		DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, new InetSocketAddress(ip, udpPort));
		try {
			datagramSocket.send(datagramPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void parse(DataInputStream dataInputStream) {
		// TODO Auto-generated method stub
		try {
			int tankID = dataInputStream.readInt();
			if (tankClient.getMyTank().getTankID() == tankID){
				return;
			}
			Direction direction = Direction.values()[dataInputStream.readInt()];
			
			boolean exist = false;
			for (int i = 0; i < tankClient.getEnemyTanks().size(); ++i){
				Tank tank = tankClient.getEnemyTanks().get(i);
				if (tank.getTankID() == tankID){
					tank.setTankDirection(direction);
					exist = true;
					break;
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
