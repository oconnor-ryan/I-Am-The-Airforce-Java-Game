package root.actors.planes;
import java.awt.Color;
import java.io.IOException;

import root.levels.Level;

public class Jet extends Plane 
{
	public Jet(Level l, int x, int y)
	{
		super(l,x,y);
		setHealth(100);
		setTimeLimit(500);
		setDirection(Left());
		setHealthFromLastTick(getHealth());
		setColor(Color.RED);
		setScore(100);
		try {
			createSprite("EnemyJet.txt");
			setPixelSize(Level.convertToRatioX(4));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Jet(Level w, int x, int y, int xSize, int ySize, int xVel, int yVel, boolean isEnemy, int direction) 
	{
		super(w, x, y, xSize, ySize, xVel, yVel, isEnemy, direction);
		setHealth(100);
		setTimeLimit(500);
		setHealthFromLastTick(getHealth());
		setColor(Color.RED);
		setScore(100);
		if(w == null)
		{
			return;
		}
		try {
			createSprite("EnemyJet.txt");
			setPixelSize(Level.convertToRatioX(4));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void processEndTimer()
	{
		shoot();
	}
}
