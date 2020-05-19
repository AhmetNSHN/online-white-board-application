package online_learing_system;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

import online_learing_system.menu.status;

public class white_board extends JPanel {
	private final ArrayList<Point> line_coordinates = new ArrayList<>();
	private final ArrayList<Point> circle_coordinates = new ArrayList<>();
	private final ArrayList<Point> rectangle_coordinates = new ArrayList<>();
	
	
		
	public white_board()
	{
		menu g = new menu();
		addMouseMotionListener(
				new MouseMotionAdapter()
				{
					@Override
					public void mouseDragged(MouseEvent e)
					{
						if(g.getdraw_status() == status.line)					
						{
							line_coordinates.add(e.getPoint());
							repaint();
						}
						if(g.getdraw_status() == status.circle)
							
						{
							circle_coordinates.add(e.getPoint());
							repaint();
						}
						if(g.getdraw_status() == status.rectangle)
						{
							rectangle_coordinates.add(e.getPoint());
							repaint();
						}
							
					}
		
				});
		
	}
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		for(Point point : line_coordinates)
		{
			g.drawLine(point.x, point.y, 100, 100);
		}
		
		for(Point point : circle_coordinates)
		{
			g.drawOval(point.x, point.y, 100, 100);
		}
		for(Point point : rectangle_coordinates)
		{
			g.drawRect(point.x, point.y, 100, 100);
		}
	}

}
