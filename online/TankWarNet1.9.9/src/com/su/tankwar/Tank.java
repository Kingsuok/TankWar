package com.su.tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
/**
 * 按键 发射子弹并让炮弹从tank中间射出
 * @author su
 *
 */
public class Tank {
	// the position of tank
	private int x = 0, y = 0;
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	// judge tank is a friend or an enemy,
	private boolean good = false;
	
	public boolean isGood() {
		return good;
	}

	// judge the tank is live or dead
	private boolean live = true;
	
	// assigned ID from server
	private int tankID;
	
	public int getTankID() {
		return tankID;
	}

	public void setTankID(int tankID) {
		this.tankID = tankID;
	}

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


	// default tank direction : stop
	private Direction tankDirection = Direction.STOP;
	public void setTankDirection(Direction tankDirection) {
		this.tankDirection = tankDirection;
	}

	public Direction getTankDirection() {
		return tankDirection;
	}

	//default missile direction: down
	private Direction barrelDirection = Direction.D;
	
	public Tank(int x, int y, boolean good){
		this.x = x;
		this.y = y;
		this.good = good;
	}
	
	public Tank(int x, int y, Boolean good, Direction direction, TankClient tankClient){
		this(x, y, good);
		this.tankClient = tankClient;
		this.tankDirection = direction;
	}
	
	// set the tank's attribute of live
	public void setLive(boolean live){
		this.live = live;
	}
	// get the status of the attribute of live
	public boolean isLive(){
		return this.live;
	}
	
	
	//draw a circle to be a tank
	public void draw(Graphics g){
		if (!this.live){
			tankClient.removeTank(this);
			return;
		}
		Color color = g.getColor();
		if (good){
			g.setColor(Color.RED);	
			g.drawString("id: "+ tankID, x, y-10);
		}else{
			g.setColor(Color.blue);
		}
		
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
		Direction oldTankDirection = tankDirection;
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
		
		if (tankDirection != oldTankDirection){
			TankMoveMessage tankMoveMessage = new TankMoveMessage(tankID, tankDirection, x, y);
			tankClient.getNetClient().send(tankMoveMessage);
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
		Missile missile = new Missile(x, y, barrelDirection, tankID, good, this.tankClient);
		tankClient.addMissile(missile);
		
		// send missle message to server
		Message message = new MissileNewMessage(missile);
		tankClient.getNetClient().send(message);
		
		return missile;
	}
	
	// get the rectangle of the tank
	public Rectangle getRectangle(){
		return  new Rectangle(x, y, WIDTH, HEIGHT);
	}
}
