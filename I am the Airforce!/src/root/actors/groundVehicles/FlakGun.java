package root.actors.groundVehicles;

import java.io.IOException;

import root.levels.Level;

public class FlakGun extends AAGun
{
	public FlakGun(Level l, int x, int y) 
	{
		super(l, x, y);
		try {
			createSprite("FlakGun.txt");
			setPixelSize(Level.convertToRatioX(4));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void processEndTimer()
	{
		shootFlak();
	}
}
