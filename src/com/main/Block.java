package com.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Block {
	private Rectangle block;
	private boolean alive;
	public static final int VALUE = 80;

	public Block(int x, int y, int width, int height) {
		alive = true;
		block = new Rectangle(x, y, width, height);
	}

	public void render(Graphics graphics) {
		if (alive) {
			graphics.setColor(new Color(0, 186, 0));
			graphics.fillRect(block.x, block.y, block.width, block.height);
			graphics.setColor(new Color(31, 0, 15));
			graphics.drawRect(block.x, block.y, block.width, block.height);
		}
	}

	public boolean collidesWith(Rectangle object) {
		//return block.intersects(object);
		return !alive ? false : block.intersects(object);
	}

	public void destroy() {
		alive = false;
	}
	
	public boolean isAlive() {
		return !alive;
	}
}
