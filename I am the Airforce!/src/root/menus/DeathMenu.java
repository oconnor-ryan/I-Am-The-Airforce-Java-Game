package root.menus;
import java.awt.Color;

import root.JetWorldGame.JetWorldRunner;

public class DeathMenu extends Menu
{
	private static final long serialVersionUID = 154687L;
	
	public DeathMenu(String s, JetWorldRunner a) 
	{
		super(s, a);
		int x = a.getWidth()/4;
		int xSize = a.getWidth()/2;
		int ySize = JetWorldRunner.convertToRatioY(50);
		getButtons().add(new Button(x, JetWorldRunner.convertToRatioY(400), xSize, ySize, "Restart Level", Color.WHITE));
		getButtons().add(new Button(x, JetWorldRunner.convertToRatioY(500), xSize, ySize, "Restart Game", Color.WHITE));
		getButtons().add(new Button(x, JetWorldRunner.convertToRatioY(600), xSize, ySize, "Main Menu", Color.WHITE));
	}

	

}
