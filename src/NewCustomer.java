import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Program Name: NewCustomer.java
 * Purpose: To create a new form that will allow an user to insert a new customer into the database
 * Coder: 	Henrique dos Santos
 * 					Ruben Dario  Mejia Cardona 
 * 					David Jimenez
 * 					Ibrahim El Hage
 * Date: 		Aug. 3, 2020
 */

public class NewCustomer extends JPanel
{
	private JLabel lblStore = new JLabel("Store: ");
	private JLabel lblFirst = new JLabel("First Name: ");
	private JLabel lblLast = new JLabel("Last Name: ");
	private JLabel lblEmail = new JLabel("Email: ");
	private JLabel lblAddress = new JLabel("Address: ");
	private JLabel lblPostal = new JLabel("Postal Code: ");	
	private JLabel lblCity = new JLabel("City: ");	
	private JLabel lblDistrict = new JLabel("District: ");	
	private JLabel lblPhone = new JLabel("Phone: ");
	
	private JPanel panel = new JPanel();
	private JComboBox storeBox;
	private JComboBox cityBox;
	private String selectedStore;
	//private String selectedCity;
	
	//listener related components
	private JTextField txtFirstName = new JTextField(20);
	private JTextField txtLastName= new JTextField(20);
	private JTextField txtEmail = new JTextField(20);   
	private JTextField txtAddress = new JTextField(20);	
	private JTextField txtDistrict = new JTextField("Alberta",20);
	private JTextField txtPostal = new JTextField(20);
	private JTextField txtPhone = new JTextField(20);
	private JCheckBox chkActive = new JCheckBox(" Active"); 
	private JButton btnNewCust = new JButton("Add a new customer");
	 
   		
	public NewCustomer()
	{
		super();
		
		//Create a new panel with GridBagLayout manager    
    this.setLayout(new GridBagLayout() );		
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.anchor = GridBagConstraints.WEST;
    constraints.insets = new Insets(0, 0, 33, 0);    
    
    //Shows a popup menu that shows a list of stores number
    storeBox = new JComboBox(storeArray);    
    Dimension dim = new Dimension(200,25);
    storeBox.setPreferredSize(dim);
    
    //Shows a popup menu that shows a list of cities
    cityBox = new JComboBox(cityArray);
    cityBox.setSelectedItem("Lethbridge");
    cityBox.setPreferredSize(dim);
           
    //Add components to the panel
    //Store
    constraints.gridx = 0;
    constraints.gridy = 0;     
    this.add(lblStore, constraints);
    constraints.gridx = 1;
    this.add(storeBox, constraints);
    
    //First Name
    constraints.gridx = 0;
    constraints.gridy = 1;     
    this.add(lblFirst, constraints);
    constraints.gridx = 1;
    this.add(txtFirstName, constraints);
    
    //Last Name
    constraints.gridx = 0;
    constraints.gridy = 2;     
    this.add(lblLast, constraints);     
    constraints.gridx = 1;
    this.add(txtLastName, constraints);
    
    //Email
    constraints.gridx = 0;
    constraints.gridy = 3;     
    this.add(lblEmail, constraints);     
    constraints.gridx = 1;
    this.add(txtEmail, constraints);
    
    //Address
    constraints.gridx = 0;
    constraints.gridy = 4;     
    this.add(lblAddress, constraints);     
    constraints.gridx = 1;
    this.add(txtAddress, constraints);
    
    //Postal Code
    constraints.gridx = 0;
    constraints.gridy = 5;     
    this.add(lblPostal, constraints);     
    constraints.gridx = 1;
    this.add(txtPostal, constraints);
        
    //City
    constraints.gridx = 0;
    constraints.gridy = 6;     
    this.add(lblCity, constraints);     
    constraints.gridx = 1;
    this.add(cityBox, constraints);    
            
    //District
    constraints.gridx = 0;
    constraints.gridy = 7;     
    this.add(lblDistrict, constraints);     
    constraints.gridx = 1;
    this.add(txtDistrict, constraints);
    
    //Phone
    constraints.gridx = 0;
    constraints.gridy = 8;     
    this.add(lblPhone, constraints);     
    constraints.gridx = 1;
    this.add(txtPhone, constraints);
    
    //Active
    constraints.gridx = 0;
    constraints.gridy = 9;     
    //this.add(lblActive, constraints);     
    constraints.gridx = 1;
    this.add(chkActive, constraints);
    
    //Button add a new customer
    constraints.gridx = 0;
    constraints.gridy = 10;
    constraints.gridwidth = 2;
    constraints.anchor = GridBagConstraints.CENTER;
    this.add(btnNewCust, constraints);
    
   //Create a listener object from the inner class
    AddCustomer newCust = new AddCustomer();
    //add listener
    btnNewCust.addActionListener(newCust);   
    
    //Set border for the panel
    this.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Add a New Customer"));      
	} //end constructor
	
