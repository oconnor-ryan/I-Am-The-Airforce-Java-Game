package root.menus;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import root.JetWorldGame.JetWorldRunner;

public class MainMenu extends Menu
{
	private static final long serialVersionUID = 1L;
	
	public MainMenu(String s, JetWorldRunner a)
	{
		super(s, a);
		int x = a.getWidth()/4;
		int xSize = a.getWidth()/2;
		int ySize = JetWorldRunner.convertToRatioY(50);
		getButtons().add(new Button(x, JetWorldRunner.convertToRatioY(300), xSize, ySize, "Start", Color.WHITE));
		getButtons().add(new Button(x, JetWorldRunner.convertToRatioY(400), xSize, ySize, "Select Level", Color.WHITE));
		getButtons().add(new Button(x, JetWorldRunner.convertToRatioY(500), xSize, ySize,  "Credits", Color.WHITE));
		getButtons().add(new Button(x, JetWorldRunner.convertToRatioY(600), xSize, ySize, "Exit", Color.WHITE));
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		int x = JetWorldRunner.convertToRatioX(10);
		int y = 15*getRunner().getHeight()/16;
		g.setFont(new Font("serif", Font.PLAIN, JetWorldRunner.convertToRatioX(50)));
		g.setColor(Color.WHITE);
		g.drawString(getRunner().getVersionNum(), x, y);
	}
	
}
