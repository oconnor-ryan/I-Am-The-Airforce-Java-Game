package root.menus;
import java.awt.Color;

import root.JetWorldGame.JetWorldRunner;

public class PauseMenu extends Menu
{
	private static final long serialVersionUID = 1L;
	
	public PauseMenu(String s, JetWorldRunner a)
	{
		super(s, a);
		int x = a.getWidth()/4;
		int xSize = a.getWidth()/2;
		int ySize = JetWorldRunner.convertToRatioY(50);
		getButtons().add(new Button(x, JetWorldRunner.convertToRatioY(250), xSize, ySize, "Resume", Color.WHITE));
		getButtons().add(new Button(x, JetWorldRunner.convertToRatioY(350), xSize, ySize, "Restart Level", Color.WHITE));
		getButtons().add(new Button(x, JetWorldRunner.convertToRatioY(450), xSize, ySize, "Restart Game", Color.WHITE));
		getButtons().add(new Button(x, JetWorldRunner.convertToRatioY(550), xSize, ySize, "Controls", Color.WHITE));
		getButtons().add(new Button(x, JetWorldRunner.convertToRatioY(650), xSize, ySize, "Main Menu", Color.WHITE));
	}
}
