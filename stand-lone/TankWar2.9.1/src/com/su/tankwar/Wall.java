package com.su.tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import org.w3c.dom.css.Rect;

public class Wall {
	// the position and width, height of the wall
	private int x, y, width, height;
	
	//tankClient
	private TankClient tankClient = null;

	public Wall(int x, int y, int width, int height, TankClient tankClient) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.tankClient = tankClient;
	}
	
	// draw the wall
	public void draw(Graphics g){
		Color color = g.getColor();
		g.setColor(Color.green);
		g.fillRect(x, y, width, height);
		g.setColor(color);
	}
	
	// rectangle for collide with the tank or the missile
	public Rectangle getRectangle(){
		return new Rectangle(x, y, width, height);
	}
}
