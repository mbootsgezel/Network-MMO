package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import model.Entity;
import model.Logger;
import model.Player;
import model.PlayerList;
import view.Window;
import controller.Game;

public class Client implements Runnable{
	
	private Logger l = new Logger("Client");
	
	private Window window;
	private Game game;
	
	private String host;
	private int port;
	private int id;

	private Socket client;
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	
	private boolean running;
	
	private Thread thread;
	
	public static void main(String[] args){
		new Client("localhost", 1500);
	}
	
	public Client(String host, int port) {
		this.host = host;
		this.port = port;
		
		this.window = new Window(this);
		this.game = window.getGame();
		
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		running = true;
		
		try {
			client = new Socket(host, port);
			toServer = new ObjectOutputStream(client.getOutputStream());
			fromServer = new ObjectInputStream(client.getInputStream());
			running = true;
			l.display("Succesfully connected to: " + host);
		} catch (Exception e){
			l.displayErr("Can't connect to server at " + host);
		}
		
		try {
			
			this.newPlayer();
			
			while(running){
				Entity ent = (Entity) fromServer.readObject();
				
				switch(ent.getType()){
				case Entity.INITPLAYER:
					game.setPlayerID(ent.getConnID());
					this.id = ent.getConnID();
					break;
				case Entity.PLAYERLIST:
					ArrayList<Player> players = (ArrayList<Player>) ent.getPlayerList();
					PlayerList.getInstance().setPlayerList(players);
					l.display("Updated playerlist on client: " + ent.getConnID() + ", " + PlayerList.getInstance().getPlayerList().toString());
					break;
				}
			}
		} catch (Exception e){
			l.displayErr("Connection to server has been lost.");
		}
	}
	
	public void newPlayer(){
		Player p = new Player(System.getProperty("user.name"));
		try {
			toServer.writeObject(new Entity(Entity.INITPLAYER, p, id));
		} catch (IOException e){
			l.displayErr(e.getMessage());
		}
	}

}
