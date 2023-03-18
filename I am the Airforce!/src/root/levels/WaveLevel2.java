package root.levels;

import root.JetWorldGame.JetWorld;
import root.actors.environment.Ground;
import root.actors.planes.PlayerJet;
import root.actors.powerUps.HealthPowerUp;
import root.actors.powerUps.InvinciblePowerUp;
import root.actors.powerUps.MysteryPowerUp;

public class WaveLevel2 extends MultiWaveLevel
{
	private Ground ground;
	public WaveLevel2(JetWorld w, PlayerJet p, int rate, int levelNum) 
	{
		super(w, p, rate, levelNum);
		setTimeLimit(10000);
		setPlayerSpawn(0,0);
		setDistanceOffScreen(500,500);
		setYPosGround(convertToRatioY(700));
		ground = new Ground(this, getYPosGround());
		add(ground);
		addWaves();
		spawnWave();
	}
	
	public void resetLevel()
	{
		super.resetLevel();
		spawnWave();
		add(ground);
	}

	public void handleEndWaves(){} //do nothing
	
	public void addWaves() 
	{
		add(new HealthPowerUp(this, convertToRatioX(600), convertToRatioY(300)));
		createWave("spawnGroundVehicleRightside1", ActorNames.FLAK_GUN);
		createWave("spawnGroundVehicleRightside1", ActorNames.SMART_FLAK_GUN);
		createWave("spawnGroundVehicleRightside1", ActorNames.SMART_SHOTGUN_AAGUN);
		createWave("spawnGroundVehicleRightside1", ActorNames.SHOTGUN_FLAK_GUN);
		createWave("spawnGroundVehicleRightside1", ActorNames.BOMB_FLINGER);
		createMultiEnemyWave(new String[] {"spawnFlag", "spawnPowerUps"}, null);
		Wave w = getWave(2);
		w.addActor(new InvinciblePowerUp(this, convertToRatioX(900), convertToRatioY(300)));
		Wave w1 = getWave(3);
		w1.addActor(new MysteryPowerUp(this, convertToRatioX(500), convertToRatioY(300)));
	}
}
