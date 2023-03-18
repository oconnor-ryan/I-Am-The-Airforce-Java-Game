package root.actors;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import root.JetWorldGame.StatusBar;
import root.actors.planes.PlayerJet;
import root.actors.projectiles.Bomb;
import root.actors.projectiles.Bullet;
import root.actors.projectiles.Explosion;
import root.actors.projectiles.Flare;
import root.actors.projectiles.Missle;
import root.actors.projectiles.Projectile;
import root.levels.Level;
import root.spriteData.Pixel;

public class Vehicle extends Actor
{
	private boolean canDieFromEnemy = true;
	private boolean isEnemy;
	private int direction;
	private static final int RIGHT = 0;
	private static final int LEFT = 180;
	private static final int UP = 90;
	private static final int DOWN = 270;
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	private static final int changeHealthColorTimer = 5;
	private int changeHealthTimer = changeHealthColorTimer;
	private int healthFromLastTick;
	private boolean hasHealthChanged = false;
	private Color defaultColor;
	private ArrayList<Color> pixelColors;
	private int maxHealth = 10000000; //initialize maxHealth BEFORE health to prevent health from being set to 0;
	private StatusBar healthBar = null;

	public Vehicle(Level l, int x, int y)
	{
		setLevel(l);
		setX(x);
		setY(y);	
		setHealth(100);
		isEnemy = true;
		direction = LEFT;
		setColor(Color.PINK);
		setHealthFromLastTick(getHealth());
	}
	public Vehicle(Level w, int x, int y, int xSize, int ySize, int xVel, int yVel, boolean isEnemy, int direction)
	{
		setLevel(w);
		setX(x);
		setY(y);
		setXSpeed(xVel);
		setYSpeed(yVel);
		setXSize(xSize);
		setYSize(ySize);
		setHealth(100);
		setColor(Color.PINK);
		this.isEnemy = isEnemy;
		this.direction = direction;
		setHealthFromLastTick(getHealth());
	}
	
	public void setIsBoss(boolean isBoss) 
	{
		if(isBoss)
		{
			int x = Level.convertToRatioX(300);
			int y = Level.convertToRatioY(50);
			int xSize = Level.convertToRatioX(700);
			int ySize = Level.convertToRatioY(50);
			healthBar = new StatusBar(x, y, xSize, ySize, getMaxHealth(), this.getClass().getSimpleName() + " Boss", Color.GRAY, Color.RED);
			healthBar.setValue(getHealth());
		}
		else
		{
			healthBar = null;
		}
	}
	
	public void setCanDieFromEnemies(boolean b) {canDieFromEnemy = b;}
	
	public boolean isBoss() {return healthBar != null;}
	
	public void setHealth(int h)
	{
		super.setHealth(h);
		if(getHealth() > maxHealth)
		{
			super.setHealth(maxHealth);
		}
	}
	
	public void setHealthFromLastTick(int h)
	{
		healthFromLastTick = h;
	}
	
	private void normalColorChange()
	{
		if(defaultColor == null)
		{
			defaultColor = getColor();
		}
		if(getHealth() < healthFromLastTick && maxHealth > 0 && healthFromLastTick <= maxHealth)
		{
			setColor(new Color(250, 40 ,10));
		}
		else if(getHealth() > healthFromLastTick || (maxHealth > 0 && maxHealth < healthFromLastTick))
		{
			setColor(new Color(0, 255 ,0));
		}
		
	}
	private void setToDefaultColor()
	{
		setColor(defaultColor);
	}
	
	private void spriteColorChange()
	{
		pixelColors = new ArrayList<Color>();
		for(Pixel p : getSprite())
		{
			pixelColors.add(p.getColor());
			if(getHealth() < healthFromLastTick && maxHealth > 0 && healthFromLastTick <= maxHealth)
			{
				Color c = p.getColor();
				int red = c.getRed() + 150;
				if(red > 255)
				{
					red = 255;
				}
				Color color = new Color(red, c.getGreen()/20, c.getBlue()/20); 
				p.setColor(color);
			}
			else if(getHealth() > healthFromLastTick || (maxHealth > 0 && maxHealth < healthFromLastTick))
			{
				p.setColor(Color.GREEN);
			}
		}
	}
	
	private void setDefaultPixelColors()
	{
		int k = 0;
		for(Pixel p : getSprite())
		{
			p.setColor(pixelColors.get(k));
			k++;
		}
	}
	
	public void showHealthColorChange()
	{
		if(getHealth() != healthFromLastTick && !hasHealthChanged)
		{
			if(getSprite() == null)
			{
				normalColorChange();
			}
			else
			{
				spriteColorChange();
			}
			hasHealthChanged = true;
		}
		if(hasHealthChanged)
		{
			changeHealthTimer--;
			if(changeHealthTimer <= 0)
			{
				hasHealthChanged = false;
				if(getSprite() == null)
				{
					setToDefaultColor();
				}
				else
				{
					setDefaultPixelColors();
				}
				changeHealthTimer = changeHealthColorTimer;
			}
		}
		healthFromLastTick = getHealth();
	}
	
