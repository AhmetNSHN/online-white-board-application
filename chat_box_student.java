package online_learning_system_student;


import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;

import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class chat_box_student extends JPanel{
	
	JTextArea message_area;
	JTextField message_box;
	JButton send;
	String IP;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Socket connection;
	public chat_box_student(String ip)
	{
		ip = IP;
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		message_area = new JTextArea();
		message_area.setEditable(false);
		message_area.setLineWrap(true);
        message_area.setWrapStyleWord(true);
		add(new JScrollPane(message_area));
		JScrollPane scrollpane = new JScrollPane(message_area);
		constraints.gridwidth = 2;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;		
		constraints.gridx = 0;
		constraints.gridy = 0;       
        add(scrollpane, constraints);
					
		message_box = new JTextField();
		constraints.gridwidth = 1;
		constraints.weighty = 0.05;		
		constraints.gridx = 0;
		constraints.gridy = 1;       
        add(message_box, constraints);
		
		send = new JButton("Send");
		setButtonEnabled(false);
		constraints.weightx = 0.05;	
		constraints.gridx = 1;       
        add(send, constraints);
		send.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {
						send(message_box.getText());
						message_box.setText(" ");						
					}			
				});
		
	}
	
	
	
	public void connect() //main function of chat box
	{
		try
		{
			try
			{
				connection = new Socket(InetAddress.getByName(IP),5000);
				add_message("SYSTEM: you are connected to teacher");
				setButtonEnabled(true);
				stream();
				processconnection();
			}
			catch(EOFException e)
			{
				add_message("SYSTEM: Teacher left the season");
				setButtonEnabled(false);
			}
			catch(IOException e)
			{
				add_message("SYSTEM: Teacher is not connected yet!");
			}
			finally
			{
				oos.close();
				ois.close();
				connection.close();
			}
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	
	private void stream() throws IOException
	{
		oos = new ObjectOutputStream(connection.getOutputStream());
		oos.flush();
		ois = new ObjectInputStream(connection.getInputStream());
		
	}
	
	
	private void processconnection() throws IOException
	{
		setButtonEnabled(true);
		String message = " ";
		while(true)
		{
			try{
				message = (String) ois.readObject();
				add_message(message);
			}
			catch(ClassNotFoundException e)
			{
				add_message("SYSTEM: Please check your internet connection");
			}
			
		}
	}
	
	
	private void send(String message)
	{
		try
		{
			oos.writeObject("Student"+ message);
			oos.flush();
			add_message("Student: "+ message);
		}
		catch(IOException e)
		{
			add_message("please check your internet connection...");
		}
	}

	
	private void add_message(final String message) {
		SwingUtilities.invokeLater(new Runnable()
				{

					@Override
					public void run() {
						message_area.append("\n"+ message);
						
					}
			
				});
				
	}
	
	public void setButtonEnabled(final boolean state)
	{
		SwingUtilities.invokeLater(new Runnable()
				{

					@Override
					public void run() {
						send.setEnabled(state);
						
					}
			
				});
	}

}
