package root.actors.planes;
import java.awt.Color;

import root.actors.Actor;
import root.actors.Flag;
import root.actors.Vehicle;
import root.actors.powerUps.PowerUp;
import root.actors.projectiles.Projectile;
import root.levels.Level;
/*
 * Superclass for all flying vehicles
 */
public class Plane extends Vehicle
{
	public Plane(Level l, int x, int y)
	{
		super(l,x,y);
		setHealth(100);
		setColor(Color.PINK);
		setScore(100);
	}
	public Plane(Level w, int x, int y, int xSize, int ySize, int xVel, int yVel, boolean isEnemy, int direction)
	{
		super(w,x,y,xSize,ySize,xVel,yVel,isEnemy,direction);
		setHealth(100);
		setColor(Color.PINK);
		setScore(100);
	}
	
	public void move()
	{
		super.move();
		if(!(this instanceof PlayerJet))
		{
			if(getDirection() == Right())
			{
				setCurrentSprite(1);
			}
			else
			{
				setCurrentSprite(0);
			}
		}
	}
	
	public void handleCollision(Actor a)
	{
		super.handleCollision(a);
		if(!(this instanceof PlayerJet) && !(a instanceof Projectile) && !(a instanceof Flag) && !(a instanceof PowerUp))
		{
			this.kill();
		}
	}
	
	public void processEndTimer()
	{
		shoot();
	}
}
