package com.su.tankwar;
/**
 * ʵ��һ������
 */
import java.awt.Color;
import java.awt.Frame;

public class TankClient extends Frame{
	
	private void launchFrame(){
		this.setLocation(400,300);//window location
		this.setSize(800, 600);//window size
		this.setTitle("TankWar");
		// set the background 
		this.setBackground(Color.GREEN);
		setVisible(true);//visible
	}
	public static void main(String[] args) {
		TankClient tankClient = new TankClient();
		tankClient.launchFrame();
	}

}
