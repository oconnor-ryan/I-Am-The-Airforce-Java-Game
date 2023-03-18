package root.actors.powerUps;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;

import root.actors.Actor;
import root.levels.Level;

public class HealthPowerUp extends PowerUp
{
	private int health;
	
	public HealthPowerUp(Level l, int x, int y)
	{
		super(l,x,y);
		setTimeLimit(10000);
		setHealthIncrease(200);
		try
		{
			createSprite("HealthPowerUp.txt");
			setPixelSize(Level.convertToRatioX(4));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void setHealthIncrease(int h)
	{
		health = h;
	}
	
	public int getHealthIncrease()
	{
		return health;
	}
	public void effect(Actor j) 
	{
		j.setHealth(j.getHealth() + health);
	}
	
	public void draw(Graphics g)
	{
		g.setFont(new Font("serif", getXSize(),getYSize()));
		g.setColor(Color.GREEN);
		g.fillRect(getX(), getY(), getXSize(), getYSize());
		g.setColor(Color.RED);
		g.drawString("H", getX() + 5, getY() + getYSize() - 5);
	}
	
}
