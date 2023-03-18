package root.actors.planes;

import root.actors.projectiles.Bullet;
import root.levels.Level;

public class SmartJet extends WaveJet
{
	public SmartJet(Level l, int x, int y)
	{
		super(l,x,y);
		setScore(200);
	}
	public SmartJet(Level w, int x, int y, int xVel, int yVel, int xSize, int ySize, boolean isEnemy, int direction) 
	{
		super(w, x, y, xVel, yVel, xSize, ySize, isEnemy, direction);
		setScore(200);
	}
	
	public double getAngle(PlayerJet player)
	{
		return Math.atan2(player.getY() + player.getYSize()/2 - this.getY(), player.getX() + player.getXSize()/2 - this.getX());
	}
	
	public void shoot()
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
				addProjectile(new Bullet(getLevel(), getX() + getXSize() + 10, getY()+getYSize()/2, xVel, yVel, this));
			}
		}
		else if(getDirection() == Left())
		{
			if(player.getX() - this.getX() < 0)
			{
				addProjectile(new Bullet(getLevel(), getX() - 10, getY()+getYSize()/2, xVel, yVel, this));
			}
		}
	}

}
