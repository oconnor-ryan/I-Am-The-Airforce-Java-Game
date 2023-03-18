package root.actors.projectiles;

import java.awt.Color;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import root.actors.Actor;
import root.levels.Level;

public class Explosion extends Projectile
{
	private int blastRadius;
	private int maxRadius;
	private int[] center;
	private int rate = 0;
	
	public Explosion(Level l, int x, int y, int maxR, Actor a)
	{
		super(l);
		if(a != null && a instanceof Projectile)
		{
			setActor(((Projectile) a).getActor());
		}
		else
		{
			setActor(a);
		}
		center = new int[] {x,y};
		setX(x);
		setY(y);
		setXSize(blastRadius);
		setYSize(blastRadius);
		setHealth(1);
		setDamage(5);
		setTimeLimit(50);
		setColor(Color.WHITE);
		maxRadius = maxR;
		blastRadius = 0;

		try {
			createSprite("Explosion.txt");
			setPixelSize(1);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		rate = Level.convertToRatioX(maxR/15);
	}
	
	public Explosion clone() {return new Explosion(getLevel(), 0 , 0, maxRadius, getActor());}
	
	@Override
	public void move() {} //prevents sprite from moving
	public void handleCollision(Actor a) {}
	public void checkCollisions(CopyOnWriteArrayList<Actor> a) {} //do not process to improve frame rate
	
	@Override
	public void processEndTimer() 
	{
		blastRadius += rate;
		if(getSprite() != null)
		{
			for(int k = Level.convertToRatioX(10), size = 1; k < maxRadius; k += Level.convertToRatioX(10), size++)
			{
				if(blastRadius >= k && blastRadius <= k + Level.convertToRatioX(10))
				{
					int oldSize = getPixelSize();
					setPixelSize(Level.convertToRatioX(size));
					setCurrentSprite((int)(Math.random() * numSprites()));
				
					if(getPixelSize() > oldSize)
					{
						int[] sizes = getNewSizes();
						setX(center[0] - sizes[0]/2);
						setY(center[1] - sizes[1]/2); //centers explosion around Actor's center
					}
					break;
				}
			}			
		}
		else
		{
			setXSize(blastRadius);
			setYSize(blastRadius);
		}
		if(blastRadius > maxRadius)
		{
			setHealth(0);
		}
	}
}
