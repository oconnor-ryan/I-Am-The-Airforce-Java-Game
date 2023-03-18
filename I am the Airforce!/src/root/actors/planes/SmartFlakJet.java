package root.actors.planes;

import root.actors.projectiles.Flak;
import root.levels.Level;

public class SmartFlakJet extends FlakJet
{

	public SmartFlakJet(Level l, int x, int y) {
		super(l, x, y);
		setScore(500);
	}
	
	public double getAngle(PlayerJet p)
	{
		return Math.atan2(p.getY() + p.getYSize()/2 - this.getY(), p.getX() + p.getXSize()/2 - this.getX());
	}
	
	public void shoot() //note: too much copy paste, make shoot method more flexible or store an ArrayList of the projectiles a jet can shoot
	{
		PlayerJet player = getLevel().getPlayer();
		int speed = 3*getStandardSpeed();
		double angle = getAngle(player);
		int xVel = (int)(speed * Math.cos(angle));
		int yVel = (int)(speed * Math.sin(angle));
		if(getDirection() == Right())
		{
			if(player.getX() - this.getX() > 0)
			{
				addProjectile(new Flak(getLevel(), getX() + getXSize() + 10, getY()+getYSize()/2, xVel, yVel, this));
			}
		}
		else if(getDirection() == Left())
		{
			if(player.getX() - this.getX() < 0)
			{
				addProjectile(new Flak(getLevel(), getX() - 10, getY()+getYSize()/2, xVel, yVel, this));
			}
		}
	}
}
