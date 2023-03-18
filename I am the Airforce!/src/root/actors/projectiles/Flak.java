package root.actors.projectiles;

import java.awt.Color;
import java.io.IOException;

import root.actors.Actor;
import root.levels.Level;

public class Flak extends Bullet
{
	public Flak(Level l, int x, int y, int xVel, int yVel, Actor a) 
	{
		super(l, x, y, xVel, yVel, a);
		try	{
			createSprite("Flak.txt");
			setPixelSize(Level.convertToRatioX(1));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		setColor(Color.ORANGE);
	}
	public void handleCollision(Actor a)
	{
		super.handleCollision(a);
		if(isDead()) //39  keep explosion size small to prevent lag and memory overflow
		{
			getLevel().add(new Explosion(getLevel(),getX()+getXSize()/2, getY()+getYSize()/2,Level.convertToRatioX(15),getActor()));
		}
	}
}
