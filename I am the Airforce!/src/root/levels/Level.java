package root.levels;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import root.JetWorldGame.JetWorld;
import root.actors.Actor;
import root.actors.Flag;
import root.actors.Vehicle;
import root.actors.environment.Ground;
import root.actors.planes.PlayerJet;
import root.actors.projectiles.Projectile;
import root.backgrounds.Background;

public abstract class Level
{
	private CopyOnWriteArrayList<Actor> actors = new CopyOnWriteArrayList<Actor>();
	private PlayerJet player;
	private int width;
	private int height;
	private boolean endOfLevel = false;
	private int yPosGround;
	private int[] playerSpawn;
	private static final long TIMER_START = 0;
	private long timerCount = TIMER_START;
	private long timeLimit;
	private static int updateRate;
	private boolean isPaused = false;
	private int levelNum;
	private JetWorld world;
	private int offScreenX; //offScreen shows how many pixels an actor can move offScreen before being deleted
	private int offScreenY;
	private Background background;

	public Level(JetWorld w, PlayerJet p, int rate, int levelNum)
	{
		width = w.getWidth();
		height = w.getHeight();
		world = w;
		setPlayer(p);
		updateRate = rate;
	}
	
	public abstract void processEndTimer();
	
	public void draw(Graphics g)
	{
		//ONLY insert Background here
		if(background != null)
		{
			background.draw(g);
		}
	}
	
	public void setBackground(Color c)
	{
		background = new Background(c, width, height);
	}
	public void setBackground(String s)
	{
		background = new Background(s, world.getWidth(), height, 20);//???
	}
	
	public JetWorld getWorld()
	{
		return world;
	}
	
	public int getLevelNum()
	{
		return levelNum;
	}
	
	public void setPaused()
	{
		isPaused = !isPaused;
	}
	
	public void setPaused(boolean b)
	{
		isPaused = b;
	}
	
	public boolean isPaused()
	{
		return isPaused;
	}
	
	public boolean actorOffScreen(Actor a)
	{
		return a.getX() + a.getXSize() < 0 || a.getX() > world.getWidth() || a.getY() + a.getYSize() < 0 || a.getY() > world.getHeight();
	}

	public void resetLevel()
	{
		for(int k = 0; k < getActors().size(); k++)
		{
			if(!(getActors().get(k) instanceof PlayerJet))
			{
				getActors().remove(k);
				k--;
			}
		}
		setTimerCount(0);
		spawnPlayer();
		setEndLevel(false);
		setPaused(false);
	}
	
	public void checkEndLevel()
	{
		if(getFlag() != null && getPlayer().hasCollided(getFlag()))
		{
			setEndLevel(true);
		}
	}
	
	public boolean allEnemiesDead() //use as indicator to progress through tutorial
	{
		for(Actor a : getActors())
		{
			if(a instanceof Vehicle && ((Vehicle) a).isEnemy())
			{
				return false;
			}
		}
		return true;
	}
	
	public void setUpdateRate(int a)
	{
		updateRate = a;
	}
	
	public final int getUpdateRate()
	{
		return updateRate;
	}
	public long getStandardTimerStart()
	{
		return TIMER_START;
	}
	public void setTimerCount(long millis)
	{
		timerCount = millis;
	}
	
	public long getTimerCount()
	{
		return timerCount;
	}
	
	public void setTimeLimit(long millis)
	{
		double mult = (double) JetWorld.getBestUpdateRate() / (double) getUpdateRate(); //allows for slow motion
		timeLimit = (long) Math.round((mult * (double)millis * (double)getUpdateRate())/1000.0);
	}
	
	public long getTimeLimit()
	{
		return timeLimit;
	}
	public void tick()
	{
		timerCount++;
		if(timerCount >= timeLimit)
		{
			processEndTimer();
			timerCount = TIMER_START;
		}
		updateActors();
	}
	
	private void updateActors()
	{
		for(Actor a : actors)
		{
			a.tick();
			a.move();
			if(a instanceof Vehicle)
			{
				for(Projectile p : ((Vehicle) a).getProjectiles())
				{
					add(p);
				}
			}
		}
		setPlayerWithinBorder(); //prevents Player from moving offscreen
		for(Actor a : actors)
		{
			a.checkCollisions(actors);
		}
		removeInvalidActors(); //remove actors with no remaining health or move too far offscreen
	}
	
	public Ground getGround()
	{
		for(Actor a : actors)
		{
			if(a instanceof Ground)
			{
				return (Ground) a;
			}
		}
		return null;
	}
	
