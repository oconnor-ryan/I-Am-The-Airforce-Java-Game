package root.actors.planes;

import root.levels.Level;

public class WaveJet extends Jet
{
	private int spawnHeight;
	
	public WaveJet(Level l, int x, int y)
	{
		super(l,x,y);
		spawnHeight = y;
	}
	public WaveJet(Level w, int x, int y, int xSize, int ySize, int xVel, int yVel, boolean isEnemy, int direction) 
	{
		super(w, x, y, xSize, ySize, xVel, yVel, isEnemy, direction);
		spawnHeight = y;
	}
	
	public void setY(int y)
	{
		super.setY(y);
		spawnHeight = y;
	}
	
	public void move()
	{
		if(getYSpeed() == 0)
		{
			setYSpeed(1);
		}
		if(getY() < spawnHeight - Level.convertToRatioY(20) || getY() > spawnHeight + Level.convertToRatioY(20))
		{
			setYSpeed(-getYSpeed());
		}

		super.move();
	}
	
}
