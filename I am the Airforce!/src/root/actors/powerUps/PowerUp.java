package root.actors.powerUps;
import java.awt.Graphics;

import root.actors.Actor;
import root.actors.planes.PlayerJet;
import root.levels.Level;

public abstract class PowerUp extends Actor
{
	private boolean isVisible = true;
	private boolean neverDisappears = false;
	
	public abstract void effect(Actor j);
	public abstract void draw(Graphics g);
	
	public PowerUp(Level l, int x, int y)
	{
		setLevel(l);
		setX(x);
		setY(y);
		setHealth(1);
		setScore(500);
	}
	
	public void drawSprite(Graphics g)
	{
		if(getTimerCount() > 5*getTimeLimit()/6)
		{
			isVisible = !isVisible;
		}
		if((isVisible && !neverDisappears) || neverDisappears) //flickers when about to disappear
		{
			super.drawSprite(g);
		}
	}
	
	public void setNeverDisappears(boolean b)
	{
		neverDisappears = b;
	}
	
	public void processEndTimer()
	{
		if(!neverDisappears)
		{
			kill();
		}
	}
	
	public void handleCollision(Actor a)
	{
		if(a instanceof PlayerJet)
		{
			this.effect(a);
			this.kill();
		}
	}
}
