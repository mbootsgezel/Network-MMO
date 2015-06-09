package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import model.Logger;
import model.Player;

public class Server implements Runnable {

	private Logger l = new Logger("Server");
	
	private ArrayList<ClientConnection> clients;

	private ServerSocket serverSocket;
	private int PORT;
	private int uniqueid = 0;
	
	private boolean running = false;
	
	private Thread thread;
	
	public static void main(String[] args){
		new Server(1500);
	}

	public Server(int port) {
		this.PORT = port;
		this.running = true;
		
		this.clients = new ArrayList<ClientConnection>();
		
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		try{
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			l.displayErr("Port is already in use.");
		}
		
		try {
			l.display("Waiting for connections on port " + PORT + ".");
			
			while(running){

				clients.add(new ClientConnection(serverSocket.accept(), ++uniqueid, this));
				
				if(!running){
					break;
				}
			}
		} catch (IOException e) {
			l.displayErr(e.getMessage());
		}

	}
	
	/*
	 * Client connection will be removed from the list, should be called on client disconnect.
	 */
	public void remove(int id){
		for(int i = 0; i < clients.size(); i++){
			if(clients.get(i).getConnectionID() == id){
				clients.remove(i);
			}
		}
	}
	
	public void sendPlayerList(){
		for(int i = 0; i < clients.size(); i++){
			clients.get(i).sendPlayerList();
			l.display("Sending playerlist update to client: " + clients.get(i).getConnectionID());
		}
	}

}
