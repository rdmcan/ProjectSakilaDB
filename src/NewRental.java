import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.TableModel;

/**
 * Program Name: NewRental.java
 * Purpose: To create a new form that will allow an user to insert into the rental table a new rental transaction
 * 					with the information provided by user.
* Coder: 		Henrique dos Santos
 * 					Ruben Dario  Mejia Cardona 
 * 					David Jimenez
 * 					Ibrahim El Hage
 * Date: 		Aug. 3, 2020
 */

public class NewRental extends JPanel
{
	
	
	//global variables that will be used through methods and listeners
	public JLabel cFirst = new JLabel("");
  JComboBox staff_idBox = StaffComboBox();
  JComboBox customer_Box = CustomerComboBox();
	public JLabel cLast = new JLabel("");
	public JTextField txtInventory = new JTextField("",20);
	public boolean isChecked = false;
	
	public NewRental()
	{
		super();

		//labels 
		JLabel c = new JLabel("Customer Name:");
		JLabel s = new JLabel("Staff Id:");
		JLabel i = new JLabel("Inventory Id:");
		 
		this.setLayout(new GridBagLayout() );
		
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.anchor = GridBagConstraints.NORTH;
    constraints.insets = new Insets(0, 0, 30, 20);    
    //buttons
    JButton btnExecute = new JButton("Check inventory id");
    JButton btnTransaction = new JButton("Create new rental transaction");


    Dimension dim = new Dimension(200,30);
    staff_idBox.setPreferredSize(dim);
    

   
    constraints.anchor = GridBagConstraints.CENTER;
    // add components to the panel
    constraints.gridx = 0;
    constraints.gridy = 0;     
    this.add(c, constraints);

    constraints.gridx = 1;
    
    //add the customer Jbox
    this.add(customer_Box, constraints);
    
    constraints.gridx = 0;
    constraints.gridy = 1;

    
    //add listeners to the buttons 
		btnListeners get = new btnListeners();
		btnExecute.addActionListener(get);
		btnTransaction.addActionListener(get);
		
    constraints.gridy = 2;
    //add each element to the frame with its specific coordinates
    this.add(s, constraints);

    constraints.gridx = 1;
    this.add(staff_idBox,constraints);
    
    constraints.gridy = 3;
    constraints.gridx = 0;
    this.add(i, constraints);
    
    constraints.gridx = 1;
    this.add(txtInventory,constraints);
    
    constraints.gridy = 4;
    this.add(btnExecute, constraints);
    
    constraints.gridx = 0; 
    this.add(btnTransaction, constraints);
    
		
	}

