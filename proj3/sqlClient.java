package proj3;

/* 
Name: Emmanuel Benjamin 
Project: A Two-tier Client-Server Application that allows the query and update of a mySQL database based off assigned user permissions
without the need for access to the database application.  
Date:  March 9, 2023 

*/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;


public class sqlClient implements ActionListener{
	static JFrame frame = new JFrame("Two Tier Client Server");
	static JPanel panel = new JPanel();
	static JLabel details = new JLabel();
	static JLabel propertyLabel = new JLabel();
	static JComboBox<String> propertyList = new JComboBox<>();
	static String[] options = {"root.properties", "client.properties", "project3app.properties", "db3.properties", "db4.properties"};
	static JLabel userLabel = new JLabel();
	static JTextField userText = new JTextField();
	static JLabel passLabel = new JLabel();
	static JPasswordField passText = new JPasswordField();
	static JButton connectButton = new JButton();
	static JLabel command = new JLabel();
	static JTextArea cmWindow = new JTextArea();
	static JButton clearSQL = new JButton();
	static JButton exeSQL = new JButton();
	static JTextField status = new JTextField();
	static JLabel execute = new JLabel();
	static DefaultTableModel model = new DefaultTableModel();
	static JTable result = new JTable(model);
	static JScrollPane scrollPane = new JScrollPane(result);
	static JButton clear = new JButton("Clear Result Window");
	
	int queryCount = 0;
	int updateCount = 0;
	
	
	
	public static void main(String[] args) throws ClassNotFoundException {
		 display();
	
	}//end main
	
	
	
	public static void display() {
		frame.setSize(725, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		
		panel.setLayout(null);
		
	 	details = new JLabel("Connection Details");
		details.setBounds(10, 5, 130, 25);
		details.setForeground(Color.blue);
		panel.add(details);
		
		propertyLabel = new JLabel("Properties File");
		propertyLabel.setBounds(10, 30, 100, 25);
		panel.add(propertyLabel);
		
		
		propertyList = new JComboBox<>(options);
		propertyList.setBounds(120, 30, 165, 25);
		panel.add(propertyList);
		
		userLabel = new JLabel("Username");
		userLabel.setBounds(10, 60, 80, 25);
		panel.add(userLabel);
		
		userText = new JTextField(20);
		userText.setBounds(120, 60, 165, 25);
		panel.add(userText);
		
		passLabel = new JLabel("Password");
		passLabel.setBounds(10, 90, 80, 25);
		panel.add(passLabel);
		
			
		passText = new JPasswordField();
		passText.setBounds(120, 90, 165, 25);
		panel.add(passText);
		
		connectButton = new JButton("Connect to Database");
		connectButton.setBounds(20, 130, 165, 25);
		connectButton.setForeground(Color.green);
		connectButton.setBackground(Color.white);
		connectButton.addActionListener(new sqlClient());
		panel.add(connectButton);
		
		command = new JLabel("Enter an SQL Command");
		command.setBounds(300, 5, 150, 25);
		command.setForeground(Color.blue);
		panel.add(command);
		
		cmWindow = new JTextArea(5,50);
		cmWindow.setLineWrap(true);
		cmWindow.setWrapStyleWord(true);
		cmWindow.setBounds(300, 30, 400, 90);
		cmWindow.setCaretPosition(0);
		cmWindow.requestFocus();
		panel.add(cmWindow);
		
		clearSQL = new JButton("Clear SQL Command");
		clearSQL.setBounds(300, 130, 165, 25);
		clearSQL.setForeground(Color.red);
		clearSQL.setBackground(Color.white);
		clearSQL.addActionListener(new sqlClient());
		panel.add(clearSQL);
		
		exeSQL = new JButton("Execute SQL Command");
		exeSQL.setBounds(500, 130, 180, 25);
		exeSQL.setForeground(Color.blue);
		exeSQL.setBackground(Color.white);
		exeSQL.addActionListener(new sqlClient());
		panel.add(exeSQL);
		
		status = new JTextField("No Connection Established");
		status.setBounds(20, 160, 660, 25);
		status.setBackground(new Color(0x3E363F));
		status.setForeground(Color.red);
		panel.add(status);
		
		execute = new JLabel("SQL Execution Result Window");
		execute.setBounds(30, 100, 660, 200);
		execute.setForeground(Color.blue);
		panel.add(execute);
		
	
		scrollPane.setBounds(20, 210, 660, 200);
		scrollPane.setBackground(Color.white);
		panel.add(scrollPane);
		
		clear = new JButton("Clear Result Window");
		clear.setBounds(20, 420, 180, 25);
		clear.setBackground(new Color(0xF88379));
		clear.setForeground(new Color(0x2D4739));
		clear.addActionListener(new sqlClient());
		panel.add(clear);
			
		
		
		frame.setVisible(true);
		
		
	}//end display



	@Override
	public void actionPerformed(ActionEvent e) {
		String user = userText.getText();
		String paassword = passText.getText();
		String selectedOption = (String) propertyList.getSelectedItem();
		String query = cmWindow.getText();
		Boolean flag = false;
		
		if(e.getSource() == connectButton) {
			
			//validate root login information
			if(user.equals("root") && paassword.equals("password") && selectedOption.equals("root.properties")) {
			     String url = "jdbc:mysql://localhost:3306/project3";
				 String username = user;
				 String password = paassword;

				 try (Connection connection = DriverManager.getConnection(url, username, password)) {
				     status.setForeground(Color.green);
				     status.setText("Connected to "+url);
				     flag = true;
				     
				 } catch (SQLException f) {
				     throw new IllegalStateException("Cannot connect the database!", f);
				 }
				 
				 		 
				 
			
			//validate client login information
			}else if(user.equals("client") && paassword.equals("client") && selectedOption.equals("client.properties")){
				String url = "jdbc:mysql://localhost:3306/project3";
				 String username = user;
				 String password = paassword;

				 try (Connection connection = DriverManager.getConnection(url, username, password)) {
				     status.setForeground(Color.green);
				     status.setText("Connected to "+url);
				 } catch (SQLException f) {
				     throw new IllegalStateException("Cannot connect the database!", f);
				 } 
				
				
			}else{
				//incorrect login handling
				status.setText("NOT CONNECTED - User Credentials Do Not Match Properties File!");
				JFrame frame = new JFrame("Login Error");
				JLabel label = new JLabel("Incorrect Username or password. Please try again.");
				JPanel errPanel = new JPanel();
				JButton button = new JButton("OK");
				Dimension labelSize = label.getPreferredSize();
				int width = labelSize.width + 50; // add 50 pixels of extra width
				int height = labelSize.height + 50; // add 50 pixels of extra height
				frame.setSize(width, 100);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(errPanel);
				errPanel.add(label);
				errPanel.add(button);
				
				
				button.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(button);
				        frame.dispose();
				    }
				});
				
				
				frame.setVisible(true);
				
				
			}//end login validation
			
			
		}//end connectButton if
		
