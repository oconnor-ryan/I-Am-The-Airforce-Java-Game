package root.actors.planes;

import root.actors.projectiles.Bullet;
import root.levels.Level;

public class ShotGunJet extends WaveJet
{
	public ShotGunJet(Level l, int x, int y) 
	{
		super(l, x, y);
		setScore(250);
	}

	public void shoot()
	{
		super.shoot();
		int bulletSpeed = 3*getStandardSpeed();
		
		if(getDirection() == Left())
		{
			addProjectile(new Bullet(getLevel(), getX() - 15, getY() + getYSize()/2, -bulletSpeed,bulletSpeed/4, this));
			addProjectile(new Bullet(getLevel(), getX() -15, getY() + getYSize()/2, -bulletSpeed,-bulletSpeed/4, this));
		}
		else 
		{
			addProjectile(new Bullet(getLevel(), getX() + getXSize() + 20, getY() + getYSize()/2, bulletSpeed,bulletSpeed/4, this));
			addProjectile(new Bullet(getLevel(), getX() + getXSize() + 20, getY() + getYSize()/2, bulletSpeed,-bulletSpeed/4, this));
		}
		
	}
}
