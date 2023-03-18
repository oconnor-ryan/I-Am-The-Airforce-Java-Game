package root.actors.planes;

import root.actors.projectiles.Flak;
import root.levels.Level;

public class SmartShotGunFlakJet extends SmartFlakJet
{

	public SmartShotGunFlakJet(Level l, int x, int y) 
	{
		super(l, x, y);
		setScore(600);
	}
	
	public void shoot()
	{
		PlayerJet player = getLevel().getPlayer();
		int speed = 3*getStandardSpeed();
		double angle = getAngle(player);
		int xVel = (int)(speed * Math.cos(angle));
		int yVel = (int)(speed * Math.sin(angle));
		double angle1 = angle + Math.PI/8;
		int xVel1 = (int)(speed * Math.cos(angle1));
		int yVel1 = (int)(speed * Math.sin(angle1));
		double angle2 = angle - Math.PI/8;
		int xVel2 = (int)(speed * Math.cos(angle2));
		int yVel2 = (int)(speed * Math.sin(angle2));
		if(getDirection() == Right())
		{
			if(player.getX() - this.getX() > 0)
			{
				addProjectile(new Flak(getLevel(), getX() + getXSize() + 10, getY()+getYSize()/2, xVel, yVel, this));
				addProjectile(new Flak(getLevel(), getX() + getXSize() + 10, getY()+getYSize()/2, xVel1, yVel1, this));
				addProjectile(new Flak(getLevel(), getX() + getXSize() + 10, getY()+getYSize()/2, xVel2, yVel2, this));
			}
		}
		else if(getDirection() == Left())
		{
			if(player.getX() - this.getX() < 0)
			{
				addProjectile(new Flak(getLevel(), getX() - 10, getY()+getYSize()/2, xVel, yVel, this));
				addProjectile(new Flak(getLevel(), getX() - 10, getY()+getYSize()/2, xVel1, yVel1, this));
				addProjectile(new Flak(getLevel(), getX() - 10, getY()+getYSize()/2, xVel2, yVel2, this));
			}
		}
	}

}
