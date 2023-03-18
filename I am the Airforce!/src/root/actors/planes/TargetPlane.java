package root.actors.planes;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import root.actors.Actor;
import root.actors.Flag;
import root.actors.powerUps.PowerUp;
import root.actors.projectiles.Projectile;
import root.levels.Level;

public class TargetPlane extends Plane
{
	private ArrayList<Projectile> onlyProjsThatDamage = new ArrayList<Projectile>();
	
	public TargetPlane(Level l, int x, int y)
	{
		super(l,x,y);
		setHealth(1);
		setHealthFromLastTick(getHealth());
		setColor(Color.ORANGE);
		setTimeLimit(1000);
		try {
			createSprite("EnemyJet.txt");
			setPixelSize(Level.convertToRatioX(4));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public TargetPlane(Level w, int x, int y, int xSize, int ySize, int xVel, int yVel, boolean isEnemy,int direction) 
	{
		super(w, x, y, xSize, ySize, xVel, yVel, isEnemy, direction);
		setHealth(1);
		setHealthFromLastTick(getHealth());
		setColor(Color.ORANGE);
		setTimeLimit(1000);
		try {
			createSprite("EnemyJet.txt");
			setPixelSize(Level.convertToRatioX(4));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setCanBeDamagedBy(Projectile p)
	{
		onlyProjsThatDamage.add(p);
	}
	
	public ArrayList<Projectile> canOnlyBeDamagedBy()
	{
		return onlyProjsThatDamage;
	}
	
	private boolean canBeDamagedBy(Projectile p)
	{
		for(Projectile pro : onlyProjsThatDamage)
		{
			if(pro.isA(p))
			{
				return true;
			}
		}
		return false;
	}
	
	public void handleCollision(Actor a)
	{
		if(a instanceof Projectile && (onlyProjsThatDamage.size() == 0 || canBeDamagedBy((Projectile) a)))
		{
			this.takeDamage(((Projectile) a).getDamage());
			if(((Projectile) a).getActor() != null && ((Projectile) a).getActor() instanceof PlayerJet)
			{
				getLevel().getPlayer().addTotalScore(this.getScore());
			}
		}
		else if(onlyProjsThatDamage.size() == 0 
				|| !(a instanceof Projectile) && !(a instanceof PowerUp) && !(a instanceof Flag))
		{
			this.kill();
		}
	}

	public void processEndTimer()
	{
		//do nothing
	}
}
