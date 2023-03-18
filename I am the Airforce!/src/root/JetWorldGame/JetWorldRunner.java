package root.JetWorldGame;
import java.awt.CardLayout;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JFrame;

import root.menus.*;

public class JetWorldRunner extends JFrame
{
	private static final long serialVersionUID = 1651115148696232000L;
	private CardLayout cards;
	private Button buttonPressed;
	private ArrayList<Menu> menus;
	private int width = 0;
	private int height = 0;
	private String mainMenuName = "I am the Airforce!";
	private String pauseMenuName = "Pause Menu";
	private String levelMenuName = "Level Select";
	private String deadMenuName = "You Died";
	private String gameName = "game";
	private String controlMenuName = "Controls";
	private String creditMenuName = "Credits";
	private String currentPanelShown;
	private JetWorld world;

	private final int[] myResolution = new int[] {1360, 768}; // 1360 768
	private static double[] scaleFactor;
	
	private static final String versionNumber = "Version 1.0.0";

	public JetWorldRunner()
	{
		//gDevice
		JFrame f = new JFrame();
		//get width and height from fullscreen instead of using GraphicEnvironments fullscreen because it is bugged and flashes white or does not work
		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(f);/////
		width = f.getWidth();
		height = f.getHeight();
		f.dispose();
		f = null;
		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(f);
		
		if(width != 0 && height != 0)
		{
			setExtendedState(MAXIMIZED_BOTH);
		}
		else
		{
			width = 800; 
			height = 600; 
			setSize(width, height); //| 1024 768 | 800 600 | 480 320 | 320 240 | 240 160 | 160 80 | 
		}
		setUndecorated(true);
		scaleFactor = findAspectRatio();
		world = new JetWorld(this);
		cards = new CardLayout();
		menus = new ArrayList<Menu>();
		menus.add(new MainMenu(mainMenuName, this));
		menus.add(new PauseMenu(pauseMenuName, this));
		menus.add(new LevelSelectMenu(levelMenuName, this));
		menus.add(new DeathMenu(deadMenuName, this));
		menus.add(new GameControlMenu(controlMenuName, this));
		menus.add(new CreditMenu(creditMenuName, this));
		setLayout(cards);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		for(Menu m : menus)
		{
			getContentPane().add(m, m.getName());
		}
		getContentPane().add(world, gameName);
		addKeyListener(world.getPlayer());
		setResizable(false);
		setVisible(true);
		
		
		//Width = 1360  Height = 768
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public final String getVersionNum()
	{
		return versionNumber;
	}
	private double[] findAspectRatio()
	{
		double x = (double)getWidth() / (double) myResolution[0];
		double y = (double)getHeight() / (double) myResolution[1];
		return new double[] {x,y};
	}
	
	public static int convertToRatioX(double n)
	{
		return (int)(Math.round(n * scaleFactor[0]) + 1);
	}
	public static int convertToRatioY(double n)
	{
		return (int)(Math.round(n * scaleFactor[1]) + 1);
	}
	
	public double[] getScaleFactor()
	{
		return scaleFactor;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource().equals(world.getPlayer()))
		{
			if(e.getActionCommand().equals("Pause") && !world.isGameOver())
			{
				world.getCurrentLevel().setPaused(true);
				currentPanelShown = pauseMenuName;
			}	
		}
		else if(e.getSource().equals(world.getCurrentLevel()) && e.getActionCommand().equals("Dead"))
		{
			currentPanelShown = deadMenuName;
			world.setGameOver(true);
		}

		if(buttonPressed != null)
		{
			String buttonName = buttonPressed.getText().trim();
		
			switch(buttonName)
			{
			case "Main Menu":
				currentPanelShown = mainMenuName;
				world.setGameOver(true);
				world.resetLevels();
				world.setLevel(0);
				world.getPlayer().resetPlayer();
				break;
			case "Exit":
				System.exit(0);
				break;
			case "Resume":
				currentPanelShown = gameName;
				world.setGameOver(false);
				world.getCurrentLevel().setPaused(false);
				break;
			case "Start":
				currentPanelShown = gameName;
				world.setGameOver(false);
				world.getCurrentLevel().setPaused(false);
				break;
			case "Controls":
				currentPanelShown = controlMenuName;
				world.setGameOver(true);
				break;
			case "Pause Menu":
				currentPanelShown = pauseMenuName;
				world.setGameOver(true);
				break;
			case "Select Level":
				currentPanelShown = levelMenuName;
				break;
			case "Tutorial":
				world.setLevel(0);
				world.setGameOver(false);
				currentPanelShown = gameName;
				break;
			case "Restart Level":
				world.getCurrentLevel().resetLevel();
				world.setGameOver(false);
				world.getCurrentLevel().setPaused(false);
				world.getPlayer().resetPlayer();
				currentPanelShown = gameName;
				break;
			case "Restart Game":
				world.resetLevels();
				world.setLevel(0);
				world.setGameOver(false);
				world.getPlayer().resetPlayer();
				world.getCurrentLevel().setPaused(false);
				currentPanelShown = gameName;
				break;
			case "Credits":
				currentPanelShown = creditMenuName;
				break;
			default:
				if(buttonName.indexOf("Level ") >= 0)
				{
					world.setLevel(Integer.valueOf(buttonName.substring(6, buttonName.length())));
					world.getCurrentLevel().setPaused(false);
					world.setGameOver(false);
					currentPanelShown = gameName;
				}
				else
				{
					currentPanelShown = null;
				}
				break;
			}
		}
		cards.show(getContentPane(), currentPanelShown);
		buttonPressed = null;
	}
	
	public void setButton(Button b)
	{
		buttonPressed = b;
	}
	
	public static void main(String[] args)
	{
		new JetWorldRunner();
	}
}
