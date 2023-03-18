package root.spriteData;
import java.awt.Color;

public class Pixel 
{
	private int x;
	private int y;
	private int size;
	private int row;
	private int col;
	private Color color;
	private boolean isBackground;
	public Pixel(int r, int c, int size)
	{
		x = col*size; // + actorX
		y = row*size; // + actorY
		row = r;
		col = c;
		this.size = size;
		isBackground = false;
		color = null;
	}
	
	public int getX() {return x;}
	public int getY() {return y;}
	public void setX(int x) {this.x = x;}
	public void setY(int y) {this.y = y;}
	public int getRow() {return row;}
	public int getCol() {return col;}
	public void setRow(int r) {row = r;}
	public void setCol(int c) {col = c;}
	public int getSize() {return size;}
	public void setSize(int s) {size = s;}
	public boolean isBackground() {return isBackground;}
	public void setBackground(boolean b) {isBackground = b;}
	public void setBackground() {isBackground = !isBackground;}
	public Color getColor() {return color;}
	public void setColor(Color c) {color = c;}
	
	public boolean contains(int ex, int ey)
	{
		return ex >= x && ex <= x + size && ey >= y && ey <= y + size;
	}
	
	public String toString()
	{
		return "PIXEL: Row:" + row + " Col: " + col;
	}
}
