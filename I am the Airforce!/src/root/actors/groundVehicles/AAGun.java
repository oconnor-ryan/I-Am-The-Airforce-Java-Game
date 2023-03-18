package root.actors.groundVehicles;

import java.awt.Color;
import java.io.IOException;

import root.levels.Level;

public class AAGun extends GroundVehicle
{
	public AAGun(Level l, int x, int y)
	{
		super(l,x,y);
		setTimeLimit(250);
		setScore(150);
		setAngle(Up());
		setDirection(Left());
		setColor(Color.WHITE);
		try
		{
			createSprite("AAGun.txt");
			setPixelSize(Level.convertToRatioX(4));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public AAGun(Level l, int x, int y, int xSize, int ySize, int xVel, int yVel, boolean isEnemy, int direction, int angle) 
	{
		super(l, x, y, xSize, ySize, xVel, yVel, isEnemy, direction, angle);
		setTimeLimit(250);
		setScore(150);
		try
		{
			createSprite("AAGun.txt");
			setPixelSize(Level.convertToRatioX(4));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void processEndTimer()
	{
		shoot();
	}
}
