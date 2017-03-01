package com.su.tankwar;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/**
 * ʵ�� ���� ���� tank�� ��Ӽ��̼�����
 * 
 * @author su
 *
 */
public class TankClient extends Frame{
	
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;
	
	
	private int x = 50, y = 50;
	private Image offScreenImage = null;// use image to store paint at first
	
	
	
	// ��д TankClient �ĸ���ķ���
	//override container's method
	// paint a circle to be a tank
	public void paint(Graphics g) {
		Color color = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);
		//y += 5;
		g.setColor(color);
	}
	
	//����ÿ��repaint�ػ�ʱ�������ȵ���update������Ȼ��update�����ڵ���paint����
	@Override
	public void update(Graphics g) {
		// TODO Auto-generated method stub
		if (offScreenImage == null){// judge whether offScreenImage has been created, no : create
			offScreenImage = this.createImage(SCREEN_WIDTH, SCREEN_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		//�Ȱ�frameˢ��һ��
		Color color = gOffScreen.getColor();
		gOffScreen.setColor(Color.green);
		gOffScreen.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		gOffScreen.setColor(color);
		
		//paint tank using the pen of the offScreenImage
		paint(gOffScreen);
		//��offScreenImage���������frame��
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
        
		// ���ü������������ڹرգ������ַ�ʽ����ʹ�ü������࣬1���ⲿ�࣬ 2�� �ڲ���  3�������ڲ���
		//�����ڲ����ʹ�ó��ϣ����С�����漰��������չ�����漰��Ҫ��ҵ���߼�
		//����ʹ�������ڲ���
		//set close
		this.addWindowListener(new WindowAdapter() {
            // override the windowClosing
			public void windowClosing(WindowEvent e) {
				System.exit(0);// normal shutdown
			}
			
		});
		
		//add keyMonitor
		this.addKeyListener(new KeyMonitor());
		
		// set show 
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
	//�����ػ�frame����ֻΪTankClient����񣬲�λ������������ʹ���ڲ���
	private class PaintThread implements Runnable{

		@Override
		public void run() {
			while (true){
				repaint();// �������ڲ��࣬���Կ��Է���TankClient�ĳ�Ա���Ժͷ����������repaint��TankClient�� repaint����
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	//���ڼ��̼�����Ҳ��ֻ��ΪtankClient �����ҿ���ʹ����ĳ�Ա���Ժͷ����������ڲ���ʵ�֣������ⲿ�࣬�������ࡣ
	//�ڲ��࣬ʵ�ֿ���ʹ�ü̳� KeyAdapter �� ʵ�� KeyListener�ӿڣ�����ѡ��KeyListenerҪʵ�����еĽӿں�������Щ����ʱ����Ҫ�ģ����鷳��
	//����ʹ�ü̳�KeyAdapter��
	private class KeyMonitor extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			//get the pressed key code
			int keyCode = e.getKeyCode();
			switch(keyCode){
			case KeyEvent.VK_LEFT:
				x -= 5;
				break;
			case KeyEvent.VK_UP:
				y -= 5;
				break;
			case KeyEvent.VK_RIGHT:
				x += 5;
				break;
			case KeyEvent.VK_DOWN:
				y += 5;
				break;
				
			}
		}
		
	}

}
