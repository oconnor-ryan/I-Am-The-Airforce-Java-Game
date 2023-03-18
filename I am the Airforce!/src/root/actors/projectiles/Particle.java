package root.actors.projectiles;

import java.awt.Color;

import root.actors.Actor;

public class Particle extends Actor
{
	private static final int GRAV = (int)(Math.random() * 2) + 3;
	public Particle(int x, int y, Color c)
	{
		setX(x);
		setY(y);
		setColor(c);
	}
	
	public void move()
	{
		setYSpeedWithoutScale(getYSpeed() + GRAV);
		if(getYSpeed() >= GRAV)
		{
			setYSpeed(GRAV);
		}
		super.move();
	}
	@Override
	public void processEndTimer() {}
	
	@Override
	public void handleCollision(Actor a) {}
	
}
