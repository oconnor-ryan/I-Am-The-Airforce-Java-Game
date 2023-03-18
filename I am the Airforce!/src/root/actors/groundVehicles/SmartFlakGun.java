package root.actors.groundVehicles;

import java.io.IOException;

import root.actors.projectiles.Flak;
import root.levels.Level;

public class SmartFlakGun extends SmartAAGun
{
	public SmartFlakGun(Level l, int x, int y) 
	{
		super(l, x, y);
		try {
			createSprite("FlakGun.txt");
			setPixelSize(Level.convertToRatioX(4));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void shoot()
	{
		int[] traj = setTrajectory();
		if(getAngle() == Up())
		{
			addProjectile(new Flak(getLevel(), getX(), getY() - 10, traj[0], traj[1], this));
		}
		else
		{
			addProjectile(new Flak(getLevel(), getX() - 10, getY(), traj[0], traj[1], this));
		}
	}

}