	/**
	* Method Name: isValidEmail()
	* Purpose: a public class method that will check whether or not the string matches the given regex.
	* Accepts: string email
	* Returns: boolean
	* Date: August 7, 2020
	* Regex: www.regxlib.com
	*/
	
	public boolean isValidEmail(String email) 
	{
		String regex = "^[-a-z0-9~!$%^&*_=+}{\\'?]+(\\.[-a-z0-9~!$%^&*_=+}{\\'?]+)*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$";
		return email.matches(regex);
  }
	
	/**
	* Method Name: isValidPostal()
	* Purpose: a public class method that will check whether or not the string matches the given regex.
	* Accepts: string Postal Code
	* Returns: boolean
	* Date: August 7, 2020
	* Regex: www.regxlib.com
	*/
	
	public boolean isValidPostal(String postalCode) 
	{
		String regex = "^((\\d{5})|(\\d{5}-\\d{4})|([A-CEGHJ-NPR-TV-Z]\\d[A-CEGHJ-NPR-TV-Z]\\s\\d[A-CEGHJ-NPR-TV-Z]\\d))$";
    return postalCode.matches(regex);
  }
	
	/**
	* Method Name: isValidPhone()
	* Purpose: a public class method that will check whether or not the string matches the given regex.
	* Accepts: string phone
	* Returns: boolean
	* Date: August 7, 2020
	* Regex: www.regxlib.com
	*/	
	
	public boolean isValidPhone(String phone) 
	{
		String regex = "^(?:(?:\\+?1[\\s])|(?:\\+?1(?=(?:\\()|(?:\\d{10})))|(?:\\+?1[\\-](?=\\d)))?(?:\\([2-9]\\d{2}\\)\\ ?|[2-9]\\d{2}(?:\\-?|\\ ?))[2-9]\\d{2}[- ]?\\d{4}$";
    return phone.matches(regex);
  }
		
