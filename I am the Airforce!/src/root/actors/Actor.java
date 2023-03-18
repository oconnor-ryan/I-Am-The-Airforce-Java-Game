package root.actors;

/*
 * Superclass of all interactable objects:
 * Planes, projectiles, ships , vehicles, etc
 * This class holds their most basic functions, mostly setters and getters
 * Does not require explicit constructor
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import root.JetWorldGame.JetWorld;
import root.levels.Level;
import root.spriteData.Pixel;
import root.spriteData.PixelGrid;
import root.spriteData.SpriteSheetProcesser;

public abstract class Actor 
{
	private int xPos;
	private int yPos;
	private int xSpeed;
	private int ySpeed;
	private int xSize;
	private int ySize;
	private int health;
	private boolean isUnKillable;
	private static final int STANDARD_SPEED = 5;
	private static final Color defaultColor = Color.BLACK;
	private Color color = defaultColor;
	private static final long TIMER_START = 0;
	private long timerCount = TIMER_START;
	private long timeLimit;
	private Level level;
	private long score;
	
	private ArrayList<PixelGrid> sprites; //MAKE SURE ALL SPRITES FACE LEFT
	private int currentSprite = 0;

	public abstract void processEndTimer();
	public abstract void handleCollision(Actor a);
	public void checkCollisions(CopyOnWriteArrayList<Actor> actors)
	{
		for(Actor a : actors)
		{
			if(!a.equals(this) && a.hasCollided(this))
			{
				handleCollision(a);
			}
		}
	}
	
	public void setColor(Color c) {color = c;}
	public Color getColor() {return color;}
	public int getX() {return xPos;}
	public int getY() {return yPos;}
	public void setX(int x) {xPos = x;}
	public void setY(int y) {yPos = y;}
	public int getXSpeed() {return xSpeed;}
	public int getYSpeed() {return ySpeed;}
	
	public void setXSpeed(int x)
	{
		if(level == null)
			xSpeed = x;
		else
		{
			xSpeed = Level.convertToRatioX(x) - 1; //lead to speed up glitch on monitors with x resolution greater than 1360
			if(xSpeed == 0)
			{
				if(x > 0)
				{
					xSpeed += 1;
				}
				else if(x < 0)
				{
					xSpeed -= 1;
				}
			}
		}
	}
	public void setYSpeed(int y)
	{
		if(level == null)
			ySpeed = y;
		else
		{
			ySpeed = Level.convertToRatioY(y) - 1; //lead to speed up glitch on monitors with x resolution greater than 768
			if(ySpeed == 0)
			{
				if(y > 0)
				{
					ySpeed += 1;
				}
				else if(y < 0)
				{
					ySpeed -= 1;
				}
			}
		}
	}
	public int getStandardSpeed() {return STANDARD_SPEED;}
	public void setXSpeedWithoutScale(int x) {xSpeed = x;}
	public void setYSpeedWithoutScale(int y) {ySpeed = y;}
	public int getXSize() {return xSize;}
	public int getYSize() {return ySize;}
	public void setXSize(int x) {xSize = x;}
	public void setYSize(int y) {ySize = y;}
	public void setPixelSize(int s) 
	{
		for(PixelGrid grid : sprites)
		{
			for(Pixel p : grid)
			{
				p.setSize(s);
			}
		}	
		int[] sizes = getNewSizes();
		setXSize(sizes[0]);
		setYSize(sizes[1]);
	}
	public int getPixelSize() 
	{
		if(sprites == null)
		{
			return 0;
		}
		else
		{
			return getSprite().get(0).getSize();
		}
	}
	
	public int[] getNewSizes()
	{
		int[][] border = getBorders(getSprite());
		int[] rowRange = border[0];
		int[] colRange = border[1];
		int newXSize = ((colRange[1] - colRange[0]) * getPixelSize()) + getPixelSize();
		int newYSize = ((rowRange[1] - rowRange[0]) * getPixelSize()) + getPixelSize();
		return new int[] {newXSize, newYSize};
	}
	
	public void createSprite(String spriteFile) throws IOException
	{
		sprites = new ArrayList<PixelGrid>();
		sprites.add(SpriteSheetProcesser.readFile(spriteFile));//right or left
		sprites.add(reverseSpriteX(sprites.get(0)));//left or right
		sprites.add(rotate90DegreesClockWise(sprites.get(0))); //up or down
		sprites.add(rotate90DegreesClockWise(sprites.get(1))); //down or up
	}
	public PixelGrid getSprite()
	{
		if(sprites == null || sprites.get(currentSprite) == null)
		{
			return null;
		}
		return sprites.get(currentSprite);
	}
	
	public int getCurrentSprite() {return currentSprite;}
	public void setCurrentSprite(int i) {currentSprite = i;}
	public int numSprites() {return sprites.size();}	
	
	public Rectangle getHitBox() {return new Rectangle(xPos, yPos, xSize, ySize);}
	
	public boolean hitBoxesCollided(Actor other) {return this.getHitBox().intersects(other.getHitBox());}
	
	public boolean hasCollided(Actor other)
	{
		if(this.getSprite() == null || other.getSprite() == null)
		{
			return hitBoxesCollided(other);
		}
		
		if(hitBoxesCollided(other)) //used so that computer only calculates each pixel if outer hitboxes intersect
		{		//is less costly to framerate, also fixed glitch where planes would not shoot
			
			for(Pixel p : this.getSprite())
			{
				Rectangle myBounds = new Rectangle(p.getX(), p.getY(), p.getSize(), p.getSize());
				for(Pixel oP : other.getSprite())
				{
					Rectangle otherBounds = new Rectangle(oP.getX(), oP.getY(), oP.getSize(), oP.getSize());
					if(myBounds.intersects(otherBounds))
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public int getHealth() {return health;}
	public void setHealth(int h) {health = h;}
	
	public void takeDamage(int d)
	{
		if(!isUnKillable)
		{
			setHealth(getHealth() - d);
		}
	}
	public void setUnkillable(boolean b) {isUnKillable = b;}
	public boolean isUnkillable() {return isUnKillable;}
	public boolean isDead() {return getHealth() <= 0;}
	public void kill() 
	{
		if(!isUnKillable)
		{
			setHealth(0);
		}
	}
	
	public long getScore() {return score;}
	public void setScore(long s) {score = s;}
	
	public Level getLevel() {return level;}
	public void setLevel(Level w) {level = w;}
	
	public long getStandardTimerStart() {return TIMER_START;}
	public void setTimerCount(long millis) {timerCount = millis;}
	public long getTimerCount() {return timerCount;}
	public long getTimeLimit() {return timeLimit;}
	
	
	public void setTimeLimit(long millis)
	{
		if(level == null)
		{
			timeLimit = (40*millis)/1000;
			return;
		}
		double mult = (double) JetWorld.getBestUpdateRate() / (double) level.getUpdateRate();//allows for slow motion
		timeLimit = (long) Math.round((mult * (double)millis * (double)level.getUpdateRate())/1000.0);
	}
	
	public void setTimeLimitWithFrames(int frames)
	{
		timeLimit = frames;
	}
	
	
	public void tick()
	{
		timerCount++;
		if(timerCount >= timeLimit)
		{
			processEndTimer();	
			timerCount = TIMER_START;
		}
	}
	
	public void move()
	{
		xPos += xSpeed;
		yPos += ySpeed;
	}

	private int[][] getBorders(PixelGrid g)
	{
		int rowMin = Integer.MAX_VALUE;
		int colMin = Integer.MAX_VALUE;
		int rowMax = Integer.MIN_VALUE;
		int colMax = Integer.MIN_VALUE;
		for(Pixel pix : g)
		{	
			if(pix.getRow() < rowMin)
			{
				rowMin = pix.getRow();
			}
			if(pix.getRow() > rowMax)
			{
				rowMax = pix.getRow();
			}
			if(pix.getCol() < colMin)
			{
				colMin = pix.getCol();
			}
			if(pix.getCol() > colMax)
			{
				colMax = pix.getCol();
			}
		}
		
		return new int[][] {{rowMin, rowMax}, {colMin, colMax}};
	}
	
	public PixelGrid rotate90DegreesClockWise(PixelGrid mainSprite)
	{
		PixelGrid grid = new PixelGrid(mainSprite.getNumCols(), mainSprite.getNumRows(), mainSprite.get(0).getSize());
		grid.removeInvalidPixels();
		
		int[][] border = getBorders(mainSprite);
		int[] rowRange = border[0];
		int[] colRange = border[1];
		
		for(int c = colRange[0]; c <= colRange[1]; c++)
		{
			for(int r = rowRange[1]; r >= rowRange[0]; r--)
			{
				for(Pixel p : mainSprite)
				{
					Pixel newPixel = null;
					if(p.getCol() == c && p.getRow() == r)
					{
						newPixel = new Pixel(c, r, p.getSize());
						newPixel.setColor(p.getColor());
						grid.add(newPixel);
					}
				}
			}
		}
		
		return grid;
	}
	
	public PixelGrid reverseSpriteX(PixelGrid mainSprite)
	{
		int[][] border = getBorders(mainSprite);
		int[] rowRange = border[0];
		int[] colRange = border[1];
		int colMin = colRange[0];
		int colMax = colRange[1];
		
		PixelGrid newGrid = new PixelGrid(mainSprite.getNumRows(), mainSprite.getNumCols(), mainSprite.get(0).getSize());
		newGrid.removeInvalidPixels();
		while(colMin <= colMax)
		{
			for(int r = rowRange[0]; r <= rowRange[1]; r++)
			{
				for(Pixel p : mainSprite)
				{
					Pixel newPixel = null;
					if(p.getCol() == colMin && p.getRow() == r)
					{
						newPixel = new Pixel(r,colMax,mainSprite.get(0).getSize());
					}
					else if(p.getCol() == colMax && p.getRow() == r)
					{
						newPixel = new Pixel(r,colMin,mainSprite.get(0).getSize());
					}
					if(newPixel != null)
					{
						newPixel.setColor(p.getColor());
						newGrid.add(newPixel);
					}
				}
			}
			colMin++;
			colMax--;
		}
		
		return newGrid;
	}
	
	public void drawSprite(Graphics g)
	{
		PixelGrid sprite = getSprite();
		if(sprite == null)
		{
			draw(g);
			return;
		}	
		int[][] border = getBorders(sprite);
		int[] rowRange = border[0];
		int[] colRange = border[1];
		
		int[] sizes = getNewSizes();
		setXSize(sizes[0]);
		setYSize(sizes[1]);

		for(Pixel p : sprite)
		{
			p.setX(xPos + getPixelSize()*(p.getCol() - colRange[0]));
			p.setY(yPos + getPixelSize()*(p.getRow() - rowRange[0]));
			g.setColor(p.getColor());
			g.fillRect(p.getX(), p.getY(), p.getSize(), p.getSize());
		}
	}
	
	public String toString()
	{
		return this.getClass().getSimpleName();
	}

	public void draw(Graphics g)
	{
		g.setColor(color);
		g.fillRect(xPos, yPos, xSize, ySize);
	}
}