	public void tick()
	{
		super.tick();
		showHealthColorChange();
	}
	
	public void processEndTimer()
	{
		shoot();
	}
	
	public static final int Right()
	{
		return RIGHT;
	}
	public static final int Left()
	{
		return LEFT;
	}
	public static final int Up()
	{
		return UP;
	}
	
	public static final int Down()
	{
		return DOWN;
	}
	
	public void setDirection(int d)
	{
		direction = d;
	}
	public int getDirection()
	{
		return direction;
	}
	
	public boolean isEnemy()
	{
		return isEnemy;
	}
	
	public void setIsEnemy(boolean b)
	{
		isEnemy = b;
	}
	
	public int getMaxHealth()
	{
		return maxHealth;
	}
	
	public void setMaxHealth(int n)
	{
		maxHealth = n;
		if(healthBar != null)
		{
			healthBar.setMaxValue(n);
		}
	}
	
	public void checkCollisions(CopyOnWriteArrayList<Actor> actors)
	{
		super.checkCollisions(actors);
		if(isDead())
		{
			explode();
		}
	}
	
	public void handleCollision(Actor a)
	{
		if(a instanceof Projectile)
		{
			if(!canDieFromEnemy && ((Projectile) a).getActor() instanceof Vehicle && ((Vehicle) ((Projectile) a).getActor()).isEnemy())
			{
				return;
			}
			if(((Projectile) a).getActor() == null || 
					((Projectile) a).getActor() != null && !((Projectile) a).getActor().equals(this))//you cannot shoot yourself(For Now)//change this later
			{
				this.takeDamage(((Projectile) a).getDamage());
				if(!(this instanceof PlayerJet) && this.isDead() && (((Projectile) a).getActor() instanceof PlayerJet) )
				{
					getLevel().getPlayer().addTotalScore(this.getScore());
				}
			}
		}
		
		else if(healthBar != null && a instanceof Vehicle)
		{
			return;
		}
		
		
		//note: checking if this.class() == Vehicle.class severely lowers frame rate
	}
	
	public void move()
	{
		if(getXSpeed() < 0)
		{
			direction = LEFT;
		}
		else if(getXSpeed() > 0)
		{
			direction = RIGHT;
		}
		super.move();
	}
	
	public void explode()
	{
		int maxRadius = 0;
		if(getXSize() >= getYSize())
		{
			maxRadius = (int)(getXSize()*1.5);
		}
		else
		{
			maxRadius = (int)(getYSize()*1.5);
		}
		getLevel().add(new Explosion(getLevel(),getX()+getXSize()/2, getY()+getYSize()/2, maxRadius,this));
	}
	
	public ArrayList<Projectile> getProjectiles()
	{
		return projectiles;
	}
	
	public void resetProjectiles()
	{
		projectiles = new ArrayList<Projectile>();
	}
	
	public void addProjectile(Projectile b)
	{
		projectiles.add(b);
	}
	
	public void dropBomb()
	{
		projectiles.add(new Bomb(getLevel(), getX() + getXSize()/2, getY() + getYSize() + 10 ,getXSpeed(), 0, this));
	}
	
	public void shoot()  //fix all shooting methods to allow things to be shot at angles
	{
		if(direction == RIGHT)
		{
			projectiles.add(new Bullet(getLevel(), getX() + getXSize() + 15, getY() + getYSize()/2, 3*getStandardSpeed(), 0, this));
		}
		else if(direction == LEFT)
		{
			projectiles.add(new Bullet(getLevel(), getX() - 15, getY() + getYSize()/2, -3*getStandardSpeed(), 0, this));
		}
		else if(direction == UP)
		{
			projectiles.add(new Bullet(getLevel(), getX(), getY() - 10, 0, -3*getStandardSpeed(), this));
		}
	}
	
	public void fireMissle()
	{
		if(direction == RIGHT)
		{
			projectiles.add(new Missle(getX() + getXSize() + 10, getY() + getYSize(), this, getLevel(), Right()));
		}
		else if(direction == LEFT)
		{
			projectiles.add(new Missle(getX() - 10, getY(), this, getLevel(), Left()));
		}
	}
	
	public void fireFlare()
	{
		projectiles.add(new Flare(getLevel(), getX() - 15, getY() + getYSize(), -1, -15, this));
	}
	
	public void drawSprite(Graphics g)
	{
		super.drawSprite(g);
		if(healthBar != null)
		{
			healthBar.setValue(getHealth());
			healthBar.draw(g);
		}
	}
}
