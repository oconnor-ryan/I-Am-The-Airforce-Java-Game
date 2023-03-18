package root.actors.powerUps;

import java.awt.Graphics;
import java.io.IOException;

import root.actors.Actor;
import root.actors.planes.PlayerJet;
import root.levels.Level;

public class SpeedPowerUp extends PowerUp
{

	public SpeedPowerUp(Level l, int x, int y) 
	{
		super(l, x, y);
		setTimeLimit(10000);
		try {
			createSprite("SpeedPowerUp.txt");
			setPixelSize(Level.convertToRatioX(4));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void effect(Actor j) 
	{
		((PlayerJet) j).addPowerUp(2);
	}

	@Override
	public void draw(Graphics g) {}

}
