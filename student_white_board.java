package online_learning_system_student;
import java.awt.GridLayout;

import javax.swing.*;
public class student_white_board extends JPanel{
	public final JButton hand_button;
	public student_white_board()
	{
		hand_button = new JButton("Rise Hand");

		add(hand_button);		
	}

}
