package root.actors.bosses;

import root.actors.seaVehicles.AircraftCarrier;
import root.levels.Level;

public class AircraftCarrierA extends AircraftCarrier
{

	public AircraftCarrierA(Level l, int x, int y) 
	{
		super(l, x, y);
		setScore(10000);
		setCanDieFromEnemies(false);
		setIsBoss(true);
	}

}
