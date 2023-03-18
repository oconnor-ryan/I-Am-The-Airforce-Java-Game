package root.levels;

import root.JetWorldGame.JetWorld;
import root.actors.Flag;
import root.actors.bosses.AircraftCarrierA;
import root.actors.environment.Ground;
import root.actors.environment.Sea;
import root.actors.planes.PlayerJet;
import root.actors.powerUps.FireRatePowerUp;
import root.actors.powerUps.SuperHealthPowerUp;

public class FinalLevel extends MultiWaveLevel
{
	private int healthTimer = 0;
	private int healthLimit = 10;
	private boolean flagSpawned = false;
	private boolean bossSpawned = false;
	private boolean endTransition = false;
	private boolean handleWaveStart = false;
	private int seaX = Level.convertToRatioX(1000);
	private int seaDiv = Level.convertToRatioX(1368)/4;
	private Sea sea = null;
	public FinalLevel(JetWorld w, PlayerJet p, int rate, int levelNum) 
	{
		super(w, p, rate, levelNum);
		setPlayerSpawn(0,0);
		setDistanceOffScreen(600,200);
		setTimeLimit(10000);
		setYPosGround(Level.convertToRatioY(700));
		addWaves();
		spawnWave();
		nextWave();
		spawnWave();
	}
	
	public void resetLevel()
	{
		super.resetLevel();
		setTimeLimit(10000);
		bossSpawned = false;
		endTransition = false;
		handleWaveStart = false;
		flagSpawned = false;
		seaX = Level.convertToRatioX(1000);
		healthTimer = 0;
		sea = null;
		spawnWave();
		nextWave();
		spawnWave();
	}
	
	@Override
	public void addWaves() 
	{
		Wave w = new Wave(this);
		w.addActor(new Ground(this, getYPosGround()));
		w.addActor(new SuperHealthPowerUp(this, convertToRatioX(100), convertToRatioY(100)));
		addWave(w);
		String[] air = new String[] {"spawnActorsRightside1", "spawnGroundVehicleRightside1"};
		String[] airPowerups = new String[] {"spawnActorsRightside1", "spawnGroundVehicleRightside1", "spawnPowerUps"};
		String[] airHealth = new String[] {"spawnActorsRightside1", "spawnGroundVehicleRightside1", "spawnHealth"};
		createMultiEnemyWave(airPowerups, new ActorNames[] {ActorNames.SMART_SHOTGUN_FLAK_JET, ActorNames.SMART_SHOTGUN_FLAK_GUN});
		createMultiEnemyWave(airHealth, new ActorNames[] {ActorNames.MISSLE_JET, ActorNames.SAM});
		createMultiEnemyWave(airHealth, new ActorNames[] {ActorNames.SMART_FLAK_JET, ActorNames.SMART_FLAK_GUN});
		createMultiEnemyWave(airHealth, new ActorNames[] {ActorNames.BOMBER, ActorNames.BOMB_FLINGER});
		createMultiEnemyWave(air, new ActorNames[] {ActorNames.SINE_WAVE_BOMBER, ActorNames.BOMB_FLINGER});
	}
	@Override
	public void handleEndWaves() 
	{
		if(!endTransition)
		{
			if(!handleWaveStart)
			{
				this.setTimeLimit(1000);
				sea = new Sea(this, getYPosGround());
				add(sea);
				handleWaveStart = true;
			}
			sea.setX(seaX);
			seaX -= seaDiv;
			if(seaX < -seaDiv)
			{
				this.getGround().setUnkillable(false);
				this.getGround().kill();
				sea = new Sea(this, getYPosGround());
				endTransition = true;
			}
		}
		else if(!bossSpawned)
		{
			AircraftCarrierA b = new AircraftCarrierA(this, convertToRatioX(1370), 0);
			b.setY(getYPosGround() - b.getYSize());
			b.setXSpeed(-3);
			add(b);
			add(new SuperHealthPowerUp(this, convertToRatioX(500), convertToRatioY(500)));
			add(new FireRatePowerUp(this, convertToRatioX(400), convertToRatioY(500)));
			bossSpawned = true;
		}
		else if(allEnemiesDead() && !flagSpawned)
		{
			add(new Flag(this, convertToRatioX(1200), convertToRatioY(300)));
			flagSpawned = true;
		}
		else if(!flagSpawned)
		{
			healthTimer++;
			if(healthTimer > healthLimit)
			{
				healthTimer = 0;
				add(new SuperHealthPowerUp(this, convertToRatioX(500), convertToRatioY(500)));
				add(new FireRatePowerUp(this, convertToRatioX(400), convertToRatioY(500)));
			}			
		}
	}
}
