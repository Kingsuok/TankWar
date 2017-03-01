package com.su.tankwar;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/**
 * 
 * @author su
 *�����Լ���̹��
 */
public class TankClient extends Frame{
	    //******
	     // ��д TankClient �ĸ���ķ���
		//override container's method
		// paint a circle to be a tank
		public void paint(Graphics g) {
			Color color = g.getColor();
			g.setColor(Color.RED);
			g.fillOval(50, 50, 30, 30);
			g.setColor(color);
		}
	
	private void launchFrame(){
		this.setLocation(400,300);//window location
		this.setSize(800, 600);//window size
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
		// set show : this code must be last 
		setVisible(true);//visible
		
	}
	public static void main(String[] args) {
		TankClient tankClient = new TankClient();
		tankClient.launchFrame();
	}

}
