package root.menus;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import root.JetWorldGame.JetWorldRunner;

public class MouseHandler implements MouseListener, MouseMotionListener
{
	private Menu menu;
	private JetWorldRunner listen;
	
	public MouseHandler(Menu m, JetWorldRunner a)
	{
		menu = m;
		listen = a;
	}
	
	@Override
	public void mouseMoved(MouseEvent e) 
	{
		for(Button b : menu.getButtons())
		{
			if(b.mouseIsInside(e.getX(),e.getY()))
			{
				b.setMouseOverButton(true);
			}
			else
			{
				b.setMouseOverButton(false);
			}
		}
		menu.repaint();
	}
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
		for(Button b : menu.getButtons())
		{
			if(b.mouseIsInside(e.getX(),e.getY()))
			{
				b.setPressed(true);
				listen.setButton(b);
				listen.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
			}
			else
			{
				b.setPressed(false);
			}
		}
		menu.repaint();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e){}
	public void mouseDragged(MouseEvent e) {}

	

}
