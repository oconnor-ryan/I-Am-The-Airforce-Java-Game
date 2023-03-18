package root.backgrounds;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

import root.spriteData.Pixel;
import root.spriteData.PixelGrid;
import root.spriteData.SpriteSheetProcesser;

public class Background 
{
	private int width;
	private int height;
	private Color color;
	private PixelGrid sprite;
	private int pixelSize;
	
	public Background(Color c, int w, int h)
	{
		color = c;
		width = w;
		height = h;
	}
	
	public Background(String file, int w, int h, int pixelSize)
	{
		try {
			sprite = SpriteSheetProcesser.readFile(file);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		width = w;
		height = h;
		this.pixelSize = pixelSize;
	}
	
	public void draw(Graphics g)
	{
		if(color != null)
		{
			g.setColor(color);
			g.fillRect(0, 0, width, height);
			return;
		}
		drawSprite(g);
	}
	private void drawSprite(Graphics g)
	{
		int[][] border = getBorders(sprite);
		int[] rowRange = border[0];
		int[] colRange = border[1];
		
		for(int y = 0, r = rowRange[0]; r <= rowRange[1]; r++, y += pixelSize)
		{
			for(int x = 0, c = colRange[0]; c <= colRange[1]; c++, x += pixelSize)
			{
				for(Pixel p : sprite)//use sorting algorithm
				{
					if(r == p.getRow() && c == p.getCol())
					{
						g.setColor(p.getColor());
						g.fillRect(x, y, pixelSize, pixelSize);
						break;		
					}
				}
			}
		}
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
}
