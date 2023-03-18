package root.levels;

import java.util.ArrayList;

import root.JetWorldGame.JetWorld;
import root.actors.planes.PlayerJet;

public abstract class MultiWaveLevel extends Level
{
	private int currentWave;
	private ArrayList<Wave> waves;
	public MultiWaveLevel(JetWorld w, PlayerJet p, int rate, int levelNum) 
	{
		super(w, p, rate, levelNum);
		waves = new ArrayList<Wave>();
		currentWave = 0;
	//	addWaves(); figure out if calling this too early affects game
	}
	
	public abstract void addWaves();
	public abstract void handleEndWaves();
	
	public void resetLevel()
	{
		super.resetLevel();
		resetWaveList();
		currentWave = 0;
	}
	
	public Wave getWave(int i)
	{
		if(i >= getNumWaves())
		{
			return null;
		}
		return waves.get(i);
	}
	
	public void spawnWave()
	{
		waves.get(currentWave).spawnEnemies();
	}
	
	public void resetWaveList()
	{
		waves = new ArrayList<Wave>();
		addWaves();
	}
	public void addWave(Wave w)
	{
		waves.add(w);
	}
	public int getCurrentWave()
	{
		return currentWave;
	}
	
	public void setCurrentWave(int a)
	{
		currentWave = a;
	}
	
	public int getNumWaves()
	{
		return waves.size();
	}
	
	public void nextWave()
	{
		currentWave++;
	}
	
	public void createWave(String method, ActorNames actorType)
	{
		Wave w1 = new Wave(this);
		w1.addActors(EnemySpawns.chooseSpawnMethod(method, actorType, this));
		addWave(w1);
	}

	//actorTypes does not have to have same length as methods, since this checks whether a method requires
	// an actorType to create the enemies
	public void createMultiEnemyWave(String[] methods, ActorNames[] actorTypes)
	{
		Wave w = new Wave(this);
		if(actorTypes == null)
		{
			for(String method : methods)
			{
				w.addActors(EnemySpawns.chooseSpawnMethod(method, null, this));
			}
		}
		else
		{
			int actorIndex = 0;
			for(String method : methods)
			{
				w.addActors(EnemySpawns.chooseSpawnMethod(method, actorTypes[actorIndex], this));
				if(method.indexOf("spawnActors") >= 0 || method.indexOf("spawnGround") >= 0) //indicates that actorType is necessary to create enemy
				{
					actorIndex++;
					if(actorTypes.length == actorIndex)
					{
						actorIndex--;
					}
				}
			}
		}		
		addWave(w);
	}


	@Override
	public void processEndTimer()
	{	
		nextWave();
		if(currentWave < waves.size())
		{	
			spawnWave();
		}
		else
		{
			setCurrentWave(waves.size());
			handleEndWaves();
		}
	}
}