	//A public method called CustomerComboBox that returns a JComboBox object that has the purpose of creating a 
	//JComboBox that will hold all customers available with their full name displayed.
	public JComboBox  CustomerComboBox()
	{
		//Create an arraylist that will hold the select statement information 
		ArrayList<String> arr = new ArrayList<String>();

		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRslt = null;
		PreparedStatement myPrepStmt = null;
		
		try
		{
			
		  myConn = DriverManager.getConnection(
			        "jdbc:mySql://localhost:3306/sakila?useSSL=false&allowPublicKeyRetrieval=true","root","password" );
			
			myStmt = myConn.createStatement();

			myPrepStmt = myConn.prepareStatement("SELECT CONCAT(first_name ,\" \",last_name ) AS name FROM customer ORDER BY name");

			myRslt = myPrepStmt.executeQuery();
			


			
		
			while(myRslt.next())
			{
				//Column index start at 1
				int count = 1;

				arr.add(myRslt.getString(count));//add each result until it hits the end to the array list
				++count;

			}//end while			
					

			
			//close connection
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
		//give the information of the array to a temporary JComboBox object that will be returned in order to create a 
		//JComboBox with the information of customers
		JComboBox tempCombo = new JComboBox(arr.toArray());
		return tempCombo;
	
	}
	//A public method called StaffComboBox that returns a JComboBox object that has the purpose of creating a 
	//JComboBox that will hold all Staff ids available in the database.
		public JComboBox  StaffComboBox()
		{
			//ArrayList to hold information
			ArrayList<String> arr = new ArrayList<String>();

			
			Connection myConn = null;
			Statement myStmt = null;
			ResultSet myRslt = null;
			PreparedStatement myPrepStmt = null;
			
			try
			{
				
			  myConn = DriverManager.getConnection(
				        "jdbc:mySql://localhost:3306/sakila?useSSL=false&allowPublicKeyRetrieval=true","root","password" );
				
				myStmt = myConn.createStatement();

				myPrepStmt = myConn.prepareStatement("SELECT staff_id FROM staff");

				
				myRslt = myPrepStmt.executeQuery();
				

			
				while(myRslt.next())
				{
					int count = 1;

					arr.add(myRslt.getString(count));//put content inside arraylist
					++count;

				}//end while			
						
				

				

				//close connection
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
			//return the tempCombo that holds the information of the staff_ids to create a comboBox with that information when called
			JComboBox tempCombo = new JComboBox(arr.toArray());
			return tempCombo;
		}

		//A public method called getRentalDuration that returns a string that holds the rental duration of a film, 
		//it gets this by accessing the inventory table through the inventory_id that the user introduced then 
		//finds the film_id that has that inventory_id on inventory table
		public String getRentalDuration()
		{

			
			String tempResult = "";//variable that will hold the rental duration
			Connection myConn = null;
			Statement myStmt = null;
			ResultSet myRslt = null;
			PreparedStatement myPrepStmt = null;
			
			try
			{
				
			  myConn = DriverManager.getConnection(
				        "jdbc:mySql://localhost:3306/sakila?useSSL=false&allowPublicKeyRetrieval=true","root","password" );
				
				myStmt = myConn.createStatement();
			

				myPrepStmt = myConn.prepareStatement("SELECT f.rental_duration FROM film f INNER JOIN INVENTORY i ON i.film_id = f.film_id " + 
						"INNER JOIN rental r ON r.inventory_id = i.inventory_id WHERE r.inventory_id = ?");
				//assign the JTextField inputs to the placeholders
				myPrepStmt.setString(1, txtInventory.getText());
				//myPrepStmt.setString(2, txtRating.getText());
				
				myRslt = myPrepStmt.executeQuery();
				

				
		
				while(myRslt.next()) {
				tempResult = myRslt.getString(1);//put inside the variable that will be returned the value obatined through the query
				}				


				
				//close connection
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
			
			//if everything went well return the result to get the rental duration of that film
			return tempResult;
		}
		
		
		
		
		//A public method called getCustId that returns a String value holding the customer_id of the customer that was
		//introduced in the customer_Box, it does this by comparing the selection in the JComboBox to the concat of the 
		//firs and last name of the customer table.
		public String getCustId()
		{
			String tempResult = "";//temporary variable that will hold the result and return it 
			Connection myConn = null;
			Statement myStmt = null;
			ResultSet myRslt = null;
			PreparedStatement myPrepStmt = null;
			
			try
			{
				
			  myConn = DriverManager.getConnection(
				        "jdbc:mySql://localhost:3306/sakila?useSSL=false&allowPublicKeyRetrieval=true","root","password" );
				
				myStmt = myConn.createStatement();
			
				myPrepStmt = myConn.prepareStatement("SELECT customer_id FROM customer " + 
						"WHERE CONCAT(first_name, \" \", last_name)  = ?");
				//assign the JTextField inputs to the placeholders
				String value = (String)customer_Box.getSelectedItem();
				myPrepStmt.setString(1,value );

				
				myRslt = myPrepStmt.executeQuery();
				

				
				
		
				while(myRslt.next()) {
				tempResult = myRslt.getString(1);//assing query result to the temporary variable
				}


				//close connection
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
			
			
			
			//return the temporary string that holds customer id 
			return tempResult;
		}
	//Listeners for the btns 
	private class btnListeners implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			//User pressed to check the inventory id was valid or not
			if(e.getActionCommand().equals("Check inventory id"))
			{
				//initalize connections
				Connection myConn = null;
				Statement myStmt = null;
				ResultSet myRslt = null;
				PreparedStatement myPrepStmt = null;
				
				try
				{
					
				  myConn = DriverManager.getConnection(
					        "jdbc:mySql://localhost:3306/sakila?useSSL=false&allowPublicKeyRetrieval=true","root","password" );
					
					myStmt = myConn.createStatement();
				
					
					myPrepStmt = myConn.prepareStatement("SELECT DISTINCT f.title FROM film f INNER JOIN INVENTORY i ON i.film_id = f.film_id \r\n" + 
							"INNER JOIN rental r ON r.inventory_id = i.inventory_id WHERE r.inventory_id = ?");
					//assign the JTextField inputs to the placeholders
					
					myPrepStmt.setString(1, txtInventory.getText());
					
				
					
					myRslt = myPrepStmt.executeQuery();
					
					
					if(!myRslt.next())
					{
						JOptionPane.showMessageDialog(null, "Inventory Id not valid!");
						cFirst.setText("");
						return;
					}
					else
					{
						//give user feedback on which film is that inventory_id
						JOptionPane.showMessageDialog(null, "Film for that inventory id is: " + myRslt.getString(1) ,"Success",JOptionPane.WARNING_MESSAGE);    
						isChecked = true;//user checked so is a valid inventory id
					}

					//close connection
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
			//Users wants to insert into rental 
			if(e.getActionCommand().equals("Create new rental transaction"))
			{
				//if user checked the inventory_id 
				if(!isChecked)
				{
					JOptionPane.showMessageDialog(null,"Please validate your inventory id!(Check inventory id button)");//Give user feedback to validate inventory_id by pressing button
					return;
				}
				//initalize connections
				Connection myConn = null;
				Statement myStmt = null;
				ResultSet myRslt = null;
				PreparedStatement myPrepStmt = null;
				
				try
				{
					
				  myConn = DriverManager.getConnection(
					        "jdbc:mySql://localhost:3306/sakila?useSSL=false&allowPublicKeyRetrieval=true","root","password" );
					
					myStmt = myConn.createStatement();
				
					
					myPrepStmt = myConn.prepareStatement("INSERT INTO RENTAL (rental_date, inventory_id, customer_id,return_date,staff_id,last_update) " + 
							"VALUES (NOW(), ?, ?, DATE_ADD(NOW(), INTERVAL ? DAY),?,NOW());");
					
					//assign the JTextField inputs to the placeholders	
					myPrepStmt.setString(1, txtInventory.getText());
					String custId = getCustId();

					myPrepStmt.setString(2, custId);//get the customerid
					
					myPrepStmt.setString(3, getRentalDuration());//get rental duration 
					
					
					myPrepStmt.setString(4, (String)staff_idBox.getSelectedItem().toString());//get the staffidbox selected item and pass it to the sql statement
		
					int temp = -1;
					
					
					temp = myPrepStmt.executeUpdate();
					
							
							
							if(temp == 1)//if the query returned a one it means it affected oen row so it worked.
							{
								JOptionPane.showMessageDialog(null, "Successfully inserted " + temp + " rental transaction!","Alert",JOptionPane.WARNING_MESSAGE);    
								txtInventory.setText("");
								staff_idBox.setSelectedIndex(0);
								customer_Box.setSelectedIndex(0);
								isChecked = false;
							
							}
							else//something went wrong while trying to add and nothing was inserted to the db
							{
								JOptionPane.showMessageDialog(null, "Oops! Something Went Wrong...");
							}
	
	
					//close connection
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
			
			
		}//end actionPerformed()
		


	}

}
