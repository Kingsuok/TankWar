package com.su.tankwar;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/**
 * 
 * @author su
 * 增加，关闭窗口和窗口大小固定不变
 */
public class TankClient extends Frame{
	
	
	private void launchFrame(){
		this.setLocation(400,300);//window location
		this.setSize(800, 600);//window size
		this.setTitle("TankWar");
		// set the background 
		this.setBackground(Color.GREEN);
		
		//size fixed
		this.setResizable(false);
	
		// 设置监听器，处理窗口关闭，有三种方式可以使用监听器类，1，外部类， 2， 内部类  3，内名内部类
		//匿名内部类的使用场合：类短小、不涉及将来的扩展、不涉及重要的业务逻辑
		//这里使用匿名内部类
		//set close
		this.addWindowListener(new WindowAdapter() {
            // override the windowClosing
			public void windowClosing(WindowEvent e) {
				System.exit(0);// normal shutdown
			}
			
		});
		// set show : this code must be last 
		setVisible(true);//visible
		
	}
	public static void main(String[] args) {
		TankClient tankClient = new TankClient();
		tankClient.launchFrame();
	}

}
