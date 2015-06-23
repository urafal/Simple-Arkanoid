package com.main;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel {

	private Dimension gameWindow;
	private Player player;
	private Ball ball;
	private volatile boolean isRunning = false;
	private volatile boolean isPaused = false;
	private int border = 100;

	Game(JFrame windowFrame) {
		super.setSize(windowFrame.getSize());

		gameWindow = new Dimension(windowFrame.getWidth() - border, windowFrame.getHeight() - border);

		int tmpWidth = (gameWindow.width - Player.WIDTH) / 2;
		int tmpHeight = (gameWindow.height - Player.HEIGHT) / 2;

		player = new Player(this, tmpWidth, tmpHeight);
		player.setPositionY(gameWindow.height - Player.HEIGHT);

		ball = new Ball(this);
		
		startGame(windowFrame);
	}
	
	private void startGame(JFrame frame) {
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				if (!isRunning || isPaused) {
					if (event.getKeyCode() == event.VK_ENTER) start();	
				} else {
					if (event.getKeyCode() == event.VK_RIGHT)
						player.move(25);
					if (event.getKeyCode() == event.VK_LEFT)
						player.move(-25);
				}
			}
		});
	}
	
	Thread gameThread = new Thread(new Runnable() {
		public void run() {
			isRunning = true;
			ball.setVector(10, 10);
			while (isRunning) {
				if (!isPaused) {
					ball.tick();
					repaint();
					try {
						Thread.sleep(40);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	});

	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
		graphics.translate((this.getWidth() - gameWindow.width) / 2, (this.getHeight() - gameWindow.height) / 2);
		graphics.setColor(new Color(31, 0, 15));
		graphics.fillRect(0, 0, gameWindow.width, gameWindow.height);
		player.render(graphics);
		ball.render(graphics);
	}

	public void fail() {
		pause();
		ball.setPosition(gameWindow.width / 2, gameWindow.height / 2);
		player.setPositionX((gameWindow.width - player.WIDTH) / 2);
		repaint();
	}

	public void start() {
		isPaused = false;
		if (!isRunning) gameThread.start();
	}

	public void pause() {
		isPaused = true;
	}

	public Dimension getGameWindow() {
		return gameWindow;
	}

	public void setGameWindow(Dimension gameWindow) {
		this.gameWindow = gameWindow;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
