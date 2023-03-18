package root.JetWorldGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

import root.actors.Actor;
import root.actors.planes.PlayerJet;
import root.levels.*;

public class JetWorld extends JPanel
{
	private static final long serialVersionUID = 5465415666468404L;
	private ArrayList<Level> levels = new ArrayList<Level>();
	private Level currentLevel;
	private int levelIndex = 0;
	private static final int BEST_UPDATE_RATE = 45;
	private static final int UPDATE_RATE = 45;
	private PlayerJet player;
	private boolean gameOver = true;
	private JetWorldRunner runner;
	private int fps = 0;

	public JetWorld(JetWorldRunner run)
	{
		setBackground(Color.BLACK);
		runner = run;
		player = new PlayerJet(runner); 
		setLevels();
		currentLevel = levels.get(levelIndex);
		player.setLevel(currentLevel);	

		Thread t = new Thread()
		{
			public void run()
			{
				while(true)
				{
					long time = System.nanoTime();
					if((runner != null && !currentLevel.isPaused() && !gameOver) || runner == null)
					{
						updateLevel();
					}

					try {Thread.sleep(1000/UPDATE_RATE);}
					catch(InterruptedException ex) {ex.printStackTrace();}
					
					fps = (int) Math.round(1000000000.0 / (System.nanoTime() - time));
					time = System.nanoTime();
				}
			}
		};
		t.start();
	}
	
	public JetWorldRunner getRunner()
	{
		return runner;
	}
	
	public int getWidth()
	{
		return runner.getWidth();
	}
	
	public int getHeight()
	{
		return runner.getHeight();
	}
	
	public static final int getBestUpdateRate()
	{
		return BEST_UPDATE_RATE;
	}
	
	public double getScaleFactorX()
	{
		return runner.getScaleFactor()[0];
	}
	public double getScaleFactorY()
	{
		return runner.getScaleFactor()[1];
	}
	
	public static final int getUpdateRate()
	{
		return UPDATE_RATE;
	}
	
	public void setLevels()
	{
		addLevel(new BetterTutorialWave(this, player, UPDATE_RATE, 0));
		addLevel(new WaveLevel4(this, player, UPDATE_RATE, 1));
		addLevel(new WaveLevel3(this, player, UPDATE_RATE, 2));
		addLevel(new WaveLevel1(this, player, UPDATE_RATE, 3));
		addLevel(new WaveLevel2(this, player, UPDATE_RATE, 4));
		addLevel(new SeaLevel1(this, player, UPDATE_RATE, 5));
		addLevel(new WaveLevel0(this, player, UPDATE_RATE, 6));	
		addLevel(new FinalLevel(this, player, UPDATE_RATE, 7));
	}
	
	public void addLevel(Level l)
	{
		levels.add(l);
	}
	
	public void resetLevels()
	{
		for(Level l : levels)
		{
			l.resetLevel();
		}
	}
	
	public void setLevel(int i)
	{
		// 0 is Tutorial, 1 is Level 1, 2 is Level 2, etc  world.getLevels.size() - 1 == final level
		currentLevel = levels.get(i);
		levelIndex = i;
		currentLevel.spawnPlayer();
		player.setLevel(currentLevel);
	}
	
	public Level getCurrentLevel()
	{
		return currentLevel;
	}
	
	public int getNumLevels()
	{
		return levels.size();
	}

	public PlayerJet getPlayer()
	{
		return player;
	}
	
	public void setGameOver(boolean b)
	{
		gameOver = b;
	}
	
	public boolean isGameOver()
	{
		return gameOver;
	}
	
	public static int convertToRatioX(double oldX)
	{
		return JetWorldRunner.convertToRatioX(oldX);
	}
	public static int convertToRatioY(double oldY)
	{
		return JetWorldRunner.convertToRatioY(oldY);
	}
	
	private void updateLevel()
	{
		currentLevel.tick();
		if(!(currentLevel instanceof WinnerStage))
		{
			currentLevel.checkEndLevel();
			if(currentLevel.hasEnded()) //changes level
			{
				levelIndex++;
				if(levelIndex >= levels.size())
				{
					levelIndex = levels.size();
					currentLevel = new WinnerStage(this, player, UPDATE_RATE, -1);
				}
				else
				{
					setLevel(levelIndex);			
				}
				currentLevel.spawnPlayer();
				player.setLevel(currentLevel);
			}
		}
		repaint();
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		currentLevel.setWidth(getWidth());
		currentLevel.setHeight(getHeight());
		currentLevel.draw(g); //levels will hold background objects that will be drawn FIRST
		
		for(Actor a : currentLevel.getActors())
		{
			a.drawSprite(g);
		}
		
		player.drawStatusBars(g);
		
		g.setColor(Color.WHITE);
		int nameX = convertToRatioX(10);
		int numX = nameX + 100;
		g.setFont(new Font("serif",convertToRatioX(30),convertToRatioY(30)));
		g.drawString("FPS", nameX, convertToRatioY(510));
		g.drawString(fps + "", numX, convertToRatioY(510));
		g.drawString("Actors", nameX, convertToRatioY(570));
		g.drawString(currentLevel.getActors().size() + "", numX, convertToRatioY(570));
		g.drawString("Score", nameX, convertToRatioY(540));
		g.drawString(player.getTotalScore() + "", numX, convertToRatioY(540));
	}
}
