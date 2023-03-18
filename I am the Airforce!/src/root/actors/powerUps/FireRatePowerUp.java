package root.actors.powerUps;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;

import root.actors.Actor;
import root.actors.planes.PlayerJet;
import root.levels.Level;

public class FireRatePowerUp extends PowerUp
{
	public FireRatePowerUp(Level l, int x, int y)
	{
		super(l,x,y);
		setTimeLimit(10000);
		try
		{
			createSprite("FireRatePowerUp.txt");
			setPixelSize(Level.convertToRatioX(4));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void effect(Actor j) 
	{
		((PlayerJet) j).addPowerUp(0);
	}

	public void draw(Graphics g) 
	{
		g.setFont(new Font("serif", getXSize(),getYSize()));
		g.setColor(Color.ORANGE);
		g.fillRect(getX(), getY(), getXSize(), getYSize());
		g.setColor(Color.RED);
		g.drawString("F", getX() + 5, getY() + getYSize() - 5);
	}

}
