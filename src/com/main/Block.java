package com.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Block {
	private Rectangle block;
	
	public Block(int x, int y, int width, int height) {
		block = new Rectangle(x, y, width, height);
	}
	
	public void render(Graphics graphics) {
		graphics.setColor(new Color(36, 143, 36));
		graphics.fillRect(block.x, block.y, block.width, block.height);
		graphics.setColor(new Color(31, 0, 15));
		graphics.drawRect(block.x, block.y, block.width, block.height);
	}
}
