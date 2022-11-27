import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.TableModel;

/**
 * Program Name: ProjectSakilaDB.java
 * Purpose: GUI front end using multiple forms for the Sakila database
 * Coder: 	Henrique dos Santos
 * 					Ruben Dario  Mejia Cardona 
 * 					David Jimenez
 * 					Ibrahim El Hage
 * Date: August 3, 2020
 */

public class ProjectSakilaDB extends JFrame
{	
	public ProjectSakilaDB()
	{
		// build the JFrame here in the main 
		JFrame frame = new JFrame("DVD Rental");
		
		//boilerplate
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(950,800);
		frame.setLocationRelativeTo(null);
		
		//create a JTabbedPane object
		JTabbedPane tPane = new JTabbedPane();
		
		//now call the JPanel constructor classes and add each one to the JTabbedPane.
		tPane.add("Add Customer", new NewCustomer() );
		
		//add the second panel for BorderLayout
		tPane.add("Add Actor ", new NewActor() );
		
		//add the third panel showing GridLayout
		tPane.add("Add Film ", new NewFilm() );
		
		//add fourth panel showing FlowLayout
		tPane.add("Add Rental Transaction", new NewRental() );
		
		//add fifth panel showing vertical BoxLayout
		tPane.add("Reports", new Reports() );
		
		//add the JTabbedPane to the JFrame
		frame.add(tPane);		
		
		//last line
		frame.setVisible(true);		
	}
	
	public static void main(String[] args)
	{
		new ProjectSakilaDB();
	}

}