/***********************************************************************************************************************/		
		
		//handling SQL query
		 if(e.getSource() == exeSQL) {
			 
			//handling the execute query as a root user
			 if(user.equals("root") && paassword.equals("password") && selectedOption.equals("root.properties")){
					String url = "jdbc:mysql://localhost:3306/project3";
					 String username = user;
					 String password = paassword;
					 
					 

					 try (Connection connection = DriverManager.getConnection(url, username, password)) {
					     
					     	// Clear the existing data in the model
					     	model.setRowCount(0);
					     	model.setColumnCount(0);
					     
					        //parse statement to be executed
							Statement stmt = connection.createStatement();
							
							try {
							
							// Get the first word of the SQL string to seperate execute from update
							String[] words = query.split("\\s+");
							String firstWord = words[0];
							
							//handling query type
							if (firstWord.equalsIgnoreCase("SELECT")) {
								//parse query
								ResultSet rs = stmt.executeQuery(query); 
								
								//update query count
								queryCount++;
								
							
							//get statement metadata
							ResultSetMetaData rsmd = rs.getMetaData();
							
							//get the number of columns in the result set
							int columnCount = rsmd.getColumnCount();
							
							//add the column names to the defaultTableModel
							String[] columnNames = new String[columnCount];
							for(int i=1; i <= columnCount; i++) {
								columnNames[i - 1] = rsmd.getColumnName(i);
								model.addColumn(rsmd.getColumnName(i));
								
								
							}//end for
							
								model.setColumnIdentifiers(columnNames);
								
							
							//add the rows of data to the defaultTableModel
							while(rs.next()) {
								Object[] row = new Object[columnCount];
								for(int i=1; i<=columnCount; i++) {
									row[i-1] = rs.getObject(i);
								}
								model.addRow(row);
							}//end while
							result.setModel(model);
							
							//update operations log
							String operationurl = "jdbc:mysql://localhost:3306/operationslog";
							 try (Connection conn = DriverManager.getConnection(operationurl, username, password)){
								 String sql = "UPDATE operationscount SET num_queries = ?";
								 System.out.println("Query count: "+queryCount);
								 PreparedStatement pstmt = conn.prepareStatement(sql);
								 pstmt.setInt(1, queryCount);
								 int rowsUpdated = pstmt.executeUpdate();
								 
							 }catch(SQLException c) {
								 JOptionPane.showMessageDialog(null, "Error: "+c.getMessage());
							 }//end try/catch
							
							
							
							
							//handling update queries
							}else if(firstWord.equalsIgnoreCase("INSERT") || firstWord.equalsIgnoreCase("UPDATE") || firstWord.equalsIgnoreCase("DELETE")) {
								try {
								int numRowsUpdated = stmt.executeUpdate(query);
								// Display success message to user if a row is updated
							    	JOptionPane.showMessageDialog(null, "Success! "+numRowsUpdated + " row(s) updated.");
							    	
							    //update updateCount log
							    updateCount++;
							    
							    String operationurl = "jdbc:mysql://localhost:3306/operationslog";
								 try (Connection conn = DriverManager.getConnection(operationurl, username, password)){
									 String sql = "UPDATE operationscount SET num_updates = ?";
									 System.out.println("Query count: "+updateCount);
									 PreparedStatement pstmt = conn.prepareStatement(sql);
									 pstmt.setInt(1, updateCount);
									 int rowsUpdated = pstmt.executeUpdate();
									 
									 
								 }catch(SQLException c) {
									 JOptionPane.showMessageDialog(null, "Error: "+c.getMessage());
								 }//end try/catch
								 
								 
								}catch(SQLException t) {
									JOptionPane.showMessageDialog(null, "Error: "+t.getMessage());
								}//end try/catch
							}
							
							}catch(SQLException t){
								JOptionPane.showMessageDialog(null, "Error: "+t.getMessage());
							} 
					 
					 } catch (SQLException f) {
					     throw new IllegalStateException("Cannot connect the database!", f);
					 } 	 
				
			 	
				
			}else if(user.equals("client") && paassword.equals("client") && selectedOption.equals("client.properties")){
				//handling client query execution
				String url = "jdbc:mysql://localhost:3306/project3";
				 String username = user;
				 String password = paassword;
				 

				 try (Connection connection = DriverManager.getConnection(url, username, password)) {
				     
				     	// Clear the existing data in the model
				     	model.setRowCount(0);
				     	model.setColumnCount(0);
				     
				        //parse statement to be executed
						Statement stmt = connection.createStatement();
						
						try {
						
						// Get the first word of the SQL string to seperate execute from update
						String[] words = query.split("\\s+");
						String firstWord = words[0];
						
						//handling client query type
						if (firstWord.equalsIgnoreCase("SELECT")) {
							ResultSet rs = stmt.executeQuery(query); 	
						
						//get statement metadata
						ResultSetMetaData rsmd = rs.getMetaData();
						
						//get the number of columns in the result set
						int columnCount = rsmd.getColumnCount();
						
						//add the column names to the defaultTableModel
						String[] columnNames = new String[columnCount];
						for(int i=1; i <= columnCount; i++) {
							columnNames[i - 1] = rsmd.getColumnName(i);
							model.addColumn(rsmd.getColumnName(i));
							
						}//end for
						
							model.setColumnIdentifiers(columnNames);
							
						
						//add the rows of data to the defaultTableModel
						while(rs.next()) {
							Object[] row = new Object[columnCount];
							for(int i=1; i<=columnCount; i++) {
								row[i-1] = rs.getObject(i);
							}
							model.addRow(row);
						}//end while
						result.setModel(model);
						
					
						}else if(firstWord.equalsIgnoreCase("INSERT") || firstWord.equalsIgnoreCase("UPDATE") || firstWord.equalsIgnoreCase("DELETE")) {
							try {
							int numRowsUpdated = stmt.executeUpdate(query);
								// Display success message to user
								JOptionPane.showMessageDialog(null, numRowsUpdated + " rows updated.");
							    
							}catch(SQLException t) {
								JOptionPane.showMessageDialog(null, "Error: "+t.getMessage());	
							}//end try/catch
						
						}
						
						}catch(SQLException t) {
							JOptionPane.showMessageDialog(null, "Error: "+t.getMessage());
						}
				 
				 } catch (SQLException f) {
				     throw new IllegalStateException("Cannot connect the database!", f);
				 } 
		 
				
			}else {
				JFrame frame = new JFrame("Execution Error");
				JLabel label = new JLabel("Please login before executing a query.");
				JPanel errPanel = new JPanel();
				JButton button = new JButton("OK");
				Dimension labelSize = label.getPreferredSize();
				int width = labelSize.width + 50; // add 50 pixels of extra width
				int height = labelSize.height + 50; // add 50 pixels of extra height
				frame.setSize(width, 100);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(errPanel);
				errPanel.add(label);
				errPanel.add(button);
				
				
				button.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(button);
				        frame.dispose();
				    }
				});
				
				
				frame.setVisible(true);
			}
		
		
		
		 }//end execBTN
		 
		 
/*****************************************************************************************************************************/
		 //handling query window clearance
		 if(e.getSource() == clearSQL) {
			 cmWindow.setText(null);
		 }
		 
/*****************************************************************************************************************************/
		 //handling query result window clearance
		 if(e.getSource() == clear) {
			 DefaultTableModel emptyModel = new DefaultTableModel();
			 result.setModel(emptyModel);
		 }

	}//end action performed
	
}
