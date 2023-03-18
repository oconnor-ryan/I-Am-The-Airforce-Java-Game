package root.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Button 
{
	private String text;
	private int xPos;
	private int yPos;
	private int xSize;
	private int ySize;
	private Color color;
	private boolean hasBeenPressed;
	private boolean mouseOverButton;
	
	public Button(int x, int y, int xSize, int ySize, String text, Color c)
	{
		xPos = x;
		yPos = y;
		this.xSize = xSize;
		this.ySize = ySize;
		this.text = text;
		color = c;
		hasBeenPressed = false;
		mouseOverButton = false;
	}
	
	public boolean mouseIsInside(int x, int y)
	{
		return x >= xPos && x < xPos + xSize && y >= yPos && y <= yPos + ySize;
	}
	
	public void setMouseOverButton(boolean b)
	{
		mouseOverButton = b;
	}
	public boolean isMouseOverButton()
	{
		return mouseOverButton;
	}
	public void setPressed()
	{
		hasBeenPressed = !hasBeenPressed;
	}
	
	public void setPressed(boolean b)
	{
		hasBeenPressed = b;
	}
	public boolean hasBeenPressed()
	{
		return hasBeenPressed;
	}
	
	public int getX()
	{
		return xPos;
	}
	public int getY()
	{
		return yPos;
	}
	public int getXSize()
	{
		return xSize;
	}
	public int getYSize()
	{
		return ySize;
	}
	public String getText()
	{
		return text;
	}
	
	public void drawCenteredString(Graphics g, Font f, String text)
	{
		FontMetrics m = g.getFontMetrics(f);
		g.setFont(f);
		int x = xPos + (xSize - m.stringWidth(text))/2;
		int y = yPos + ((ySize - m.getHeight())/2) + m.getAscent();
		g.drawString(text, x, y);
	}
	
	public void draw(Graphics g)
	{
		g.setColor(color);
		if(mouseOverButton)
		{
			g.fillRect(xPos, yPos, xSize,ySize);
			g.setColor(new Color(255 - g.getColor().getRed(), 255 - g.getColor().getGreen(), 255 - g.getColor().getBlue()));
		}
		else
		{
			g.drawRect(xPos, yPos, xSize,ySize);
		}
		
		drawCenteredString(g, new Font("serif", Font.PLAIN, ySize), text);
	}
}
