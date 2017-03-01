package com.su.tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.nio.channels.NetworkChannel;
import java.util.ArrayList;
import java.util.Random;

import com.su.tankwar.Tank.Direction;
/**
 * 按键 发射子弹并让炮弹从tank中间射出
 * @author su
 *
 */
public class Tank {
	// the position of tank
	private int x = 0, y = 0;
	
	// judge tank is a friend or an enemy,
	private boolean good = false;
	
	// judge the tank is live or dead
	private boolean live = true;
		
	private TankClient tankClient = null;//++++++
	
	// random generator
	private static Random random = new Random();
	
	// count moved steps
	private static int steps;
	
	
	// the position before the tank collide the wall
	private int oldX, oldY;
	
	// the life value of the tank
	private int life = 100;
	
	// instantiate bloodBar
	private BloodBar bloodBar = new BloodBar();
	
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
	//default barrel direction: down
	private Direction barrelDirection = Direction.D;
	
	public Tank(int x, int y, boolean good){
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		this.good = good;
	}
	
	public Tank(int x, int y, Boolean good, Direction tankDirection, TankClient tankClient){
		this(x, y, good);
		this.tankClient = tankClient;
		this.tankDirection = tankDirection;
	}
	
	// set the tank's attribute of live
	public void setLive(boolean live){
		this.live = live;
	}
	// get the status of the attribute of live
	public boolean isLive(){
		return this.live;
	}
	
	
	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	// set the life value
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	
	//draw a circle to be a tank
	public void draw(Graphics g){
		if (!this.live){
			// if the tank is false:
			if (!good){
				tankClient.removeEnemyTank(this);
			}
			return;
		}
		Color color = g.getColor();
		if (good){
			g.setColor(Color.RED);
			// draw the bloodBar
			bloodBar.draw(g);
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
		
		// change the direction randomly after each move
		if (!good){
			// change Direction from enum to array 
			Direction[] directions = Direction.values(); 
			
			if (steps == 0){
				steps = random.nextInt(12) + 3;
				// change the direction to an array
				int randomNum = random.nextInt(directions.length);
				tankDirection = directions[randomNum];
			}
			--steps;
			
			if (random.nextInt(50) > 47) fire();
		}
	}
	
	
	/**
	 * tank position based on the tankDirection 
	 */
	private void move(){
		// record the last position
		oldX = x;
		oldY = y;
		
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
		
		// chagne the barrel direction with the tank direction
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
		
		// change the direction randomly after each move
		if (!good){
			// change Direction from enum to array 
			Direction[] directions = Direction.values(); 
			
			if (steps == 0){
				steps = random.nextInt(12) + 3;
				// change the direction to an array
				int randomNum = random.nextInt(directions.length);
				tankDirection = directions[randomNum];
			}
			--steps;
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
		case KeyEvent.VK_A:
			superMissile();
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
		if (!live){
			return null;
		}
		int x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
		// create missile
		Missile missile = new Missile(x, y, barrelDirection, this.good, this.tankClient);
		tankClient.addMissile(missile);
		return missile;
	}
	
	// fire based on a direction
	private Missile fire(Direction direction){
		if (!live){
			return null;
		}
		int x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
		// create missile
		Missile missile = new Missile(x, y, direction, this.good, this.tankClient);
		tankClient.addMissile(missile);
		return missile;
	}
	
	// get the rectangle of the tank
	public Rectangle getRectangle(){
		return  new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	private void stay(){
		this.x = oldX;
		this.y = oldY;
	}
	public boolean hitWall(Wall wall){
		if (this.live && this.getRectangle().intersects(wall.getRectangle())){
			stay();
			return true;
		}
		return false;
	}
	
	public boolean hitTank(Tank tank){
		if (this.live && this != tank && this.getRectangle().intersects(tank.getRectangle())){
			this.stay();
			return true;
		}
		return false;
	}
	
	public boolean hitTanks(ArrayList<Tank> enemyTanks){
		for (int i = 0; i < enemyTanks.size(); ++i){
			Tank tank = enemyTanks.get(i);
			if (hitTank(tank)){
				stay();
				return true;
			}
		}
		return false;
	}
	
	public void superMissile(){
		Direction[] directions = Direction.values();
		//for (int i = 0; i < directions.length; ++i){ // 不能使用 directions.length因为会有一个stop
		for (int i = 0; i < 8; ++i){
			fire(directions[i]);
		}
	}
	
	// 由于blood bar 是隶属于 tank 但是又是一个 比较独立的类 所以使用内部类
	private class BloodBar{
		private void draw(Graphics g){
			//draw a hollow
			Color color = g.getColor();
			g.setColor(Color.orange);
			g.drawRect(x, y - 10, WIDTH, 10);
			int bloodWidth = WIDTH * life/100;
			g.fillRect(x, y - 10, bloodWidth, 10);
			g.setColor(color);
		}
	}
}
