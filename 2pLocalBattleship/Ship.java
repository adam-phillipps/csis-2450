package battleship;

import java.util.*;

public class Ship {
	
	private List<Coordinate> location = new ArrayList<>();
	private ShipType type;
		
	public Ship(List<Coordinate> location, ShipType type) {
		this.location = location;
		this.type = type;
	}

	public boolean checkAlive(){
		if(location.size() > 0)
			return true;
		return false;
	}

	public List<Coordinate> getLocation()
	{
		return location;
	}
	
	public Shot receiveShot(Coordinate shotLocation){
		if(checkPoint(shotLocation)){
			location.remove(shotLocation);
			if(checkAlive()) return new Hit(shotLocation, this);
			return new Sink(shotLocation, this);
		}
		return null;
	}
	
	public boolean checkPoint(Coordinate point){
		for (Coordinate l: location){
			if (point.equals(l))	return true;
		}
		return false;
	}
	
	public ShipType getType(){
		return type;
	}
	
}
