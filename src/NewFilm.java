import java.awt.Dimension;
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
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Program Name: NewFilm.java
 * Purpose: To add new rental transaction
 * Coder: 	Henrique dos Santos
 * 					Ruben Dario  Mejia Cardona 
 * 					David Jimenez
 * 					Ibrahim El Hage
 * Date: 		Aug. 8, 2020
 */

public class NewFilm extends JPanel
{	
	//Declaring the textfields and labels to be used
	JLabel lblTitle,lblDescription,lblreleaseYear,lblrentalDuration,lblrentalRate,lbllength,lblreplacementCost,lblRating,lblFeatures,lblActors,lblActorids,
	lblInstructions1,lblInstructions2;
JTextField  txtTitle, txtDescription,txtreleaseYear,txtrentalDuration,txtrentalRate,txtlength,txtreplacementCost,txtRating, txtFeatures,
            txtActors,txtActorIds;
JButton btnaddFilm , btnAddActorInFilm,btnAddList;
int LastID;
ArrayList<Integer> SelectedIndexes = new ArrayList<Integer>();
int countOfArrayIndexes = 0;

String [] actIds;
  //declaring the comboboxes
  private JComboBox durationBox;
	private JComboBox rateBox;
	private JComboBox ratingBox;
	private JComboBox featuresBox;
	private JComboBox actorsBox;
	private JComboBox idBox;
	
	
public NewFilm()
{
	super();
	//initializing the labels
	lblTitle = new JLabel("Film Title");
	lblDescription = new JLabel("Film Description ");
	lblreleaseYear = new JLabel("Release Year");
	lblrentalDuration = new JLabel("Rental Duration");
	lblrentalRate = new JLabel("Rental Rate");
	lbllength = new JLabel("Length");
	lblreplacementCost = new JLabel("Cost");
	lblRating = new JLabel("Rating");
	lblFeatures = new JLabel("Features");
	lblActors = new JLabel("Actors");
	lblInstructions1 = new JLabel("Note: 1.Add Film  2.Add Actors in Film");
	lblInstructions2 = new JLabel(" 3.Add List Of actors to database. Must follow this steps");
	
	
	//initializing the textfields
	txtTitle = new JTextField("",15);
	txtDescription = new JTextField("",15);
	txtreleaseYear = new JTextField("",15);
	txtrentalDuration = new JTextField("",15);
	txtrentalRate = new JTextField("",15);
	txtlength = new JTextField("",15);
	txtreplacementCost = new JTextField("",15);
	txtRating = new JTextField("",15);
	txtFeatures = new JTextField("",15);
	txtActors = new JTextField("",15);
	
	//fullActNames[0] ="";
	Dimension dim = new Dimension(156,25);
	//passing the values from arrays to comboBoxes
	durationBox = new JComboBox(durationArray);
	
	durationBox.setPreferredSize(dim);
	
	rateBox = new JComboBox(ratesArray);
	
	rateBox.setPreferredSize(dim);
	
	ratingBox = new JComboBox(ratingArray);
	ratingBox.setPreferredSize(dim);
	
	featuresBox = new JComboBox(featuresArray);
	featuresBox.setPreferredSize(dim);
	
	//String [] fullNames = new String[300];
	actorsBox = new JComboBox(fillActorIdAndNames());
	actorsBox.setPreferredSize(dim);
	
	 
	  btnaddFilm = new JButton("Add Film");
	  btnAddActorInFilm = new JButton("Add Actor to List");
	  btnAddList = new JButton("Add List of Actors To Database");
	  
  
// create a new panel with GridBagLayout manager    
  this.setLayout(new GridBagLayout() );
	
  GridBagConstraints constraints = new GridBagConstraints();
  constraints.anchor = GridBagConstraints.WEST;
  constraints.insets = new Insets(0, 0, 30, 0);    
  
 
  
  // add components to the panel
  // adding components differently using x and y values in the panel
  constraints.gridx = 0;
  constraints.gridy = 0;     
  this.add(lblInstructions1, constraints);
  constraints.gridx = 1;
  constraints.gridy = 0;     
  this.add(lblInstructions2, constraints);
    
  constraints.gridx = 0;
  constraints.gridy = 1;     
  this.add(lblTitle, constraints);
  constraints.gridx = 1;
  this.add(txtTitle, constraints);
   
  constraints.gridx = 0;
  constraints.gridy = 2;     
  this.add(lblDescription, constraints);     
  constraints.gridx = 1;
  this.add(txtDescription, constraints); 
  
  constraints.gridx = 0;
  constraints.gridy = 3;     
  this.add(lblreleaseYear, constraints);     
  constraints.gridx = 1;
  this.add(txtreleaseYear, constraints); 
  
  constraints.gridx = 0;
  constraints.gridy = 4;     
  this.add(lblrentalDuration, constraints);     
  constraints.gridx = 1;
  this.add(durationBox, constraints); 
  
  constraints.gridx = 0;
  constraints.gridy = 5;     
  this.add(lblrentalRate, constraints);     
  constraints.gridx = 1;
  this.add(rateBox, constraints); 
  
  constraints.gridx = 0;
  constraints.gridy = 6;     
  this.add(lbllength, constraints);     
  constraints.gridx = 1;
  this.add(txtlength, constraints); 
  
  constraints.gridx = 0;
  constraints.gridy = 7;     
  this.add(lblreplacementCost, constraints);     
  constraints.gridx = 1;
  this.add(txtreplacementCost, constraints); 
  
  constraints.gridx = 0;
  constraints.gridy = 8;     
  this.add(lblRating, constraints);     
  constraints.gridx = 1;
  this.add(ratingBox, constraints); 
  
  constraints.gridx = 0;
  constraints.gridy = 9;     
  this.add(lblFeatures, constraints);     
  constraints.gridx = 1;
  this.add(featuresBox, constraints); 
  
  constraints.gridx = 0;
  constraints.gridy = 10;     
  this.add(lblActors, constraints);     
  constraints.gridx = 1;
  this.add(actorsBox, constraints); 
    
  constraints.gridx = 0;
  constraints.gridy = 11;
  constraints.gridwidth = 1;
  //constraints.anchor = GridBagConstraints.CENTER;
  constraints.gridx = 0;
  this.add(btnaddFilm, constraints);
   
  constraints.gridx = 1;
  constraints.gridy = 11;
  constraints.gridwidth = 2;
  constraints.anchor = GridBagConstraints.WEST;
  this.add(btnAddActorInFilm, constraints);
  
  constraints.gridx = 1;
  constraints.gridy = 12;
  constraints.gridwidth = 1;
  constraints.anchor = GridBagConstraints.WEST;
  this.add(btnAddList, constraints);
  
  
// set border for the panel
  this.setBorder(BorderFactory.createTitledBorder(
          BorderFactory.createEtchedBorder(), "Add a new film Here"));	 
  
//add listener
		AddFilm add = new AddFilm();
		btnaddFilm.addActionListener(add);
		//adding other listener for adding actors in the film
		AddActorToFilm actorAdd = new AddActorToFilm(); 
		btnAddActorInFilm.addActionListener(actorAdd);
		//add list of actors to database
		AddTheList addList = new AddTheList(); 
		btnAddList.addActionListener(addList);
	
}
public void insertIntoInventory()
{

	
	Connection myConn = null;
	Statement myStmt = null;
	ResultSet myRslt = null;
	PreparedStatement myPrepStmt = null;
	
	try
	{
		
	  myConn = DriverManager.getConnection(
		        "jdbc:mySql://localhost:3306/sakila?useSSL=false&allowPublicKeyRetrieval=true","root","password" );
		
		myStmt = myConn.createStatement();
	 //SELECT MAX(film_id) FROM film;
		myRslt = myStmt.executeQuery("SELECT MAX(film_id) FROM sakila.film");
		//System.out.print(myRslt.toString());
	  myRslt.next();
			LastID = myRslt.getInt(1);
		
	 //simple sql statement to insert into inventory and using 1 as language value
		myPrepStmt = myConn.prepareStatement("INSERT INTO inventory " +
		                                     "(film_id,store_id)" +
				                                  "VALUES (?,1)" );
		//assign the JTextField inputs to the placeholders
		myPrepStmt.setInt(1, LastID);
		//after adding the placeholder we execute statement
		
	  myPrepStmt.executeUpdate();
		
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
//===============

//Insert into the film_actor table

public void insertIntoFilmActor()
{
	Connection myConn = null;
	Statement myStmt = null;
	ResultSet myRslt = null;
	PreparedStatement myPrepStmt = null;
	
	try
	{
		
	  myConn = DriverManager.getConnection(
		        "jdbc:mySql://localhost:3306/sakila?useSSL=false&allowPublicKeyRetrieval=true","root","password" );
		
		myStmt = myConn.createStatement();
	 //SELECT MAX(film_id) FROM film;
		//taking the last id in the film table 
		myRslt = myStmt.executeQuery("SELECT MAX(film_id) FROM sakila.film");
		 //passing the result to variable by using 1 meaning only one value
	    myRslt.next();
			LastID = myRslt.getInt(1);
			int aCount;
		  for(int i = 0; i < countOfArrayIndexes; i++ )
		  {
		  	//using the statement 
		  	myPrepStmt = myConn.prepareStatement("INSERT INTO film_actor " +
	          "(actor_id,film_id)" +
	           "VALUES (?,?)" );
		  	//passing the id of the actor stored in SelectedIndexes to add actorId
		  	myPrepStmt.setInt(1,SelectedIndexes.get(i));
		  	myPrepStmt.setInt(2,LastID);
		  	myPrepStmt.executeUpdate();
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
//===============


public static String[] fillActorIdAndNames()
{

	Connection myConn = null;
	Statement myStmt = null;
	ResultSet myRslt = null;
	ResultSet myRslt2 = null;
	PreparedStatement myPrepStmt = null;
	
	
	String [] namesArray = new String[300];
	try
	{
		
	  myConn = DriverManager.getConnection(
		        "jdbc:mySql://localhost:3306/sakila?useSSL=false&allowPublicKeyRetrieval=true","root","password" );
		
		myStmt = myConn.createStatement();
	 //SELECT MAX(film_id) FROM film;
		myRslt = myStmt.executeQuery("SELECT actor_id,first_name,last_name FROM sakila.actor");
		//myRslt2 = myStmt.executeQuery("SELECT first_name, last_name FROM sakila.actor;");
		//System.out.print(myRslt.toString());
	//String [] fullActNames;
	//	int actIds;
		
		
	int count = 0;
	while(myRslt.next())	
	{
		String firstname = myRslt.getString("first_name");
		String lastname = myRslt.getString("last_name");
		//int id = myRslt.getInt("actor_id");
		namesArray[count] = firstname + " " + lastname;
		//actIds[count] = id;	
		count++;
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
	return namesArray;
	
	
	//return namesArray;
}


private class AddActorToFilm implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("Add Actor to List"))
		{
			
			Connection myConn = null;
			Statement myStmt = null;
			ResultSet myRslt = null;
			PreparedStatement myPrepStmt = null;
			
			
			try
			{
				myConn = DriverManager.getConnection(
		        "jdbc:mysql://localhost:3306/sakila?useSSL=false&allowPublicKeyRetrieval=true","root","password" );
				
		 myStmt = myConn.createStatement();
		
		  //adding into arraylist
		  SelectedIndexes.add(actorsBox.getSelectedIndex() + 1);	
		  //global variabe that will increment in click of button
			countOfArrayIndexes++;
			
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


//This fill an array with the actor's id
private class AddTheList implements ActionListener
{
  //simple listener to add the list of actors
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("Add List of Actors To Database"))
		{
			insertIntoFilmActor();		
		}
		
	}//end actionPerformed
	
}//end inner class




//========
private class AddFilm implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("Add Film"))
		{
			
			Connection myConn = null;
			Statement myStmt = null;
			ResultSet myRslt = null;
			PreparedStatement myPrepStmt = null;
			
			try
			{
				myConn = DriverManager.getConnection(
		        "jdbc:mysql://localhost:3306/sakila?useSSL=false&allowPublicKeyRetrieval=true","root","password" );
				
		myStmt = myConn.createStatement();
		
		/*
		 * String insertString = "INSERT INTO actor " + "(first_name, last_name) " +
		 * "VALUES ('Brown','David','dbrown@fanshawec.ca') ";
		 */
		
		myPrepStmt = myConn.prepareStatement("INSERT INTO film " +
                                    "(title,description,release_year,language_id,rental_duration,rental_rate,length,replacement_cost,rating,special_features) " +
                                          "VALUES (?,?,?,?,?,?,?,?,?,?)");
		
		//int year = 0;
		//year = Integer.parseInt(txtreleaseYear.getText());
		
		  myPrepStmt.setString(1, txtTitle.getText());
		  myPrepStmt.setString(2,txtDescription.getText());
		  myPrepStmt.setString(3,txtreleaseYear.getText());
		  myPrepStmt.setInt(4,1);
		  myPrepStmt.setString(5, durationBox.getSelectedItem().toString());
		  myPrepStmt.setString(6, rateBox.getSelectedItem().toString());
		  myPrepStmt.setString(7, txtlength.getText());
		  myPrepStmt.setString(8, txtreplacementCost.getText());
		  myPrepStmt.setString(9, ratingBox.getSelectedItem().toString());
		  myPrepStmt.setString(10, featuresBox.getSelectedItem().toString());
		
		myPrepStmt.executeUpdate();
		
		
		//insertIntoFilmActor();
		insertIntoInventory();
		
		//call method in Dbutils and pass it to myrslt object
		
		
		////REVISION: reference the view object and call the updateTable() method to update its model.
		
		//view.updateTable(model);
		//view.setVisible(true);
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
	
	
	
	//Inventory method =-============

	
}//end inner class

//declaring arrays for combo boxes
String [] ratesArray = {"4.99","2.99","0.99"};
String [] durationArray = {"3","4","5","6","7"};
String [] ratingArray = {"G","PG","PG-13","R","NC-17"};
String [] featuresArray = {"Trailers","Commentaries","Deleted Scenes","Behind the Scenes"};

}
