package starter;

import server.Server;
import client.Client;

public class Starter {
	
	public static void main(String[] args){
		
		new Server(1500);
		new Client("localhost", 1500);
		//new Client("localhost", 1500);
		//new Client("localhost", 1500);
	}

}
