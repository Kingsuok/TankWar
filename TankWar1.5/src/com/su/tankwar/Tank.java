package com.su.tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Paint;
import java.awt.event.KeyEvent;

import com.su.tankwar.Tank.Direction;
/**
 * 按键 发射子弹并让炮弹从tank中间射出
 * @author su
 *
 */
public class Tank {
	// the position of tank
	private int x = 0, y = 0;
	
	private TankClient tankClient = null;//++++++
	
	// tank's width and height
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	
	// define the constant speed
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	
	// pressed key status: pressed -> true, not pressed -> false
	private boolean bL = false, bU = false, bR = false, bD = false;
	
	// enum: 8 direction and stop
	// enum 不能定义为private 因为，后面的missile会用到
	enum Direction {L, LU, U, RU, R, RD, D, LD, STOP};
	// default tank direction : stop
	private Direction tankDirection = Direction.STOP;
	//default missile direction: down
	private Direction barrelDirection = Direction.D;
	
	public Tank(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Tank(int x, int y, TankClient tankClient){
		this(x, y);
		this.tankClient = tankClient;
	}
	
	//draw a circle to be a tank
	public void draw(Graphics g){
		Color color = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, WIDTH, HEIGHT);
		//y += 5;
		g.setColor(color);
		
		// draw a barrel for the tank
		int originalX = x + WIDTH/2;
		int originalY = y + HEIGHT/2;
		switch (barrelDirection){
		case L:
			g.drawLine(originalX, originalY, x, originalY);
			break;
		case LU:
			g.drawLine(originalX, originalY, x, y);
			break;
		case U:
			g.drawLine(originalX, originalY, originalX, y);
			break;
		case RU:
			g.drawLine(originalX, originalY, x + WIDTH, y);
			break;
		case R:
			g.drawLine(originalX, originalY, x + WIDTH, originalY);
			break;
		case RD:
			g.drawLine(originalX, originalY, x + WIDTH, y + WIDTH);
			break;
		case D:
			g.drawLine(originalX, originalY, originalX, y + WIDTH);
			break;
		case LD:
			g.drawLine(originalX, originalY, x, y + WIDTH);
			break;
		default:
			break;	
		}		
		move();
	}
	
	
	/**
	 * tank position based on the tankDirection 
	 */
	private void move(){
		switch (tankDirection){
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		case STOP:
			break;	
		}
		
		// change the barrel direction with the tank direction
		if (tankDirection != Direction.STOP){
			barrelDirection = tankDirection;
		}
		
		// judge the tank is out the screen or not
		if (x < 0){
			x = 0;
		}
		if (y < 30){
			y = 30;
		}
		if (x > TankClient.SCREEN_WIDTH - Tank.WIDTH){
			x = TankClient.SCREEN_WIDTH - Tank.WIDTH;
		}
		if (y > TankClient.SCREEN_HEIGHT - Tank.HEIGHT){
			y = TankClient.SCREEN_HEIGHT - Tank.HEIGHT;
		}
	}
	
	//tank moves by the key event
	public void keyPressed(KeyEvent e){
		//get the pressed key code
		int keyCode = e.getKeyCode();
		switch(keyCode){
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
			
		}
		locateDirection();//determine the direction
	}
	
	private void locateDirection(){
		if (bL && !bU  && !bR  && !bD){
			tankDirection = Direction.L;
		}else if (bL== true && bU == true && bR == false && bD == false){
			tankDirection = Direction.LU;
		}else if (bL== false && bU == true && bR == false && bD == false){
			tankDirection = Direction.U;
		}else if (bL== false && bU == true && bR == true && bD == false){
			tankDirection = Direction.RU;
		}else if (bL== false && bU == false && bR == true && bD == false){
			tankDirection = Direction.R;
		}else if (bL== false && bU == false && bR == false && bD == true){
			tankDirection = Direction.D;
		}else if (bL== false && bU == false && bR == true && bD == true){
			tankDirection = Direction.RD;
		}else if (bL== true && bU == false && bR == false && bD == true){
			tankDirection = Direction.LD;
		}else if (bL== false && bU == false && bR == false && bD == false){
			tankDirection = Direction.STOP;
		}
	}

	public void keyReleased(KeyEvent e){
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_CONTROL:
			fire();// 对tankClient的missile赋值
			break;
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		default:
			break;
		}
		locateDirection();// redirection
	}
	
	private Missile fire(){
		int x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
		// create missile
		Missile missile = new Missile(x, y, barrelDirection, this.tankClient);
		tankClient.setMissile(missile);
		return missile;
	}
}
