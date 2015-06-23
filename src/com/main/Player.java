package com.main;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Player {
	public static final int WIDTH = 70;
	public static final int HEIGHT = 15;
	
	private Rectangle hitBox;
	private Game instance;
	
	public Player(Game instance, int x, int y) {
		hitBox = new Rectangle(x, y, WIDTH, HEIGHT);
		this.instance = instance;
	}

	public void render(Graphics graphics) {
		graphics.setColor(new Color(76, 149, 197));
		graphics.fillRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);

	}

	public void setPositionY(int y) {
		hitBox.y = y;
	}
	
	public void setPositionX(int x) {
		hitBox.x = x;
	}
	
	public boolean collidesWith(Rectangle object) {
		return hitBox.intersects(object);
	}
	
	public void move(int speed) {
		hitBox.x += speed;
		if(hitBox.x < 0) hitBox.x = 0;
		if (hitBox.x >= instance.getGameWindow().width - WIDTH) 
			hitBox.x = (int) (instance.getGameWindow().width - WIDTH);
		
	}
}
