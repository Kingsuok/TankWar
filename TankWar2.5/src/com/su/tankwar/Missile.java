package com.su.tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.su.tankwar.Tank.Direction;

public class Missile {
	// missile's position
	private int x = 0, y = 0;
	
	//TankClient quote
	private TankClient  tankClient = null;
	
	// missle's status: live or not 
	private boolean live = true;
	
	// missle from which tank: false: enemy, true: my
	private boolean good = false;
	
	// missile's width and height
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	//missile speed
	public static final int XSPEED = 10;
	public static final int YSPEED = 10;
	
	//direction based on the tank's direction
	private Tank.Direction direction;
	
	public Missile(int x, int y, Tank.Direction direction) {
		super();
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	// Ôö¼Ó tankClient quote
	public Missile(int x, int y, Tank.Direction direction, Boolean good, TankClient tankClient){
		this(x, y, direction);
		this.tankClient = tankClient;
		this.good = good;
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
		if ( this.live && tank.isLive() && this.getRectangle().intersects(tank.getRectangle()) && this.good != tank.isGood()){
			if (tank.isGood()){
				tank.setLife(tank.getLife() - 20);
				if (tank.getLife() <= 0){
					tank.setLive(false);
				}
			}else {
				tank.setLive(false);// intersection: tank's live -> false
			}
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
	
	// hit the wall
	public boolean hitWall(Wall wall){
		if (this.live && this.getRectangle().intersects(wall.getRectangle())){
			this.live = false;
			return true;
		}
		return false;
	}
	
}
