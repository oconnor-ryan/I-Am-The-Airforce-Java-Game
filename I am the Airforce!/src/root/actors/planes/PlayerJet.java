package root.actors.planes;
/*
 * Player controls this jet
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

import root.JetWorldGame.JetWorld;
import root.JetWorldGame.JetWorldRunner;
import root.JetWorldGame.StatusBar;
import root.actors.Actor;
import root.actors.Flag;
import root.actors.powerUps.PowerUp;
import root.actors.projectiles.Bullet;
import root.actors.projectiles.Flak;
import root.actors.projectiles.Missle;
import root.actors.projectiles.Projectile;
import root.actors.projectiles.SuperBomb;

public class PlayerJet extends Jet implements KeyListener
{
	private ArrayList<Integer> keys = new ArrayList<Integer>();
	private ArrayList<Integer> powerTimers;
	private int[] weaponTimers;
	
	//add 1 to desired timer since timers are activated by subtracting the max value by 1
	private static final int[] MAX_WEAPON_TIMERS = new int[] {5, 31, 21, 51}; 
	private int[] weaponButtons = new int[] {KeyEvent.VK_E, KeyEvent.VK_SPACE, KeyEvent.VK_R, KeyEvent.VK_F};
	private static final int RATE_OF_FIRE = 100; //affects speed of firing weapons
	private static final int NORMAL_HEALTH = 500; //500
	private long totalScore = 0;
	private int speed = getStandardSpeed();

	private JetWorldRunner run;
	private ArrayList<StatusBar> bars;
	
	public PlayerJet(JetWorldRunner run) 
	{
		super(null, 0, 0);
		setXSpeed(0);
		setYSpeed(0);
		setIsEnemy(false);
		setHealth(NORMAL_HEALTH);
		setTimeLimit(RATE_OF_FIRE);
		setColor(Color.BLUE);
		setDirection(Right());
		this.run = run;
		setMaxHealth(1000); //1000
		setPowerTimers();
		setWeaponTimers();
		setStatusBars();
		try {
			createSprite("PlayerJet.txt");
			setPixelSize(JetWorld.convertToRatioX(4)); 
			setCurrentSprite(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setPowerTimers()
	{
		powerTimers = new ArrayList<Integer>();
		for(int k = 0; k < 6; k++) //5 timed PowerUps currently available
		{
			powerTimers.add(0); //0 = fireRate; 1 = Flak; 2 = Speed; 3 ShotGun
		}
	}
	
	private void setWeaponTimers()
	{
		weaponTimers = new int[MAX_WEAPON_TIMERS.length];
		for(int k = 0; k < weaponTimers.length; k++)
		{
			weaponTimers[k] = MAX_WEAPON_TIMERS[k]; // 0 = bullet; 1 = bomb; 2 = missle; 3 = flare;
		}
	}
	
	public int getSpeed() {return speed;}
	public void setSpeed(int s) {speed = s;}
	
	public final int getNormalHealth(){return NORMAL_HEALTH;}
	public long getTotalScore(){return totalScore;}
	
	public boolean hasFirePowerUp() {return powerTimers.get(0) > 0;}
	public boolean hasFlakPowerUp(){return powerTimers.get(1) > 0;}
	public boolean hasSpeedPowerUp() {return powerTimers.get(2) > 0;}
	public boolean hasShotGunPowerUp() {return powerTimers.get(3) > 0;}
	public boolean hasInvinciblePowerUp() {return powerTimers.get(4) > 0;}
	public boolean hasSuperBombPowerUp() {return powerTimers.get(5) > 0;}
	
	public boolean hasPowerUp()
	{
		for(Integer k : powerTimers)
		{
			if(k > 0)
			{
				return true;
			}
		}
		return false;
	}
	
	public int getFirePowerTimer()
	{
		return powerTimers.get(0);
	}
	public int getFlakPowerTimer()
	{
		return powerTimers.get(1);
	}
	public int getSpeedPowerTimer()
	{
		return powerTimers.get(2);
	}
	public int getShotGunPowerTimer()
	{
		return powerTimers.get(3);
	}
	public int getInvinciblePowerTimer()
	{
		return powerTimers.get(4);
	}
	public int getSuperBombPowerTimer()
	{
		return powerTimers.get(5);
	}
	public int getMissleTimer()
	{
		return weaponTimers[2];
	}
	public int getBombTimer()
	{
		return weaponTimers[1];
	}
	public int getFlareTimer()
	{
		return weaponTimers[3];
	}
	public int getMaxMissleTimer() {return MAX_WEAPON_TIMERS[2];}
	public int getMaxFlareTimer() {return MAX_WEAPON_TIMERS[3];}
	public int getMaxBombTimer() {return MAX_WEAPON_TIMERS[1];}
	public int getMaxPowerUpTimer() 
	{
		double mult = (double) JetWorld.getBestUpdateRate() / (double) JetWorld.getUpdateRate();//allows for slow motion
		return (int) Math.round(mult * (double) JetWorld.getUpdateRate() * 10.0); 
	}
	
	public void removePowerUp(int k)
	{
		switch(k)
		{
			case 0: //fireRate
				setTimeLimit(RATE_OF_FIRE);
				weaponTimers[0] = MAX_WEAPON_TIMERS[0];
				break;
			case 1: //flak
				break;
			case 2: //speed
				speed = getStandardSpeed();
				break;
			case 3:   //shotgun
				break;
			case 4:    //invincibility
				setUnkillable(false);
				break;
			case 5:   //superBomb
				break;
		}
		powerTimers.set(k,  0);
	}
	
	public void addPowerUp(int k)
	{
		switch(k)
		{
			case -1:
				setHealth(getHealth() + 200);
				return;
			case 0: //fireRate
				setTimeLimit(10);
				setTimerCount(0);
				break;
			case 1: //flak
				break;
			case 2: //speed
				speed = 2*getStandardSpeed();
				if(getXSpeed() > 0)
				{
					setXSpeed(speed);
				}
				else if(getXSpeed() < 0)
				{
					setXSpeed(-speed);
				}
				if(getYSpeed() > 0)
				{
					setYSpeed(speed);
				}
				else if(getYSpeed() < 0)
				{
					setYSpeed(-speed);
				}
				break;
			case 3:  //shotgun
				break;
			case 4:   //invincibility
				setUnkillable(true);
				break;
			case 5:   //superBomb
				break;
				
		}
		powerTimers.set(k, getMaxPowerUpTimer()); //activate PowerUpTimer
	}
	
	public boolean canUseWeapon(int k)
	{
		if(hasFirePowerUp() && k == 0) //bullets have infinite fire rate
		{
			return true;
		}
		return weaponTimers[k] == MAX_WEAPON_TIMERS[k];
	}
	
	public void addTotalScore(long score)
	{
		totalScore += score;
	}
	
	public ArrayList<Integer> getKeysPressed()
	{
		return keys;
	}
	
	public void resetPlayer()
	{
		setHealth(NORMAL_HEALTH);
		setTimeLimit(RATE_OF_FIRE);
		setTimerCount(0);
		setPowerTimers();
		setWeaponTimers();
		speed = getStandardSpeed();
		totalScore = 0;
	}
	
	public void handleCollision(Actor a)
	{
		super.handleCollision(a);
		if(a instanceof Flag || a instanceof PowerUp)
		{
			this.addTotalScore(a.getScore());
		}
		else if(!(a instanceof Projectile))
		{
			this.kill();
		}
	}

	public boolean isPressing(int key)
	{
		for(int k : keys)
		{
			if(k == key)
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean canDropBomb()
	{
		return canUseWeapon(1);
	}
	
	public boolean canFireMissle()
	{
		return canUseWeapon(2);
	}
	
	public boolean canFireFlare()
	{
		return canUseWeapon(3);
	}
	
	public void tick()
	{
		super.tick();

		for(int k = 0; k < weaponTimers.length; k++)
		{
			if(canUseWeapon(k) && isPressing(weaponButtons[k]))
			{
				switch(k)
				{
				case 0:
					if(hasShotGunPowerUp())
					{
						shootShotGun();
					}
					else if(hasFlakPowerUp())
					{
						shootFlak();
					}
					else
					{
						shoot();
					}
					break;
				case 1:
					if(hasSuperBombPowerUp())
					{
						dropSuperBomb();
					}
					else
					{
						dropBomb();
					}
					break;
				case 2:
					fireMissle();
					break;
				case 3:
					fireFlare();
					break;	
				}
				weaponTimers[k] = MAX_WEAPON_TIMERS[k] - 1; //minus 1 activates timer by making canUseWeapon(k) false
			}
		}

		for(int k = 0; k < powerTimers.size(); k++)
		{
			if(powerTimers.get(k) > 0) //if timer is active
			{
				powerTimers.set(k,  powerTimers.get(k) - 1);  //cooldown rate
				if(powerTimers.get(k) <= 0)
				{
					removePowerUp(k); //disables powerUp
				}
			}
		}
		
		if(!canUseWeapon(0))
		{
			weaponTimers[0] -= 1; //cooldown rate
			if(weaponTimers[0] <= 0)
			{
				weaponTimers[0] = MAX_WEAPON_TIMERS[0]; //allows another bullet to be shot
			}
		}
		
	}
	
	private void setStatusBars()
	{
		bars = new ArrayList<StatusBar>();
		int[][] barValues = new int[][]
				{
					new int[] {this.getHealth(), this.getMaxHealth()},
					new int[] {this.getMissleTimer(), this.getMaxMissleTimer()},
					new int[] {this.getBombTimer(), this.getMaxBombTimer()},
					new int[] {this.getFlareTimer(), this.getMaxFlareTimer()},
					new int[] {this.getFirePowerTimer(), this.getMaxPowerUpTimer()}, //powerUps
					new int[] {this.getFlakPowerTimer(), this.getMaxPowerUpTimer()},
					new int[] {this.getSpeedPowerTimer(), this.getMaxPowerUpTimer()},
					new int[] {this.getShotGunPowerTimer(), this.getMaxPowerUpTimer()},
					new int[] {this.getInvinciblePowerTimer(), this.getMaxPowerUpTimer()},
					new int[] {this.getSuperBombPowerTimer(), this.getMaxPowerUpTimer()}
				};
		String[] barNames = new String[]{"Health", "Missle", "Bomb", "Flare", "FireRate++", 
										 "Flak++", "Speed++", "ShotGun++", "Invincibility", "Bomb++"};
		Color[][] colors = new Color[][]
		{
			new Color[] {Color.RED, Color.GREEN},
			new Color[] {Color.RED, Color.DARK_GRAY},
			new Color[] {Color.RED, Color.MAGENTA}
		};
		
		int x = JetWorld.convertToRatioX(10);
		int y = JetWorld.convertToRatioY(600);
		int xSize = JetWorld.convertToRatioX(150);
		int ySize = JetWorld.convertToRatioY(30);
		
		for(int k = 0, colIndex = 0; k < barNames.length; k++, y += ySize + 5)
		{
			makeStatusBar(x,y,xSize,ySize,barValues[k][0], barValues[k][1], barNames[k], colors[colIndex][0], colors[colIndex][1]);
			if(k == 0 || k == 3)
			{
				colIndex++;
			}
			if(k == 3)
			{
				y = JetWorld.convertToRatioY(10) - ySize - 5;
			}
		}
	}
	
	private void makeStatusBar(int x, int y, int xSize, int ySize, int val, int max, String name, Color back, Color fore)
	{
		StatusBar b = new StatusBar(x,y,xSize,ySize,max,name,back,fore);
		b.setValue(val);
		bars.add(b);
	}
	
	public void drawStatusBars(Graphics g)
	{
		for(StatusBar b : bars)
		{
			switch(b.getName())
			{
			case "Health":
				b.setValue(getHealth());
				b.draw(g);
				break;
			case "Missle":
				b.setValue(getMissleTimer());
				b.draw(g);
				break;
			case "Bomb":
				b.setValue(getBombTimer());
				b.draw(g);
				break;
			case "Flare":
				b.setValue(getFlareTimer());
				b.draw(g);
				break;
			case "FireRate++":
				b.setValue(getFirePowerTimer());
				if(b.getValue() > 0)
				{
					b.draw(g);
				}
				break;
			case "Flak++":
				b.setValue(getFlakPowerTimer());
				if(b.getValue() > 0)
				{
					b.draw(g);
				}
				break;
			case "Speed++":
				b.setValue(getSpeedPowerTimer());
				if(b.getValue() > 0)
				{
					b.draw(g);
				}
				break;
			case "ShotGun++":
				b.setValue(getShotGunPowerTimer());
				if(b.getValue() > 0)
				{
					b.draw(g);
				}
				break;
			case "Invincibility":
				b.setValue(getInvinciblePowerTimer());
				if(b.getValue() > 0)
				{
					b.draw(g);
				}
				break;
			case "Bomb++":
				b.setValue(getSuperBombPowerTimer());
				if(b.getValue() > 0)
				{
					b.draw(g);
				}
				break;
			}
		}
	}
	
	@SuppressWarnings("static-access")
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == e.VK_ESCAPE)
		{
			getLevel().setPaused();
			if(run != null)
			{
				run.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Pause"));
			}
			return;
		}
		boolean sameKey = false;
		for(int key : keys)
		{
			if(key == e.getKeyCode())
			{
				sameKey = true;
				break;
			}
		}
		if(!sameKey)
		{
			keys.add(e.getKeyCode());
		}
		
		for(int key : keys)
		{
			if(key == e.VK_W || key == e.VK_UP)
			{
				setYSpeed(-speed);
			}
			if(key == e.VK_S || key == e.VK_DOWN)
			{
				setYSpeed(speed);
			}
			if(key == e.VK_A || key == e.VK_LEFT) 
			{
				setXSpeed(-speed);
			}
			if(key == e.VK_D || key == e.VK_RIGHT)
			{
				setXSpeed(speed);
			}
		}
	}
	
	@SuppressWarnings("static-access")
	public void keyReleased(KeyEvent e) 
	{
		for(int k = 0; k < keys.size(); k++)
		{
			if(keys.get(k) == e.getKeyCode()) 
			{
				keys.remove(k);
				break;
			}
		}
	
		if(isPressing(e.VK_D) || isPressing(e.VK_RIGHT))
		{
			setXSpeed(speed);
		}
		else if(isPressing(e.VK_A) || isPressing(e.VK_LEFT))
		{
			setXSpeed(-speed);
		}
		else
		{
			setXSpeed(0);
		}
		if(isPressing(e.VK_S) || isPressing(e.VK_DOWN))
		{
			setYSpeed(speed);
		}
		else if(isPressing(e.VK_W) || isPressing(e.VK_UP))
		{
			setYSpeed(-speed);
		}
		else
		{
			setYSpeed(0);
		}
	}

	public void shoot()
	{
		addProjectile(new Bullet(getLevel(), getX() + getXSize() + 20, getY() + getYSize()/2, 3*getStandardSpeed(), 0, this));
	}
	
	public void dropSuperBomb()
	{
		addProjectile(new SuperBomb(getLevel(), getX() + getXSize()/2, getY() + getYSize() + 10, this.getXSpeed(), 0, this));
	}
	
	public void shootShotGun()
	{
		if(hasFlakPowerUp())
		{
			shootFlakShotGun();
			return;
		}
		shoot();
		int bulletSpeed = 3*getStandardSpeed();
		addProjectile(new Bullet(getLevel(), getX() + getXSize() + 20, getY() + getYSize()/2, bulletSpeed,bulletSpeed/4, this));
		addProjectile(new Bullet(getLevel(), getX() + getXSize() + 20, getY() + getYSize()/2, bulletSpeed,-bulletSpeed/4, this));
	}
	
	public void shootFlakShotGun()
	{
		shootFlak();
		int bulletSpeed = 3*getStandardSpeed();
		addProjectile(new Flak(getLevel(), getX() + getXSize() + 20, getY() + getYSize()/2, bulletSpeed,bulletSpeed/4, this));
		addProjectile(new Flak(getLevel(), getX() + getXSize() + 20, getY() + getYSize()/2, bulletSpeed,-bulletSpeed/4, this));
	}
	
	public void shootFlak()
	{
		addProjectile(new Flak(getLevel(), getX() + getXSize() + 20, getY() + getYSize()/2, 3*getStandardSpeed(), 0, this));
	}
	
	public void fireMissle()
	{
		addProjectile(new Missle(getX() + getXSize() + 20, getY() + getYSize(), this, getLevel(), Right()));
	}
	
	public void processEndTimer()
	{
		for(int k = 1; k < weaponTimers.length; k++) //does not apply to bullets k = 0
		{
			if(!canUseWeapon(k)) //if weapon is cooling down 
			{
				weaponTimers[k] -= 1;
				if(weaponTimers[k] <= 0)
				{
					weaponTimers[k] = MAX_WEAPON_TIMERS[k]; //allows weapon to be shot again
				}
			}
		}
	}
	//Part of KeyListener interface
	public void keyTyped(KeyEvent e) {}
}
