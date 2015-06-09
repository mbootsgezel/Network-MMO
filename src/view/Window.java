package view;

import javax.swing.JFrame;

import client.Client;
import controller.Game;

public class Window extends JFrame{

	private static final long serialVersionUID = -3739008754324139579L;
	
	private Game game;

	public Window(Client client) {
		this.game = new Game(client);
		
		this.setTitle(game.TITLE);
		this.add(game);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		game.start();
		
	}
	
	public Game getGame(){
		return game;
	}

}
