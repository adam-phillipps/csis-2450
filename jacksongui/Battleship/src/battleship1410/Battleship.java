package battleship1410;

import java.util.ArrayList;

public class Battleship {
	
	private ArrayList<String> location = new ArrayList<String>();
	private String name;
	private int size;
	
	public Battleship(String name, int size){
		this.name = name;
		this.size = size;
	}
	public ArrayList<String> getLocation(){
		return location;
	}
	public int getSize(){
		return size;
	}
	public void setLocation(ArrayList<String> locationToSet){
		this.location.addAll(locationToSet);
	}
	public String check(String guess){
		String result ="miss";
		
		if(location.contains(guess)){
			location.remove(guess);
			result = location.isEmpty()? "kill" : "hit";
		}
		return result;
	}
	public String getName(){
		return name;
	}
}
