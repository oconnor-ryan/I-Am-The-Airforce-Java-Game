package root.actors.environment;
import java.awt.Color;

import root.actors.Actor;
import root.actors.groundVehicles.GroundVehicle;
import root.levels.Level;

public class EnvironmentHazard extends Actor
{
	public EnvironmentHazard(Level l, int x, int y)
	{
		setLevel(l);
		setX(x);
		setY(y);
		setXSize(10);
		setYSize(100);
		setXSpeed(0);
		setYSpeed(0);
		setColor(Color.WHITE);
		setHealth(100000);
		setUnkillable(false);
		setScore(0);
	}
	public EnvironmentHazard(Level l, int x, int y, int xSize, int ySize, int xVel, int yVel)
	{
		setLevel(l);
		setX(x);
		setY(y);
		setXSpeed(xVel);
		setYSpeed(yVel);
		setXSize(xSize);
		setYSize(ySize);
		setHealth(1000000);
		setUnkillable(false);
		setColor(Color.WHITE);
		setScore(0);
	}

	@Override
	public void processEndTimer() 
	{

	}
	
	public void handleCollision(Actor a)
	{
		if(a instanceof GroundVehicle || (a instanceof EnvironmentHazard && !(a instanceof Ground)))
		{
			this.setXSpeed(-this.getXSpeed());
		}
	}
}
