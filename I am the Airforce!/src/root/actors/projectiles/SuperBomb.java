package root.actors.projectiles;

import root.actors.Actor;
import root.levels.Level;

public class SuperBomb extends Bomb
{
	public SuperBomb(Level l, int x, int y, int xVel, int yVel, Actor a) 
	{
		super(l, x, y, xVel, yVel, a);
	}
	public void explode()
	{
		getLevel().add(new Explosion(getLevel(),getX()+getXSize()/2, getY()+getYSize()/2,Level.convertToRatioX(250), this));
	}
}
