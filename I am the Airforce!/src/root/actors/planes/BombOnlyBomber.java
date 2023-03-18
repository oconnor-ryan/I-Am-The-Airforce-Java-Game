package root.actors.planes;

import root.levels.Level;

public class BombOnlyBomber extends Bomber
{
	public BombOnlyBomber(Level l, int x, int y)
	{
		super(l,x,y);
		setTimeLimit(700);
		setHealth(500);
	}
	public BombOnlyBomber(Level w, int x, int y, int xSize, int ySize, int xVel, int yVel, boolean isEnemy, int direction) 
	{
		super(w, x, y, xSize, ySize, xVel, yVel, isEnemy, direction);
		setTimeLimit(700);
		setHealth(500);
	}
	public void processEndTimer()
	{
		dropBomb();
	}
}
