package root.levels;

import java.awt.Color;
import java.awt.Graphics;

import root.JetWorldGame.JetWorld;
import root.actors.Flag;
import root.actors.environment.Ground;
import root.actors.groundVehicles.AAGun;
import root.actors.groundVehicles.SAM;
import root.actors.planes.Bomber;
import root.actors.planes.PlayerJet;
import root.actors.projectiles.Bullet;
import root.actors.projectiles.Flare;

//Adaptation of first Level ever tested, named Level2
public class WaveLevel0 extends MultiWaveLevel
{
	public WaveLevel0(JetWorld w, PlayerJet p, int rate, int levelNum) 
	{
		super(w, p, rate, levelNum);
		setTimeLimit(10000);
		setPlayerSpawn(0,0);
		setYPosGround(convertToRatioY(700));
		setDistanceOffScreen(500,500);
		addWaves();
		spawnWave();
	}
	
	public void resetLevel()
	{
		super.resetLevel();
		spawnWave();
	}
	
	private void makeCustomWave(Wave w1)
	{
		w1.addActor(new Ground(this, 0, getYPosGround(), convertToRatioX(1370), convertToRatioY(400), 0, 0));
		w1.addActor(new AAGun(this, convertToRatioX(550), convertToRatioY(550), 1,1, -3, -1, true, 180, 90));
		w1.addActor(new AAGun(this, 0, convertToRatioY(600), 1,1, 3, -1, true, 0, 90));
		w1.addActor(new Bomber(this, convertToRatioX(200),convertToRatioY(201),1,1,1,0,true,0));
		w1.addActor(new Bomber(this, convertToRatioX(255),convertToRatioY(100),1,1,1,0,true,0));
		w1.addActor(new Bullet(this, convertToRatioX(900), convertToRatioY(105), -10, 0, null));
		w1.addActor(new Bullet(this, convertToRatioX(910), convertToRatioY(105), -10, 0, null));
		w1.addActor(new SAM(this, convertToRatioX(800), getYPosGround() - 1, 1, 1, 0, 0, true, 180, 90));
		w1.addActor(new SAM(this, convertToRatioX(200), getYPosGround() - 1, 1, 1, 0, 0, true, 180, 90));
		w1.addActor(new Flare(this, convertToRatioX(100), convertToRatioY(300), 1, -3, null));
	}

	@Override
	public void addWaves() 
	{
		Wave w1 = new Wave(this);
		makeCustomWave(w1);
		addWave(w1);
		createMultiEnemyWave(new String[] {"spawnBomberFormation", "spawnSAMS", "spawnPowerUps"}, null);
		createMultiEnemyWave(new String[] {"spawnJetFormation", "spawnBombFlingers"}, null);
		createMultiEnemyWave(new String[] {"spawnSAMS", "spawnPowerUps"}, null);
		createWave("spawnHighBombers", null);
		createMultiEnemyWave(new String[] {"spawnAAGuns", "spawnPowerUps"}, null);
		createMultiEnemyWave(new String[] {"spawnSmartAAGuns", "spawnPowerUps"}, null);
		createMultiEnemyWave(new String[] {"spawnSmartJets", "spawnSAMS"}, null);
		createMultiEnemyWave(new String[] {"spawnMissleJets", "spawnSAMS"}, null);
		Wave w2 = new Wave(this);
		w2.addActor(new Flag(this, convertToRatioX(1000), 2));
		addWave(w2);
	}

	@Override
	public void handleEndWaves() {}
	
	public void draw(Graphics g)
	{
		super.draw(g);
		g.setColor(Color.WHITE);
		g.drawLine(convertToRatioX(500), 0, convertToRatioX(500), getHeight());
	}
}
