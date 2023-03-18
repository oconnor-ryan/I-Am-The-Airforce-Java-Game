package root.actors.groundVehicles;

import root.actors.planes.PlayerJet;
import root.actors.projectiles.Bullet;
import root.levels.Level;

public class SmartAAGun extends AAGun
{
	public SmartAAGun(Level l, int x, int y)
	{
		super(l,x,y);
	}
	public SmartAAGun(Level w, int x, int y, int xSize, int ySize, int xVel, int yVel, boolean isEnemy, int direction,
			int angle) 
	{
		super(w, x, y, xSize, ySize, xVel, yVel, isEnemy, direction, angle);
	}
	
	public double getAngle(PlayerJet player)
	{
		return Math.atan2(player.getY() + player.getYSize()/2 - this.getY(), player.getX() + player.getXSize()/2 - this.getX());
	}
	
	public int[] setTrajectory()
	{
		PlayerJet player = getLevel().getPlayer();
		int speed = 3*getStandardSpeed();
		double angle = getAngle(player);
		int xVel = (int)(speed * Math.cos(angle));
		int yVel = (int)(speed * Math.sin(angle));
		return new int[] {xVel,yVel};
	}
	
	public void shoot()
	{
		int[] traj = setTrajectory();
		if(getAngle() == Up())
		{
			addProjectile(new Bullet(getLevel(), getX(), getY() - 10, traj[0], traj[1], this));
		}
		else
		{
			addProjectile(new Bullet(getLevel(), getX() - 10, getY(), traj[0], traj[1], this));
		}
	}

}
