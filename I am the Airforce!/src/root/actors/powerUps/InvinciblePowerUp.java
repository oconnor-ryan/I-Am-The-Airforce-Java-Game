package root.actors.powerUps;

import java.awt.Graphics;

import root.actors.Actor;
import root.actors.planes.PlayerJet;
import root.levels.Level;

public class InvinciblePowerUp extends PowerUp
{

	public InvinciblePowerUp(Level l, int x, int y) 
	{
		super(l, x, y);
		setTimeLimit(10000);
		try {
			createSprite("InvinciblePowerUp.txt");
			setPixelSize(Level.convertToRatioX(4));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void effect(Actor j) 
	{
		if(j instanceof PlayerJet)
		{
			((PlayerJet) j).addPowerUp(4);
		}
		else
		{
			j.setUnkillable(true);
		}
	}

	@Override
	public void draw(Graphics g) {}

}
