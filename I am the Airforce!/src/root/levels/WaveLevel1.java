package root.levels;

import root.JetWorldGame.JetWorld;
import root.actors.Flag;
import root.actors.environment.Ground;
import root.actors.planes.PlayerJet;

public class WaveLevel1 extends MultiWaveLevel
{
	private boolean endWaves = false;
	private int numResets = 0;
	private boolean flagSpawned = false;
	private Ground ground;
	public WaveLevel1(JetWorld w, PlayerJet p, int rate, int levelNum) 
	{
		super(w, p, rate, levelNum);
		setTimeLimit(5000);
		setPlayerSpawn(0,0);
		setYPosGround(convertToRatioY(700));
		setDistanceOffScreen(500,500);
		ground = new Ground(this, getYPosGround());
		add(ground);
		addWaves();
		spawnWave();
	}

	@Override
	public void addWaves() 
	{
		createWave("spawnGroundVehicleRightside1", ActorNames.AAGUN);
		createWave("spawnActorsRightside1", ActorNames.SINE_WAVE_BOMBER);
		createWave("spawnActorsRightside1", ActorNames.MISSLE_JET);
		createWave("spawnPowerUps", null);
	}
	
	public void resetLevel()
	{
		super.resetLevel();
		add(ground);
		spawnWave();
		numResets = 0;
		endWaves = false;
		flagSpawned = false;
	}
	
	public void handleEndWaves()
	{
		if(numResets < 2) //3 wave repeats
		{
			setCurrentWave(0);
			resetWaveList();
			numResets++;
			spawnWave();
		}
		else
		{
			endWaves = true;
		}
	}
	
	public void tick()
	{
		super.tick();
		if(endWaves && allEnemiesDead() && !flagSpawned)
		{
			add(new Flag(this, convertToRatioX(400),convertToRatioY(400)));
			flagSpawned = true;
		}
	}
}
