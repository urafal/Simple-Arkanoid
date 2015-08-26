package com.main;


import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		frame.setSize(900, 700);
		frame.setTitle("Simple Arkanoid");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Game game = new Game(frame);
		
		frame.add(game);		
		frame.setVisible(true);
	}
}
