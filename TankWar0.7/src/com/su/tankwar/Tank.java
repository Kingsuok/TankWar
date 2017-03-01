package com.su.tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Paint;
import java.awt.event.KeyEvent;
/**
 * 使用面向对象的思想，建立Tank类，然后进行encapsulate attributes and method
 * attribute： tank's position x and y
 * method: draw() and keyPressed() 
 * @author su
 *
 */
public class Tank {
	// the position of tank
	private int x = 0, y = 0;
	
	public Tank(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	//draw a circle to be a tank
	public void draw(Graphics g){
		Color color = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);
		//y += 5;
		g.setColor(color);
	}
	
	//tank moves by the key event
	public void keyPressed(KeyEvent e){
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
