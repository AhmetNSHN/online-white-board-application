package online_learing_system;

import java.awt.*;

import javax.swing.*;
//import java.io.BufferedInputStream;
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.rmi.UnknownHostException;

public class TeacherServer {

    public static void main(String[] args) throws IOException{
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
        
        white_board p = new white_board();
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        Application.add(p, constraints);
        //p.connect("localHost",5001);
                        
        constraints.weightx = 1;
        constraints.weighty = 0.2;
        constraints.gridx = 0;
        ++constraints.gridy;
        Application.add(chatbox, constraints);
        
        Application.setSize(900,1000);
        Application.setVisible(true);
        Application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

	

}

//constructor with port
//private Socket socket = null;
//private DataInputStream input = null;
//private DataOutputStream out = null;
//private DataInputStream in = null;

//constructor to put ip address and port
//public TeacherServer("127.0.0.1", 5001) {

//}