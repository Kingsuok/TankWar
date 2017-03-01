package com.su.tankwar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

import javax.xml.crypto.Data;

public class TankNewMessage implements Message {
	private Tank tank;
	
	private int messageType = Message.TANK_NEW_MESSAGE;
	
	private TankClient tankClient = null;
	
	//send message then call this constructor
	public TankNewMessage(Tank tank){
		this.tank = tank;
	}
	// receive the message then call this constructor
    public TankNewMessage(TankClient tankClient){
    	this.tankClient = tankClient;
    }
	public void send(DatagramSocket datagramSocket, String ip, int udpPort) {
		// TODO Auto-generated method stub
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
		try {
			dataOutputStream.writeInt(messageType);
			dataOutputStream.writeInt(tank.getTankID());
			dataOutputStream.writeInt(tank.getX());
			dataOutputStream.writeInt(tank.getY());
			dataOutputStream.writeInt(tank.getTankDirection().ordinal());
			dataOutputStream.writeBoolean(tank.isGood());
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
			int x = dataInputStream.readInt();
			int y = dataInputStream.readInt();
			Direction direction = Direction.values()[dataInputStream.readInt()];
			boolean good = dataInputStream.readBoolean();
			System.out.println("id: " + tankID + " X: " + x + " y:" + y + " direction: " + direction +" good: " + good);
		    Tank tank = new Tank(x, y, good, direction, tankClient);
		    tank.setTankID(tankID);
		    tankClient.getEnemyTanks().add(tank);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
