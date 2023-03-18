package root.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import root.JetWorldGame.JetWorldRunner;

public class CreditMenu extends Menu
{
	private static final long serialVersionUID = 1L;

	public CreditMenu(String s, JetWorldRunner a) 
	{
		super(s, a);
		int x = a.getWidth()/4;
		int xSize = a.getWidth()/2;
		int y = JetWorldRunner.convertToRatioY(400);
		int ySize = JetWorldRunner.convertToRatioY(50);
		getButtons().add(new Button(x, y, xSize, ySize,  "Main Menu", Color.WHITE));
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.YELLOW);
		Font f = new Font("serif",Font.PLAIN, JetWorldRunner.convertToRatioY(100));
		int[] a = getCenteredString(g, f, "Ryan O,", getRunner().getWidth(), 2*getRunner().getHeight()/3);
		g.drawString("Ryan O.", a[0] + 5, a[1]);
	}

}
