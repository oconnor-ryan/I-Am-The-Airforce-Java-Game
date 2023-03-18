package root.levels;

import java.awt.Color;

import root.JetWorldGame.JetWorld;
import root.actors.planes.PlayerJet;
import root.actors.planes.SmartJet;

public class WaveLevel4 extends MultiWaveLevel
{
	public WaveLevel4(JetWorld w, PlayerJet p, int rate, int levelNum) 
	{
		super(w, p, rate, levelNum);
		setYPosGround(1000);
		setPlayerSpawn(30,30);
		setTimeLimit(1000);
		setDistanceOffScreen(20,20);
		setBackground(new Color(30, 10, 50));
		addWaves();
		spawnWave();
	}
	
	public void resetLevel()
	{
		super.resetLevel();
		spawnWave();
	}
	
	private Wave createShortWave()
	{
		Wave w = new Wave(this);
		SmartJet j = new SmartJet(this, convertToRatioX(1000),(int) (Math.random() * convertToRatioY(400)));
		j.setXSpeed(-1);
		j.setYSpeed(-2);
		w.addActor(j);
		return w;
	}

	@Override
	public void addWaves() 
	{
		for(int k = 0; k < 10; k++)
		{
			addWave(createShortWave());
		}
		createWave("spawnFlag", null);
	}

	@Override
	public void handleEndWaves() {}
}
