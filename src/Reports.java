import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;

/**
 * Program Name:Reports.java
 * Purpose: To generate a rental income report based on some criteria that the user might enter or not.
 * Coder: Henrique dos Santos
 * 				Ruben Dario  Mejia Cardona 
 * 				David Jimenez
 * 				Ibrahim El Hage
 * Date: Aug. 3, 2020
 */

public class Reports extends JPanel
{
	public JLabel reportLabel,  resultLabel, storeLbl, categoryLabel, yearLabel;
	public JComboBox categoryBox, storeBox;
	public JButton reportButton, clearButton;
	public JTextField yearTxt;
	public String message;



	public Reports()
	{
		super();

		Dimension dim = new Dimension(200, 30);

		reportLabel = new JLabel("Generate a rental income report.");
		storeLbl = new JLabel("Select a store: ");
		categoryLabel = new JLabel("Select a movie category: ");
		yearLabel = new JLabel("Enter a year (leave empty for no specific year): ");
		yearTxt = new JTextField();
		reportButton = new JButton("Generate income report");
		clearButton = new JButton("Clear results");
		categoryBox = CategoryComboBox();
		storeBox = StoreComboBox();
		btnListener generateReport = new btnListener();
		clearListener clear = new clearListener();
		reportButton.addActionListener(generateReport);
		clearButton.addActionListener(clear);
		categoryBox.setPreferredSize(dim);
		storeBox.setPreferredSize(dim);
		yearTxt.setPreferredSize(dim);
		resultLabel = new JLabel("");

		this.setLayout(new FlowLayout());
		this.add(reportLabel);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 2, 20, 30));
		panel.add(storeLbl);
		panel.add(storeBox);
		panel.add(categoryLabel);
		panel.add(categoryBox);
		panel.add(yearLabel);
		panel.add(yearTxt);
		panel.add(reportButton);
		panel.add(clearButton);

		this.add(panel);
		this.add(resultLabel);

	}


	/*
	Class: btnListener
	Purpose: private inner class that acts as a ActionListener
	 */
	private class btnListener implements ActionListener
	{

		public void actionPerformed(ActionEvent e)
		{
			String store, category, yearText;
			int year, storeId;
			double total;
			boolean yearSet = false;
			boolean valid = true;
			year = 2010;
			store = (String)storeBox.getSelectedItem();
			category = (String)categoryBox.getSelectedItem();
			yearText = yearTxt.getText();
			if(!yearText.isEmpty())
			{
				try
				{
					year = Integer.parseInt(yearTxt.getText());
				}
				catch (Exception ex) {
					resultLabel.setText("Invalid year! Please enter a valid year");
					valid = false;
				}
				yearSet = true;
			}



			if(valid)//only proceed if the year entered is a number 
			{
				Connection myConn = null;
				Statement myStmt = null;
				ResultSet myRslt = null;
				PreparedStatement myPrepStmt = null;

				if(store.equals("47 MySakila Drive"))
					storeId = 1;
				else if(store.equals("28 MySQL Boulevard"))
					storeId = 2;
				else
					storeId = 0;

				try
				{
					myConn = DriverManager.getConnection(
							"jdbc:mySql://localhost:3306/sakila?useTimezone=true&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true","root","password" );

					myStmt = myConn.createStatement();

					if(!yearSet && storeId == 0 && category.equals("All categories"))
					{
						//no criteria entered
						myPrepStmt = myConn.prepareStatement("SELECT SUM(amount) FROM payment");
						myRslt = myPrepStmt.executeQuery();
						myRslt.next();
						total = 0.0;
						try
						{
							total = myRslt.getDouble(1);
						}
						catch (SQLException x)
						{
							message = "No results for the specified criteria!";
							resultLabel.setText(message);
						}
						message = "Total income for all stores: $" + total;
						resultLabel.setText(message);

					}
					else if(!yearSet && storeId != 0 && category.equals("All categories"))
					{
						//store selected
						myPrepStmt = myConn.prepareStatement("SELECT SUM(amount) FROM payment INNER JOIN rental ON payment.rental_id = rental.rental_id INNER JOIN inventory ON rental.inventory_id = inventory.inventory_id " +
								"WHERE inventory.store_id = " + storeId);
						myRslt = myPrepStmt.executeQuery();
						myRslt.next();
						total = 0.0;
						try
						{
							total = myRslt.getDouble(1);
						}
						catch (SQLException x)
						{
							message = "No results for the specified criteria!";
							resultLabel.setText(message);
						}
						message = "Total income for store " + store + " is $" + total;
						resultLabel.setText(message);
					}
					else if(!yearSet && storeId == 0 && !category.equals("All categories"))
					{
						//category selected
						myPrepStmt = myConn.prepareStatement("SELECT SUM(amount) FROM payment INNER JOIN rental ON payment.rental_id = rental.rental_id" +
								" INNER JOIN inventory ON rental.inventory_id = inventory.inventory_id INNER JOIN film ON inventory.film_id = film.film_id" +
								" INNER JOIN film_category ON film.film_id = film_category.film_id INNER JOIN category ON film_category.category_id = category.category_id WHERE category.name = '" + category + "'");
						myRslt = myPrepStmt.executeQuery();
						myRslt.next();
						total = 0.0;
						try
						{
							total = myRslt.getDouble(1);
						}
						catch (SQLException x)
						{
							message = "No results for the specified criteria!";
							resultLabel.setText(message);
						}
						message = "Total income for category " + category + " is $" + total;
						resultLabel.setText(message);
					}
					else if(!yearSet && storeId != 0 && !category.equals("All categories"))
					{
						//category and store selected
						myPrepStmt = myConn.prepareStatement("SELECT SUM(amount) FROM payment INNER JOIN rental ON payment.rental_id = rental.rental_id" +
								" INNER JOIN inventory ON rental.inventory_id = inventory.inventory_id INNER JOIN film ON inventory.film_id = film.film_id" +
								" INNER JOIN film_category ON film.film_id = film_category.film_id INNER JOIN category ON film_category.category_id = category.category_id WHERE category.name = '" + category + "' AND inventory.store_id = " + storeId);
						myRslt = myPrepStmt.executeQuery();
						myRslt.next();
						total = 0.0;
						try
						{
							total = myRslt.getDouble(1);
						}
						catch (SQLException x)
						{
							message = "No results for the specified criteria!";
							resultLabel.setText(message);
						}
						message = "Total income for category " + category + " at store " + store + " is $" + total;
						resultLabel.setText(message);
					}
					else if(yearSet && storeId == 0 && category.equals("All categories"))
					{
						//only year selected
						myPrepStmt = myConn.prepareStatement("SELECT SUM(amount) FROM payment WHERE EXTRACT(YEAR FROM payment_date) = " + year);
						myRslt = myPrepStmt.executeQuery();
						myRslt.next();
						total = 0.0;
						try
						{
							total = myRslt.getDouble(1);
						}
						catch (SQLException x)
						{
							message = "No results for the specified criteria!";
							resultLabel.setText(message);
						}
						message = "Total income for year " + year + " is $" + total;
						resultLabel.setText(message);
					}
					else if(yearSet && storeId == 0 && !category.equals("All categories"))
					{
						//category and year selected
						myPrepStmt = myConn.prepareStatement("SELECT SUM(amount) FROM payment INNER JOIN rental ON payment.rental_id = rental.rental_id" +
								" INNER JOIN inventory ON rental.inventory_id = inventory.inventory_id INNER JOIN film ON inventory.film_id = film.film_id" +
								" INNER JOIN film_category ON film.film_id = film_category.film_id INNER JOIN category ON film_category.category_id = category.category_id WHERE category.name = '" + category + "' AND EXTRACT(YEAR FROM payment_date) = " + year);
						myRslt = myPrepStmt.executeQuery();
						myRslt.next();
						total = 0.0;
						try
						{
							total = myRslt.getDouble(1);
						}
						catch (SQLException x)
						{
							message = "No results for the specified criteria!";
							resultLabel.setText(message);
						}
						message = "Total income for year " + year + " for " + category + " movies is $" + total;
						resultLabel.setText(message);
					}
					else if(yearSet && storeId != 0 && !category.equals("All categories"))
					{
						//category and year and store selected
						myPrepStmt = myConn.prepareStatement("SELECT SUM(amount) FROM payment INNER JOIN rental ON payment.rental_id = rental.rental_id" +
								" INNER JOIN inventory ON rental.inventory_id = inventory.inventory_id INNER JOIN film ON inventory.film_id = film.film_id" +
								" INNER JOIN film_category ON film.film_id = film_category.film_id INNER JOIN category ON film_category.category_id = category.category_id WHERE category.name = '" + category + "' AND EXTRACT(YEAR FROM payment_date) = " + year + " AND inventory.store_id = " + storeId);
						myRslt = myPrepStmt.executeQuery();
						myRslt.next();
						total = 0.0;
						try
						{
							total = myRslt.getDouble(1);
						}
						catch (SQLException x)
						{
							message = "No results for the specified criteria!";
							resultLabel.setText(message);
						}
						message = "Total income in " + year + " at store " + store + " for " + category + " movies is $" + total;
						resultLabel.setText(message);
					}
					else if(yearSet && storeId != 0 && category.equals("All categories"))
					{
						//only store and year selected
						myPrepStmt = myConn.prepareStatement("SELECT SUM(amount) FROM payment INNER JOIN rental ON payment.rental_id = rental.rental_id INNER JOIN " +
								"inventory ON rental.inventory_id = inventory.inventory_id WHERE EXTRACT(YEAR FROM payment_date) = " + year + " AND inventory.store_id = " + storeId);
						myRslt = myPrepStmt.executeQuery();
						myRslt.next();
						total = 0.0;
						try
						{
							total = myRslt.getDouble(1);
						}
						catch (SQLException x)
						{
							message = "No results for the specified criteria!";
							resultLabel.setText(message);
						}
						message = "Total income for year " + year + " at " + store + " store is $" + total;
						resultLabel.setText(message);
					}

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

		}
	}//end class

	/*
	Class: clearListener
	Purpose: to act as a action listener to the clear button
	 */
	private class clearListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			resultLabel.setText("");
		}
	}

	/*
	Method Name: CategoryComboBox()
	Purpose: to populate the category combo box with data retrieved from the database
	Accepts: nothing
	Returns: JComboBox
	 */

	public JComboBox CategoryComboBox()
	{
		ArrayList<String> arr = new ArrayList<String>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRslt = null;
		PreparedStatement myPrepStmt = null;

		try
		{

			myConn = DriverManager.getConnection(
					"jdbc:mySql://localhost:3306/sakila?useTimezone=true&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true","root","password" );

			myStmt = myConn.createStatement();

			myPrepStmt = myConn.prepareStatement("SELECT name FROM category ORDER BY name");

			myRslt = myPrepStmt.executeQuery();

			arr.add("All categories");

			while(myRslt.next())
			{
				int count = 1;
				arr.add(myRslt.getString(count));
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

		JComboBox tempCombo = new JComboBox(arr.toArray());
		return tempCombo;
	}

		/*
	Method Name: StoreComboBox()
	Purpose: to populate the store combo box with data retrieved from the database
	Accepts: nothing
	Returns: JComboBox
	 */


	public JComboBox StoreComboBox()
	{
		ArrayList<String> arr = new ArrayList<String>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRslt = null;
		PreparedStatement myPrepStmt = null;

		try
		{

			myConn = DriverManager.getConnection(
					"jdbc:mySql://localhost:3306/sakila?useTimezone=true&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true","root","password" );

			myStmt = myConn.createStatement();

			myPrepStmt = myConn.prepareStatement("SELECT address FROM address INNER JOIN store ON address.address_id = store.address_id");

			myRslt = myPrepStmt.executeQuery();

			arr.add("Both stores");

			while(myRslt.next())
			{
				int count = 1;
				arr.add(myRslt.getString(count));
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

		JComboBox tempCombo = new JComboBox(arr.toArray());
		return tempCombo;
	}

}//end class Reports
