package online_learing_system;
import java.awt.BorderLayout;

import javax.swing.JFrame;
public class main {
	public static void main(String[] args)
	{
		JFrame Application = new JFrame();
		Application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu menu_bar = new menu();
		white_board whiteboard = new white_board();
		Application.add(menu_bar,BorderLayout.NORTH);
		Application.add(whiteboard);
		Application.setSize(900,1300);
		Application.setVisible(true);
	}

}
