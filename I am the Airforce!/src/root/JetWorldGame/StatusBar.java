package root.JetWorldGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class StatusBar 
{
	private int x;
	private int y;
	private int xSize;
	private int ySize;
	private int maxNum;
	private int value;
	private String name;
	private Color backColor;
	private Color frontColor;
	public StatusBar(int x, int y, int xSize, int ySize, int max, String name, Color b, Color f)
	{
		this.x = x;
		this.y = y;
		this.xSize = xSize;
		this.ySize = ySize;
		maxNum = max;
		this.name = name;
		backColor = b;
		frontColor = f;
	}
	public String getName() {return name;}
	public int getValue() {return value;}
	public int getMaxValue() {return maxNum;}
	public void setValue(int n) {value = n;}
	public void setMaxValue(int n) {maxNum = n;}
	
	public void drawCenteredString(Graphics g, Font f, String text)
	{
		FontMetrics m = g.getFontMetrics(f);
		g.setFont(f);
		int x = this.x + (xSize - m.stringWidth(text))/2;
		int y = this.y + ((ySize - m.getHeight())/2) + m.getAscent();
		g.drawString(text, x, y);
	}
	
	public void draw(Graphics g)
	{
		g.setColor(backColor);
		g.fillRect(x, y, xSize, ySize);
		g.setColor(frontColor);
		int barSize = (value*xSize)/maxNum;
		g.fillRect(x, y, barSize, ySize);
		g.setColor(Color.WHITE);
		drawCenteredString(g, new Font("serif", Font.BOLD, ySize), name);
	}
}
