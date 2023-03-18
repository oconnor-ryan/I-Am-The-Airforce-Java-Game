package root.actors.seaVehicles;

import java.io.IOException;

import root.actors.projectiles.Bullet;
import root.levels.Level;

public class PTBoat extends SeaVehicle
{
	public PTBoat(Level l, int x, int y) 
	{
		super(l, x, y);
		setScore(150);
		setTimeLimit(500);
		try
		{
			createSprite("PTBoat.txt");
			setPixelSize(Level.convertToRatioX(4));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void shoot()
	{
		getProjectiles().add(new Bullet(getLevel(), getX() + getXSize()/2, getY() - 10, 0, -3*getStandardSpeed(), this));
	}
}
