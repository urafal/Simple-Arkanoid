package com.main;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel {

	private Dimension gameFrame;
	private Player player;
	private Ball ball;
	private volatile boolean isRunning;
	private volatile boolean isPaused;
	private int border;
	private int playerSpeed;

	Game(JFrame mainFrame) {
		super.setSize(mainFrame.getSize());
		
		isRunning = false;
		isPaused = false;
		playerSpeed = 25;
		border = 100;
		//Instead of popular Linux DE (such as Unity or KDE), windows in Microsoft Windows OS family has borders, 
		//that closes elements of the game, so I decided to use "frame inside frame" (we need to go deeper...)
		//with the border, which controlling by a "border" variable.
		gameFrame = new Dimension(mainFrame.getWidth() - border, mainFrame.getHeight() - border);

		int tmpWidth = (gameFrame.width - Player.WIDTH) / 2;
		int tmpHeight = (gameFrame.height - Player.HEIGHT) / 2;

		player = new Player(this, tmpWidth, tmpHeight);
		player.setPositionY(gameFrame.height - Player.HEIGHT);

		ball = new Ball(this);
		
		startGame(mainFrame);
	}
	
	private void startGame(JFrame frame) {
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				if (!isRunning || isPaused) {
					if (event.getKeyCode() == event.VK_ENTER) start();	
				} else {
					if (event.getKeyCode() == event.VK_RIGHT)
						player.move(playerSpeed);
					if (event.getKeyCode() == event.VK_LEFT)
						player.move(-playerSpeed);
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
		//placing gameFrame in the middle of mainFrame
		graphics.translate((this.getWidth() - gameFrame.width) / 2, (this.getHeight() - gameFrame.height) / 2);
		graphics.setColor(new Color(31, 0, 15));
		graphics.fillRect(0, 0, gameFrame.width, gameFrame.height);
		player.render(graphics);
		ball.render(graphics);
	}

	public void fail() {
		pause();
		ball.setPosition(gameFrame.width / 2, gameFrame.height / 2);
		player.setPositionX((gameFrame.width - player.WIDTH) / 2);
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
		return gameFrame;
	}

	public void setGameWindow(Dimension gameWindow) {
		this.gameFrame = gameWindow;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
