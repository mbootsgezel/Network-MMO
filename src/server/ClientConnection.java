package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import model.Entity;
import model.Logger;
import model.Player;
import model.PlayerList;

public class ClientConnection implements Runnable{
	
	private Logger l = new Logger("Connection");
	
	private Socket socket;
	private int id;
	
	private boolean running;

	private ObjectOutputStream toClient;
	private ObjectInputStream fromClient;

	private Server server;
	
	private Thread thread;
	
	public ClientConnection(Socket socket, int id, Server server) {
		this.socket = socket;
		this.id = id;
		this.server = server;
		
		/*
		 * Connection succesfully created, trying to display the connectee
		 */
		try {
			l.display("Connection from: " + socket.getInetAddress().getHostAddress() + ", ID = " + id);
		} catch (Exception e) {
			l.displayErr("Connected from unknown host.");
		}

		/*
		 * Try to create IO for the client connection
		 */
		try {
			toClient = new ObjectOutputStream(socket.getOutputStream());
			fromClient = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			l.displayErr("Failed to load IO: " + e);
		}
		
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		running = true;
		try {
			
			while(running){
				Entity ent = (Entity) fromClient.readObject();
				
				switch(ent.getType()){
				case Entity.INITPLAYER:
					Player p = (Player) ent.getObject();
					p.setUserID(id);
					PlayerList.getInstance().addPlayer(p);
					sendPlayerClient(p);
					server.sendPlayerList();
					break;
				};
				
			}
			
		} catch (Exception e){
			server.remove(id);
			PlayerList.getInstance().removePlayer(id);
			l.displayErr("Client " + id + " disconnected!");
		}
		
	}
	
	public void sendPlayerClient(Player player){
		try {
			toClient.writeObject(new Entity(Entity.INITPLAYER, player, id));
		} catch (IOException e){
			l.displayErr(e.getMessage());
		}
	}
	
	public void sendPlayerList(){
		try {
			toClient.writeObject(new Entity(Entity.PLAYERLIST, PlayerList.getInstance().getPlayerList(), id));
		} catch (IOException e){
			l.displayErr(e.getMessage());
		}
	}
	
	public int getConnectionID(){
		return id;
	}

}
