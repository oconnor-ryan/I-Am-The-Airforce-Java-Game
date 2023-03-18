package root.levels;

import root.JetWorldGame.JetWorld;
import root.actors.Flag;
import root.actors.bosses.SuperBattleShip;
import root.actors.environment.Sea;
import root.actors.planes.PlayerJet;
import root.actors.powerUps.FireRatePowerUp;
import root.actors.powerUps.SuperBombPowerUp;

public class SeaLevel1 extends MultiWaveLevel
{
	private boolean flagSpawned = false;
	
	public SeaLevel1(JetWorld w, PlayerJet p, int rate, int levelNum) 
	{
		super(w, p, rate, levelNum);
		setYPosGround(Level.convertToRatioY(700));
		setPlayerSpawn(0,0);
		setDistanceOffScreen(400,400);
		setTimeLimit(10000);
		addWaves();
		spawnWave();
		nextWave();
		spawnWave();
	}
	
	public void resetLevel()
	{
		super.resetLevel();
		spawnWave();
		nextWave();
		spawnWave();
		flagSpawned = false;
	}

	@Override
	public void addWaves() 
	{
		Wave w = new Wave(this);
		w.addActor(new Sea(this, Level.convertToRatioY(700)));
		addWave(w);
		createWave("spawnGroundVehicleRightside1", ActorNames.PT_BOAT);
		Wave w1 = new Wave(this);
		SuperBattleShip b = new SuperBattleShip(this, convertToRatioX(1370), 0);
		b.setY(getYPosGround() - b.getYSize());
		b.setXSpeed(-1);
		w1.addActor(b);
		w1.addActor(new SuperBombPowerUp(this, convertToRatioX(1000), convertToRatioY(200)));
		w1.addActor(new FireRatePowerUp(this, convertToRatioX(900), convertToRatioY(200)));
		addWave(w1);
	}

	@Override
	public void handleEndWaves() 
	{
		if(!flagSpawned && allEnemiesDead())
		{
			add(new Flag(this, convertToRatioX(1200), convertToRatioY(300)));
			flagSpawned = true;
		}
	}
}
