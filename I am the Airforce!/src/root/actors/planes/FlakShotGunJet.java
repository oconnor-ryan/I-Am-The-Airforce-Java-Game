package root.actors.planes;

import root.actors.projectiles.Flak;
import root.levels.Level;

public class FlakShotGunJet extends FlakJet
{

	public FlakShotGunJet(Level l, int x, int y) 
	{
		super(l, x, y);
		setScore(360);
	}
	
	public void shoot()
	{
		super.shoot();
		
		int bulletSpeed = 3*getStandardSpeed();
		if(getDirection() == Right())
		{	
			addProjectile(new Flak(getLevel(), getX() + getXSize() + 15, getY() + getYSize()/2, bulletSpeed,bulletSpeed/4, this));
			addProjectile(new Flak(getLevel(), getX() + getXSize() + 15, getY() + getYSize()/2, bulletSpeed,-bulletSpeed/4, this));
		}
		else if(getDirection() == Left())
		{
			addProjectile(new Flak(getLevel(), getX() - 15, getY() + getYSize()/2, -bulletSpeed,bulletSpeed/4, this));
			addProjectile(new Flak(getLevel(), getX() - 15, getY() + getYSize()/2, -bulletSpeed,-bulletSpeed/4, this));
		}
	}
}
