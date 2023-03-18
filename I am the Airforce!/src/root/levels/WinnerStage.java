package root.levels;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import root.JetWorldGame.JetWorld;
import root.actors.planes.PlayerJet;

public class WinnerStage extends Level
{
	private	int r = (int)(Math.random() * 256);
	private int a = (int)(Math.random() * 256);
	private int b = (int)(Math.random() * 256);
		
	public WinnerStage(JetWorld w, PlayerJet p, int rate, int levelNum) 
	{
		super(w, p, rate, levelNum);
		setPlayerSpawn(650,400);
		setTimeLimit(500);
	}
	public void processEndTimer()
	{
		r = (int)(Math.random() * 256);
		a = (int)(Math.random() * 256);
		b = (int)(Math.random() * 256);
	}

	@Override
	public void checkEndLevel() {} //no end
	
	public void draw(Graphics g)
	{
		super.draw(g);
		String title = "End of Game!";
		String points = getPlayer().getTotalScore() + " Points!";
		g.setColor(new Color(r,a,b));
		g.setFont(new Font("serif", convertToRatioX(100),convertToRatioY(100)));
		int[] xy = getCenteredString(g, g.getFont(), title, getWidth(), getHeight());
		g.drawString(title, xy[0], xy[1]);
		g.setFont(new Font("serif", convertToRatioX(50),convertToRatioY(50)));
		int[] xy1 = getCenteredString(g, g.getFont(), points, getWidth(), getHeight());
		g.drawString(points, xy1[0], 5*getHeight()/8);
		g.drawString("Press Pause to Quit", convertToRatioX(20), convertToRatioY(150));
	}
}