	//Inner Class for AddCustomer
	private class AddCustomer implements ActionListener
	{	
		@Override
		public void actionPerformed(ActionEvent e)
		{
			//Validate user inputs
			if(txtFirstName.getText().equals("") || txtLastName.getText().equals("") || txtAddress.getText().equals("") || txtDistrict.getText().equals(""))
				JOptionPane.showMessageDialog(panel, "Please provide the missing information", "Warning", JOptionPane.WARNING_MESSAGE);
			else if(!isValidEmail(txtEmail.getText().trim()))
				JOptionPane.showMessageDialog(panel, "Please enter a valid email address", "Warning", JOptionPane.WARNING_MESSAGE);
			else if(!isValidPostal(txtPostal.getText().trim()))
				JOptionPane.showMessageDialog(panel, "Please enter a valid postal code", "Warning", JOptionPane.WARNING_MESSAGE);
			else if(!isValidPhone(txtPhone.getText().trim()))
				JOptionPane.showMessageDialog(panel, "Please enter a valid phone", "Warning", JOptionPane.WARNING_MESSAGE);
			else 
			{
				if(e.getActionCommand().equals("Add a new customer"))
				{
					//initialize connections
					Connection myConn = null;
					Statement myStmt = null;
					ResultSet myRslt = null;
					PreparedStatement myPrep = null,  myPrepStmt = null;
					
					try
					{
						//Create a Connection object by calling a static method of DriverManager class
						myConn = DriverManager.getConnection(
								"jdbc:mySql://localhost:3306/sakila?useSSL=false&allowPublicKeyRetrieval=true","root","password" );;
						
						//Create a Statement object by calling a method of the Connection object
						myStmt = myConn.createStatement();
						
						//String that holds the INSERT statement that will be passed to the DB
						myPrep = myConn.prepareStatement("INSERT INTO address (address, district, city_id, postal_code, phone, location) VALUES(?, ?, (SELECT city_id FROM city WHERE city=?), ?, ?, ST_GeomFromText('POINT(0 0)'))");
						myPrep.setString(1, txtAddress.getText());
						myPrep.setString(2, txtDistrict.getText());
						myPrep.setString(3, (String)cityBox.getSelectedItem());
						myPrep.setString(4, txtPostal.getText());
						myPrep.setString(5, txtPhone.getText());
						
						//Catch the returned int value after the insert is executed
						int myQuery1 = myPrep.executeUpdate();
						
						//String that holds the INSERT statement that will be passed to the DB
						myPrepStmt = myConn.prepareStatement("INSERT INTO customer (store_id, first_name, last_name, email, address_id, active) VALUES(?, ?, ?, ?, last_insert_id(), ?)");
						myPrepStmt.setString(1, (String)storeBox.getSelectedItem());
						myPrepStmt.setString(2, txtFirstName.getText());
						myPrepStmt.setString(3, txtLastName.getText());
						myPrepStmt.setString(4, txtEmail.getText());
						myPrepStmt.setString(5, String.valueOf(chkActive.isSelected() ? '1':'0'));	
						
						//Catch the returned int value after the insert is executed
						int myQuery2 = myPrepStmt.executeUpdate();					
						
						//Information message
						if((myQuery1+myQuery2)==2)
						{
							//Set to default values
							txtFirstName.setText("");
							txtLastName.setText("");
							txtEmail.setText("");														
							txtAddress.setText("");
							txtDistrict.setText("Alberta");
							txtPostal.setText("");
							txtPhone.setText("");
							storeBox.setSelectedItem("1");
							cityBox.setSelectedItem("Lethbridge");
							chkActive.setSelected(false);
							//Confirm that new record is created
							JOptionPane.showMessageDialog(panel, "New Customer has been Created", "Info", JOptionPane.INFORMATION_MESSAGE);							
						}
						else
							JOptionPane.showMessageDialog(panel, "The Record has not been created", "Warning", JOptionPane.WARNING_MESSAGE);
						
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
					
				}//end inner if
		
			}//end if 
			
		}//end actionPerformed()
	
	}//end inner class	

	
	//JComboBox arrays
	//Store array
	String[]storeArray = {"1","2"};
	
