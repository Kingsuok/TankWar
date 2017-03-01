package com.su.tankwar;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.List;
import java.awt.TextField;
import java.awt.RenderingHints.Key;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.security.acl.Group;
import java.util.ArrayList;

import javax.swing.JComboBox;

import org.omg.PortableInterceptor.ServerRequestInterceptor;

import com.su.tankwar.Direction;
/**
 *���� ����ѡ�� �Ǹ��ӵ�̹�ˣ��Լ����Դ�̹����ʧ
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
	
	public ArrayList<Missile> getMissiles() {
		return missiles;
	}

	public void setMissiles(ArrayList<Missile> missiles) {
		this.missiles = missiles;
	}

	//container for blast
	private ArrayList<Blast> blasts = new ArrayList<Blast>();
	
	// contatiner for tanks
	private ArrayList<Tank> tanks = new ArrayList<Tank>();
	private int enemyNum;
	
	public int getEnemyNum() {
		return enemyNum;
	}

	public void setEnemyNum(int enemyNum) {
		this.enemyNum = enemyNum;
	}

	public ArrayList<Tank> getEnemyTanks() {
		return tanks;
	}

	public static final String serverIP = "192.168.0.11";
	//private Missile missile = null; // ������private��so there is a method to set it
	
	//private Tank myTank = new Tank(80, 80, true,Direction.STOP, this); // instantiate a tank ++++++++
	private boolean tankGroup = true;
	private Tank myTank;
	public Tank getMyTank() {
		return myTank;
	}

	public void setMyTank(Tank myTank) {
		this.myTank = myTank;
	}

	//NetCliet
	private NetClient netClient = new NetClient(this, serverIP, TankServer.UDP_PORT);
	
	public NetClient getNetClient() {
		return netClient;
	}

	// dialog 
	private ConnectionDialog connectionDialog = new ConnectionDialog();
	
	// ��д TankClient �ĸ���ķ���
	//override container's method
	// paint a circle to be a tank
	public void paint(Graphics g) {

		
		
		//draw all the missiles
		for (int i = 0 ; i < missiles.size(); i ++){
		    Missile m = missiles.get(i);
		    // check whether the missle hit the tank
		    m.hitTanks(tanks);
			m.draw(g);
		}
	    
		// draw all the blasts
		for (int i = 0; i < blasts.size(); ++i){
			Blast b = blasts.get(i);
			b.draw(g);
		}
        
		int coutEnemyTank = 0;
		// draw all the tanks
		for (int i = 0; i < tanks.size(); ++i){
			Tank t = tanks.get(i);
			if (t.isGood() != myTank.isGood()){
				++coutEnemyTank; 
			}
			t.draw(g);
		}
		enemyNum = coutEnemyTank;
		
		// show the quantity of the missiles 
		g.drawString("Misslie's quantity: " + missiles.size(), 10, 40);
		
		// show the quantity of the missiles 
		g.drawString("Blast's quantity: " + blasts.size(), 10, 55);
		
		// show the quantity of the enemy tanks 
		g.drawString("Enemy's quantity: " + enemyNum, 10, 70);
		
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
	
	// set attribute: enemyTanks because it is a private 
		public void addTank(Tank tank){ 
			this.tanks.add(tank);
		}
	// remove blast from the missiles
		public void removeTank(Tank tank){
			this.tanks.remove(tank);
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
		/*// add enemies 
		for (int i = 0; i < 10; ++i){
			Tank  tank = new Tank(50 * (i +3), 50, false, this );
			enemyTanks.add(tank);
		}*/
		
		this.setLocation(300,60);//window location
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
		
		//set the client's upd port
		connectionDialog.setVisible(true);
		
		// connect to the server 
		//netClient.connect(serverIP,TankServer.TCP_PORT);
		
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
			//if (e.getKeyCode() == KeyEvent.VK_C){
				//connectionDialog.setVisible(true);
			//}else{
				myTank.keyReleased(e);//+++++++++
			//}
			
		}
	}
	
	// dialog class
	private class ConnectionDialog extends Dialog{
		private Button button = new Button("OK");
		//private TextField serverTcpIP = new TextField(TankServer.TCP_IP, 12);
		//private TextField serverTcpPort = new TextField("" + TankServer.TCP_PORT,4);
		//private TextField serverUdpPort = new TextField("" + TankServer.UDP_PORT,4);
		// client udp port: this will be set the default value
		private TextField clientUdpPort = new TextField(4);
		private JComboBox comboBox = new JComboBox();
		private boolean group = true;

		public ConnectionDialog() {
			// dialog's owner: TankClient.this , model dialog-> true
			super(TankClient.this, true);
			// TODO Auto-generated constructor stub
			// set the layout of the dialog
			this.setLayout(new FlowLayout());
//			this.add(new Label("SERVER-TCP-IP:"));
//			this.add(serverTcpIP);
//			serverTcpIP.setEditable(false);
//			this.add(new Label("SERVER-TCP-PORT:"));
//			this.add(serverTcpPort);
//			serverTcpPort.setEditable(false);
//			this.add(new Label("SERVER-UDP-PORT:"));
//			this.add(serverUdpPort);
//			serverUdpPort.setEditable(false);
			this.add(new Label("UDP-PORT:"));
			//get the updPort
			String udpPortValue = PropertyManager.getProperty("CLIENT_UDP_PORT");
			clientUdpPort.setText(udpPortValue);
		
			this.add(clientUdpPort);
			
			this.add(new Label("GROUP:"));
			comboBox.addItem("group 1");
			comboBox.addItem("group 2");
			this.add(comboBox);
			this.add(button);
			this.setLocation(500, 300);
			//set the size, ע�⣬��������������е�components֮�󣬷���֮���component����ʾ
			this.pack();
			
			this.addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosing(WindowEvent e) {
					// TODO Auto-generated method stub
					setVisible(false);
				}
			});
			
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					int UDP_PORT = Integer.parseInt(clientUdpPort.getText().trim());
					PropertyManager.setProperty("CLIENT_UDP_PORT", UDP_PORT+1+"");
					netClient.setUPD_PORT(UDP_PORT);
					
					myTank = new Tank(80, 80, group,Direction.STOP, TankClient.this);
					tanks.add(myTank);
					// connect to the server 
					netClient.connect(serverIP,TankServer.TCP_PORT);
					
					setVisible(false);					
				}
			});
			
			comboBox.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if (comboBox.getSelectedItem().equals("group 1")){
						group = true;
					}else{
						group = false;
					}
				}
			});
			
		}
		
	}

}
