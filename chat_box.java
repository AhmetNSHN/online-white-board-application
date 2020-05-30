package online_learing_system;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
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
	JButton Attendance;
	
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
		Attendance = new JButton("Attendance");
		constraints.weightx = 0.05;	
		constraints.gridx = 2;       
        add(Attendance, constraints);
		Attendance.addActionListener(new ActionListener()
				{
			String student_name;
			
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
						      File myObj = new File("ATTENDANCE.txt");
						      Scanner myReader = new Scanner(myObj);
						      while (myReader.hasNextLine()) {
						        student_name = myReader.nextLine();
						        System.out.println(student_name);
						      }
						      myReader.close();
						    } catch (FileNotFoundException event) {
						      System.out.println("An error occurred.");
						      event.printStackTrace();
						    }	
						JOptionPane.showMessageDialog(null, student_name,"ATTENDANCE",JOptionPane.INFORMATION_MESSAGE);
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
					send.setEnabled(true);
					stream();
					
					
					try
					{
						String message = " ";
						message = (String) ois.readObject();
						try {
				            File myObj = new File("ATTENDANCE.txt");
				            if (myObj.createNewFile()) {
				              System.out.println("File created: " + myObj.getName());
				            } else {
				              System.out.println("File already exists.");
				            }
				          } catch (IOException e) {
				            System.out.println("An error occurred.");
				            e.printStackTrace();
				          }
				        try {
				            FileWriter myWriter = new FileWriter("ATTENDANCE.txt");
				            myWriter.write(message);
				            myWriter.close();
				            
				          } catch (IOException e) {
				            System.out.println("Cannot write to file.");
				            e.printStackTrace();
				          }
					
					}
					catch(ClassNotFoundException e)
					{
						add_message("Can't take the attendance");
					}
					
					
					processconnection();
				}
				catch(EOFException e)
				{
					add_message("SYSTEM: No student left");
					setButtonEnabled(false);
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
//				if(message.substring(0,11).equals("ATTENDANCE:"))
//				{
//					System.out.print(message);
//					 
////			        String[] arrOfStr = message.split(":", 1);
//			
//			        try {
//			            File myObj = new File("ATTENDANCE.txt");
//			            if (myObj.createNewFile()) {
//			              System.out.println("File created: " + myObj.getName());
//			            } else {
//			              System.out.println("File already exists.");
//			            }
//			          } catch (IOException e) {
//			            System.out.println("An error occurred.");
//			            e.printStackTrace();
//			          }
//			        try {
//			            FileWriter myWriter = new FileWriter("ATTENDANCE.txt");
//			            myWriter.write(message);
//			            myWriter.close();
//			            
//			          } catch (IOException e) {
//			            System.out.println("Cannot write to file.");
//			            e.printStackTrace();
//			          }
//			        
//					
//				}
			    if(message.equals("NOTIFICATION"))
				{
					JOptionPane.showMessageDialog(null,"Student Raising Hand","NOTIFICATION",JOptionPane.WARNING_MESSAGE);
				}
				else
				{
				add_message(message);
				}
		
			}
			catch(ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			catch(StringIndexOutOfBoundsException e)
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
