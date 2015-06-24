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
	private volatile boolean running;
	private volatile boolean paused;
	private int border;
	private int playerSpeed;
	private Block[][] blocks;
	private int rows;
	private int platformsInRow;
	private volatile boolean win;
	private volatile boolean lose;
	private int lives;
	private static final String GAMEOVER = "GAME OVER!";
	private static final String GAMEWIN = "Congratulations! You win!";

	Game(JFrame mainFrame) {
		super.setSize(mainFrame.getSize());
		
		running = false;
		paused = false;
		playerSpeed = 25;
		border = 100;
		platformsInRow = 10;
		rows = 4;
		win = false;
		lose = false;
		lives = 3;
		//Instead of popular Linux DE (such as Unity or KDE), windows in Microsoft Windows OS family has borders, 
		//that closes elements of the game, so I decided to use "frame inside frame" (we need to go deeper...)
		//with the border, which controlling by a "border" variable.
		gameFrame = new Dimension(mainFrame.getWidth() - border, mainFrame.getHeight() - border);

		int tmpWidth = (gameFrame.width - Player.WIDTH) / 2;
		int tmpHeight = (gameFrame.height - Player.HEIGHT) / 2;

		player = new Player(this, tmpWidth, tmpHeight);
		player.setPositionY(gameFrame.height - Player.HEIGHT);

		ball = new Ball(this);
		
		int blockWidth = gameFrame.width / platformsInRow;
		int blockHeight = (gameFrame.height / 4) / rows; 
		blocks = new Block[platformsInRow][rows];
		for(int x = 0; x < blocks.length; x++) {
			for(int y = 0; y < blocks[0].length; y++) {
				if (x == blocks.length - 1) {
					blocks[x][y] = new Block(x * blockWidth, y * blockHeight, blockWidth - 1, blockHeight);
				} else {
					blocks[x][y] = new Block(x * blockWidth, y * blockHeight, blockWidth, blockHeight);	
				}
			}
		}
		
		startGame(mainFrame);
	}
	
	private void startGame(JFrame frame) {
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				if (!running && !lose && !win ||paused && !lose && !win) {
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
			running = true;
			ball.setVector(10, 10);
			while (running) {
				if (!paused) {
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
		//moving gameFrame in the middle of mainFrame
		graphics.translate((this.getWidth() - gameFrame.width) / 2, (this.getHeight() - gameFrame.height) / 2);
		graphics.setColor(new Color(178, 0, 71));
		int rd = 8;
		for (int i = 1; i <= lives; i++) {
			graphics.fillOval(i * rd * 2, -(rd * 2 + rd), rd * 2, rd * 2);
		}
		graphics.setColor(new Color(31, 0, 15));
		graphics.fillRect(0, 0, gameFrame.width, gameFrame.height);
		player.render(graphics);
		ball.render(graphics);
		for(Block[] blarr : blocks) {
			for(Block bl: blarr) {
				bl.render(graphics);
			}
		}
		graphics.setColor(new Color(255, 255, 255));
		if (win) {
			graphics.drawString(GAMEWIN, gameFrame.width / 2 - GAMEWIN.length() - 30, gameFrame.height / 2);
			stop();
		}
		if (lose) {
			graphics.drawString(GAMEOVER, gameFrame.width / 2 - GAMEOVER.length() - 15, gameFrame.height / 2);
			stop();
		}
	}

	public void fail() {
		pause();
		lives -= 1;
		if(lives <= 0) lose = true;
		ball.setPosition(gameFrame.width / 2, gameFrame.height / 2);
		player.setPositionX((gameFrame.width - player.WIDTH) / 2);
		repaint();
	}

	public void start() {
		paused = false;
		if (!running) gameThread.start();
	}

	public void pause() {
		paused = true;
	}
	
	public void stop() {
		running = false;
	}

	public Dimension getGameFrame() {
		return gameFrame;
	}

	public void setGameFrame(Dimension gameWindow) {
		this.gameFrame = gameWindow;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Block[][] getBlocks() {
		return blocks;
	}

	public void setBlocks(Block[][] blocks) {
		this.blocks = blocks;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public boolean isLose() {
		return lose;
	}

	public void setLose(boolean lose) {
		this.lose = lose;
	}
	
}
