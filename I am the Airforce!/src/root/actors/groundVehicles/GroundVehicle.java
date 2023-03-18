package root.actors.groundVehicles;
import java.awt.Color;

import root.actors.Actor;
import root.actors.Flag;
import root.actors.Vehicle;
import root.actors.environment.EnvironmentHazard;
import root.actors.environment.Ground;
import root.actors.powerUps.PowerUp;
import root.actors.projectiles.Bullet;
import root.actors.projectiles.Flak;
import root.actors.projectiles.Missle;
import root.actors.projectiles.Projectile;
import root.levels.Level;

public class GroundVehicle extends Vehicle
{
	private static final int GRAV = 2;
	private int angleOfShooting;
	
	public GroundVehicle(Level l, int x, int y)
	{
		super(l,x,y);
		setAngle(Up());
		setScore(150);
		setIsEnemy(true);
	}
	
	public GroundVehicle(Level w, int x, int y, int xSize, int ySize, int xVel, int yVel, boolean isEnemy, int direction, int angle)
	{
		super(w,x,y,xSize,ySize,xVel,yVel,isEnemy,direction);
		angleOfShooting = angle;
		setHealth(100);
		setHealthFromLastTick(getHealth());
		setColor(Color.WHITE);
		setScore(150);
	}
	
	public void processEndTimer()
	{
		shoot();
	}
	
	public int getAngle()
	{
		return angleOfShooting;
	}
	
	public void setAngle(int n)
	{
		angleOfShooting = n;
	}
	
	public static final int gravity()
	{
		return GRAV;
	}
	
	public void move()
	{
		setYSpeed(GRAV);
		super.move();
	}
	
	public void shoot()
	{
		if(angleOfShooting == Up())
		{
			getProjectiles().add(new Bullet(getLevel(), getX()+getXSize()/2, getY() - 10, 0, -3*getStandardSpeed(), this));
		}
		else
		{
			getProjectiles().add(new Bullet(getLevel(), getX() - 10, getY(), -3*getStandardSpeed(), 0, this));
		}
	}
	
	public void shootFlak()
	{
		if(angleOfShooting == Up())
		{
			getProjectiles().add(new Flak(getLevel(), getX()+getXSize()/2, getY() - 10, 0, -3*getStandardSpeed(), this));
		}
		else
		{
			getProjectiles().add(new Flak(getLevel(), getX() - 10, getY(), -3*getStandardSpeed(), 0, this));
		}
	}
	
	public void fireMissle()
	{
		if(angleOfShooting == Up())
		{
			getProjectiles().add(new Missle(getX(), getY() - 10, this, getLevel(), Up()));
		}
		else
		{
			getProjectiles().add(new Missle(getX() - 30, getY(), this, getLevel(), Left()));
		}
	}
	
	public void handleCollision(Actor a)
	{
		super.handleCollision(a);
		if(a instanceof Ground)
		{
			this.setY(a.getY() - this.getYSize());
			this.setYSpeed(0);
		}
		else if(a instanceof GroundVehicle || a instanceof EnvironmentHazard)
		{
			this.setXSpeed(-this.getXSpeed());
		}	
		else if(!(a instanceof Projectile) && !(a instanceof Flag) && !(a instanceof PowerUp))
		{
			this.kill();
		}
	}
}
