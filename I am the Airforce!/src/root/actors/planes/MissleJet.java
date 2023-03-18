package root.actors.planes;

import java.util.concurrent.CopyOnWriteArrayList;

import root.actors.Actor;
import root.actors.projectiles.Missle;
import root.levels.Level;

public class MissleJet extends WaveJet
{
	public MissleJet(Level l, int x, int y)
	{
		super(l,x,y);
		setTimeLimit(2000);
	}
	public MissleJet(Level w, int x, int y, int xSize, int ySize, int xVel, int yVel, boolean isEnemy, int direction) 
	{
		super(w, x, y, xSize, ySize, xVel, yVel, isEnemy, direction);
		setTimeLimit(2000);
	}
	
	public void checkCollisions(CopyOnWriteArrayList<Actor> actors)
	{
		super.checkCollisions(actors);
		if(isDead())
		{
			explode();
		}
	}
	
	public void processEndTimer()
	{
		if((getLevel().getPlayer().getX() > this.getX() && getDirection() == Right())
				|| (getLevel().getPlayer().getX() < this.getX() && getDirection() == Left()))
		{
			fireMissle();
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
			Missle m;
			if(getDirection() == Right())
			{
				m = new Missle(getX() + getXSize() + 30, getY() + getYSize(), this, getLevel(), Right());
			}
			else
			{
				m = new Missle(getX() - 30, getY() + getYSize(), this, getLevel(), Left());
			}
			m.setTarget(j);
			addProjectile(m);
		}
	}
}
