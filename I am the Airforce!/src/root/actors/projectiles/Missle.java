package root.actors.projectiles;
import java.awt.Color;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import root.actors.Actor;
import root.actors.Flag;
import root.actors.Vehicle;
import root.actors.powerUps.PowerUp;
import root.levels.Level;

public class Missle extends Projectile
{
	//figure out how to have Missle find new Target after old target is dead
	private Actor target = null;
	private Actor oldTarget = null;
	private boolean hasSetTarget = false;
	private int direction;
	private static final int RIGHT = 0;
	private static final int LEFT = 180;
	
	public Missle(int x, int y, Actor a, Level w, int d)
	{
		super(w);
		setX(x);
		setY(y);
		setXSize(20);
		setYSize(5);
		setDamage(100);
		setHealth(1);
		setColor(Color.MAGENTA);
		setLevel(w);
		setActor(a);
		direction = d;
		try
		{
			createSprite("Missle.txt");
			setPixelSize(Level.convertToRatioX(2));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public Missle clone() {return new Missle(0 , 0, getActor(), getLevel(), 0);}
	
	public void setTarget(Actor a)
	{
		target = a; //aims for specified target even if far away
		oldTarget = a;
		hasSetTarget = true;
	}
	
	public void processEndTimer()
	{
		//use to prevnet projectile from killing Player until this is activated and moves away from player
	}
	
	private boolean targetStillAlive(Actor target, CopyOnWriteArrayList<Actor> actors)
	{
		if(target != null)
		{
			for(Actor a : actors)
			{
				if(a.equals(target))
				{
					return true;
				}
			}
		}
		return false;
	}
	
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
		getLevel().add(new Explosion(getLevel(), getX() + getXSize()/2,getY() + getYSize()/2, Level.convertToRatioX(50), this));
	}
	
	public void move()
	{
		CopyOnWriteArrayList<Actor> actors = getLevel().getActors();
		int shortDis = Integer.MAX_VALUE;

		for(Actor a : actors)
		{
			if(a instanceof Flare)
			{
				if(((Projectile) a).getActor() == null || !(((Projectile) a).getActor().equals(this.getActor()))) //prevents actor missles from targeting actors flares
				{
					target = a;
					break;	
				}	
			}
		}

		if(!targetStillAlive(target, actors)) //prevents missle from staying where the target died
		{
			if(!targetStillAlive(oldTarget, actors))
			{
				oldTarget = null;
			}
			
			target = oldTarget;
		}
		
		if(target == null) 
		{
			if(oldTarget != null || !hasSetTarget) //prevent missle from targeting its friends if its target was the player
			{
				for(Actor a : actors)
				{
					//prevents missle from moving toward the shooter by moving backwards
					if((direction == LEFT && getX() - a.getX() > 0) || (direction == RIGHT && getX() - a.getX() < 0)
							|| (direction != LEFT && direction != RIGHT)) 
					{
						if(!a.equals(getActor()) && a instanceof Vehicle)
						{
							int dis = (int)(Math.sqrt(Math.pow(a.getX() - this.getX(), 2) + Math.pow(a.getY() - this.getY(), 2)));
							if(dis < shortDis)
							{
								shortDis = dis;
								target = a;
							}
						}
					}	
				}
			}	
		}
		
		if(target != null)
		{
			int speed = (int)(1.5*getStandardSpeed());
			double angle = Math.atan2(target.getY() + target.getYSize()/2 - this.getY(), target.getX() + target.getXSize()/2 - this.getX());
			setXSpeed((int)(speed * Math.cos(angle)));
			setYSpeed((int)(speed * Math.sin(angle) * .75));
		}
		else if(getXSpeed() == 0 && getYSpeed() == 0)
		{
			setXSpeed(2*getStandardSpeed());
		}

		super.move();	
	}
}
