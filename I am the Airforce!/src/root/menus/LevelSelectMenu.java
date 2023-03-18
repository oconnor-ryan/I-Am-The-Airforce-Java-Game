package root.menus;
import java.awt.Color;

import root.JetWorldGame.JetWorldRunner;

public class LevelSelectMenu extends Menu
{
	private static final long serialVersionUID = 1L;
	
	public LevelSelectMenu(String s, JetWorldRunner a)
	{
		super(s, a);
		int x = a.getWidth()/8;
		int xSize = a.getWidth()/4;
		int ySize = JetWorldRunner.convertToRatioY(50);
		getButtons().add(new Button(x, JetWorldRunner.convertToRatioY(300), xSize, ySize, "Tutorial", Color.WHITE));
		getButtons().add(new Button(x, JetWorldRunner.convertToRatioY(400), xSize, ySize, "Level 1", Color.WHITE));
		getButtons().add(new Button(x, JetWorldRunner.convertToRatioY(500), xSize, ySize, "Level 2", Color.WHITE));
		getButtons().add(new Button(x, JetWorldRunner.convertToRatioY(600), xSize, ySize, "Level 3", Color.WHITE));
		getButtons().add(new Button(x * 5, JetWorldRunner.convertToRatioY(300), xSize, ySize, "Level 4", Color.WHITE));
		getButtons().add(new Button(x * 5, JetWorldRunner.convertToRatioY(400), xSize, ySize, "Level 5", Color.WHITE));
		getButtons().add(new Button(x * 5, JetWorldRunner.convertToRatioY(500), xSize, ySize, "Level 6", Color.WHITE));
		getButtons().add(new Button(x * 5, JetWorldRunner.convertToRatioY(600), xSize, ySize, "Level 7", Color.WHITE));
		getButtons().add(new Button(x * 2, JetWorldRunner.convertToRatioY(700), xSize * 2, ySize, "Main Menu", Color.WHITE));
	}
	
}
