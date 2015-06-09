package model;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
	
	private String source;
	
	public Logger(String source) {
		this.source = source;
	}
	
	public String now(){
		Date now = new Date();
		return sdf.format(new Date());
	}
	
	public void display(String msg){
		System.out.println(now() + " - " + source + " - " + msg);
	}
	
	public void displayErr(String msg){
		System.err.println(now() + " - " + source + " - " + msg);
	}

}
