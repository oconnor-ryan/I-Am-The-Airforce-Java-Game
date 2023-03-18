package root.actors.seaVehicles;

import root.actors.Actor;
import root.actors.environment.Sea;
import root.actors.groundVehicles.GroundVehicle;
import root.actors.projectiles.Projectile;
import root.levels.Level;

public class SeaVehicle extends GroundVehicle
{
	public SeaVehicle(Level l, int x, int y)
	{
		super(l,x,y);
		setScore(200);
		setHealth(200);
		setHealthFromLastTick(getHealth());
	}
	
	public void handleCollision(Actor a)
	{
		if(a instanceof Projectile) 
		{
			//you cannot shoot yourself(For Now)//change this later
			if(((Projectile) a).getActor() == null 
					|| ((Projectile) a).getActor() != null && !((Projectile) a).getActor().equals(this))
			{
				this.takeDamage(((Projectile) a).getDamage());
			}
			if(this.getHealth() <= 0 &&  ((Projectile) a).getActor() != null 
					&& 	((Projectile) a).getActor().equals(getLevel().getPlayer()))
			{
				getLevel().getPlayer().addTotalScore(this.getScore());
			}
		}
		else if(a instanceof Sea)
		{
			this.setY(a.getY() - this.getYSize());
			this.setYSpeed(0);
		}
		else if(!this.isBoss())
		{
			this.kill();
		}
		if(isDead())
		{
			explode();
		}
	}
}
