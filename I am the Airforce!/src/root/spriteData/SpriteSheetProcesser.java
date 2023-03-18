package root.spriteData;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SpriteSheetProcesser
{
	public static PixelGrid readFile(final String fileName) throws IOException
	{
		//InputStream in = Actor.class.getClassLoader().getResource("spriteFiles/" + spriteFile).openStream(); //used so jar can read txt files
		InputStream in = SpriteSheetProcesser.class.getClassLoader().getResourceAsStream(fileName);
		if(in == null) {
			System.out.println("NULL");
		}
 		InputStreamReader inRead = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(inRead);
		PixelGrid grid = null;
		int numRows = 0;
		int numCols = 0;
		int size = 0;
		String line = br.readLine();
		int atLine = 1;
		int pixelIndex = 0;
		while(line != null)
		{
			if(line.indexOf("NumRows=") >= 0)
			{
				numRows = Integer.valueOf(line.substring(line.indexOf("=") + 1, line.length()));
			}
			else if(line.indexOf("NumCols=") >= 0)
			{
				numCols = Integer.valueOf(line.substring(line.indexOf("=") + 1, line.length()));
			}
			else if(line.indexOf("Size=") >= 0)
			{
				size = Integer.valueOf(line.substring(line.indexOf("=") + 1, line.length()));
				grid = new PixelGrid(numRows, numCols, size);
			}
			else if(atLine > 4)
			{
				int r = Integer.valueOf(line.substring(line.indexOf("Row=") + 4, line.indexOf("Col=")));
				int c = Integer.valueOf(line.substring(line.indexOf("Col=") + 4, line.indexOf("R=")));
				int[] rgb = new int[3];
				rgb[0] = Integer.valueOf(line.substring(line.indexOf("R=") + 2, line.indexOf("G=")));
				rgb[1] = Integer.valueOf(line.substring(line.indexOf("G=") + 2, line.indexOf("B=")));
				rgb[2] = Integer.valueOf(line.substring(line.indexOf("B=") + 2, line.length()));
				Color color = new Color(rgb[0], rgb[1], rgb[2]);
				Pixel p = grid.get(pixelIndex);
				p.setColor(color);
				p.setRow(r);
				p.setCol(c);
				pixelIndex++;
			}
			line = br.readLine();
			atLine++;
		}
		
		for(int k = grid.size() - 1; k >= pixelIndex; k--)//removes extra pixels from initial constructor if there were blank pixels
		{
			grid.remove(k);
		}
		br.close();
		return grid;
	}
}
