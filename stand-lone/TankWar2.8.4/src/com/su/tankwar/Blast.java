package com.su.tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Blast {
	// the position of the blast
	private int x, y;
	
	// the existence of the blast
	private boolean live = true;
	
	// TankClient 
	private TankClient tankClient = null;
	
	// toolkit to get the resource in the PC, like the images
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	// import the images
	// because the images only need to be imported once, so it should be static
	private static Image[] images = {
			toolkit.getImage(Blast.class.getClassLoader().getResource("images/0.gif")),
			toolkit.getImage(Blast.class.getClassLoader().getResource("images/1.gif")),
			toolkit.getImage(Blast.class.getClassLoader().getResource("images/2.gif")),
			toolkit.getImage(Blast.class.getClassLoader().getResource("images/3.gif")),
			toolkit.getImage(Blast.class.getClassLoader().getResource("images/4.gif")),
			toolkit.getImage(Blast.class.getClassLoader().getResource("images/5.gif")),
			toolkit.getImage(Blast.class.getClassLoader().getResource("images/6.gif")),
			toolkit.getImage(Blast.class.getClassLoader().getResource("images/7.gif")),
			toolkit.getImage(Blast.class.getClassLoader().getResource("images/8.gif")),
			toolkit.getImage(Blast.class.getClassLoader().getResource("images/9.gif")),
			toolkit.getImage(Blast.class.getClassLoader().getResource("images/10.gif"))
	};
	
	private static boolean initialImages = false; 
	
	// the step of change of the diameter of the blast
	private int step = 0;

	public Blast(int x, int y, TankClient tankClient) {
		this.x = x;
		this.y = y;
		this.tankClient = tankClient;
	}
	
	//draw the blast
	public void draw(Graphics g){
		
		// judge show the images firstly
		if (initialImages == false){
			// show image out of the screen and the image will not show for  the first time 
			for (int i = 0; i < images.length; ++i){
				g.drawImage(images[i], -100, -100, null);
			}
			initialImages = true;
		}
		
		// judge whether the blast is dead  
		if (!live || step == images.length){
			live = false;
			tankClient.removeBlast(this);
			step = 0;
			return;
		}
		
		g.drawImage(images[step], x, y, null);
		++ step;
	}
	
	
}
