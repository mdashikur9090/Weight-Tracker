package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controler.DbConnection;
import model.User;

public class ProfileUpdate implements ActionListener {
	private JFrame frame;
	private JTextField txtName;
	private JTextField txtAge;
	private JTextField txtEmail;
	private JTextField txtPassword;
	
	private JButton btnCancel;
	private JButton btnUpdate;
	
	private JComboBox cmbGender;
	
	private User user;

	/**
	 * Create the application.
	 */
	public ProfileUpdate(User user) {
		this.user = user;
		initialize();
		frame.setTitle("Profile Update");
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
		lblName.setBounds(41, 26, 100, 50);
		lblName.setFont(new Font("Verdana", Font.BOLD, 16));
		frame.getContentPane().add(lblName);
		
		txtName = new JTextField();
		txtName.setText(user.getName());
		txtName.setBounds(248, 26, 300, 50);
		txtName.setFont(new Font("Verdana", Font.BOLD, 16));
		frame.getContentPane().add(txtName);
		txtName.setColumns(10);
		
		JLabel lblAge = new JLabel("Age : ");
		lblAge.setBounds(41, 105, 80, 50);
		lblAge.setFont(new Font("Verdana", Font.BOLD, 16));
		frame.getContentPane().add(lblAge);
		
		JLabel lblGender = new JLabel("Gender : ");
		lblGender.setFont(new Font("Verdana", Font.BOLD, 16));
		lblGender.setBounds(275, 105, 100, 50);
		frame.getContentPane().add(lblGender);
		
		txtAge = new JTextField();
		txtAge.setColumns(10);
		txtAge.setFont(new Font("Verdana", Font.BOLD, 16));
		txtAge.setText(String.valueOf(user.getAge()));
		txtAge.setBounds(133, 105, 117, 50);
		frame.getContentPane().add(txtAge);
		
		cmbGender = new JComboBox();
		cmbGender.setFont(new Font("Verdana", Font.BOLD, 16));
		cmbGender.setModel(new DefaultComboBoxModel(new String[] {"Male", "Female"}));
		if(user.getGender().equals("Male")) {
			cmbGender.setSelectedIndex(0);
		}else {
			cmbGender.setSelectedIndex(1);
		}
		cmbGender.setBounds(365, 106, 183, 50);
		frame.getContentPane().add(cmbGender);
		
		JLabel lblEmail = new JLabel("Email : ");
		lblEmail.setBounds(41, 209, 100, 50);
		lblEmail.setFont(new Font("Verdana", Font.BOLD, 16));
		frame.getContentPane().add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setFont(new Font("Verdana", Font.BOLD, 16));
		txtEmail.setBounds(248, 209, 300, 50);
		txtEmail.setText(user.getEmail());
		txtEmail.setEditable(false);
		frame.getContentPane().add(txtEmail);
		
		JLabel lblPassword = new JLabel("Password : ");
		lblPassword.setFont(new Font("Verdana", Font.BOLD, 16));
		lblPassword.setBounds(41, 298, 100, 50);
		frame.getContentPane().add(lblPassword);
		
		txtPassword = new JTextField();
		txtPassword.setColumns(10);
		txtPassword.setFont(new Font("Verdana", Font.BOLD, 16));
		txtPassword.setText(user.getPassword());
		txtPassword.setBounds(248, 298, 300, 50);
		frame.getContentPane().add(txtPassword);
		
		btnUpdate = new JButton("Update");
		btnUpdate.setBounds(41, 383, 507, 50);
		btnUpdate.setFont(new Font("Verdana", Font.BOLD, 16));
		btnUpdate.addActionListener(this);
		frame.getContentPane().add(btnUpdate);
		
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(41, 467, 507, 50);
		btnCancel.setFont(new Font("Verdana", Font.BOLD, 16));
		btnCancel.addActionListener(this);
		frame.getContentPane().add(btnCancel);
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnUpdate) {
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
					//store new user into database
		            String profileUpdateSQL="UPDATE `user` SET name='"+name
		                    + "', gerder='"+gender
		                    + "', age="+Integer.parseInt(txtAge.getText().toString())
		                    + ", password ='"+password
		                    + "' WHERE u_id="+user.getUser_id();
		            
		            System.out.println(profileUpdateSQL);
		            
		            
		            try {
		            		DbConnection dbConnection = new DbConnection();
		            		dbConnection.getCon();
		            		dbConnection.statement.executeUpdate(profileUpdateSQL);
		            		
		            		
		            		user.setName(name);
		            		user.setGender(gender);
		            		user.setAge(Integer.parseInt(txtAge.getText().toString()));
		            		user.setPassword(password);
		            		frame.dispose();
		            		new DashBoard(user);
		                
		            } catch (SQLException ex) {
		                Logger.getLogger(ProfileUpdate.class.getName()).log(Level.SEVERE, null, ex);
		            }
		            
		
			}
			
		}else {
			frame.dispose();
			new DashBoard(user);
		}
		
	}
}
