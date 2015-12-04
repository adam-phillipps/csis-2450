package battleship;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public enum ShipType {
	AIRCRAFT_CARRIER(5), BATTLESHIP(4), DESTROYER(3), SUBMARINE(3), PATROL_BOAT(2);
	
	private int size;
	private String name = "";
	private Icon icon;
	
	ShipType(int size){
		this.size = size;
		this.icon = new ImageIcon(this.getClass().getResource("img/" + this.name() + ".png"));
		String[] nameTmp = this.name().split("_");
		for(String piece : nameTmp)
			this.name += piece.substring(0, 1) + piece.substring(1, piece.length()).toLowerCase() + " ";
		this.name.trim();
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getSize(){
		return this.size;
	}
	
	public Icon getIcon(){
		return icon;
	}
	
}
