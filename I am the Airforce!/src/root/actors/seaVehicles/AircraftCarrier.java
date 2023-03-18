package root.actors.seaVehicles;

import java.awt.Color;
import java.awt.Graphics;

import root.actors.planes.Bomber;
import root.actors.planes.FlakJet;
import root.actors.planes.MissleJet;
import root.actors.planes.Plane;
import root.actors.planes.PlayerJet;
import root.actors.planes.SmartFlakJet;
import root.actors.projectiles.Flak;
import root.actors.projectiles.Flare;
import root.actors.projectiles.Missle;
import root.levels.Level;

public class AircraftCarrier extends SeaVehicle
{
	private boolean showWarningRect = false;
	private int planeTimer = 0;
	private int planeLimit = 300;
	private int flareTimer = 0;
	private int flareLimit = 30;
	private int missleTimer = 0;
	private int missleLimit = 40;
	public AircraftCarrier(Level l, int x, int y) 
	{
		super(l, x, y);
		setTimeLimit(150);
		setMaxHealth(10000);
		setHealth(getMaxHealth());
		setScore(5000);
		try
		{
			createSprite("AircraftCarrier.txt");
			setPixelSize(Level.convertToRatioX(10));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void move()
	{
		super.move();
		if(getX() < 0)
		{
			setXSpeed(-getXSpeed());
			setX(0);
		}
		else if(getX() > Level.convertToRatioX(1370))
		{
			setXSpeed(-Math.abs(getXSpeed()));
			setX(Level.convertToRatioX(1370));
		}
	}
	
	public double getAngle(PlayerJet player, int x, int y)
	{
		return Math.atan2(player.getY() + player.getYSize()/2 - y, player.getX() + player.getXSize()/2 - x);
	}
	
	public int[] setTrajectory(double angle)
	{
		int speed = 3*getStandardSpeed();
		int xVel = (int)(speed * Math.cos(angle));
		int yVel = (int)(speed * Math.sin(angle));
		return new int[] {xVel,yVel};
	}

	public void tick()
	{
		super.tick();
		planeTimer++;
		if(planeTimer > planeLimit)
		{
			planeTimer = 0;
			for(int x = getX() - getXSize()/2; x < getX() + 3*getXSize()/2; x += getXSize()/2)
			{
				int planeNum = (int)(Math.random() * 4);
				Plane p = null;
				switch(planeNum)
				{
				case 0:
					p = new SmartFlakJet(getLevel(),0,0);
					break;
				case 1:
					p = new MissleJet(getLevel(),0,0);
					break;
				case 2:
					p = new Bomber(getLevel(),0,0);
					break;
				case 3:
					p = new FlakJet(getLevel(),0,0);
					break;
				}
				p.setX(x);
				p.setY(getY() - 3*p.getYSize());
				p.setXSpeed(Level.convertToRatioX(-3));
				p.setYSpeed(-1);
				p.setCanDieFromEnemies(false);
				getLevel().add(p);
			}
		}
	}

	public void shoot()
	{
		PlayerJet j = getLevel().getPlayer();
		int turr1X = getX();
		int turr1Y = getY() + getYSize()/2;
		int turr2X = getX() + 2*getXSize()/3;
		int turr2Y = getY();
		int turr3X = getX() + getXSize()/2;
		int turr3Y = getY() + getYSize()/4;
		
		int[] turrent1Vel = setTrajectory(getAngle(j, turr1X, turr1Y));
		int[] turrent2Vel = setTrajectory(getAngle(j, turr2X, turr2Y));
		int[] turrent3Vel = setTrajectory(getAngle(j, turr3X, turr3Y));
		
		getProjectiles().add(new Flak(getLevel(), turr1X, turr1Y, turrent1Vel[0], turrent1Vel[1], this));
		getProjectiles().add(new Flak(getLevel(), turr2X, turr2Y, turrent2Vel[0], turrent2Vel[1], this));
		getProjectiles().add(new Flak(getLevel(), turr3X, turr3Y, turrent3Vel[0], turrent3Vel[1], this));
		
		flareTimer++;
		if(flareTimer > flareLimit)
		{
			flareTimer = 0;
			getProjectiles().add(new Flare(getLevel(), getX(), getY(), -getStandardSpeed()/2, -6*getStandardSpeed(), this));
			getProjectiles().add(new Flare(getLevel(), getX() + getXSize()/2, getY(), 0, -6*getStandardSpeed(), this));
			getProjectiles().add(new Flare(getLevel(), getX() + getXSize(), getY(), getStandardSpeed()/2, -6*getStandardSpeed(), this));
		}
		
		missleTimer++;
		if(missleTimer > missleLimit)
		{
			missleTimer = 0;
			Missle[] missles = new Missle[3];
			missles[0] = new Missle(getX() - 40, getY(), this, getLevel(), Up());
			missles[1] = new Missle(getX() + getXSize()/2, getY() - 40, this, getLevel(), Up());
			missles[2] = new Missle(getX() + getXSize() + 40, getY(), this, getLevel(), Up());
			for(Missle m : missles)
			{
				m.setTarget(getLevel().getPlayer());
				getProjectiles().add(m);
			}
		}
	}
	
	public void drawSprite(Graphics g)
	{
		super.drawSprite(g);
		showWarningRect = !showWarningRect;
		if(planeTimer > 3*planeLimit/4 && showWarningRect)
		{
			g.setColor(Color.RED);
			g.fillRect(getX() - getXSize()/2, getY() - getYSize(), 2*getXSize(), getYSize()/2);
		}
	}
}
