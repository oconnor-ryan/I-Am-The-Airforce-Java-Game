package root.menus;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

import root.JetWorldGame.JetWorldRunner;

public class Menu extends JPanel
{
	private static final long serialVersionUID = 1L;
	private MouseHandler mouse;
	private ArrayList<Button> buttons;
	private String name;
	private JetWorldRunner runner;
	
	public Menu(String s, JetWorldRunner a)
	{
		setBackground(Color.BLACK);
		runner = a;
		name = s;
		buttons = new ArrayList<Button>();
		mouse = new MouseHandler(this, a);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	
	public JetWorldRunner getRunner()
	{
		return runner;
	}
	
	public MouseHandler getMouse()
	{
		return mouse;
	}
	
	public String getName()
	{
		return name;
	}
	
	public ArrayList<Button> getButtons()
	{
		return buttons;
	}
	public int[] getCenteredString(Graphics g, Font f, String text, int w, int h)
	{
		FontMetrics m = g.getFontMetrics(f);
		g.setFont(f);
		int x = (w - m.stringWidth(text))/2;
		int y = ((h - m.getHeight())/2) + m.getAscent();
		return new int[] {x,y};
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		for(Button b : buttons)
		{
			b.draw(g);
		}
		
		g.setColor(Color.WHITE);
		
		int w = runner.getWidth();
		int h = runner.getHeight()/4;
		int[] a = getCenteredString(g,new Font("serif",JetWorldRunner.convertToRatioX(100), JetWorldRunner.convertToRatioY(100)),
				name, w, h);
		g.drawString(name, a[0], a[1]);
	}
}
