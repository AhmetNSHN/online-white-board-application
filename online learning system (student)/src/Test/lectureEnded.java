package Test;

import java.awt.BorderLayout;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import online_learning_system_student.*;

public class lectureEnded {
    public lectureEnded() {
        JFrame L= new JFrame();
        L.setSize(250,300);
        L.setVisible(true);
        L.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout b = new BorderLayout();
        L.setLayout(b);

        JPanel p =new JPanel();

        JLabel E = new JLabel("CLass ended");
        p.add(E);
        L.add(p,BorderLayout.CENTER);
    }
}
