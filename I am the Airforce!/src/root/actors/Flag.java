package root.actors;
import java.awt.Color;
import java.io.IOException;

import root.levels.Level;

public class Flag extends Actor
{
	public Flag(Level l, int x, int y)
	{
		setX(x);
		setY(y);
		setXSize(Level.convertToRatioX(40));
		setYSize(Level.convertToRatioX(40));
		setColor(Color.GREEN);
		setUnkillable(true);
		setScore(1000);
		try
		{
			createSprite("Flag.txt");
			setPixelSize(Level.convertToRatioX(4));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public void processEndTimer(){}

	public void handleCollision(Actor a){}
}
