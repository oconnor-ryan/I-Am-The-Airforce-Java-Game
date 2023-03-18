package root.actors.powerUps;

import java.awt.Graphics;

import root.actors.Actor;
import root.actors.planes.PlayerJet;
import root.levels.Level;

public class ShotgunPowerUp extends PowerUp
{
	public ShotgunPowerUp(Level l, int x, int y) 
	{
		super(l, x, y);
		setTimeLimit(10000);
		
		try {
			createSprite("ShotgunPowerUp.txt");
			setPixelSize(Level.convertToRatioX(4));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void effect(Actor j) 
	{
		((PlayerJet) j).addPowerUp(3);
	}

	@Override
	public void draw(Graphics g) 
	{
		// TODO Auto-generated method stub
		
	}

}
