package root.actors.groundVehicles;

import java.io.IOException;

import root.actors.projectiles.Bomb;
import root.levels.Level;

public class BombFlinger extends GroundVehicle
{
	public BombFlinger(Level l, int x, int y)
	{
		super(l,x,y);
		setTimeLimit(2000);
		setScore(300);
		setAngle(Up());
		setDirection(Left());
		try
		{
			createSprite("SAM.txt");
			setPixelSize(Level.convertToRatioX(4));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public BombFlinger(Level l, int x, int y, int xSize, int ySize, int xVel, int yVel, boolean isEnemy, int direction,int angle) 
	{
		super(l, x, y, xSize, ySize, xVel, yVel, isEnemy, direction, angle);
		setTimeLimit(2000);
		setScore(300);
		try
		{
			createSprite("SAM.txt");
			setPixelSize(Level.convertToRatioX(4));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void processEndTimer()
	{
		dropBomb();
	}
	
	public void dropBomb()
	{
		int xSpeed = 0;
		if(getDirection() == Right())
		{
			xSpeed = getXSpeed() + 3;
		}
		else if(getDirection() == Left())
		{
			xSpeed = getXSpeed() - 3;
		}
		double mult = Math.random() * 20;
		int h = 90;
		addProjectile(new Bomb(getLevel(), getX() + getXSize()/2, getY() - 10, xSpeed, (int)(mult-h), this));
	}
}
