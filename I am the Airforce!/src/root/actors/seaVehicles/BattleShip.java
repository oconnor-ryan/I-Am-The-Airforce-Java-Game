package root.actors.seaVehicles;

import java.io.IOException;

import root.actors.projectiles.Flak;
import root.actors.projectiles.Flare;
import root.actors.projectiles.Missle;
import root.levels.Level;

public class BattleShip extends SeaVehicle
{
	private int missleTimer = 0;
	private int flareTimer = 0;
	private int missleDelay = 20;
	private int flareDelay = 40;
	public BattleShip(Level l, int x, int y) 
	{
		super(l, x, y);
		setTimeLimit(200);
		setScore(1000);
		setHealth(5000);
		try
		{
			createSprite("BattleShip.txt");
			setPixelSize(Level.convertToRatioX(10));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void move()
	{
		super.move();
		if(getX() < 0)
		{
			setXSpeed(-getXSpeed());
			setX(0);
		}
		else if(getX() > Level.convertToRatioX(1370))
		{
			setXSpeed(-Math.abs(getXSpeed()));
			setX(Level.convertToRatioX(1370));
		}
	}
	public void shoot()
	{
		for(int x = getX(); x <= getX() + getXSize(); x += getXSize()/3)
		{
			addProjectile(new Flak(getLevel(), x, getY() - 10, 0, -3*getStandardSpeed(), this));
		}
		missleTimer++;
		flareTimer++;
		if(missleTimer >= missleDelay)
		{
			Missle[] missles = new Missle[2];
			missles[0] = new Missle(getX() - 40, getY() - 10, this, getLevel(), Up());
			missles[1] = new Missle(getX() + 20 + getXSize(), getY() - 10, this, getLevel(), Up());
			for(Missle m : missles)
			{
				m.setTarget(getLevel().getPlayer());
				getProjectiles().add(m);
			}
			missleTimer = 0;
		}
		
		if(flareTimer >= flareDelay)
		{
			addProjectile(new Flare(getLevel(), getX(), getY() - 10, getXSpeed(), -7*getStandardSpeed(), this));
			addProjectile(new Flare(getLevel(), getX() + getXSize(), getY() - 10, getXSpeed(), -7*getStandardSpeed(), this));
			flareTimer = 0;
		}
	}
	
}
