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

public class TankNewMessage {
	private Tank tank;
	
	public TankNewMessage(Tank tank){
		this.tank = tank;
	}
    public TankNewMessage(){
    	
    }
	public void send(DatagramSocket datagramSocket, String ip, int udpPort) {
		// TODO Auto-generated method stub
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
		try {
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
			int x = dataInputStream.readInt();
			int y = dataInputStream.readInt();
			Direction direction = Direction.values()[dataInputStream.readInt()];
			boolean good = dataInputStream.readBoolean();
			System.out.println("id: " + tankID + " X: " + x + " y:" + y + " direction: " + direction +" good: " + good);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
