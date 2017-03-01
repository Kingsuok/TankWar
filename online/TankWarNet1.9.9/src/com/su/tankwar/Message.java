package com.su.tankwar;

import java.io.DataInputStream;
import java.net.DatagramSocket;

public interface Message {
	// message type
	public static final int TANK_NEW_MESSAGE = 1;
	public static final int TANK_MOVE_MESSAGE = 2;
	public static final int MISSILE_NEW_MESSAGE = 3;
	
	// message process
	public void send(DatagramSocket datagramSocket, String IP, int udpPort);
	public void parse(DataInputStream dataInputStream);
}
