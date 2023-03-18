package root.actors.projectiles;
import java.awt.Color;
import java.io.IOException;

import root.actors.Actor;
import root.actors.Flag;
import root.actors.powerUps.PowerUp;
import root.levels.Level;

public class Flare extends Projectile
{
	private static final int GRAV = 1;
	public Flare(Level l, int x, int y, int xVel, int yVel, Actor a)
	{
		super(l);
		setX(x);
		setY(y);
		setXSpeed(xVel);
		setYSpeed(yVel);
		setXSize(10);
		setYSize(10);
		setHealth(1);
		setDamage(10);
		setColor(Color.YELLOW);
		setActor(a);
		try
		{
			createSprite("Flare.txt");
			setPixelSize(Level.convertToRatioX(1));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public Flare clone() {return new Flare(getLevel(), 0 , 0, 0, 0, getActor());}
	
	public void processEndTimer()
	{
		//use to prevnet projectile from killing Player until this is activated and moves away from player
	}
	
	public void handleCollision(Actor a)
	{
		if(!(a instanceof Flag) && !(a instanceof PowerUp) && !(a instanceof Explosion) && !(a instanceof Bullet)
				&& !(a instanceof Bomb) && !(a instanceof Flare))
		{
			if(getActor() == null || (getActor() != null && !a.equals(getActor())))
			{
				this.kill();
			}
		}
	}
	
	public void move()
	{		
		if(getYSpeed() < 2)
		{
			setYSpeedWithoutScale(getYSpeed() + GRAV);
		}
		super.move();
	}
}
