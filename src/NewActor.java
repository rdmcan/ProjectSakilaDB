import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

/**
 * Program Name: NewActor.java
 * Purpose: To add new actor to the database actor table
 * Coder: 	Henrique dos Santos
 * 					Ruben Dario  Mejia Cardona 
 * 					David Jimenez
 * 					Ibrahim El Hage
 * Date: Aug. 7, 2020
 */

public class NewActor extends JPanel
{
	//declaring some swing objects to use later
	JLabel lblFirst,lblLast;
	JTextField txtFirstName, txtLastName;
	JButton btnaddAct;
	
	
	public NewActor()
	{
		super();
		  lblFirst = new JLabel("First Name  ");
		  lblLast = new JLabel("Last Name  ");
		  
		  txtFirstName = new JTextField("",15);
		  txtLastName = new JTextField("",15);
		  
		  btnaddAct = new JButton("Add actor/actress");
		  

		
	// create a new panel with GridBagLayout manager    
    this.setLayout(new GridBagLayout() );
		
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.anchor = GridBagConstraints.NORTH;
    constraints.insets = new Insets(0, 0, 30, 0);    
    
   
    
    // add components to the panel
    //adding components with different x and y values
    constraints.gridx = 0;
    constraints.gridy = 1;     
    this.add(lblFirst, constraints);
    constraints.gridx = 1;
    this.add(txtFirstName, constraints);
     
    constraints.gridx = 0;
    constraints.gridy = 2;     
    this.add(lblLast, constraints);     
    constraints.gridx = 1;
    this.add(txtLastName, constraints); 
    
    constraints.gridx = 0;
    constraints.gridy = 11;
    constraints.gridwidth = 2;
    constraints.anchor = GridBagConstraints.CENTER;
    this.add(btnaddAct, constraints);
    
 // set border for the panel
    this.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Add a New Actors and Actresses Here "));	 
    
  //add listener
  		AddActors add = new AddActors();
  		btnaddAct.addActionListener(add);
		
	}
	private class AddActors implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(e.getActionCommand().equals("Add actor/actress"))
			{
				//Creating the jdbc objects to use here in the listener to insert into database
				Connection myConn = null;
				Statement myStmt = null;
				ResultSet myRslt = null;
				PreparedStatement myPrepStmt = null;
				
				try
				{
					myConn = DriverManager.getConnection(
			        "jdbc:mysql://localhost:3306/sakila?useSSL=false&allowPublicKeyRetrieval=true","root","password" );
					
			myStmt = myConn.createStatement();
			
			myPrepStmt = myConn.prepareStatement("INSERT INTO actor " +
                                           "(first_name, last_name) " +
                                            "VALUES (?,?) ");
			
			myPrepStmt.setString(1, txtFirstName.getText());
			myPrepStmt.setString(2, txtLastName.getText());
			
			
			myPrepStmt.executeUpdate();
			//insert already executed
		
				}
				catch(SQLException e1)
				{
					System.out.println("SQL Exeption, message is: " + e1.getMessage());
				}
				catch(Exception ex)
				{
					System.out.println("Some other Exception, message is: " + ex.getMessage());
				}
				
				finally
				{
					
					//standard clean up code to make sure connection to DB is closed
					//NOTE: we CONFESS the SQLExceptions in a throws clause on the 
					// main method header.
					
					try
					{
						if(myRslt != null)
							myRslt.close();
						if(myStmt != null)
							myStmt.close();
						if(myConn != null)
							myConn.close();
					}
					catch(SQLException ex)
					{
						System.out.println("SQL Exception INSIDE finally block: " + ex.getMessage());
						ex.printStackTrace();
					}
				}//end finally
			}
			
		}//end actionPerformed
		
	}//end inner class
	
	

}
