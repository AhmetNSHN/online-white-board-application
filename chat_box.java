package online_learing_system;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class chat_box extends JPanel{
	BorderLayout border_layout;
	GridLayout grid_layout;
	JTextArea message_area;
	JTextField message_box;
	JButton send;
	
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private ServerSocket socket;
	private Socket connection;
	public chat_box()
	{
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
		send.setEnabled(true);
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
	
	
	
		
	
	
	public void connect()
	{
		try
		{
			socket = new ServerSocket(5000,100);			
			while(true)
			{
				try
				{
					connection = socket.accept();
					add_message("SYSTEM: student connected");
					stream();
					processconnection();
				}
				catch(EOFException e)
				{
					add_message("SYSTEM: No student left");
				}
				finally
				{
					oos.close();
					ois.close();
					connection.close();
				}
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
				e.printStackTrace();
			}
			
		}
	}
	
	
	private void send(String message)
	{
		try
		{
			oos.writeObject("Teacher:"+ message);
			oos.flush();
			add_message("Teacher:"+ message);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	
	private void add_message(final String message) {
		SwingUtilities.invokeLater(new Runnable()
				{

					@Override
					public void run() {
						message_area.append("\n" + message);
						
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
