package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import controler.DbConnection;
import model.User;

import javax.swing.JButton;
import java.awt.Font;

public class Login {

	private JFrame frame;
	private JTextField txtEmail;
	private JTextField txtPassword;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Login();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	public Login() {
		initialize();
		frame.setVisible(true);
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Login");
		frame.setBounds(100, 100, 625, 426);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		JLabel lblEmail = new JLabel("Email : ");
		lblEmail.setFont(new Font("Verdana", Font.BOLD, 16));
		lblEmail.setBounds(146, 63, 100, 50);
		frame.getContentPane().add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Verdana", Font.BOLD, 16));
		txtEmail.setBounds(257, 63, 250, 50);
		frame.getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password : ");
		lblPassword.setFont(new Font("Verdana", Font.BOLD, 16));
		lblPassword.setBounds(146, 158, 100, 50);
		frame.getContentPane().add(lblPassword);
		
		txtPassword = new JTextField();
		txtPassword.setFont(new Font("Verdana", Font.BOLD, 16));
		txtPassword.setColumns(10);
		txtPassword.setBounds(257, 158, 250, 50);
		frame.getContentPane().add(txtPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Verdana", Font.BOLD, 16));
		btnLogin.setBounds(145, 242, 362, 50);
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				String email = txtEmail.getText().toString().trim();
		        String password = txtPassword.getText().toString().trim();
		        
		        if(email.equals("") || password.equals("") ) {
					new JOptionPane();
					JOptionPane.showMessageDialog(frame, "You must fiil all field.");
				}else {
					
					DbConnection dbConnection = new DbConnection();
					dbConnection.getCon();
					
					String checkUserSQL = "SELECT * FROM `user` WHERE `email`='"+email+"' AND `password`='"+password+"';";
					
					try {
						ResultSet rs = dbConnection.statement.executeQuery(checkUserSQL);
						if (rs.next()) {
							//close database connection
							
							frame.dispose();
							new DashBoard(new User(rs.getInt("u_id"), rs.getString("name"), rs.getString("gerder"), 
									rs.getString("activity_level"), rs.getString("email"), rs.getString("password"), rs.getInt("age")));
							//System.out.println(checkUserSQL);
							//System.out.println(rs.getInt("u_id"));
//							System.out.println(rs.getString(1));
//							System.out.println(rs.getString("gerder"));
//							System.out.println(rs.getString("activity_level"));
//							System.out.println(rs.getString("email"));
//							System.out.println(rs.getString("password"));
//							System.out.println(rs.getInt("age"));
							
							dbConnection.con.close();
						}else {
							JOptionPane.showMessageDialog(frame, "Sorry You Email and password is worng.");
						}
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
			
					
				}						
				
			}
		});
		frame.getContentPane().add(btnLogin);
		
		JLabel lblIfYouUser = new JLabel("If you user account you can register now.");
		lblIfYouUser.setBounds(195, 304, 288, 16);
		frame.getContentPane().add(lblIfYouUser);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.setFont(new Font("Verdana", Font.BOLD, 16));
		btnRegister.setBounds(145, 323, 362, 50);
		btnRegister.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new Register();
				
			}
		});
		frame.getContentPane().add(btnRegister);
	}
}