	//City array
	String[]cityArray = {
			"A Corua (La Corua)",
			"Abha",
			"Abu Dhabi",
			"Acua",
			"Adana",
			"Addis Abeba",
			"Aden",
			"Adoni",
			"Ahmadnagar",
			"Akishima",
			"Akron",
			"al-Ayn",
			"al-Hawiya",
			"al-Manama",
			"al-Qadarif",
			"al-Qatif",
			"Alessandria",
			"Allappuzha (Alleppey)",
			"Allende",
			"Almirante Brown",
			"Alvorada",
			"Ambattur",
			"Amersfoort",
			"Amroha",
			"Angra dos Reis",
			"Anpolis",
			"Antofagasta",
			"Aparecida de Goinia",
			"Apeldoorn",
			"Araatuba",
			"Arak",
			"Arecibo",
			"Arlington",
			"Ashdod",
			"Ashgabat",
			"Ashqelon",
			"Asuncin",
			"Athenai",
			"Atinsk",
			"Atlixco",
			"Augusta-Richmond County",
			"Aurora",
			"Avellaneda",
			"Bag",
			"Baha Blanca",
			"Baicheng",
			"Baiyin",
			"Baku",
			"Balaiha",
			"Balikesir",
			"Balurghat",
			"Bamenda",
			"Bandar Seri Begawan",
			"Banjul",
			"Barcelona",
			"Basel",
			"Bat Yam",
			"Batman",
			"Batna",
			"Battambang",
			"Baybay",
			"Bayugan",
			"Bchar",
			"Beira",
			"Bellevue",
			"Belm",
			"Benguela",
			"Beni-Mellal",
			"Benin City",
			"Bergamo",
			"Berhampore (Baharampur)",
			"Bern",
			"Bhavnagar",
			"Bhilwara",
			"Bhimavaram",
			"Bhopal",
			"Bhusawal",
			"Bijapur",
			"Bilbays",
			"Binzhou",
			"Birgunj",
			"Bislig",
			"Blumenau",
			"Boa Vista",
			"Boksburg",
			"Botosani",
			"Botshabelo",
			"Bradford",
			"Braslia",
			"Bratislava",
			"Brescia",
			"Brest",
			"Brindisi",
			"Brockton",
			"Bucuresti",
			"Buenaventura",
			"Bydgoszcz",
			"Cabuyao",
			"Callao",
			"Cam Ranh",
			"Cape Coral",
			"Caracas",
			"Carmen",
			"Cavite",
			"Cayenne",
			"Celaya",
			"Chandrapur",
			"Changhwa",
			"Changzhou",
			"Chapra",
			"Charlotte Amalie",
			"Chatsworth",
			"Cheju",
			"Chiayi",
			"Chisinau",
			"Chungho",
			"Cianjur",
			"Ciomas",
			"Ciparay",
			"Citrus Heights",
			"Citt del Vaticano",
			"Ciudad del Este",
			"Clarksville",
			"Coacalco de Berriozbal",
			"Coatzacoalcos",
			"Compton",
			"Coquimbo",
			"Crdoba",
			"Cuauhtmoc",
			"Cuautla",
			"Cuernavaca",
			"Cuman",
			"Czestochowa",
			"Dadu",
			"Dallas",
			"Datong",
			"Daugavpils",
			"Davao",
			"Daxian",
			"Dayton",
			"Deba Habe",
			"Denizli",
			"Dhaka",
			"Dhule (Dhulia)",
			"Dongying",
			"Donostia-San Sebastin",
			"Dos Quebradas",
			"Duisburg",
			"Dundee",
			"Dzerzinsk",
			"Ede",
			"Effon-Alaiye",
			"El Alto",
			"El Fuerte",
			"El Monte",
			"Elista",
			"Emeishan",
			"Emmen",
			"Enshi",
			"Erlangen",
			"Escobar",
			"Esfahan",
			"Eskisehir",
			"Etawah",
			"Ezeiza",
			"Ezhou",
			"Faaa",
			"Fengshan",
			"Firozabad",
			"Florencia",
			"Fontana",
			"Fukuyama",
			"Funafuti",
			"Fuyu",
			"Fuzhou",
			"Gandhinagar",
			"Garden Grove",
			"Garland",
			"Gatineau",
			"Gaziantep",
			"Gijn",
			"Gingoog",
			"Goinia",
			"Gorontalo",
			"Grand Prairie",
			"Graz",
			"Greensboro",
			"Guadalajara",
			"Guaruj",
			"guas Lindas de Gois",
			"Gulbarga",
			"Hagonoy",
			"Haining",
			"Haiphong",
			"Haldia",
			"Halifax",
			"Halisahar",
			"Halle/Saale",
			"Hami",
			"Hamilton",
			"Hanoi",
			"Hidalgo",
			"Higashiosaka",
			"Hino",
			"Hiroshima",
			"Hodeida",
			"Hohhot",
			"Hoshiarpur",
			"Hsichuh",
			"Huaian",
			"Hubli-Dharwad",
			"Huejutla de Reyes",
			"Huixquilucan",
			"Hunuco",
			"Ibirit",
			"Idfu",
			"Ife",
			"Ikerre",
			"Iligan",
			"Ilorin",
			"Imus",
			"Inegl",
			"Ipoh",
			"Isesaki",
			"Ivanovo",
			"Iwaki",
			"Iwakuni",
			"Iwatsuki",
			"Izumisano",
			"Jaffna",
			"Jaipur",
			"Jakarta",
			"Jalib al-Shuyukh",
			"Jamalpur",
			"Jaroslavl",
			"Jastrzebie-Zdrj",
			"Jedda",
			"Jelets",
			"Jhansi",
			"Jinchang",
			"Jining",
			"Jinzhou",
			"Jodhpur",
			"Johannesburg",
			"Joliet",
			"Jos Azueta",
			"Juazeiro do Norte",
			"Juiz de Fora",
			"Junan",
			"Jurez",
			"Kabul",
			"Kaduna",
			"Kakamigahara",
			"Kaliningrad",
			"Kalisz",
			"Kamakura",
			"Kamarhati",
			"Kamjanets-Podilskyi",
			"Kamyin",
			"Kanazawa",
			"Kanchrapara",
			"Kansas City",
			"Karnal",
			"Katihar",
			"Kermanshah",
			"Kilis",
			"Kimberley",
			"Kimchon",
			"Kingstown",
			"Kirovo-Tepetsk",
			"Kisumu",
			"Kitwe",
			"Klerksdorp",
			"Kolpino",
			"Konotop",
			"Koriyama",
			"Korla",
			"Korolev",
			"Kowloon and New Kowloon",
			"Kragujevac",
			"Ktahya",
			"Kuching",
			"Kumbakonam",
			"Kurashiki",
			"Kurgan",
			"Kursk",
			"Kuwana",
			"La Paz",
			"La Plata",
			"La Romana",
			"Laiwu",
			"Lancaster",
			"Laohekou",
			"Lapu-Lapu",
			"Laredo",
			"Lausanne",
			"Le Mans",
			"Lengshuijiang",
			"Leshan",
			"Lethbridge",
			"Lhokseumawe",
			"Liaocheng",
			"Liepaja",
			"Lilongwe",
			"Lima",
			"Lincoln",
			"Linz",
			"Lipetsk",
			"Livorno",
			"Ljubertsy",
			"Loja",
			"London",
			"London",
			"Lublin",
			"Lubumbashi",
			"Lungtan",
			"Luzinia",
			"Madiun",
			"Mahajanga",
			"Maikop",
			"Malm",
			"Manchester",
			"Mandaluyong",
			"Mandi Bahauddin",
			"Mannheim",
			"Maracabo",
			"Mardan",
			"Maring",
			"Masqat",
			"Matamoros",
			"Matsue",
			"Meixian",
			"Memphis",
			"Merlo",
			"Mexicali",
			"Miraj",
			"Mit Ghamr",
			"Miyakonojo",
			"Mogiljov",
			"Molodetno",
			"Monclova",
			"Monywa",
			"Moscow",
			"Mosul",
			"Mukateve",
			"Munger (Monghyr)",
			"Mwanza",
			"Mwene-Ditu",
			"Myingyan",
			"Mysore",
			"Naala-Porto",
			"Nabereznyje Telny",
			"Nador",
			"Nagaon",
			"Nagareyama",
			"Najafabad",
			"Naju",
			"Nakhon Sawan",
			"Nam Dinh",
			"Namibe",
			"Nantou",
			"Nanyang",
			"NDjamna",
			"Newcastle",
			"Nezahualcyotl",
			"Nha Trang",
			"Niznekamsk",
			"Novi Sad",
			"Novoterkassk",
			"Nukualofa",
			"Nuuk",
			"Nyeri",
			"Ocumare del Tuy",
			"Ogbomosho",
			"Okara",
			"Okayama",
			"Okinawa",
			"Olomouc",
			"Omdurman",
			"Omiya",
			"Ondo",
			"Onomichi",
			"Oshawa",
			"Osmaniye",
			"ostka",
			"Otsu",
			"Oulu",
			"Ourense (Orense)",
			"Owo",
			"Oyo",
			"Ozamis",
			"Paarl",
			"Pachuca de Soto",
			"Pak Kret",
			"Palghat (Palakkad)",
			"Pangkal Pinang",
			"Papeete",
			"Parbhani",
			"Pathankot",
			"Patiala",
			"Patras",
			"Pavlodar",
			"Pemalang",
			"Peoria",
			"Pereira",
			"Phnom Penh",
			"Pingxiang",
			"Pjatigorsk",
			"Plock",
			"Po",
			"Ponce",
			"Pontianak",
			"Poos de Caldas",
			"Portoviejo",
			"Probolinggo",
			"Pudukkottai",
			"Pune",
			"Purnea (Purnia)",
			"Purwakarta",
			"Pyongyang",
			"Qalyub",
			"Qinhuangdao",
			"Qomsheh",
			"Quilmes",
			"Rae Bareli",
			"Rajkot",
			"Rampur",
			"Rancagua",
			"Ranchi",
			"Richmond Hill",
			"Rio Claro",
			"Rizhao",
			"Roanoke",
			"Robamba",
			"Rockford",
			"Ruse",
			"Rustenburg",
			"s-Hertogenbosch",
			"Saarbrcken",
			"Sagamihara",
			"Saint Louis",
			"Saint-Denis",
			"Sal",
			"Salala",
			"Salamanca",
			"Salinas",
			"Salzburg",
			"Sambhal",
			"San Bernardino",
			"San Felipe de Puerto Plata",
			"San Felipe del Progreso",
			"San Juan Bautista Tuxtepec",
			"San Lorenzo",
			"San Miguel de Tucumn",
			"Sanaa",
			"Santa Brbara dOeste",
			"Santa F",
			"Santa Rosa",
			"Santiago de Compostela",
			"Santiago de los Caballeros",
			"Santo Andr",
			"Sanya",
			"Sasebo",
			"Satna",
			"Sawhaj",
			"Serpuhov",
			"Shahr-e Kord",
			"Shanwei",
			"Shaoguan",
			"Sharja",
			"Shenzhen",
			"Shikarpur",
			"Shimoga",
			"Shimonoseki",
			"Shivapuri",
			"Shubra al-Khayma",
			"Siegen",
			"Siliguri (Shiliguri)",
			"Simferopol",
			"Sincelejo",
			"Sirjan",
			"Sivas",
			"Skikda",
			"Smolensk",
			"So Bernardo do Campo",
			"So Leopoldo",
			"Sogamoso",
			"Sokoto",
			"Songkhla",
			"Sorocaba",
			"Soshanguve",
			"Sousse",
			"South Hill",
			"Southampton",
			"Southend-on-Sea",
			"Southport",
			"Springs",
			"Stara Zagora",
			"Sterling Heights",
			"Stockport",
			"Sucre",
			"Suihua",
			"Sullana",
			"Sultanbeyli",
			"Sumqayit",
			"Sumy",
			"Sungai Petani",
			"Sunnyvale",
			"Surakarta",
			"Syktyvkar",
			"Syrakusa",
			"Szkesfehrvr",
			"Tabora",
			"Tabriz",
			"Tabuk",
			"Tafuna",
			"Taguig",
			"Taizz",
			"Talavera",
			"Tallahassee",
			"Tama",
			"Tambaram",
			"Tanauan",
			"Tandil",
			"Tangail",
			"Tanshui",
			"Tanza",
			"Tarlac",
			"Tarsus",
			"Tartu",
			"Teboksary",
			"Tegal",
			"Tel Aviv-Jaffa",
			"Tete",
			"Tianjin",
			"Tiefa",
			"Tieli",
			"Tokat",
			"Tonghae",
			"Tongliao",
			"Torren",
			"Touliu",
			"Toulon",
			"Toulouse",
			"Trshavn",
			"Tsaotun",
			"Tsuyama",
			"Tuguegarao",
			"Tychy",
			"Udaipur",
			"Udine",
			"Ueda",
			"Uijongbu",
			"Uluberia",
			"Urawa",
			"Uruapan",
			"Usak",
			"Usolje-Sibirskoje",
			"Uttarpara-Kotrung",
			"Vaduz",
			"Valencia",
			"Valle de la Pascua",
			"Valle de Santiago",
			"Valparai",
			"Vancouver",
			"Varanasi (Benares)",
			"Vicente Lpez",
			"Vijayawada",
			"Vila Velha",
			"Vilnius",
			"Vinh",
			"Vitria de Santo Anto",
			"Warren",
			"Weifang",
			"Witten",
			"Woodridge",
			"Wroclaw",
			"Xiangfan",
			"Xiangtan",
			"Xintai",
			"Xinxiang",
			"Yamuna Nagar",
			"Yangor",
			"Yantai",
			"Yaound",
			"Yerevan",
			"Yinchuan",
			"Yingkou",
			"York",
			"Yuncheng",
			"Yuzhou",
			"Zalantun",
			"Zanzibar",
			"Zaoyang",
			"Zapopan",
			"Zaria",
			"Zeleznogorsk",
			"Zhezqazghan",
			"Zhoushan",
			"Ziguinchor",
			};	
}
