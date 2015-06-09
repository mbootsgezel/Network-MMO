package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Entity implements Serializable {

	private static final long serialVersionUID = 1912203421649094179L;

	public static final int INITPLAYER = 0, PLAYERLIST = 1;
	
	private int type;
	private int connid;
	private Object o;
	
	private ArrayList<Player> players;
	
	public Entity(int type, Object o, int connid) {
		this.type = type;
		this.o = o;
		this.connid = connid;
	}
	
	public Entity(int type, ArrayList<Player> players, int connid){
		this.type = type;
		this.players = players;
		this.connid = connid;
	}
	
	public ArrayList<Player> getPlayerList(){
		return players;
	}
	
	public int getType(){
		return type;
	}
	
	public Object getObject(){
		return o;
	}
	
	public int getConnID(){
		return connid;
	}

}
