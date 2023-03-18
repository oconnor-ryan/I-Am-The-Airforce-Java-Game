package root.actors.planes;

import root.actors.projectiles.Flak;
import root.levels.Level;

public class FlakJet extends WaveJet
{
	public FlakJet(Level l, int x, int y)
	{
		super(l,x,y);
		setScore(200);
	}
	
	public void shoot()
	{
		if(getDirection() == Right())
		{
			addProjectile(new Flak(getLevel(), getX() + getXSize() + 15, getY() + getYSize()/2, 3*getStandardSpeed(), 0, this));
		}
		else if(getDirection() == Left())
		{
			addProjectile(new Flak(getLevel(), getX() - 15, getY() + getYSize()/2, -3*getStandardSpeed(), 0, this));
		}
	}
}
