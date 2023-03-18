package root.spriteData;
import java.util.ArrayList;

public class PixelGrid extends ArrayList<Pixel>
{
	private static final long serialVersionUID = 418782305617702095L;
	private int numRows;
	private int numCols;
	
	public PixelGrid(int rows, int cols, int pixelSize)
	{
		super();
		numRows = rows;
		numCols = cols;
		for(int r = 0; r < numRows; r++)
		{
			for(int c = 0; c < numCols; c++)
			{
				this.add(new Pixel(r,c,pixelSize));
			}
		}
	}
	
	public void removeInvalidPixels()
	{
		for(int k = this.size() - 1; k >= 0; k--)
		{
			if(this.get(k).getColor() == null)
			{
				this.remove(k);
			}
		}
	}
	public int getNumRows() {return numRows;}
	public int getNumCols() {return numCols;}
}
