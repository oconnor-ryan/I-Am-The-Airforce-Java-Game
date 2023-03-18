package root.actors.planes;
import java.awt.Color;
import java.io.IOException;

import root.actors.projectiles.Bullet;
import root.levels.Level;

public class Bomber extends Plane
{
	public Bomber(Level l, int x, int y)
	{
		super(l,x,y);
		setHealth(200);
		setHealthFromLastTick(getHealth());
		setTimeLimit(1000);
		setColor(Color.WHITE);
		setScore(300);
		try
		{
			createSprite("Bomber.txt");
			setPixelSize(Level.convertToRatioX(4));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public Bomber(Level w, int x, int y, int xSize, int ySize, int xVel, int yVel, boolean isEnemy, int direction)
	{
		super(w,x, y, xSize, ySize, xVel, yVel, isEnemy, direction);
		setHealth(200);
		setHealthFromLastTick(getHealth());
		setTimeLimit(1000);
		setColor(Color.WHITE);
		setScore(300);
		try
		{
			createSprite("Bomber.txt");
			setPixelSize(Level.convertToRatioX(4));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void shoot()
	{
		if(getDirection() == Right())
		{
			addProjectile(new Bullet(getLevel(), getX() + getXSize() + 10, getY(), 3*getStandardSpeed(), 0, this));
		}
		else if(getDirection() == Left())
		{
			addProjectile(new Bullet(getLevel(), getX() - 10, getY(), -3*getStandardSpeed(), 0, this));
		}
	}
	
	public void processEndTimer()
	{
		dropBomb();
		shoot();
	}
}
