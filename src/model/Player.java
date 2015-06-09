package model;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.Timer;

public class Player implements Serializable{
	
	private static final long serialVersionUID = 8376511587684270668L;
	
	private int posX, posY;
	private int velX, velY;
	
	private boolean movingRight = false;
	private boolean movingLeft = false;
	private boolean onGround = false;
	
	private long timer = System.currentTimeMillis();
	
	private float gravity = 0.5f; 
	
	private String username;
	private int userid;
	
	public Player(String username) {
		this.username = username;
		setPos(200, 200);
	}
	
	public void tick(){
		if(movingRight){
			posX+=2;
		}
		if(movingLeft){
			posX-=2;
		}
	}
	
	public void render(Graphics g){
		g.fillRect(posX, posY, 50, 50);
	}
	
	public void setMovingLeft(boolean b){
		this.resetTimer();
		this.movingLeft = b;
	}

	public void setMovingRight(boolean b){
		this.resetTimer();
		this.movingRight = b;
	}

	public boolean isMovingLeft(){
		return movingLeft;
	}

	public boolean isMovingRight(){
		return movingRight;
	}

	public void resetTimer(){
		timer = System.currentTimeMillis();
	}
	
	public void setUserID(int userid){
		this.userid = userid;
	}
	
	public void setPos(int x, int y){
		this.posX = x;
		this.posY = y;
	}
	
	public int getX(){
		return posX;
	}
	
	public int getY(){
		return posY;
	}
	
	public int getVelX(){
		return velX;
	}
	
	public int getVelY(){
		return velY;
	}
	
	public void setVelX(int velX){
		this.velX = velX;
	}
	
	public void setVelY(int velY){
		this.velY = velY;
	}
	
	public int getUserID(){
		return userid;
	}
	
	public String toString(){
		return "Player: " + username + ", ID = " + userid;
	}

}
