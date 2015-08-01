//Travus Helmly
//wthelmly
/*********************************************************************
American Idol Voting Application

Following is a little web application that allows the user to 
vote for his or her choice.  A simple imitation of the American Idol
voting scheme.

**********************************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class AmericanIdolWebApp extends JApplet implements ActionListener							
{
	//Create the GUI
	JCheckBox contestant1 = new JCheckBox();
	JCheckBox contestant2 = new JCheckBox();
	JCheckBox contestant3 = new JCheckBox();
	JCheckBox contestant4 = new JCheckBox();
	JCheckBox contestant5 = new JCheckBox();
	
	JPanel 	  boxPanel	  = new JPanel();
	JCheckBox[] contestantBoxes = new JCheckBox[6];
	String [] contestantNames = new String [6];
	Color[] color = new Color[6];
	
	
	public void init()
	{
		System.out.println("Travus Helmly wthelmly");

		color[1] = Color.pink;
		color[2] = Color.MAGENTA;
		color[3] = Color.ORANGE;
		color[4] = Color.YELLOW;
		color[5] = Color.cyan;
		
		contestantNames[1] = getParameter("contestant1");
		contestantNames[2] = getParameter("contestant2");
		contestantNames[3] = getParameter("contestant3");
		contestantNames[4] = getParameter("contestant4");
		contestantNames[5] = getParameter("contestant5");

		contestantBoxes[1] = contestant1;
		contestantBoxes[2] = contestant2;
		contestantBoxes[3] = contestant3;
		contestantBoxes[4] = contestant4;
		contestantBoxes[5] = contestant5;
		
		for(int i = 1; i < contestantBoxes.length; i++)
			{
			boxPanel.add(contestantBoxes[i]);
			contestantBoxes[i].setOpaque(true);
			contestantBoxes[i].setText(contestantNames[i]);
			contestantBoxes[i].setBackground(color[i]);
			contestantBoxes[i].setFont(new Font("SansSerif",Font.ITALIC, 15));
			contestantBoxes[i].addActionListener(this);
			contestantBoxes[i].setBorder(BorderFactory.createLineBorder(Color.black));
			}

		add(boxPanel);
		

		
	}
	
	public void actionPerformed(ActionEvent ae)
	{
			//get the choice from the client
		JLabel confirmationMessage = new JLabel();

		 int i; 
		   for (i = 1; i < contestantBoxes.length; i++)
		       {
		       if (ae.getSource() == contestantBoxes[i]) break;
		       }
		   System.out.println("Box " + i + " was checked.");
		   
		   //handle the vote being sent to the server
		   
		   String portNumber = getParameter("port");
		   int port = Integer.parseInt(portNumber);
		   System.out.println("AmericanIdolServer port number is " + port);
		   String webServerAddress = getDocumentBase().getHost();
		   String idolServerAddress = "localhost";
		   if ((webServerAddress != null) && (webServerAddress.length() != 0))
		        idolServerAddress = webServerAddress;//running with a Web Server!
		   System.out.println("IdolServer address is " + idolServerAddress);
		   InetSocketAddress isa = new InetSocketAddress(idolServerAddress,port);
		   System.out.println("The InetSocketAddress object contains " + isa);
		   byte[] sendBuffer = (String.valueOf(i) + " " + contestantNames[i]).getBytes();
		   try {
			    DatagramPacket dgPacket = new DatagramPacket(sendBuffer,sendBuffer.length,isa);
			    DatagramSocket dgSocket = new DatagramSocket();
			    dgSocket.send(dgPacket);
			    boxPanel.removeAll();
				boxPanel.add(confirmationMessage);
				boxPanel.repaint();
				boxPanel.validate();
				boxPanel.setBackground(randomColor());
				confirmationMessage.setFont(new Font("sansserif",Font.BOLD, 32));
			    confirmationMessage.setText("Your vote for " 
			                              + contestantNames[i] 
			                              + " has been submitted!");
			    
			    }
			catch(IOException ioe)
			    {
				confirmationMessage.setText("Error sending your vote. " 
                        + "Sorry! Please try again.");  
			    }

	}
	
	//Poorly placed randomColor generator
	public Color randomColor()
	{
		int r = (int)(Math.random()*256);
		int g = (int)(Math.random()*256);
		int b = (int)(Math.random()*256);
		return(new Color(r,g,b));
	}
}
