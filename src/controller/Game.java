package controller;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import model.Logger;
import model.Player;
import model.PlayerList;
import client.Client;

public class Game extends Canvas implements Runnable{
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public final String TITLE = "Network MMO";
	
	private Logger l = new Logger("Game");
	
	private boolean running = false;
	private Thread thread;
	
	private int userid;
	
	private int FPS = 0;
	
	public Game(Client client) {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
	}
	
	public void init(){
		requestFocus();
		
		addKeyListener(new KeyInput(this));
	}

	@Override
	public void run() {
		init();
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1){
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				this.FPS = frames;
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	private void tick(){
		for(int playerCount = 0; playerCount < PlayerList.getInstance().getPlayerList().size(); playerCount++){
			PlayerList.getInstance().getPlayerList().get(playerCount).tick();
		}
	}
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		//////////////////////////////////
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.drawString(Integer.toString(FPS), 10, 31);
		
//		l.display("Players.size() = " + players.size());
		for(int playerCount = 0; playerCount < PlayerList.getInstance().getPlayerList().size(); playerCount++){
			PlayerList.getInstance().getPlayerList().get(playerCount).render(g);
		}
		
		//////////////////////////////////
		g.dispose();
		bs.show();
		
	}
	
	private Player getCurrentPlayer(){
		for(int i = 0; i < PlayerList.getInstance().getPlayerList().size(); i++){
//			System.out.println("Looping through players looking for " + userid);
			if(PlayerList.getInstance().getPlayerList().get(i).getUserID() == userid){
				return PlayerList.getInstance().getPlayerList().get(i);
			}
		}
		return null;
	}
	
	public void keyPressed(KeyEvent e){
		switch(e.getKeyCode()){
		case KeyEvent.VK_A:
//			System.out.println("Moving left");
			getCurrentPlayer().setMovingLeft(true);
			break;
		case KeyEvent.VK_D:
//			System.out.println("Moving right");
			getCurrentPlayer().setMovingRight(true);
			break;
		}
	}
	
	public void keyReleased(KeyEvent e){
		switch(e.getKeyCode()){
		case KeyEvent.VK_A:
			getCurrentPlayer().setMovingLeft(false);
			break;
		case KeyEvent.VK_D:
			getCurrentPlayer().setMovingRight(false);
			break;
		}
	}
	
	public synchronized void start(){
		if(running){
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		if(!running){
			return;
		}
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}
	
	public void paint(Graphics g){
		
	}
	
	public void setPlayerID(int userid){
		this.userid = userid;
		l = new Logger("Game " + userid);
	}

}
