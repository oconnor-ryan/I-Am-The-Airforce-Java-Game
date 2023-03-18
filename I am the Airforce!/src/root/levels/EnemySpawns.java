package root.levels;

import java.util.ArrayList;

import root.actors.Actor;
import root.actors.Flag;
import root.actors.environment.EnvironmentHazard;
import root.actors.groundVehicles.AAGun;
import root.actors.groundVehicles.BombFlinger;
import root.actors.groundVehicles.SAM;
import root.actors.groundVehicles.SmartAAGun;
import root.actors.planes.BombOnlyBomber;
import root.actors.planes.MissleJet;
import root.actors.planes.SineWaveBomber;
import root.actors.planes.SmartJet;
import root.actors.planes.WaveJet;
import root.actors.powerUps.FireRatePowerUp;
import root.actors.powerUps.FlakPowerUp;
import root.actors.powerUps.HealthPowerUp;
import root.actors.powerUps.ShotgunPowerUp;
import root.actors.powerUps.SpeedPowerUp;
import root.actors.powerUps.SuperHealthPowerUp;

public class EnemySpawns 
{

	private static int convertToRatioX(double n)
	{
		return Level.convertToRatioX(n);
	}
	
	private static int convertToRatioY(double n)
	{
		return Level.convertToRatioY(n);
	}
	
	private static boolean isValidSpawn(Level l, Actor actor)
	{
		for(Actor a : l.getActors())
		{
			if(!a.equals(actor) && actor.hasCollided(a))
			{
				return false;
			}
		}
		return true;
	}	
	
	//boolean determines if spawnMethod required an actorType parameter
	public static ArrayList<Actor> chooseSpawnMethod(String method, ActorNames actorType, Level l)
	{
		ArrayList<Actor> actors = new ArrayList<Actor>();
		switch(method)
		{
		case "spawnAAGuns":
			spawnAAGuns(actors, l);
			break;
		case "spawnSmartAAGuns":
			spawnSmartAAGuns(actors, l);
			break;
		case "spawnBombFlingers":
			spawnBombFlingers(actors, l);
			break;
		case "spawnSAMS":
			spawnSAMS(actors, l);
			break;
		case "spawnBomberFormation":
			spawnBomberFormation(actors, l);
			break;
		case "spawnJetFormation":
			spawnJetFormation(actors, l);
			break;
		case "spawnHighBombers":
			spawnHighBombers(actors, l);
			break;
		case "spawnSmartJets":
			spawnSmartJets(actors, l);
			break;
		case "spawnMissleJets":
			spawnMissleJets(actors, l);
			break;
		case "spawnPowerUps":
			spawnPowerUps(actors, l);
			break;
		case "spawnHealth":
			spawnHealth(actors, l);
			break;
		case "spawnBuilding":
			spawnBuilding(actors, l);
			break;
		case "spawnFlag":
			spawnFlag(actors, l);
			break;
		case "spawnActorsRightside1":
			spawnActorsRightSide1(actors, l, actorType);
			break;
		case "spawnGroundVehicleRightside1":
			spawnGroundVehicleRightside1(actors, l, actorType);
			break;
		}
		
		return actors;
	}
	
	/*
	 * Make ONE method that can spawn ANY Vehicle or Actor
	 * Must make empty constructor for each VEHICLE which contains its default sizes
	 * and call the setX(), setY(), and other methods to adjust each VEHICLE
	 */
	
	private static void spawnActorsRightSide1(ArrayList<Actor> actors, Level level, ActorNames name)
	{
		int x = level.getWidth();
		while(x <= level.getWidth() + level.getOffScreenX())
		{
			int y = Level.convertToRatioY(50); 
			while(y <= Level.convertToRatioY(650))
			{
				Actor a = ActorFactory.getActor(level, name);
				if(a == null)
				{
					return;
				}
				a.setX(x);
				a.setY(y);
				a.setXSpeed((int)(Math.random() * -3) - 1);
				a.setYSpeed((int)(Math.random() * 3));
				actors.add(a);
				
				y += (int)(Math.random() * convertToRatioY(300) + convertToRatioY(100));
			}
			
			x += (int)(Math.random() * convertToRatioX(300) + convertToRatioX(100));
		}
	}
	
