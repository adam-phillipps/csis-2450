package battleship;

public class Shot
{
	private Coordinate shotLocation;
	private Ship shipAffected;

	public Shot(Coordinate shotLocation, Ship shipAffected) {
		this.shotLocation = shotLocation;
		this.shipAffected = shipAffected;
	}

	public Coordinate getShotLocation()
	{
		return shotLocation;
	}

	public Ship getShipAffected()
	{
		return shipAffected;
	}
	
}
