package root.actors.bosses;

import root.actors.seaVehicles.BattleShip;
import root.levels.Level;

public class SuperBattleShip extends BattleShip
{
	public SuperBattleShip(Level l, int x, int y) 
	{
		super(l, x, y);
		setCanDieFromEnemies(false);
		setScore(5000);
		setMaxHealth(9500);
		setHealth(getMaxHealth());
		setIsBoss(true);
	}
}
