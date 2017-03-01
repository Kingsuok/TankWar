package com.su.tankwar;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.List;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
/**
 * �ж��tank ��ը������Ӧ������missilesһ�����ж��blast����container�д洢
 * ���� blast��λ�ú�֮��remove the blast
 * @author su
 *
 */
public class TankClient extends Frame{
	
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;
	
	
	//private int x = 50, y = 50;
	private Image offScreenImage = null;// use image to store paint at first
	
	//container for missile
	private ArrayList<Missile> missiles = new ArrayList<Missile>();
	
	//container for blast
	private ArrayList<Blast> blasts = new ArrayList<Blast>();
	
	//private Missile missile = null; // ������private��so there is a method to set it
	
	Tank myTank = new Tank(60, 60, true, this); // instantiate a tank ++++++++
	Tank enemyTank =  new Tank(100, 100, false, this); //
	
	
	
	// ��д TankClient �ĸ���ķ���
	//override container's method
	// paint a circle to be a tank
	public void paint(Graphics g) {
		myTank.draw(g);//draw itself ++++++++
		enemyTank.draw(g);
		
		//draw all the missiles
		for (int i = 0 ; i < missiles.size(); i ++){
		    Missile m = missiles.get(i);
		    // check whether the missle hit the tank
		    m.hitTank(enemyTank);
			m.draw(g);
		}
	    
		// draw all the blasts
		for (int i = 0; i < blasts.size(); ++i){
			Blast b = blasts.get(i);
			b.draw(g);
		}
		
		// show the quantity of the missiles 
		g.drawString("Misslie's quantity: " + missiles.size(), 10, 40);
		
		// show the quantity of the missiles 
		g.drawString("Blast's quantity: " + blasts.size(), 10, 55);
		
	}
	
	// set attribute: missile because it is a private 
	public void addMissile(Missile missile){ 
		this.missiles.add(missile);
	}
	// remove missile from the missiles
	public void removeMissile(Missile missile){
		this.missiles.remove(missile);
	}
	
	// set attribute: blast because it is a private 
		public void addBlast(Blast blast){ 
			this.blasts.add(blast);
		}
		// remove blast from the missiles
		public void removeBlast(Blast blast){
			this.blasts.remove(blast);
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
			myTank.keyPressed(e);//+++++++++
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			myTank.keyReleased(e);
		}
		
		
	}

}
