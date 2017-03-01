package com.su.tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import org.w3c.dom.css.Rect;

public class Blood {
	private int x, y, width, height;
	
	private boolean live = true;

	private static int steps = 0;
	
	private int[][] route = {
							{350, 300}, {350, 250}, {400, 230}, {410, 280}, {450, 300}, {500, 350}, {450,375}, {425, 320}, {400,300}, {375,250}, {360,300}
							};

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	
	// blood width and height
	public Blood(int width, int height) {
		this.width = width;
		this.height = height;
		x = route[0][0];
	    y = route[0][1];
	}
    
	public void draw(Graphics g){
		if (!live){
			return;
		}
		Color color = g.getColor();
		g.setColor(Color.magenta);
		g.fillRect(x, y, width, height);
		g.setColor(color);
		move();
	}

	private void move() {
		if (steps == route.length){
			steps = 0;
		}
		x = route[steps][0];
		y = route[steps][1];
		++steps;
	}
	
	public Rectangle getRectangle(){
		return new Rectangle(x, y, width, height);
	}
}
