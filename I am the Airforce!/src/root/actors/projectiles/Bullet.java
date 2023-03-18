package root.actors.projectiles;
import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

import root.actors.Actor;
import root.actors.Flag;
import root.actors.planes.PlayerJet;
import root.actors.powerUps.PowerUp;
import root.levels.Level;

public class Bullet extends Projectile
{
	public Bullet(Level l, int x, int y, int xVel, int yVel, Actor a)
	{
		super(l);
		setX(x);
		setY(y);
		setXSpeed(xVel);
		setYSpeed(yVel);
		setXSize(7);
		setYSize(3);
		setDamage(20);
		setHealth(1);
		setActor(a);
		try
		{
			createSprite("Bullet.txt");
			setPixelSize(Level.convertToRatioX(1));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public Bullet clone() {return new Bullet(getLevel(), 0, 0, 0, 0, getActor());}
	
	public void tick()
	{
		super.tick();
		double scale = getLevel().getWorld().getRunner().getScaleFactor()[0];
		if(this.getActor() != null && this.getActor() instanceof PlayerJet && ((PlayerJet) this.getActor()).hasFirePowerUp()
				&&  scale != 1)
		{
			if(this.getXSpeed() > 0 && scale < 1)
			{
				setXSpeed((int)(Math.round(3*getStandardSpeed()/scale)));
			}
			else if(this.getXSpeed() < 0 && scale < 1)
			{
				setXSpeed((int)(Math.round(-3*getStandardSpeed()/scale)));
			}
		}
	}
	
	public void handleCollision(Actor a)
	{
		if(!(a instanceof Flag) && !(a instanceof PowerUp) && !(a instanceof Explosion))
		{
			if(a instanceof Bullet && ((Bullet) a).getActor() != null && this.getActor() != null)
			{
				if(!((Bullet) a).getActor().equals(this.getActor()))
				{
					this.kill();
				}
			}
			else if(getActor() == null || (getActor() != null && !a.equals(getActor())))
			{
				this.kill();
			}
			
		}	
	}
	
	public void processEndTimer()
	{
		//use to prevent projectile from killing Player until this is activated and moves away from player
	}
	
	public void draw(Graphics g)
	{
		g.setColor(Color.RED);
		
		if(Math.abs(getYSpeed()) > Math.abs(getXSpeed()))
		{
			g.fillRect(getX(), getY(), getYSize(), getXSize());
		}
		else
		{
			g.fillRect(getX(), getY(), getXSize(), getYSize());
		}
	}
}
