package root.levels;

import root.JetWorldGame.JetWorld;
import root.actors.planes.PlayerJet;

public class WaveLevel3 extends MultiWaveLevel
{
	public WaveLevel3(JetWorld w, PlayerJet p, int rate, int levelNum) 
	{
		super(w, p, rate, levelNum);
		setPlayerSpawn(0,0);
		setTimeLimit(10000);
		setDistanceOffScreen(500, 100);
		addWaves();
		spawnWave();
	}
	
	public void resetLevel()
	{
		super.resetLevel();
		spawnWave();
	}

	@Override
	public void addWaves() 
	{
		createWave("spawnActorsRightside1", ActorNames.WAVE_JET);
		for(int k = 0; k < 4; k++)
		{
			
			if(k % 2 == 0) // 0 % 2 = 2, not 0
			{
				createMultiEnemyWave(new String[] {"spawnPowerUps", "spawnActorsRightside1", 
												   "spawnActorsRightside1","spawnActorsRightside1", "spawnActorsRightside1"},
									 new ActorNames[] {ActorNames.SHOTGUN_JET, ActorNames.FLAK_SHOTGUN_JET,
											 		   ActorNames.WAVE_JET, ActorNames.FLAK_JET});
			}
			else
			{
				createMultiEnemyWave(new String[] {"spawnActorsRightside1", "spawnActorsRightside1"}, 
							     new ActorNames[] {ActorNames.WAVE_JET, ActorNames.FLAK_JET});
			}
		}
		createWave("spawnFlag", null);
	}
	
	@Override
	public void handleEndWaves(){}
}
