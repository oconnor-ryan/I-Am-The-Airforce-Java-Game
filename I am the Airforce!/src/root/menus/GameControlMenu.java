package root.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import root.JetWorldGame.JetWorldRunner;

public class GameControlMenu extends Menu
{
	private static final long serialVersionUID = 1L;

	public GameControlMenu(String s, JetWorldRunner a) 
	{
		super(s, a);
		int x = a.getWidth()/4;
		int xSize = a.getWidth()/2;
		int y = JetWorldRunner.convertToRatioY(650);
		int ySize = JetWorldRunner.convertToRatioY(50);
		getButtons().add(new Button(x, y, xSize, ySize, "Pause Menu", Color.WHITE));
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		int x = JetWorldRunner.convertToRatioX(400);
		g.setFont(new Font("serif",JetWorldRunner.convertToRatioX(50),JetWorldRunner.convertToRatioY(50)));
		g.setColor(new Color(100, 150, 50));
		g.drawString("W or Up Arrow = Up", x, JetWorldRunner.convertToRatioY(200));
		g.drawString("A or Left Arrow = Left", x, JetWorldRunner.convertToRatioY(250));
		g.drawString("S or Down Arrow = Down", x, JetWorldRunner.convertToRatioY(300));
		g.drawString("D or Right Arrow = Right", x, JetWorldRunner.convertToRatioY(350));
		g.drawString("Hold E = Shoot Bullets", x, JetWorldRunner.convertToRatioY(400));
		g.drawString("R = Fire Missle", x, JetWorldRunner.convertToRatioY(450));
		g.drawString("F = Shoot Flare", x, JetWorldRunner.convertToRatioY(500));
		g.drawString("Space Bar = Drop Bomb", x, JetWorldRunner.convertToRatioY(550));	
	}

}
