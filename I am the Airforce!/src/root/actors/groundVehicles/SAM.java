package root.actors.groundVehicles;
import java.awt.Color;
import java.io.IOException;

import root.actors.Actor;
import root.actors.planes.PlayerJet;
import root.actors.projectiles.Missle;
import root.levels.Level;

public class SAM extends GroundVehicle
{
	public SAM(Level l, int x, int y)
	{
		super(l,x,y);
		setTimeLimit(5000);
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
	public SAM(Level l, int x, int y, int xSize, int ySize, int xVel, int yVel, boolean isEnemy, int direction, int angle) 
	{
		super(l, x, y, xSize, ySize, xVel, yVel, isEnemy, direction, angle);
		setTimeLimit(5000);
		setColor(Color.CYAN);
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
	
	public void fireMissle()
	{
		PlayerJet j = null;
		for(Actor a : getLevel().getActors()) 
		{
			if(a instanceof PlayerJet)
			{
				j = (PlayerJet)a;
				break;
			}
		}
		if(j != null)
		{
			Missle m = new Missle(getX() + getXSize()/2, getY() - 10, this, getLevel(), Up());
			m.setTarget(j);
			addProjectile(m);
		}
	}
	
	public void processEndTimer()
	{
		fireMissle();
	}
}