	private static void spawnGroundVehicleRightside1(ArrayList<Actor> actors, Level level, ActorNames name)
	{
		int x = convertToRatioX(1000);
		while(x <= level.getWidth() + convertToRatioX(level.getOffScreenX()))
		{
			Actor a = ActorFactory.getActor(level, name);	
			if(a == null)
			{
				return;
			}
			int y = level.getYPosGround(); 
			a.setX(x);
			a.setY(y);
			a.setXSpeed(-3);
			a.setYSpeed(0);
			actors.add(a);
			x += (int)((Math.random() * convertToRatioX(20)) + 2*a.getXSize());
		}
	}
	
	private static void spawnFlag(ArrayList<Actor> actors, Level level)
	{
		actors.add(new Flag(level, convertToRatioX(1000), convertToRatioY(500)));
	}
	
	private static void spawnPowerUps(ArrayList<Actor> actors, Level level)
	{
		actors.add(new HealthPowerUp(level, convertToRatioX((Math.random() * 400)+300),convertToRatioY(Math.random()*200 + 400)));
		actors.add(new FireRatePowerUp(level,convertToRatioX((Math.random() * 400)+300), convertToRatioY(Math.random()*200 + 400)));
		actors.add(new FlakPowerUp(level, convertToRatioX((Math.random() * 400)+300),convertToRatioY(Math.random()*200 + 400)));
		actors.add(new SpeedPowerUp(level, convertToRatioX((Math.random() * 400)+300),convertToRatioY(Math.random()*200 + 400)));
		actors.add(new ShotgunPowerUp(level, convertToRatioX((Math.random() * 400)+300),convertToRatioY(Math.random()*200 + 400)));
	}
	
	public static void spawnHealth(ArrayList<Actor> actors, Level level)
	{
		actors.add(new SuperHealthPowerUp(level, convertToRatioX((Math.random() * 400)+300),convertToRatioY(Math.random()*200 + 400)));
	}
	private static void spawnBombFlingers(ArrayList<Actor> actors, Level level)
	{
		int x = (int)Math.round(400 * level.getWorld().getScaleFactorX());
		int size = 1;
		while(x < level.getWidth())
		{
			actors.add(new BombFlinger(level, x, level.getYPosGround() - size, size, size, -2, -1, true, 180, 90));
			x += (int)Math.round(150 * level.getWorld().getScaleFactorX());
		}
	}
	
	private static void spawnAAGuns(ArrayList<Actor> actors, Level level)
	{
		int x = (int)Math.round(400 * level.getWorld().getScaleFactorX());
		int size = 1;
		while(x < level.getWidth())
		{
			actors.add(new AAGun(level, x, level.getYPosGround() - size, size, size, -4, -1, true, 180, 90));
			x += (int)Math.round(90 * level.getWorld().getScaleFactorX());
		}
	}
	
	private static void spawnSmartAAGuns(ArrayList<Actor> actors, Level level)
	{
		int x = (int)Math.round(400 * level.getWorld().getScaleFactorX());
		int size = 1;
		while(x < level.getWidth())
		{
			actors.add(new SmartAAGun(level, x, level.getYPosGround() - size, size, size, -4, -1, true, 180, 90));
			x += (int)Math.round(200 * level.getWorld().getScaleFactorX());
		}
	}
	
	private static void spawnSAMS(ArrayList<Actor> actors, Level level)
	{
		int x = (int)Math.round(500 * level.getWorld().getScaleFactorX());
		int size = 1;
		while(x < level.getWidth())
		{
			actors.add(new SAM(level, x, level.getYPosGround() - size, size, size, -2, 0, true, 180, 90));
			x += (int)Math.round(100 * level.getWorld().getScaleFactorX()); 
		}
	}
	
