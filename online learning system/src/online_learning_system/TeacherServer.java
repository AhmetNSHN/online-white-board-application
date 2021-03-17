package online_learning_system;

import java.awt.*;
import javax.swing.*;
import java.io.IOException;
import java.net.ConnectException;

public class TeacherServer {

    public static void main(String[] args) throws IOException {
        JFrame Application = new JFrame();

        Application.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;

        menu menu_bar = new menu();
        Application.setJMenuBar(menu_bar);

        chat_box chatbox = new chat_box();
        Thread t = new Thread() {
            public void run() {
                chatbox.connect();
            }
        };
        t.start();

        try {
            white_board p = new white_board();
            constraints.weightx = 1.0;
            constraints.weighty = 1.0;
            constraints.gridx = 0;
            constraints.gridy = 0;
            Application.add(p, constraints);
        }
        catch (ConnectException e)
        {
            System.out.println("Cannot connect!");
        }


        constraints.weightx = 1;
        constraints.weighty = 0.2;
        constraints.gridx = 0;
        ++constraints.gridy;
        Application.add(chatbox, constraints);

        Application.setSize(900, 1000);
        Application.setVisible(true);
        Application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}