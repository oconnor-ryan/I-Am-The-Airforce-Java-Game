package root.actors.groundVehicles;

import root.actors.projectiles.Bullet;
import root.levels.Level;

public class ShotGunAAGun extends AAGun
{

	public ShotGunAAGun(Level l, int x, int y) 
	{
		super(l, x, y);
		setScore(200);
	}
	
	public void shoot()
	{
		super.shoot();
		if(getAngle() == Up())
		{
			int bulletSpeed = 3*getStandardSpeed();
			addProjectile(new Bullet(getLevel(), getX()+getXSize()/2, getY() - 10, bulletSpeed/4, -bulletSpeed, this));
			addProjectile(new Bullet(getLevel(), getX()+getXSize()/2, getY() - 10, -bulletSpeed/4, -bulletSpeed, this));
		}
	}

}
