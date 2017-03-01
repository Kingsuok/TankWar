package com.su.tankwar;

import java.awt.Color;
import java.awt.Graphics;

public class Blast {
	// the position of the blast
	private int x, y;
	
	// the existence of the blast
	private boolean live = true;
	
	// TankClient 
	private TankClient tankClient = null;
	
	// the the diameter of the blast 
	private int[] diameter = {4, 7, 12, 18, 40, 15, 8, 3};
	
	// the step of change of the diameter of the blast
	private int step = 0;

	public Blast(int x, int y, TankClient tankClient) {
		this.x = x;
		this.y = y;
		this.tankClient = tankClient;
	}
	
	//draw the blast
	public void draw(Graphics g){
		
		// judge whether the blast is dead  
		if (!live || step == diameter.length){
			live = false;
			tankClient.removeBlast(this);
			step = 0;
			return;
		}
		
		Color color = g.getColor();
		g.setColor(Color.yellow);
		g.fillOval(x, y, diameter[step], diameter[step]);
		g.setColor(color);
		++ step;
	}
	
	
}
