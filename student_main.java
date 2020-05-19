package online_learning_system_student;

import java.awt.BorderLayout;

import javax.swing.JFrame;


public class student_main {

	public static void main(String[] args) {
		JFrame Application = new JFrame();
		Application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		student_white_board p = new student_white_board();
		Application.add(p);
		Application.setSize(900,1300);
		Application.setVisible(true);

	}

}
