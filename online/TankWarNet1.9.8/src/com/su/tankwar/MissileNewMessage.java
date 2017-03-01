package com.su.tankwar;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class MissileNewMessage implements Message {
	
	private Missile missile = null;
	private TankClient tankClient = null;
	private int messageType = Message.MISSILE_NEW_MESSAGE;
	
	// send message
	public MissileNewMessage(Missile missile) {
		this.missile = missile;
	}

	// receive message 
	public MissileNewMessage(TankClient tankClient) {
		// TODO Auto-generated constructor stub
		this.tankClient = tankClient;
	}
	
	@Override
	public void send(DatagramSocket datagramSocket, String IP, int udpPort) {
		// TODO Auto-generated method stub
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
		try {
			dataOutputStream.writeInt(messageType);
			dataOutputStream.writeInt(missile.getTankID());
			dataOutputStream.writeInt(missile.getX());
			dataOutputStream.writeInt(missile.getY());
			dataOutputStream.writeInt(missile.getDirection().ordinal());
			dataOutputStream.writeBoolean(missile.isGood());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] buffer = byteArrayOutputStream.toByteArray();
		DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, new InetSocketAddress(IP, udpPort));
		try {
			datagramSocket.send(datagramPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void parse(DataInputStream dataInputStream) {
		// TODO Auto-generated method stub
		try {
			int tankID = dataInputStream.readInt();
			
			if (tankClient.getMyTank().getTankID() == tankID){
				return;
			}
			int x = dataInputStream.readInt();
			int y = dataInputStream.readInt();
		    Direction direction = Direction.values()[dataInputStream.readInt()];
			boolean good = dataInputStream.readBoolean();
			
		    Missile missile = new Missile(x, y, direction, tankID, good, tankClient);
		    tankClient.getMissiles().add(missile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
