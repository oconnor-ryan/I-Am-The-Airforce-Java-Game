package root.actors.powerUps;

import java.awt.Graphics;
import java.io.IOException;

import root.actors.Actor;
import root.actors.planes.PlayerJet;
import root.levels.Level;

public class FlakPowerUp extends PowerUp
{
	public FlakPowerUp(Level l, int x, int y) 
	{
		super(l, x, y);
		setTimeLimit(10000);
		try
		{
			createSprite("FlakPowerUp.txt");
			setPixelSize(Level.convertToRatioX(4));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void effect(Actor j) 
	{
		((PlayerJet) j).addPowerUp(1);
	}

	@Override
	public void draw(Graphics g) 
	{

	}

}
