package online_learing_system;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;


public class main {
	public static void main(String[] args)
	{
		JFrame Application = new JFrame();
		
		Application.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
				
		menu menu_bar = new menu();
		Application.setJMenuBar(menu_bar);
		
        white_board p = new white_board();		
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;		
		constraints.gridx = 0;
		constraints.gridy = 0;       
        Application.add(p, constraints);
				
		chat_box chatbox = new chat_box();
		constraints.weightx = 1;
		constraints.weighty = 0.2;		
		constraints.gridx = 0;
		++constraints.gridy;       
        Application.add(chatbox, constraints);
        
        Application.setSize(900,1000);
		Application.setVisible(true);
		Application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        chatbox.connect();
		
		
	}

}
