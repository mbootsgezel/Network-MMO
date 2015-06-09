package model;

import java.util.ArrayList;

public class PlayerList {
	
	private static PlayerList instance;
	
	private static ArrayList<Player> playerList;
	
	private Logger l = new Logger("Player List");
	
	private PlayerList() {
		playerList = new ArrayList<Player>();
	}
	
	public ArrayList<Player> getPlayerList(){
		return playerList;
	}
	
	public void setPlayerList(ArrayList<Player> players){
		playerList = players;
	}
	
	public void addPlayer(Player player){
		playerList.add(player);
		l.display("Playerlist updated with: " + playerList.toString());
	}
	
	public void removePlayer(int userid){
		for(int i = 0; i < playerList.size(); i++){
			if(playerList.get(i).getUserID() == userid){
				playerList.remove(i);
			}
		}
	}
	
	public void clearPlayerList(){
		playerList = new ArrayList<Player>();
	}
	
	public static PlayerList getInstance(){
		if(instance == null){
			instance = new PlayerList();
		}
		return instance;
	}

}
