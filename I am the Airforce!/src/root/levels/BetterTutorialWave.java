package root.levels;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;

import root.JetWorldGame.JetWorld;
import root.actors.Actor;
import root.actors.Flag;
import root.actors.planes.MissleJet;
import root.actors.planes.PlayerJet;
import root.actors.planes.TargetPlane;
import root.actors.powerUps.FireRatePowerUp;
import root.actors.powerUps.FlakPowerUp;
import root.actors.powerUps.HealthPowerUp;
import root.actors.powerUps.PowerUp;
import root.actors.powerUps.ShotgunPowerUp;
import root.actors.powerUps.SpeedPowerUp;
import root.actors.projectiles.Bomb;
import root.actors.projectiles.Bullet;
import root.actors.projectiles.Missle;

public class BetterTutorialWave extends MultiWaveLevel
{
	private String[] tutorText = new String[] {
			"Welcome to the Game! Press ESC to see Pause Menu. Check the controls and shoot the jet!",
			"Great Job! You have 4 weapons: Bullets, Missles, Bombs, and Flares.",
			"Bullets shoot straight. Hold the E button to shoot. Bullets can also destroy enemies and other projectiles. Shoot the Jet!",
			"Bombs fall from the bottom of the plane. You can only drop them if the bomb meter is filled. The meter will fill up overtime. Bomb the jet by pressing SPACEBAR.",
			"Missles follow the nearest target in front of you. Like bombs, you must wait each time you fire a missle. Shoot a missle by pressing R!",
			"Enemies can also shoot missles. Press F to fire a flare that attracts every enemy missle to its location. Shoot the flare now and destroy the jet.",
			"There are also powerups! GREEN + restores 200 health points. ORANGE increases fire rate. RED AND ORANGE gives you explosive bullets. BLUE increases speed YELLOW gives you a shotgun!",
			"Now that you know your jet, touch the Flag to go to the next level."
	};
	
	private boolean endTutorial = false;
	private boolean spawnedWaitingEnemy = false;
	private boolean setTimerForMissle = false;
	
	public BetterTutorialWave(JetWorld w, PlayerJet p, int rate, int levelNum) 
	{
		super(w, p, rate, levelNum);
		setPlayerSpawn(0,0);
		setTimeLimit(10000);
		setDistanceOffScreen(20,20);
		addWaves();
		spawnWave();
	}
	
	public void resetLevel()
	{
		super.resetLevel();
		endTutorial = false;
		spawnedWaitingEnemy = false;
		setTimerForMissle = false;
		spawnWave();
	}
	
	private TargetPlane createPlane(int waveIndex)
	{
		int x = (4*getWidth())/9;
		int y;
		if(waveIndex % 2 == 0)
		{
			y = getHeight()/20;
		}
		else
		{
			y = (10*getHeight())/13;
		}
		TargetPlane p = new TargetPlane(this, x, y);
		p.setXSpeed(0);
		p.setYSpeed(0);
		switch(waveIndex)
		{
		case 2:
			p.setCanBeDamagedBy(new Bullet(this, 0,0,0,0, null));
			break;
		case 3:
			p.setCanBeDamagedBy(new Bomb(this, 0,0,0,0, null));
			break;
		case 4:
			p.setCanBeDamagedBy(new Missle(0,0,p,this,0));
			break;
		}
		
		return p;
	}
	
	public void tick()
	{
		super.tick();
		if(getCurrentWave() == 5 && !spawnedWaitingEnemy)
		{
			if(!setTimerForMissle)
			{
				setTimeLimit(10000);
				setTimerCount(0);
				setTimerForMissle = true;
			}	
		}
		else if(!endTutorial && allEnemiesDead())
		{
			nextWave();
			if(getCurrentWave() == 5)
			{
				return;
			}
			spawnWave();
			if(getCurrentWave() >= getNumWaves() - 1)
			{
				handleEndWaves();
			}
		}
	}

	@Override
	public void addWaves() 
	{
		for(int k = 0; k < tutorText.length; k++)
		{
			Wave w1 = new Wave(this);
			if(k == 5)
			{
				MissleJet m = new MissleJet(this, convertToRatioX(1000), convertToRatioY(650), 1, 1, 0, 0, true, 180);
				m.setTimeLimit(7000);
				m.fireMissle();
				m.setHealth(1);
				w1.addActor(m);
			}
			else if(k == 6)
			{
				int x = (4*getWidth())/9;
				int y = getHeight()/2;
				ArrayList<Actor> powerUps = new ArrayList<Actor>();
				powerUps.add(new HealthPowerUp(this, x, y));
				powerUps.add(new FireRatePowerUp(this, x, y));
				powerUps.add(new FlakPowerUp(this, x, y));
				powerUps.add(new SpeedPowerUp(this, x, y));
				powerUps.add(new ShotgunPowerUp(this, x, y));
				for(Actor a : powerUps)
				{
					((PowerUp) a).setNeverDisappears(true);
					a.setY(y);
					y += a.getYSize() + 3;
				}
				w1.addActors(powerUps);	
				w1.addActor(createPlane(k));
			}
			else if(k == tutorText.length - 1)
			{
				Flag f = new Flag(this, convertToRatioX(1000), convertToRatioY(600));
				w1.addActor(f);
			}
			else
			{
				w1.addActor(createPlane(k));
			}
			addWave(w1);
		}	
	}
	
	public void processEndTimer() 
	{
		if(getCurrentWave() == 5 && !spawnedWaitingEnemy)
		{
			spawnWave();
			spawnedWaitingEnemy = true;
		}
	} 

	@Override
	public void handleEndWaves() 
	{
		endTutorial = true;
	}
	
	private void drawText(Graphics g)
	{
		Font f = new Font("serif", Font.PLAIN, convertToRatioY(50));
		g.setFont(f);
		g.setColor(new Color(100,50,150));
		FontMetrics m = g.getFontMetrics(f);
		
		int x = getWidth()/2;
		int y = m.getHeight();
		int spaceWidth = m.stringWidth(" ");
		int i = 0;
		int begWord = 0;
		String s = tutorText[getCurrentWave()];
		while(i < s.length())
		{
			if((s.charAt(i) == ' ' || i == s.length() - 1) && begWord != i)
			{
				String sub; 
				if(i == s.length() - 1)
				{
					sub = s.substring(begWord, i + 1);
				}
				else
				{
					sub = s.substring(begWord, i);
				}
				if(m.stringWidth(sub) + x >= getWidth())
				{
					x = getWidth()/2;
					y += 5*m.getHeight()/6;
				}
				g.drawString(sub, x, y);
				x += spaceWidth + m.stringWidth(sub);
				begWord = i + 1;
			}
			i++;
		}
	}
	
	public void draw(Graphics g)
	{
		drawText(g);
		if(getCurrentWave() == 5 && !spawnedWaitingEnemy)
		{
			g.drawString(getTimeLimit() - getTimerCount() + "", 3*getWidth()/4, convertToRatioY(650));
		}
		super.draw(g);
	}
}
