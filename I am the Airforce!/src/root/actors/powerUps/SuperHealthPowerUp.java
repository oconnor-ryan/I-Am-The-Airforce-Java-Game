package root.actors.powerUps;

import root.levels.Level;

public class SuperHealthPowerUp extends HealthPowerUp
{
	public SuperHealthPowerUp(Level l, int x, int y) 
	{
		super(l, x, y);
		this.setHealthIncrease(10000);
	}
}
