package com.su.tankwar;

import java.awt.Color;
import java.awt.Graphics;

import com.su.tankwar.Tank.Direction;

public class Missile {
	// missile's position
	private int x = 0, y = 0;
	
	//TankClient quote
	private TankClient  tankClient = null;
	
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
	public Missile(int x, int y, Tank.Direction direction, TankClient tankClient){
		this(x, y, direction);
		this.tankClient = tankClient;
	}
	//draw a missile
	public void draw(Graphics g){
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
			this.tankClient.removeMissile(this);
		}
	}
	
	
	
}
