package com.su.tankwar;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/**
 * 处理 窗口大小 随时改变 改动多处不变的问题：
 * 把窗口的 size 设置成 常量
 * 
 * @author su
 *
 */
public class TankClient extends Frame{
	
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;
	
	
	private int x = 50, y = 50;
	private Image offScreenImage = null;// use image to store paint at first
	
	
	
	// 重写 TankClient 的父类的方法
	//override container's method
	// paint a circle to be a tank
	public void paint(Graphics g) {
		Color color = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);
		y += 5;
		g.setColor(color);
	}
	
	//由于每次repaint重画时，都会先调用update方法，然后update方法在调用paint方法
	@Override
	public void update(Graphics g) {
		// TODO Auto-generated method stub
		if (offScreenImage == null){// judge whether offScreenImage has been created, no : create
			offScreenImage = this.createImage(SCREEN_WIDTH, SCREEN_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		//先把frame刷新一遍
		Color color = gOffScreen.getColor();
		gOffScreen.setColor(Color.green);
		gOffScreen.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		gOffScreen.setColor(color);
		
		//paint tank using the pen of the offScreenImage
		paint(gOffScreen);
		//把offScreenImage画到桌面的frame上
		g.drawImage(offScreenImage, 0, 0, null);
	}

	
	private void launchFrame(){
		this.setLocation(400,300);//window location
		this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);//window size
		this.setTitle("TankWar");
		//size fixed
		this.setResizable(false);
		// set the background 
		this.setBackground(Color.GREEN);
        
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
		//repaint the frame: start thread
		PaintThread paintThread = new PaintThread();
		Thread thread = new Thread(paintThread);
		thread.start();
		
	}
	public static void main(String[] args) {
		TankClient tankClient = new TankClient();
		tankClient.launchFrame();
	}
	
	//*************
	//由于重画frame的类只为TankClient类服务，部位别的类服务，所以使用内部类
	private class PaintThread implements Runnable{

		@Override
		public void run() {
			while (true){
				repaint();// 由于是内部类，所以可以访问TankClient的成员属性和方法，这里的repaint是TankClient的 repaint方法
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

}