	private static void spawnHighBombers(ArrayList<Actor> actors, Level level)
	{
		int x = (int)Math.round(400 * level.getWorld().getScaleFactorX() + (400 * level.getWorld().getScaleFactorX()));
		int size = 60;
		for(int k = 0; k < 30; k++)
		{
			BombOnlyBomber b = new BombOnlyBomber(level, x, -19, size,size, -2, 0, true, 180);
			actors.add(b);
			x += (int)Math.round(20 *level.getWorld().getScaleFactorX() + (int)(2.5*b.getXSize())); 
		}
	}
	
	private static void spawnBomberFormation(ArrayList<Actor> actors, Level level)
	{
		int x = (int)Math.round((Math.random() * 400) * level.getWorld().getScaleFactorX() + (500 *level.getWorld().getScaleFactorX()));
		int size = 1;
		double ratioY = level.getWorld().getScaleFactorY();
		for(int k = 0; k < 10; k++)
		{
			actors.add(new SineWaveBomber(level, x,(int)Math.round((Math.random() * 600) * ratioY + (60*ratioY)), size,size, -1, 0,true, 180));
			x += (int)Math.round(100 * level.getWorld().getScaleFactorX()); 
		}
	}

	private static void spawnJetFormation(ArrayList<Actor> actors, Level level)
	{
		int x = (int)Math.round((Math.random() * 400) * level.getWorld().getScaleFactorX() + (500 * level.getWorld().getScaleFactorX()));
		int size = 1;
		double ratioY = level.getWorld().getScaleFactorY();
		for(int k = 0; k < 15; k++)
		{
			actors.add(new WaveJet(level, x,(int)Math.round((Math.random() * 600) * ratioY + (60*ratioY)), size,size, -1, 0,true, 180));
			x += (int)Math.round(60 *level.getWorld().getScaleFactorX()); 
		}
	}
	
	private static void spawnSmartJets(ArrayList<Actor> actors, Level level)
	{
		int x = (int)Math.round((Math.random() * 400) * level.getWorld().getScaleFactorX() + (500 * level.getWorld().getScaleFactorX()));
		int size = 1;
		double ratioY = level.getWorld().getScaleFactorY();
		for(int k = 0; k < 15; k++)
		{
			actors.add(new SmartJet(level, x,(int)Math.round((Math.random() * 600) * ratioY + (60*ratioY)), size,size, -1, 0, true, 180));
			x += (int)Math.round(60 * level.getWorld().getScaleFactorX()); 
		}
	}
	
	private static void spawnBuilding(ArrayList<Actor> actors, Level level)
	{
		int xPos = level.getWidth();
		int ySize = (int)(Math.round(level.getYPosGround() - (500 * level.getWorld().getScaleFactorY())));
		int xSize = (int)(Math.round(40 * level.getWorld().getScaleFactorX()));
		while(xPos > 0) //checks for available SpawnPoints
		{
			EnvironmentHazard e = new EnvironmentHazard(level, xPos - xSize,(int)(500 * level.getWorld().getScaleFactorY()),xSize, ySize, -3, 0);
			if(isValidSpawn(level, e))
			{
				actors.add(e);
				return;
			}
			xPos--;
		}	
	}
	private static void spawnMissleJets(ArrayList<Actor> actors, Level level)
	{
		int x = level.getWidth();	
		int size = 1;
		while(x <= level.getWidth() + level.getOffScreenX())
		{
			int y = convertToRatioY(50); 
			while(y <= convertToRatioY(650))
			{
				actors.add(new MissleJet(level, x, y, size, size, -(int)(Math.random() * 2) - 4, 2, true, 180));
				y += (int)(Math.random() * convertToRatioY(200) + convertToRatioY(300));
			}
			
			x += (int)(Math.random() * convertToRatioX(200) + convertToRatioX(300));
		}
	}

}
