package root.actors.environment;

import java.awt.Color;

import root.levels.Level;

public class Sea extends Ground
{
	public Sea(Level l, int y)
	{
		super(l,y);
		setColor(Color.BLUE);
	}
	public Sea(Level l, int x, int y, int xSize, int ySize, int xVel, int yVel) 
	{
		super(l, x, y, xSize, ySize, xVel, yVel);
		setColor(Color.BLUE);
	}
}
