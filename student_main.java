package online_learning_system_student;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;


public class student_main {

	public static void main(String[] args) {
		JFrame Application = new JFrame();
		Application.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		
		student_white_board p = new student_white_board();		
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;		
		constraints.gridx = 0;
		constraints.gridy = 0;       
        Application.add(p, constraints);
				
		chat_box_student chatbox = new chat_box_student("127.0.0.1");
		constraints.weightx = 1;
		constraints.weighty = 0.2;		
		constraints.gridx = 0;
		++constraints.gridy;       
        Application.add(chatbox, constraints);
				
		Application.setSize(1300,1300);
		Application.setVisible(true); 
		Application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		chatbox.connect();
		

	}

}
