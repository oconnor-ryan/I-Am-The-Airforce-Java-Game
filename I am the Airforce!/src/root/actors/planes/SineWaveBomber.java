package root.actors.planes;

import root.levels.Level;

public class SineWaveBomber extends Bomber
{
	private int spawnHeight;

	public SineWaveBomber(Level l, int x, int y)
	{
		super(l,x,y);
		spawnHeight = y;
	}
	
	public SineWaveBomber(Level w, int x, int y, int xVel, int yVel, int xSize, int ySize, boolean isEnemy, int direction) 
	{
		super(w, x, y, xVel, yVel, xSize, ySize, isEnemy, direction);
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
		if(getY() < spawnHeight - Level.convertToRatioY(40) || getY() > spawnHeight + Level.convertToRatioY(40))
		{
			setYSpeed(-getYSpeed());
		}
		super.move();
	}

}