	private void setPlayerWithinBorder()
	{
		if(player != null)
		{
			if(player.getX() < 0)
			{
				player.setX(0);
				player.setXSpeed(0);
			}
			else if(player.getX() + player.getXSize() > getWidth())
			{
				player.setX(getWidth() - player.getXSize());
				player.setXSpeed(0);
			}
			
			Ground g = getGround();
			if(player.getY() < 0)
			{
				player.setY(0);
				player.setYSpeed(0);
			}
			else if(g != null && player.getY() + player.getYSize() > g.getY())
			{
				player.setY(g.getY() - player.getYSize() + 1); // +1 allows player to die from hitting ground
				player.setYSpeed(0);
			}
			else if(player.getY() + player.getYSize() > getHeight())
			{
				player.setY(getHeight() - player.getYSize());
				player.setYSpeed(0);
			}
		}	
	}

	public void setPlayerSpawn(int x, int y)
	{
		playerSpawn = new int[] {x,y};
		spawnPlayer();
	}
	public void spawnPlayer()
	{
		player.setX(playerSpawn[0]);
		player.setY(playerSpawn[1]);
	}
	
	public Flag getFlag()
	{
		for(Actor a : actors)
		{
			if(a instanceof Flag)
			{
				return (Flag)a;
			}
		}
		return null;
	}
	
	public void setDistanceOffScreen(int x, int y) //dont convertToRatio because its already done in removeActors
	{
		offScreenX = x;
		offScreenY = y;
	}
	
	public int getOffScreenX()
	{
		return offScreenX;
	}
	public int getOffScreenY()
	{
		return offScreenY;
	}
	
	public void setYPosGround(int g)
	{
		yPosGround = g;
	}
	
	public int getYPosGround()
	{
		return yPosGround;
	}
	
	public void setPlayer(PlayerJet a)
	{
		player = a;
		add(player);
	}
	public PlayerJet getPlayer()
	{
		return player;
	}
	
	public CopyOnWriteArrayList<Actor> getActors()
	{
		return actors;
	}
	
	public void add(Actor a)
	{
		actors.add(a);
	}
	
	public void addActors(ArrayList<Actor> moreActors)
	{
		for(Actor a : moreActors)
		{
			actors.add(a);
		}
	}
	public void remove(int a)
	{
		actors.remove(a);
	}
	
	public void setEndLevel(boolean b)
	{
		endOfLevel = b;
	}
	
	public boolean hasEnded()
	{
		return endOfLevel;
	}
	
	public void setWidth(int w)
	{
		width = w;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public void setHeight(int h)
	{
		height = h;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int[] getCenteredString(Graphics g, Font f, String text, int w, int h)
	{
		FontMetrics m = g.getFontMetrics(f);
		g.setFont(f);
		int x = (w - m.stringWidth(text))/2;
		int y = ((h - m.getHeight())/2) + m.getAscent();
		return new int[] {x,y};
	}
	
	
	public static int convertToRatioX(double oldX)
	{
		return JetWorld.convertToRatioX(oldX);
	}
	public static int convertToRatioY(double oldY)
	{
		return JetWorld.convertToRatioY(oldY);
	}
	
	//removes actors that are offscreen or have no health
	public void removeInvalidActors()
	{
		for(int k = 0; k < actors.size(); k++)
		{
			Actor a = actors.get(k);

			if(a instanceof Vehicle)
			{
				((Vehicle) a).resetProjectiles(); //prevents strange glitches with player bullets
			}
			
			if(!a.isUnkillable() && 
				  (a.getX() > world.getRunner().getWidth() + convertToRatioX(offScreenX) 
				|| a.getX() + a.getXSize() < -convertToRatioX(offScreenX)
				|| a.getY() > world.getRunner().getHeight() 
				|| a.getY() + a.getYSize() < -convertToRatioY(offScreenY)
				|| a.isDead()))
			{
				if(a instanceof PlayerJet)
				{
					getWorld().setGameOver(true);
					a.kill();
					if(world.getRunner() != null)
					{
						world.getRunner().actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Dead"));
					}
					return;
				}
				remove(k);
				k--;
			}
			//removes player projectiles that go offscreen, prevents extreme framerate drops
			else if(a instanceof Projectile && ((Projectile) a).getActor() != null && ((Projectile) a).getActor() instanceof PlayerJet)
			{
				if(actorOffScreen(a))
				{
					remove(k);
					k--;
				}
			}
		}
	}
}
