package root.levels;

import java.awt.Graphics;
import java.util.ArrayList;
import root.actors.Actor;

/*
 * Used to spawn enemy waves and draw on the level if there is more than one "wave"
 */
public class Wave
{
	private Level level;
	private ArrayList<Actor> actors = new ArrayList<Actor>();
	private int index = 0;
	private int tickDelay;
	
	public Wave(Level l)
	{
		level = l;
	}
	
	public int getTickDelay()
	{
		return tickDelay;
	}
	
	public void setTickDelay(int n)
	{
		tickDelay = n;
	}
	
	public Level getLevel()
	{
		return level;
	}
	
	public void addActor(Actor a)
	{
		actors.add(a);
	}
	
	public int getNumActors()
	{
		return actors.size();
	}
	
	public void removeActor(int i)
	{
		actors.remove(i);
	}
	
	public void addActors(ArrayList<Actor> actors)
	{
		for(Actor a : actors)
		{
			this.actors.add(a);
		}
	}
	
	public void spawnEnemy()
	{
		level.add(actors.get(index));
		index++;
	}
	
	public void spawnEnemies()
	{
		for(Actor a : actors)
		{
			level.add(a);
		}
	}
	
	public void draw(Graphics g)
	{
		
	}
}
