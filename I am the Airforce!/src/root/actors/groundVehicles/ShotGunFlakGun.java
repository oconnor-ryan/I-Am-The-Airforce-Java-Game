package root.actors.groundVehicles;

import root.actors.projectiles.Flak;
import root.levels.Level;

public class ShotGunFlakGun extends FlakGun
{

	public ShotGunFlakGun(Level l, int x, int y) 
	{
		super(l, x, y);
		setScore(350);
	}

	public void shootFlak()
	{
		super.shootFlak();
		int bulletSpeed = 3*getStandardSpeed();
		addProjectile(new Flak(getLevel(), getX()+getXSize()/2, getY() - 10, bulletSpeed/4, -bulletSpeed, this));
		addProjectile(new Flak(getLevel(), getX()+getXSize()/2, getY() - 10, -bulletSpeed/4, -bulletSpeed, this));
	}
}
