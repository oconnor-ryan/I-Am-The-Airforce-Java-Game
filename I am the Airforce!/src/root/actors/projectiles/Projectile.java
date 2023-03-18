package root.actors.projectiles;

import root.actors.Actor;
import root.levels.Level;

public abstract class Projectile extends Actor
{
	private int damage;
	private Actor actorShotFrom;
	
	@Override
	public abstract void processEndTimer(); 
	public abstract void handleCollision(Actor a);
	public abstract Projectile clone();
	
	public Projectile(Level l)
	{
		setLevel(l);
	}

	public int getDamage()
	{
		return damage;
	}
	
	public void setDamage(int d)
	{
		damage = d;
	}
	
	public void setActor(Actor a)
	{
		actorShotFrom = a;
	}
	public Actor getActor()
	{
		return actorShotFrom;
	}
	
	public boolean isA(Projectile b)
	{
		return this.toString().equals(b.toString());
	}
	
	public void move()
	{
		super.move();
		if(getXSpeed() >= 0 && Math.abs(getXSpeed()) >= Math.abs(getYSpeed()))
		{
			setCurrentSprite(0);//right
		}
		else if(getXSpeed() < 0 && Math.abs(getXSpeed()) >= Math.abs(getYSpeed()))
		{
			setCurrentSprite(1);//left
		}
		else if(getYSpeed() >= 0)
		{
			setCurrentSprite(2);//down
		}
		else if(getYSpeed() < 0)
		{
			setCurrentSprite(3); //up
		}
	}
}
