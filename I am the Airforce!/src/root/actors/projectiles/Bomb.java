package root.actors.projectiles;
import java.awt.Color;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import root.actors.Actor;
import root.actors.Flag;
import root.actors.powerUps.PowerUp;
import root.levels.Level;

public class Bomb extends Projectile
{
	private static final int GRAV = 5;
	
	public Bomb(Level l, int x, int y, int xVel, int yVel, Actor a)
	{
		super(l);
		setX(x);
		setY(y);
		setXSpeed(xVel);
		setYSpeed(yVel);
		setXSize(10);
		setYSize(5);
		setHealth(1);
		setDamage(300);
		setColor(Color.RED);
		setActor(a);
		try
		{
			createSprite("Bomb.txt");
			setPixelSize(Level.convertToRatioX(3));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public Bomb clone() {return new Bomb(getLevel(), 0, 0, 0, 0, getActor());}
	
	public void checkCollisions(CopyOnWriteArrayList<Actor> actors)
	{
		super.checkCollisions(actors);
		if(isDead())
		{
			explode();
		}
	}
	
	public void handleCollision(Actor a)
	{
		if(!(a instanceof Flag) && !(a instanceof PowerUp))
		{
			if(getActor() == null || (getActor() != null && !a.equals(getActor())))
			{
				this.kill();
			}
		}
	}
	
	public void explode()
	{
		getLevel().add(new Explosion(getLevel(),getX()+getXSize()/2, getY()+getYSize()/2,Level.convertToRatioX(60), this));
	}

	public void move()
	{
		setYSpeedWithoutScale(getYSpeed() + GRAV);
		if(getYSpeed() >= GRAV)
		{
			setYSpeed(GRAV);
		}
		super.move();
		if(getXSpeed() > 0)
		{
			setCurrentSprite(0);
		}
		else if(getXSpeed() < 0)
		{
			setCurrentSprite(1);
		}
	}
	
	public void processEndTimer()
	{
		//use to prevent projectile from killing Player until this is activated and moves away from player
	}
}

