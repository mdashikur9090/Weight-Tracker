package view;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import controler.DbConnection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import java.awt.Font;

public class Register implements ActionListener {

	private JFrame frame;
	private JTextField txtName;
	private JTextField txtAge;
	private JTextField txtEmail;
	private JTextField txtPassword;
	
	private JButton btnLogin;
	private JButton btnRegister;
	
	private JComboBox cmbGender;


	/**
	 * Create the application.
	 */
	public Register() {
		initialize();
		frame.setTitle("Register");
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 602, 570);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblName = new JLabel("Name : ");
		lblName.setFont(new Font("Verdana", Font.BOLD, 16));
		lblName.setBounds(41, 26, 100, 50);
		frame.getContentPane().add(lblName);
		
		txtName = new JTextField();
		txtName.setFont(new Font("Verdana", Font.BOLD, 16));
		txtName.setBounds(248, 26, 300, 50);
		frame.getContentPane().add(txtName);
		txtName.setColumns(10);
		
		JLabel lblAge = new JLabel("Age : ");
		lblAge.setFont(new Font("Verdana", Font.BOLD, 16));
		lblAge.setBounds(41, 105, 80, 50);
		frame.getContentPane().add(lblAge);
		
		JLabel lblGender = new JLabel("Gender : ");
		lblGender.setFont(new Font("Verdana", Font.BOLD, 16));
		lblGender.setBounds(275, 105, 100, 50);
		frame.getContentPane().add(lblGender);
		
		txtAge = new JTextField();
		txtAge.setFont(new Font("Verdana", Font.PLAIN, 16));
		txtAge.setColumns(10);
		txtAge.setBounds(133, 105, 117, 50);
		frame.getContentPane().add(txtAge);
		
		cmbGender = new JComboBox();
		cmbGender.setFont(new Font("Verdana", Font.BOLD, 16));
		cmbGender.setModel(new DefaultComboBoxModel(new String[] {"Male", "Female"}));
		cmbGender.setBounds(365, 106, 183, 50);
		frame.getContentPane().add(cmbGender);
		
		JLabel lblEmail = new JLabel("Email : ");
		lblEmail.setFont(new Font("Verdana", Font.BOLD, 16));
		lblEmail.setBounds(41, 209, 100, 50);
		frame.getContentPane().add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Verdana", Font.BOLD, 16));
		txtEmail.setColumns(10);
		txtEmail.setBounds(248, 209, 300, 50);
		frame.getContentPane().add(txtEmail);
		
		JLabel lblPassword = new JLabel("Password : ");
		lblPassword.setFont(new Font("Verdana", Font.BOLD, 16));
		lblPassword.setBounds(41, 298, 100, 50);
		frame.getContentPane().add(lblPassword);
		
		txtPassword = new JTextField();
		txtPassword.setFont(new Font("Verdana", Font.BOLD, 16));
		txtPassword.setColumns(10);
		txtPassword.setBounds(248, 298, 300, 50);
		frame.getContentPane().add(txtPassword);
		
		btnRegister = new JButton("Register");
		btnRegister.setFont(new Font("Verdana", Font.BOLD, 16));
		btnRegister.setBounds(41, 383, 507, 50);
		btnRegister.addActionListener(this);
		frame.getContentPane().add(btnRegister);
		
		JLabel lblAlreadyHaveGo = new JLabel("Already have go to login");
		lblAlreadyHaveGo.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlreadyHaveGo.setBounds(41, 449, 507, 16);
		frame.getContentPane().add(lblAlreadyHaveGo);
		
		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Verdana", Font.BOLD, 16));
		btnLogin.setBounds(41, 467, 507, 50);
		btnLogin.addActionListener(this);
		frame.getContentPane().add(btnLogin);
	}
	
	private Boolean emailExitCheck(String email) {
		Boolean emailExis = false;
		DbConnection dbConnection = new DbConnection();
		dbConnection.getCon();
		
		String checkUserSQL = "SELECT COUNT(email) AS existEmail FROM user WHERE email = '"+email+"';";
		
		
		try {
			ResultSet rs = dbConnection.statement.executeQuery(checkUserSQL);
			
			if (rs.next()) {
				if(rs.getInt("existEmail") > 0) {
					emailExis = true;
				}
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(emailExis);
		
		return emailExis;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnRegister) {
			String name = txtName.getText().toString().trim();
	        String gender = (String) cmbGender.getSelectedItem();
	        //String age =Integer.parseInt(txtAge.getText().toString());
	        String age =txtAge.getText().toString();
	        String email = txtEmail.getText().toString().trim();
	        String password = txtPassword.getText().toString().trim();
	        
			if(name.equals("") || gender.equals("") || age.equals("") || gender.equals("") || password.equals("")) {
				new JOptionPane();
				JOptionPane.showMessageDialog(frame, "You must fiil all field.");
			}else {
				if(emailExitCheck(email)==false) {
					//store new user into database
		            String addUserSQL="INSERT INTO `user`(`name`, `gerder`, `age`, `activity_level`, `email`, `password`) VALUES ('"
		                    + name+"', '"
		                    + gender+ "', "
		                    + Integer.parseInt(txtAge.getText().toString())+ ", '"
		                    +  "', '"
		                    + email+ "', '"
		                    + password+ "');";
		            
		            System.out.println(addUserSQL);
		            
		            
		            try {
		            		DbConnection dbConnection = new DbConnection();
		            		dbConnection.getCon();
		            		dbConnection.statement.executeUpdate(addUserSQL);
		            		frame.dispose();
		            		new Login();
		                
		            } catch (SQLException ex) {
		                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
		            }
		            
					
					
					
				}else {
					new JOptionPane();
					JOptionPane.showMessageDialog(frame, "Already Register with this email.");
				}
			}
			
		}else {
			frame.dispose();
			new Login();
			
		}
		
	}
}
