package com.main;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;


public class Ball {
	public static final int RADIUS = 7;
	private Game instance;
	private Dimension vector;
	private Point position;
	
	public Ball(Game instance) {
		vector = new Dimension(0, 0);
		position = new Point(instance.getGameFrame().width / 2, instance.getGameFrame().height / 2);
		this.instance = instance;
	}
	
	public void setPosition(int x, int y) {
		position = new Point(x, y);
	}
	
	public void setVector(int x, int y) {
		vector = new Dimension(x, y);
	}
	
	public void tick() {
		if (position.x - RADIUS*2 <= 0 && vector.width < 0) vector.width = -vector.width;
		if ((position.x + RADIUS*2) >+ instance.getGameFrame().width && vector.width > 0) vector.width = -vector.width;
		if (position.y - RADIUS*2 <= 0 && vector.height < 0) vector.height = -vector.height;
		if ((position.y + RADIUS*2) >= instance.getGameFrame().height && vector.height > 0) instance.fail();
		
		if(instance.getPlayer() != null) {
			if(instance.getPlayer().collidesWith(new Rectangle(position.x - RADIUS, position.y - RADIUS, RADIUS*2, RADIUS*2))) {
				vector.height = -vector.height;
			}
		}
		boolean win = true;
		for(Block[] blarr : instance.getBlocks()) {
			for(Block bl: blarr) {
				if (bl.collidesWith(new Rectangle(position.x - RADIUS, position.y - RADIUS, RADIUS*2, RADIUS*2))) {
					bl.destroy();
					vector.height = -vector.height;
					instance.addScore(Block.VALUE);
					for(Block[] blarr2 : instance.getBlocks()) {
						for (Block bl2 : blarr2) {
							if (bl2.isAlive()) win = false;
						}
					}
					if (win) instance.setWin(true);
				}
			}
		}
		position.move(position.x + vector.width, position.y + vector.height);
	}
	
	
	
	public void render(Graphics graphics) {
		graphics.setColor(new Color(178, 0, 71));
		graphics.fillOval(position.x - RADIUS, position.y - RADIUS, RADIUS * 2, RADIUS * 2);
	}
}
