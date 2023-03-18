package root.actors.environment;

import root.levels.Level;

public class Ground extends EnvironmentHazard
{
	public Ground(Level l, int y)
	{
		super(l,Level.convertToRatioX(-l.getOffScreenX()),y);
		setUnkillable(true);
		setXSize(l.getWidth() + 2*Level.convertToRatioX(l.getOffScreenX()));
		setYSize(l.getHeight()/3);
	}
	
	public Ground(Level l, int x, int y, int xSize, int ySize, int xVel, int yVel) 
	{
		super(l, x, y, xSize, ySize, xVel, yVel);
		setUnkillable(true);
	}
}
