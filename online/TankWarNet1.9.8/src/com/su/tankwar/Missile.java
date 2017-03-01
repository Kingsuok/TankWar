package com.su.tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.su.tankwar.Direction;

public class Missile {
	// missile's position
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
    
	private boolean good;
	public boolean isGood() {
		return good;
	}

	//TankClient quote
	private TankClient  tankClient = null;
	
	// missle's status: live or not 
	private boolean live = true;
	
	// missile's width and height
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	//missile speed
	public static final int XSPEED = 10;
	public static final int YSPEED = 10;
	
	//direction based on the tank's direction
	private Direction direction;
	
	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	private int tankID;
	
	public int getTankID() {
		return tankID;
	}

	public Missile(int x, int y, Direction direction, int tankID,boolean good) {
		super();
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.tankID = tankID;
		this.good = good;
	}
	
	// Ôö¼Ó tankClient quote
	public Missile(int x, int y, Direction direction, int tankID, boolean good, TankClient tankClient){
		this(x, y, direction, tankID, good);
		this.tankClient = tankClient;
	}
	//draw a missile
	public void draw(Graphics g){
		// if the missile is dead, then remove the missile and not paint the missile
		if (!live){
			this.tankClient.removeMissile(this);
			return;
		}
		Color color = g.getColor();
		g.setColor(Color.black);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(color);
		move();
	}

	private void move() {
		// TODO Auto-generated method stub
		switch (direction){
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
		}
		
		// judge the missile is out of the screen or not
		if (x < 0 || x > TankClient.SCREEN_WIDTH || y < 0 || y > TankClient.SCREEN_HEIGHT){
			live = false;
		}
	}
	
	// get the rectangle of the missile
	public Rectangle getRectangle(){
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	// target the tank method
	public boolean hitTank(Tank tank){
		// judge whether the two rectangles intersect or not 
		if ( tank.isLive()&& tank.getTankID() != tankID && this.getRectangle().intersects(tank.getRectangle())){
			tank.setLive(false);// intersection: tank's live -> false
			live = false;
			Blast blast = new Blast(x, y, tankClient);
			tankClient.addBlast(blast);
			return true;
		}
		return false;
	}
	
	// hit all the enemy
	public boolean hitTanks(ArrayList<Tank> tanks){
		for (int i = 0; i < tanks.size(); ++i){
			if (hitTank(tanks.get(i))){
				return true;
			}
		}
		return false;
	}
	
}
