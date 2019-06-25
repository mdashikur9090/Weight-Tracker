package view;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jfree.ui.RefineryUtilities;

import controler.CalculateCaloriesDeficiency;
import controler.DbConnection;
import model.Course;
import model.Exercise;
import model.Meal;
import model.User;
import model.Waist;
import model.Weight;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.BoxLayout;

public class DashBoard implements ActionListener {

	private JFrame frame;
	
	private JPanel jpCourseList;
	private JPanel jpViewBoard;
	
	private JTable jtbOutput;
	
	private JButton btnActivityEdit;
	private JButton btnCourseAdd;
	private JButton btnCourseDelete;
	private JButton btnMeal;
	private JButton btnExercise;
	private JButton btnWeight;
	private JButton btnWaist;
	private JButton btnMaintain;
	private JButton btnUpdateProfile;
	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnDelete;
	private JButton btnChart;
	private JButton btnAnalysis;
	
	private User user;
	private ArrayList<Course> courseList = new ArrayList<Course>();
	
	private DbConnection dbConnection = new DbConnection();
	
	private int currentViewCourseId;	
	
	public JTable mealTable;
    public DefaultTableModel mealTableModel;
    
    public JTable exerciseTable;
    public DefaultTableModel exerciseTableModel;
    
    public JTable weightTable;
    public DefaultTableModel weightTableModel;

    public JTable waistTable;
    public DefaultTableModel waistTableModel;
    
    public JTable analysisTable;
    public DefaultTableModel analysisTableModel;
    
    public static String currentTableView="";
    public static String currentAddViewTable="";
    
    

	/**
	 * Create the application.
	 */
	public DashBoard(User user) {
		this.user = user;
		initialize();
		viewAllCourse();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("DashBoard");
		frame.setBounds(100, 100, 930, 770);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel jpUserInformation = new JPanel();
		jpUserInformation.setBorder(new LineBorder(new Color(0, 0, 0)));
		jpUserInformation.setBounds(6, 6, 514, 320);
		frame.getContentPane().add(jpUserInformation);
		jpUserInformation.setLayout(null);
		
		JLabel lblName = new JLabel("Log As : ");
		lblName.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 20));
		lblName.setText("Log As: "+user.getName());
		lblName.setBounds(45, 30, 355, 50);
		jpUserInformation.add(lblName);
		
		JLabel lblGender = new JLabel("Gender");
		lblGender.setFont(new Font("Verdana", Font.BOLD, 16));
		lblGender.setText("Gender : "+user.getGender());
		lblGender.setBounds(45, 118, 151, 32);
		jpUserInformation.add(lblGender);
		
		JLabel lblAge = new JLabel("Age: ");
		lblAge.setFont(new Font("Verdana", Font.BOLD, 16));
		lblAge.setText("Age : "+user.getAge());
		lblAge.setBounds(357, 118, 151, 32);
		jpUserInformation.add(lblAge);
		
		JLabel lblActivityLevel = new JLabel("Activity Level : ");
		lblActivityLevel.setFont(new Font("Verdana", Font.BOLD, 16));
		lblActivityLevel.setText("Activity : "+user.getActivity_level());
		lblActivityLevel.setBounds(45, 201, 245, 50);
		jpUserInformation.add(lblActivityLevel);
		
		btnActivityEdit = new JButton("Edit");
		btnActivityEdit.setFont(new Font("Verdana", Font.BOLD, 16));
		btnActivityEdit.setBounds(357, 202, 75, 50);
		btnActivityEdit.addActionListener(this);
		jpUserInformation.add(btnActivityEdit);
		
		JPanel jpCourse = new JPanel();
		jpCourse.setBorder(new LineBorder(new Color(0, 0, 0)));
		jpCourse.setBounds(532, 33, 388, 293);
		frame.getContentPane().add(jpCourse);
		jpCourse.setLayout(null);
		
		btnCourseAdd = new JButton("Add");
		btnCourseAdd.setFont(new Font("Verdana", Font.BOLD, 13));
		btnCourseAdd.setBounds(6, 237, 182, 50);
		btnCourseAdd.addActionListener(this);
		jpCourse.add(btnCourseAdd);
		
		btnCourseDelete = new JButton("Delete");
		btnCourseDelete.setFont(new Font("Verdana", Font.BOLD, 13));
		btnCourseDelete.setBounds(200, 237, 182, 50);
		btnCourseDelete.setEnabled(false);
		btnCourseDelete.addActionListener(this);
		jpCourse.add(btnCourseDelete);
		
		jpCourseList = new JPanel();
		jpCourseList.setLayout(new GridLayout(0, 1));
		//panel_1.add(jpCourseList);
		
		JScrollPane jspCourseList = new JScrollPane();
		jspCourseList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jspCourseList.setBounds(6, 6, 376, 228);
		jspCourseList.setViewportView(jpCourseList);
		jpCourse.add(jspCourseList);
		
		JPanel jpButtonSection = new JPanel();
		jpButtonSection.setBorder(new LineBorder(new Color(0, 0, 0)));
		jpButtonSection.setBounds(6, 338, 331, 404);
		frame.getContentPane().add(jpButtonSection);
		jpButtonSection.setLayout(null);
		
		btnMaintain = new JButton("Maintain Weight");
		btnMaintain.setFont(new Font("Verdana", Font.BOLD, 13));
		btnMaintain.setBounds(175, 31, 150, 50);
		btnMaintain.setEnabled(false);
		btnMaintain.addActionListener(this);
		jpButtonSection.add(btnMaintain);
		
		btnUpdateProfile = new JButton("Update Profile");
		btnUpdateProfile.setFont(new Font("Verdana", Font.BOLD, 13));
		btnUpdateProfile.setBounds(6, 31, 150, 50);
		btnUpdateProfile.addActionListener(this);
		jpButtonSection.add(btnUpdateProfile);
		
		btnMeal = new JButton("Meal");
		btnMeal.setFont(new Font("Verdana", Font.BOLD, 13));
		btnMeal.setBounds(6, 124, 150, 50);
		btnMeal.setEnabled(false);
		btnMeal.addActionListener(this);
		jpButtonSection.add(btnMeal);
		
		btnExercise = new JButton("Exercise");
		btnExercise.setFont(new Font("Verdana", Font.BOLD, 13));
		btnExercise.setBounds(175, 124, 150, 50);
		btnExercise.addActionListener(this);
		btnExercise.setEnabled(false);
		jpButtonSection.add(btnExercise);
		
		btnWeight = new JButton("Weight");
		btnWeight.setFont(new Font("Verdana", Font.BOLD, 13));
		btnWeight.setBounds(6, 232, 150, 50);
		btnWeight.addActionListener(this);
		btnWeight.setEnabled(false);
		jpButtonSection.add(btnWeight);
		
		btnWaist = new JButton("Waist");
		btnWaist.setFont(new Font("Verdana", Font.BOLD, 13));
		btnWaist.setBounds(175, 232, 150, 50);
		btnWaist.addActionListener(this);
		btnWaist.setEnabled(false);
		jpButtonSection.add(btnWaist);
		
		btnAnalysis = new JButton("Analysis");
		btnAnalysis.setFont(new Font("Verdana", Font.BOLD, 13));
		btnAnalysis.setBounds(6, 334, 150, 50);
		btnAnalysis.addActionListener(this);
		btnAnalysis.setEnabled(false);
		jpButtonSection.add(btnAnalysis);
		
		btnChart = new JButton("Weight Change Chart");
		btnChart.setFont(new Font("Verdana", Font.BOLD, 11));
		btnChart.setBounds(175, 334, 150, 50);
		btnChart.addActionListener(this);
		btnChart.setEnabled(false);
		jpButtonSection.add(btnChart);
		
		JPanel jpOutputSection = new JPanel();
		jpOutputSection.setBounds(349, 338, 571, 404);
		frame.getContentPane().add(jpOutputSection);
		jpOutputSection.setLayout(null);
		
		btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Verdana", Font.BOLD, 13));
		btnAdd.setBounds(6, 363, 117, 35);
		btnAdd.setEnabled(false);
		btnAdd.addActionListener(this);
		jpOutputSection.add(btnAdd);
		
		btnEdit = new JButton("Edit");
		btnEdit.setFont(new Font("Verdana", Font.BOLD, 13));
		btnEdit.setBounds(235, 363, 117, 35);
		btnEdit.setEnabled(false);
		btnEdit.addActionListener(this);
		jpOutputSection.add(btnEdit);
		
		btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Verdana", Font.BOLD, 13));
		btnDelete.setBounds(448, 363, 117, 35);
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(this);
		jpOutputSection.add(btnDelete);
		
		jpViewBoard = new JPanel();
		jpViewBoard.setBorder(new LineBorder(new Color(0, 0, 0)));
		jpViewBoard.setBounds(6, 6, 559, 352);
		jpOutputSection.add(jpViewBoard);
		jpViewBoard.setLayout(null);
		
		JLabel lblPleaseSelectAt = new JLabel("Please Select At Lest One Coure. If No Coure Available You Need to Create One");
		lblPleaseSelectAt.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblPleaseSelectAt.setHorizontalAlignment(SwingConstants.CENTER);
		lblPleaseSelectAt.setBounds(6, 131, 547, 88);
		jpViewBoard.add(lblPleaseSelectAt);
		
	
		
		JLabel lblCourseList = new JLabel("Course List");
		lblCourseList.setFont(new Font("Verdana", Font.BOLD, 14));
		lblCourseList.setHorizontalAlignment(SwingConstants.CENTER);
		lblCourseList.setBounds(532, 6, 388, 25);
		frame.getContentPane().add(lblCourseList);
	}

	private void enableButtonByCourseSelect() {
		btnMaintain.setEnabled(true);
		btnMeal.setEnabled(true);
		btnExercise.setEnabled(true);
		btnWeight.setEnabled(true);
		btnWaist.setEnabled(true);
		btnChart.setEnabled(true);
		btnAnalysis.setEnabled(true);
		btnCourseDelete.setEnabled(true);
	}
	private void enableButton() {
		btnAdd.setEnabled(true);
		btnEdit.setEnabled(true);
		btnDelete.setEnabled(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnActivityEdit) {
			editActivityLevel();
		}else if(e.getSource()==btnCourseAdd) {
			courseAdd();
			
		}else if(e.getSource()==btnCourseDelete) {
			deleteCourse(currentViewCourseId);
			
		}else if(e.getSource()==btnUpdateProfile) {
			frame.dispose();
			ProfileUpdate profileUpdate = new ProfileUpdate(user);
			
			
		}else if(e.getSource()==btnMaintain) {
			maintainWeight(currentViewCourseId);
		}else if(e.getSource()==btnMeal) {
			viewMeal(currentViewCourseId);
			System.out.println("btn Meal Click");
		}else if(e.getSource()==btnExercise) {
			viewExercise(currentViewCourseId);
			System.out.println("btn Exercise Click");
			
		}else if(e.getSource()==btnWeight) {
			viewWeight(currentViewCourseId);
			System.out.println("btn Weight Click");
			
		}else if(e.getSource()==btnWaist) {
			viewWaist(currentViewCourseId);
			System.out.println("btn Waist Click");
			
		}else if(e.getSource()==btnAdd) {
			if(currentAddViewTable.equals("mealTable")) {
				System.out.println("Add Meal");
				addMeal(currentViewCourseId);
				
			}else if(currentAddViewTable.equals("exerciseTable")) {
				System.out.println("Add Exercise");
				addExercise(currentViewCourseId);
				
			}else if(currentAddViewTable.equals("weightTable")) {
				System.out.println("Add Weight");
				addWeight(currentViewCourseId);
				
			}else if(currentAddViewTable.equals("waistTable")) {
				System.out.println("Add Waist");
				addWaist(currentViewCourseId);
			}
			
			
		}else if(e.getSource()==btnEdit) {
			if(currentAddViewTable.equals("mealTable")) {
				System.out.println("Edit Meal");
				
				int a = mealTable.getSelectedRow();
                if(a >= 0) 
                {
                    Meal userMeal = new Meal(Integer.parseInt(mealTableModel.getValueAt(a, 0).toString()), 
                            mealTableModel.getValueAt(a, 1).toString(),
                            mealTableModel.getValueAt(a, 2).toString(), 
                            Float.parseFloat((String) mealTableModel.getValueAt(a, 3).toString()), 
                            Float.parseFloat((String) mealTableModel.getValueAt(a, 4).toString()), 
                            Float.parseFloat((String) mealTableModel.getValueAt(a, 5).toString()));
                    
                    //weightTableModel.removeRow(i);
                    editMeal(currentViewCourseId, userMeal);
                   
                }
				
			}else if(currentAddViewTable.equals("exerciseTable")) {
				System.out.println("Exercise");
				int b = exerciseTable.getSelectedRow();
                if(b >= 0) 
                {
                    Exercise userExercise = new Exercise(Integer.parseInt((String) exerciseTableModel.getValueAt(b, 0).toString()), 
                            exerciseTableModel.getValueAt(b, 1).toString(),
                            exerciseTableModel.getValueAt(b, 2).toString(), 
                            Float.parseFloat((String) exerciseTableModel.getValueAt(b, 3).toString()),
                            Float.parseFloat((String) exerciseTableModel.getValueAt(b, 4).toString()),
                            Float.parseFloat((String) exerciseTableModel.getValueAt(b, 5).toString()));
                    
                    editExercise(currentViewCourseId, userExercise);
                   
                }
                else{
                    System.out.println("Update Error");
                }
				
			}else if(currentAddViewTable.equals("weightTable")) {
				System.out.println("Weight");
				int i = weightTable.getSelectedRow();
                if(i >= 0) 
                {
                    Weight userWeight = new Weight(Integer.parseInt((String) weightTableModel.getValueAt(i, 0)), 
                            weightTableModel.getValueAt(i, 1).toString(),
                            Float.parseFloat(weightTableModel.getValueAt(i, 2).toString()), 
                            Float.parseFloat((String) weightTableModel.getValueAt(i, 3).toString()), 
                            Float.parseFloat((String) weightTableModel.getValueAt(i, 4).toString()));
                    
                    editWeight(currentViewCourseId, userWeight);
                   
                }
                else{
                    System.out.println("Update Error");
                }
				
			
				
				
			}else if(currentAddViewTable.equals("waistTable")) {
				System.out.println("Edit Waist");
				int j = waistTable.getSelectedRow();
                if(j >= 0) 
                {
                    Waist userWaist = new Waist(Integer.parseInt((String) waistTableModel.getValueAt(j, 0)), 
                            waistTableModel.getValueAt(j, 1).toString(),
                            Float.parseFloat(waistTableModel.getValueAt(j, 2).toString()), 
                            Float.parseFloat((String) waistTableModel.getValueAt(j, 3).toString()), 
                            Float.parseFloat((String) waistTableModel.getValueAt(j, 4).toString()));
                            
                    
                  
                    editWaist(currentViewCourseId, userWaist);
                   
                }
				
				
			}
		}else if(e.getSource()==btnDelete) {
			if(currentAddViewTable.equals("mealTable")) {
				System.out.println("Delete Meal");
				int a = mealTable.getSelectedRow();
                if(a >= 0) 
                {
                    Meal userMeal = new Meal(Integer.parseInt(mealTableModel.getValueAt(a, 0).toString()), 
                            mealTableModel.getValueAt(a, 1).toString(),
                            mealTableModel.getValueAt(a, 2).toString(), 
                            Float.parseFloat((String) mealTableModel.getValueAt(a, 3).toString()),
                            Float.parseFloat((String) mealTableModel.getValueAt(a, 4).toString()), 
                            Float.parseFloat((String) mealTableModel.getValueAt(a, 5).toString()));
                    
                    
                    deleteMeal(currentViewCourseId, userMeal);
                    viewMeal(currentViewCourseId);
                   
                }
                else{
                    System.out.println("Delete Error");
                }
				
			}else if(currentAddViewTable.equals("exerciseTable")) {
				System.out.println("Delete Exercise");
				int b = exerciseTable.getSelectedRow();
                if(b >= 0) 
                {
                    Exercise userExercise = new Exercise(Integer.parseInt((String) exerciseTableModel.getValueAt(b, 0).toString()), 
                            exerciseTableModel.getValueAt(b, 1).toString(),
                            exerciseTableModel.getValueAt(b, 2).toString(), 
                            Float.parseFloat((String) exerciseTableModel.getValueAt(b, 3).toString()),
                            Float.parseFloat((String) exerciseTableModel.getValueAt(b, 4).toString()),
                            Float.parseFloat((String) exerciseTableModel.getValueAt(b, 5).toString()));
                    
                   
                    deleteExercise(currentViewCourseId, userExercise);
                    viewExercise(currentViewCourseId);
                   
                }
                else{
                    System.out.println("Delete Error");
                }
				
				
			}else if(currentAddViewTable.equals("weightTable")) {
				System.out.println("Delete Weight");
				int i = weightTable.getSelectedRow();
                if(i >= 0) 
                {
                    Weight userWeight = new Weight(Integer.parseInt((String) weightTableModel.getValueAt(i, 0)), 
                            weightTableModel.getValueAt(i, 1).toString(),
                            Float.parseFloat(weightTableModel.getValueAt(i, 2).toString()), 
                            Float.parseFloat((String) weightTableModel.getValueAt(i, 3).toString()), 
                            Float.parseFloat((String) weightTableModel.getValueAt(i, 4).toString()));
                    
                  
                    deleteWeight(currentViewCourseId, userWeight);
                    viewWeight(currentViewCourseId);
                   
                }
                else{
                    System.out.println("Delete Error");
                }
				
			}else if(currentAddViewTable.equals("waistTable")) {
				System.out.println("Delete Waist");
				int j = waistTable.getSelectedRow();
                if(j >= 0) 
                {
                    Waist userWaist = new Waist(Integer.parseInt((String) waistTableModel.getValueAt(j, 0)), 
                            waistTableModel.getValueAt(j, 1).toString(),
                            Float.parseFloat(waistTableModel.getValueAt(j, 2).toString()), 
                            Float.parseFloat((String) waistTableModel.getValueAt(j, 3).toString()), 
                            Float.parseFloat((String) waistTableModel.getValueAt(j, 4).toString()));
                            
                    
                    
                    deleteWaist(currentViewCourseId, userWaist);
                    viewWaist(currentViewCourseId);
                   
                }
                else{
                    System.out.println("Delete Error");
                }
				
			}
			
		}else if(e.getSource()==btnChart) {
			System.out.println("ShowLineChart");
            final Chart showLineChart = new Chart("Weight Changes in Line Chart", user.getUser_id(), currentViewCourseId);
            showLineChart.pack();
            RefineryUtilities.centerFrameOnScreen(showLineChart);
            showLineChart.setVisible(true); 
			
		}else if(e.getSource()==btnAnalysis) {
			CalculateCaloriesDeficiency calculateCaloriesDeficiency = new CalculateCaloriesDeficiency(user.getUser_id(), currentViewCourseId);
			analysis(calculateCaloriesDeficiency.getSelectedDateList(),  calculateCaloriesDeficiency.getMealCalories(), calculateCaloriesDeficiency.getExerciseCalories());
	        
		}
		
	}
	
	
	private void viewWaist(int currentViewCourseId) {
		enableButton();
		currentAddViewTable="waistTable";
	    jpViewBoard.removeAll();

	    waistTable = new JTable(); 

	    // create a weightTable weightTablemModel and set a Column Identifiers to this weightTablemModel 
	    Object[] columns = {"ID", "Date","Morning Weight","Evening Weight","Average"};
	    waistTableModel = new DefaultTableModel();
	    waistTableModel.setColumnIdentifiers(columns);

	    // set the weightTablemModel to the weightTable
	    waistTable.setModel(waistTableModel);

	    // Change A JTable Background Color, Font Size, Font Color, Row Height
	    waistTable.setBackground(Color.LIGHT_GRAY);
	    waistTable.setForeground(Color.black);
	    Font font = new Font("",1,14);
	    waistTable.setFont(font);
	    waistTable.setRowHeight(18);

	    //

	    //get database value
	    String weightSQL = "SELECT * FROM `waist` WHERE `user_id`="+user.getUser_id()+" AND `course_id`="+currentViewCourseId+";";
	    ResultSet rsWeight;
	    try {
	    		dbConnection.getCon();
	        rsWeight = dbConnection.statement.executeQuery(weightSQL);
	        while(rsWeight.next()){
	                // create an array of objects to set the row data
	                Object[] row = new Object[5];

	                row[0] = rsWeight.getString("id");
	                row[1] = rsWeight.getString("m_date");
	                row[2] = rsWeight.getString("mor_waist");
	                row[3] = rsWeight.getString("eve_waist");
	                row[4] = ((Float.parseFloat(rsWeight.getString("mor_waist"))+Float.parseFloat(rsWeight.getString("eve_waist")))/2);
	                waistTableModel.addRow(row);

	            }
	    } catch (SQLException ex) {
	        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
	    }



	    jpViewBoard.setLayout(new BoxLayout(jpViewBoard, BoxLayout.Y_AXIS));


	    JPanel jPanel = new JPanel();
	    jPanel.setLayout(new BorderLayout());

	    JScrollPane jspWaist = new JScrollPane(jPanel);
	    jPanel.add(waistTable);
	    jspWaist.setViewportView(waistTable);

	    //set mouse listener
	    waistTable.addMouseListener(new MouseInputAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e){

	                currentTableView="waistTable";
	            }
	        });


	    //now outpu panel add
	    jpViewBoard.add(jspWaist);
	    jpViewBoard.revalidate();
	}
	private void addWaist(int currentViewCourseId) {
		JPanel jpaAddWaist = new JPanel();
        
	    Calendar today = Calendar.getInstance();

	    Date date = new Date();
	    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    UtilDateModel model = new UtilDateModel();
	    model.setDate(localDate.getYear(), localDate.getMonthValue()-1, localDate.getDayOfMonth());
	    model.setSelected(true);



	    Properties p = new Properties();
	    p.put("text.today", "Today");
	    p.put("text.month", "Month");
	    p.put("text.year", "Year");
	    JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
	    JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

	    //add jdate picker into panel
	    jpaAddWaist.add(datePicker);


	    JTextField jtfMorningWaist = new JTextField(10);
	    JTextField jtfEveningWaist = new JTextField(10);


	    jpaAddWaist.add(new JLabel("Morning:"));
	    jpaAddWaist.add(jtfMorningWaist);
	    jpaAddWaist.add(Box.createHorizontalStrut(15)); // a spacer
	    jpaAddWaist.add(new JLabel("Evening:"));
	    jpaAddWaist.add(jtfEveningWaist);

	    int result = JOptionPane.showConfirmDialog(null, jpaAddWaist, 
	             "Please Enter Morning And Evening Waist.", JOptionPane.OK_CANCEL_OPTION);
	    if (result == JOptionPane.OK_OPTION) {
	        Date selectedDate = (Date) datePicker.getModel().getValue();
	        SimpleDateFormat dateFormate = new SimpleDateFormat("dd-MM-yyyy");
	        if(jtfMorningWaist.getText().equals("") || jtfEveningWaist.getText().equals("")){

	            System.out.println("Must be require all fields");


	        }else{
	            System.out.println("EveryThing Is Working"+dateFormate.format(selectedDate));

	            try {
	                String sql = "SELECT `id` FROM `waist` WHERE `m_date`='"+dateFormate.format(selectedDate)+"';";
	                dbConnection.getCon();
	                ResultSet rs = dbConnection.statement.executeQuery(sql);

	                if(rs.next()){
	                    System.out.println("Already Input Waist On this date. So you cant Add new on this date but can edit");
	                    dbConnection.con.close();
	                    JOptionPane.showMessageDialog(frame, "Already Input Waist On this date.");
	                    
	                }else{
	                    String inputUerDateSQL = "INSERT INTO `waist`(`user_id`, `course_id`, `m_date`, `mor_waist`, `eve_waist`) VALUES ("
	                    							+user.getUser_id()+", "
	                    							+currentViewCourseId+", '"
	                    							+dateFormate.format(selectedDate)+"', "
	                    							+jtfMorningWaist.getText()+", "
	                    							+jtfEveningWaist.getText()+");";
	                               System.out.println(inputUerDateSQL);
	                               dbConnection.statement.executeUpdate(inputUerDateSQL);
	                               
	                               
	                               //get the data again because data table updated
	                               viewWaist(currentViewCourseId);
	                }

	            } catch (SQLException ex) {
	                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }
	    }
		
	}
	private void editWaist(int currentViewCourseId, Waist userWaist) {
		JPanel jpaEditWaist = new JPanel();
        
	    

	    Date date = new Date();
	    UtilDateModel model = new UtilDateModel();
	    model.setDate(Integer.parseInt(userWaist.getM_date().substring(6, 10)), 
	            Integer.parseInt(userWaist.getM_date().substring(3, 5))-1, 
	            Integer.parseInt(userWaist.getM_date().substring(0, 2)));
	    model.setSelected(true);



	    Properties p = new Properties();
	    p.put("text.today", "Today");
	    p.put("text.month", "Month");
	    p.put("text.year", "Year");
	    JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
	    JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

	    //add jdate picker into panel
	    jpaEditWaist.add(datePicker);


	    JTextField jtfMorningWaist = new JTextField(10);
	    jtfMorningWaist.setText(String.valueOf(userWaist.getMor_waist()));
	    JTextField jtfEveningWaist = new JTextField(10);
	    jtfEveningWaist.setText(String.valueOf(userWaist.getEve_waist()));


	    jpaEditWaist.add(new JLabel("Morning Waist:"));
	    jpaEditWaist.add(jtfMorningWaist);
	    jpaEditWaist.add(Box.createHorizontalStrut(15)); // a spacer
	    jpaEditWaist.add(new JLabel("Evening Waist:"));
	    jpaEditWaist.add(jtfEveningWaist);

	    int result = JOptionPane.showConfirmDialog(null, jpaEditWaist, 
	             "Wasit edit.", JOptionPane.OK_CANCEL_OPTION);
	    if (result == JOptionPane.OK_OPTION) {
	        Date selectedDate = (Date) datePicker.getModel().getValue();
	        SimpleDateFormat dateFormate = new SimpleDateFormat("dd-MM-yyyy");
	        if(jtfMorningWaist.getText().equals("") || jtfEveningWaist.getText().equals("")){

	            System.out.println("Must be require all fields");


	        }else{
	            System.out.println("EveryThing Is Working"+dateFormate.format(selectedDate));

	            try {
	            		dbConnection.getCon();
	                String sql = "SELECT `id` FROM `waist` WHERE user_id="+user.getUser_id()
	                										+" AND course_id="+currentViewCourseId
	                										+" AND m_date='"+dateFormate.format(selectedDate)+"';";
	                ResultSet rs = dbConnection.statement.executeQuery(sql);

	                if(rs.next()){
	                		if(rs.getInt("id")==userWaist.getId()) {
	                			String inputUerDateSQL = "UPDATE `waist` SET `m_date`='"
	    	                            +dateFormate.format(selectedDate)+"', `mor_waist`="
	    	                            +jtfMorningWaist.getText()+", `eve_waist`="
	    	                            +jtfEveningWaist.getText()+" WHERE `id`="+userWaist.getId()+" AND user_id="
	    	                            +user.getUser_id()+" AND course_id="
	    	                            +currentViewCourseId+";";
	    	                    
	    	                            System.out.println(inputUerDateSQL);
	    	                            dbConnection.statement.executeUpdate(inputUerDateSQL);
	    	                            dbConnection.con.close();
	    	                            
	    	                            waistTableModel.setValueAt(dateFormate.format(selectedDate), waistTable.getSelectedRow(), 1);
	    	                            waistTableModel.setValueAt(jtfMorningWaist.getText(), waistTable.getSelectedRow(), 2);
	    	                            waistTableModel.setValueAt(jtfEveningWaist.getText(), waistTable.getSelectedRow(), 3);
	    	                            Float average = Float.parseFloat(jtfMorningWaist.getText())+Float.parseFloat(jtfEveningWaist.getText());
	    	                            waistTableModel.setValueAt(average/2, waistTable.getSelectedRow(), 4);
	    	                            
	                		}else {
	                			System.out.println("Already Input Weight On this date.");
	    	                     dbConnection.con.close();
	                			JOptionPane.showMessageDialog(frame, "Already Input Waist On this date.");
	                		}
	                    
	                }else{
	                    String inputUerDateSQL = "UPDATE `waist` SET `m_date`='"
	                            +dateFormate.format(selectedDate)+"', `mor_waist`="
	                            +jtfMorningWaist.getText()+", `eve_waist`="
	                            +jtfEveningWaist.getText()+" WHERE `id`="+userWaist.getId()+" AND user_id="
	                            +user.getUser_id()+" AND course_id="
	                            +currentViewCourseId+";";
	                    
	                            System.out.println(inputUerDateSQL);
	                            dbConnection.statement.executeUpdate(inputUerDateSQL);
	                            dbConnection.con.close();
	                            
	                            waistTableModel.setValueAt(dateFormate.format(selectedDate), waistTable.getSelectedRow(), 1);
	                            waistTableModel.setValueAt(jtfMorningWaist.getText(), waistTable.getSelectedRow(), 2);
	                            waistTableModel.setValueAt(jtfEveningWaist.getText(), waistTable.getSelectedRow(), 3);
	                            Float average = Float.parseFloat(jtfMorningWaist.getText())+Float.parseFloat(jtfEveningWaist.getText());
	                            waistTableModel.setValueAt(average/2, waistTable.getSelectedRow(), 4);
	                }

	            } catch (SQLException ex) {
	                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }
	    }
	}
	private void deleteWaist(int currentViewCourseId, Waist userWaist ) {
		try {
	        String SQL = "DELETE FROM `waist` WHERE `id`="+userWaist.getId()+" AND user_id="+user.getUser_id()+" AND course_id="+currentViewCourseId+";";
	        System.out.println(SQL);
	        dbConnection.getCon();
	        dbConnection.statement.executeUpdate(SQL);
	        dbConnection.con.close();

	    } catch (SQLException ex) {
	        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	
	private void viewWeight(int currentViewCourseId) {
		enableButton();
		currentAddViewTable="weightTable";
	    jpViewBoard.removeAll();

	    weightTable = new JTable(); 

	    // create a weightTable weightTablemModel and set a Column Identifiers to this weightTablemModel 
	    Object[] columns = {"ID", "Date","Morning Weight","Evening Weight","Average"};
	    weightTableModel = new DefaultTableModel();
	    weightTableModel.setColumnIdentifiers(columns);

	    // set the weightTablemModel to the weightTable
	    weightTable.setModel(weightTableModel);

	    // Change A JTable Background Color, Font Size, Font Color, Row Height
	    weightTable.setBackground(Color.LIGHT_GRAY);
	    weightTable.setForeground(Color.black);
	    Font font = new Font("",1,14);
	    weightTable.setFont(font);
	    weightTable.setRowHeight(18);

	    //

	    //get database value
	    String weightSQL = "SELECT * FROM `weight` WHERE `user_id`="+user.getUser_id()+" AND `course_id`="+currentViewCourseId+";";
	    ResultSet rsWeight;
	    try {
	    		dbConnection.getCon();
	        rsWeight = dbConnection.statement.executeQuery(weightSQL);
	        while(rsWeight.next()){
	                // create an array of objects to set the row data
	                Object[] row = new Object[5];

	                row[0] = rsWeight.getString("id");
	                row[1] = rsWeight.getString("m_date");
	                row[2] = rsWeight.getString("mor_weight");
	                row[3] = rsWeight.getString("eve_weight");
	                row[4] = ((Float.parseFloat(rsWeight.getString("mor_weight"))+Float.parseFloat(rsWeight.getString("eve_weight")))/2);
	                weightTableModel.addRow(row);

	            }
	        dbConnection.con.close();
	    } catch (SQLException ex) {
	        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
	    }



	    jpViewBoard.setLayout(new BoxLayout(jpViewBoard, BoxLayout.Y_AXIS));


	    JPanel jPanel = new JPanel();
	    jPanel.setLayout(new BorderLayout());

	    JScrollPane jScrollPane = new JScrollPane(jPanel);
	    jPanel.add(weightTable);
	    jScrollPane.setViewportView(weightTable);

	    //set mouse listener
	    weightTable.addMouseListener(new MouseInputAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e){

	                currentTableView="weightTable";
	            }
	        });


	    //now outpu panel add
	    jpViewBoard.add(jScrollPane);
	    jpViewBoard.revalidate();
		
	}
	private void addWeight(int currentViewCourseId) {
		JPanel jpaAddWeight = new JPanel();
        
	    Calendar today = Calendar.getInstance();

	    Date date = new Date();
	    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    UtilDateModel model = new UtilDateModel();
	    model.setDate(localDate.getYear(), localDate.getMonthValue()-1, localDate.getDayOfMonth());
	    model.setSelected(true);



	    Properties p = new Properties();
	    p.put("text.today", "Today");
	    p.put("text.month", "Month");
	    p.put("text.year", "Year");
	    JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
	    JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

	    //add jdate picker into panel
	    jpaAddWeight.add(datePicker);


	    JTextField jtfMorningWeight = new JTextField(10);
	    JTextField jtfEveningWeight = new JTextField(10);


	    jpaAddWeight.add(new JLabel("Morning:"));
	    jpaAddWeight.add(jtfMorningWeight);
	    jpaAddWeight.add(Box.createHorizontalStrut(15)); // a spacer
	    jpaAddWeight.add(new JLabel("Evening:"));
	    jpaAddWeight.add(jtfEveningWeight);

	    int result = JOptionPane.showConfirmDialog(null, jpaAddWeight, 
	             "Please Enter Morning And Evening Weight.", JOptionPane.OK_CANCEL_OPTION);
	    if (result == JOptionPane.OK_OPTION) {
	        Date selectedDate = (Date) datePicker.getModel().getValue();
	        SimpleDateFormat dateFormate = new SimpleDateFormat("dd-MM-yyyy");
	        if(jtfMorningWeight.getText().equals("") || jtfEveningWeight.getText().equals("")){

	            System.out.println("Must be require all fields");


	        }else{
	            System.out.println("EveryThing Is Working"+dateFormate.format(selectedDate));

	            try {
	                String sql = "SELECT `id` FROM `weight` WHERE `m_date`='"+dateFormate.format(selectedDate)+"';";
	                dbConnection.getCon();
	                ResultSet rs = dbConnection.statement.executeQuery(sql);

	                if(rs.next()){
	                    System.out.println("Already Input Weight On this date.");
	                    JOptionPane.showMessageDialog(frame, "Already set weight on this day.So you can't add on this day but you can edit.");
	                }else{
	                    String inputUerDateSQL = "INSERT INTO `weight`(`user_id`, `course_id`, `m_date`, `mor_weight`, `eve_weight`) VALUES ("
	                    					+user.getUser_id()+", "
	                    					+currentViewCourseId+", '"
	                    					+dateFormate.format(selectedDate)+"', "+jtfMorningWeight.getText()+", "+jtfEveningWeight.getText()+");";
	                               System.out.println(inputUerDateSQL);
	                               dbConnection.statement.executeUpdate(inputUerDateSQL);
	                               
	                               //table updated so get the data again
	                               viewWeight(currentViewCourseId);
	                }

	            } catch (SQLException ex) {
	                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }
	    }
		
	}
	private void editWeight(int currentViewCourseId, Weight userWeight) {
		JPanel jpaEditWeight = new JPanel();
        
	    

	    Date date = new Date();
	    UtilDateModel model = new UtilDateModel();
	    model.setDate(Integer.parseInt(userWeight.getM_date().substring(6, 10)), 
	            Integer.parseInt(userWeight.getM_date().substring(3, 5))-1, 
	            Integer.parseInt(userWeight.getM_date().substring(0, 2)));
	    model.setSelected(true);



	    Properties p = new Properties();
	    p.put("text.today", "Today");
	    p.put("text.month", "Month");
	    p.put("text.year", "Year");
	    JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
	    JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
	    //add jdate picker into panel
	    jpaEditWeight.add(datePicker);


	    JTextField jtfMorningWeight = new JTextField(10);
	    jtfMorningWeight.setText(String.valueOf(userWeight.getMor_weight()));
	    JTextField jtfEveningWeight = new JTextField(10);
	    jtfEveningWeight.setText(String.valueOf(userWeight.getEve_weight()));


	    jpaEditWeight.add(new JLabel("Morning:"));
	    jpaEditWeight.add(jtfMorningWeight);
	    jpaEditWeight.add(Box.createHorizontalStrut(15)); // a spacer
	    jpaEditWeight.add(new JLabel("Evening:"));
	    jpaEditWeight.add(jtfEveningWeight);

	    int result = JOptionPane.showConfirmDialog(null, jpaEditWeight, 
	             "Weight edit.", JOptionPane.OK_CANCEL_OPTION);
	    if (result == JOptionPane.OK_OPTION) {
	        Date selectedDate = (Date) datePicker.getModel().getValue();
	        SimpleDateFormat dateFormate = new SimpleDateFormat("dd-MM-yyyy");
	        if(jtfMorningWeight.getText().equals("") || jtfEveningWeight.getText().equals("")){

	            System.out.println("Must be require all fields");


	        }else{
	            System.out.println("EveryThing Is Working"+dateFormate.format(selectedDate));

	            try {
	                String sql = "SELECT `id` FROM `weight` WHERE user_id="+user.getUser_id()+
	                										" AND course_id="+currentViewCourseId+
	                										" AND `m_date`='"+dateFormate.format(selectedDate)+"';";
	                dbConnection.getCon();
	                ResultSet rs = dbConnection.statement.executeQuery(sql);

	                if(rs.next()){
	                		if(rs.getInt("id")==userWeight.getId()) {
	                			System.out.println("Already Input Weight On this date.");
	                			String inputUerDateSQL = "UPDATE `weight` SET `m_date`='"
	    	                            +dateFormate.format(selectedDate)+"', `mor_weight`="
	    	                            +jtfMorningWeight.getText()+", `eve_weight`="
	    	                            +jtfEveningWeight.getText()+" WHERE `id`="+userWeight.getId()
	    	                            +" AND user_id="+user.getUser_id()
	    	                            +" AND course_id="+currentViewCourseId+";";
	    	                    
	    	                            System.out.println(inputUerDateSQL);
	    	                            dbConnection.statement.executeUpdate(inputUerDateSQL);
	    	                            dbConnection.con.close();
	    	                            
	    	                            weightTableModel.setValueAt(dateFormate.format(selectedDate), weightTable.getSelectedRow(), 1);
	    	                            weightTableModel.setValueAt(jtfMorningWeight.getText(), weightTable.getSelectedRow(), 2);
	    	                            weightTableModel.setValueAt(jtfEveningWeight.getText(), weightTable.getSelectedRow(), 3);
	    	                            Float average = Float.parseFloat(jtfMorningWeight.getText())+Float.parseFloat(jtfEveningWeight.getText());
	    	                            weightTableModel.setValueAt(average/2, weightTable.getSelectedRow(), 4);
	    	                            
	    	                            
	                		}else {
	                			JOptionPane.showMessageDialog(frame, "Already have another another input on this date.");
	                		}
	                    
	                }else{
	                    	String inputUerDateSQL = "UPDATE `weight` SET `m_date`='"
	                            +dateFormate.format(selectedDate)+"', `mor_weight`="
	                            +jtfMorningWeight.getText()+", `eve_weight`="
	                            +jtfEveningWeight.getText()+" WHERE `id`="+userWeight.getId()
	                            +" AND user_id="+user.getUser_id()
	                            +" AND course_id="+currentViewCourseId+";";
	                    
	                            System.out.println(inputUerDateSQL);
	                            dbConnection.statement.executeUpdate(inputUerDateSQL);
	                            dbConnection.con.close();
	                            
	                            weightTableModel.setValueAt(dateFormate.format(selectedDate), weightTable.getSelectedRow(), 1);
	                            weightTableModel.setValueAt(jtfMorningWeight.getText(), weightTable.getSelectedRow(), 2);
	                            weightTableModel.setValueAt(jtfEveningWeight.getText(), weightTable.getSelectedRow(), 3);
	                            Float average = Float.parseFloat(jtfMorningWeight.getText())+Float.parseFloat(jtfEveningWeight.getText());
	                            weightTableModel.setValueAt(average/2, weightTable.getSelectedRow(), 4);
	                                    
	                            
	                            
	                }

	            } catch (SQLException ex) {
	                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }
	    }
	}
	private void deleteWeight(int currentViewCourseId, Weight userWeight) {
		try {
	        String SQL = "DELETE FROM `weight` WHERE `id`="+userWeight.getId()+" AND user_id="+user.getUser_id()+" AND course_id="+currentViewCourseId+";";
	        System.out.println(SQL);
	        dbConnection.getCon();
	        dbConnection.statement.executeUpdate(SQL);
	        dbConnection.con.close();

	    } catch (SQLException ex) {
	        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	
	
	private void viewExercise(int currentViewCourseId) {
		currentAddViewTable="exerciseTable";
		enableButton();
		jpViewBoard.removeAll();

	    exerciseTable = new JTable(); 

	    // create a weightTable weightTablemModel and set a Column Identifiers to this weightTablemModel 
	    Object[] columns = {"ID", "Date","Exercise Name","Minit","Cal Burn PM","Cal Burn"};
	    exerciseTableModel = new DefaultTableModel();
	    exerciseTableModel.setColumnIdentifiers(columns);

	    // set the weightTablemModel to the weightTable
	    exerciseTable.setModel(exerciseTableModel);

	    // Change A JTable Background Color, Font Size, Font Color, Row Height
	    exerciseTable.setBackground(Color.LIGHT_GRAY);
	    exerciseTable.setForeground(Color.black);
	    Font font = new Font("",1,14);
	    exerciseTable.setFont(font);
	    exerciseTable.setRowHeight(18);

	    //

	    //get database value
	    String mealSQL = "\n" + 
	    		" SELECT exercise.id, exercise.m_date, exercise_calories.name, exercise.minit, exercise_calories.calories_burn_pm FROM `exercise`"
	    		+ " INNER JOIN exercise_calories ON(exercise.exercise_id=exercise_calories.id) WHERE "
	    		+ "user_id = "+user.getUser_id()+" AND course_id = "+currentViewCourseId+";";
	    ResultSet rsExercise;
	    try {
	    		dbConnection.getCon();
	        rsExercise = dbConnection.statement.executeQuery(mealSQL);
	        while(rsExercise.next()){
	                // create an array of objects to set the row data
	                Object[] row = new Object[6];

	                row[0] = rsExercise.getInt("id");
	                row[1] = rsExercise.getString("m_date");
	                row[2] = rsExercise.getString("name");
	                row[3] = rsExercise.getFloat("minit");
	                row[4] = rsExercise.getFloat("calories_burn_pm");
	                float totalCaloriesBurn = rsExercise.getFloat("minit") * rsExercise.getFloat("calories_burn_pm");
	                row[5] = totalCaloriesBurn;
	                exerciseTableModel.addRow(row);

	            }
	    } catch (SQLException ex) {
	        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
	    }



	    jpViewBoard.setLayout(new BoxLayout(jpViewBoard, BoxLayout.Y_AXIS));


	    JPanel jpExercise = new JPanel();
	    jpExercise.setLayout(new BorderLayout());

	    JScrollPane jspExercise = new JScrollPane(jpExercise);
	    jpExercise.add(exerciseTable);
	    jspExercise.setViewportView(exerciseTable);

	    //set mouse listener
	    exerciseTable.addMouseListener(new MouseInputAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e){

	                currentTableView="exerciseTable";
	            }
	        });


	    //now outpu panel add
	    jpViewBoard.add(jspExercise);
	    jpViewBoard.revalidate();
		
	}
	private void addExercise(int currentViewCourseId) {
		JPanel jpaAddExcercise = new JPanel();
        
	    Calendar today = Calendar.getInstance();

	    Date date = new Date();
	    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    UtilDateModel model = new UtilDateModel();
	    model.setDate(localDate.getYear(), localDate.getMonthValue()-1, localDate.getDayOfMonth());
	    model.setSelected(true);



	    Properties p = new Properties();
	    p.put("text.today", "Today");
	    p.put("text.month", "Month");
	    p.put("text.year", "Year");
	    JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
	    JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

	    //add jdate picker into panel
	    jpaAddExcercise.add(datePicker);


	    JTextField jtfExerciseName = new JTextField(10);
	    JTextField jtfTimeInMinit = new JTextField(5);


	    jpaAddExcercise.add(new JLabel("Exercise Name:"));
	    jpaAddExcercise.add(jtfExerciseName);
	    jpaAddExcercise.add(Box.createHorizontalStrut(15)); // a spacer
	    jpaAddExcercise.add(new JLabel("Time In Minit:"));
	    jpaAddExcercise.add(jtfTimeInMinit);



	    int result = JOptionPane.showConfirmDialog(null, jpaAddExcercise, 
	             "Please Enter Exercise Name and Time In Minit", JOptionPane.OK_CANCEL_OPTION);
	    if (result == JOptionPane.OK_OPTION) {
	        Date selectedDate = (Date) datePicker.getModel().getValue();
	        SimpleDateFormat dateFormate = new SimpleDateFormat("dd-MM-yyyy");
	        if(jtfExerciseName.getText().equals("") || jtfTimeInMinit.getText().equals("")){

	            System.out.println("Must be require all fields");


	        }else{
	            System.out.println("EveryThing Is Working"+dateFormate.format(selectedDate));

	            try {
	                String sql = "SELECT * FROM `exercise_calories` WHERE `name`='"+jtfExerciseName.getText()+"';";
	                dbConnection.getCon();
	                ResultSet rs = dbConnection.statement.executeQuery(sql);
	                if(rs.next()){
	                    System.out.println("Exercise Found In database.");

	                    String inputExerciseSQL = "INSERT INTO `exercise`(`user_id`, `course_id`, `exercise_id`, `m_date`, `minit`) VALUES ("
	                    					+user.getUser_id()+", "
	                    					+currentViewCourseId+", "
	                    					+rs.getInt("id")+", '"
	                    					+dateFormate.format(selectedDate)+"', "
	                    					+Integer.parseInt(jtfTimeInMinit.getText())+");";
	                    System.out.println(inputExerciseSQL);       
	                    dbConnection.statement.executeUpdate(inputExerciseSQL);
	                    
	                    //add data into table
	                    viewExercise(currentViewCourseId);
	                    dbConnection.con.close();

	                }else{
	                    System.out.println("No Exercise found in databae");
	                    JPanel jpCaloriesInput = new JPanel();
	                    JTextField jtfCalories = new JTextField(5);
	                    jpCaloriesInput.add(new JLabel("Calories:"));
	                    jpCaloriesInput.add(jtfCalories);

	                    int caloriesResult = JOptionPane.showConfirmDialog(null, jpCaloriesInput, 
	                            "Enter how many calories burn this exercise.", JOptionPane.OK_CANCEL_OPTION);
	                   if (caloriesResult == JOptionPane.OK_OPTION) {
	                       if(jtfCalories.equals("")){
	                       }else{
	                           float caloriesBurnPerMin = Float.parseFloat(jtfCalories.getText())/Float.parseFloat(jtfTimeInMinit.getText());


	                           String inputExerciseSQL = "INSERT INTO `exercise_calories`(`name`, `calories_burn_pm`) VALUES ('"
	                                   +jtfExerciseName.getText()+"', "+caloriesBurnPerMin+");";
	                           dbConnection.statement.executeUpdate(inputExerciseSQL);

	                           ResultSet rsGetExerciseId = dbConnection.statement.executeQuery("SELECT `id` FROM `exercise_calories` WHERE `name`='"+jtfExerciseName.getText()+"';");
	                           if(rsGetExerciseId.next()){
	                               System.out.println("Exercise id: "+rsGetExerciseId.getInt(1));
	                               String inputUerExerciseSQL = "INSERT INTO `exercise`(`user_id`, `course_id`, `exercise_id`, `m_date`, `minit`) VALUES ("
	                            		   +user.getUser_id()+", "
	                            		   +currentViewCourseId+", "
	                            		   +rsGetExerciseId.getInt("id")+", '"
	                            		   +dateFormate.format(selectedDate)+"', "+jtfTimeInMinit.getText()+");";
	                               
	                               System.out.println(inputUerExerciseSQL);
	                               dbConnection.statement.executeUpdate(inputUerExerciseSQL);
	                               
	                               //add data into table
	                               viewExercise(currentViewCourseId);
	                               
	                               
	                           }else{
	                           }



	                       }
	                   }else{

	                   }
	                }

	            } catch (SQLException ex) {
	                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
	            }

	        }
	    }
		
	}
	private void editExercise(int currentViewCourseId, Exercise userExercise) {
		JPanel jpaEditExercise = new JPanel();
		   
	    
	    UtilDateModel model = new UtilDateModel();
	    model.setDate(Integer.parseInt(userExercise.getM_date().substring(6, 10)), 
	            Integer.parseInt(userExercise.getM_date().substring(3, 5))-1, 
	            Integer.parseInt(userExercise.getM_date().substring(0, 2)));
	    model.setSelected(true);



	    Properties p = new Properties();
	    p.put("text.today", "Today");
	    p.put("text.month", "Month");
	    p.put("text.year", "Year");
	    JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
	    JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

	    //add jdate picker into panel
	    jpaEditExercise.add(datePicker);


	    JTextField jtfExerciseName = new JTextField(10);
	    jtfExerciseName.setText(String.valueOf(userExercise.getExercise_name()));
	    JTextField jtfMinit = new JTextField(10);
	    jtfMinit.setText(String.valueOf(userExercise.getMinit()));


	    jpaEditExercise.add(new JLabel("Exercise Name:"));
	    jpaEditExercise.add(jtfMinit);
	    jpaEditExercise.add(Box.createHorizontalStrut(15)); // a spacer
	    jpaEditExercise.add(new JLabel("Minit:"));
	    jpaEditExercise.add(jtfMinit);

	    int result = JOptionPane.showConfirmDialog(null, jpaEditExercise, 
	             "Exercise Edit.", JOptionPane.OK_CANCEL_OPTION);
	    if (result == JOptionPane.OK_OPTION) {
	        Date selectedDate = (Date) datePicker.getModel().getValue();
	        SimpleDateFormat dateFormate = new SimpleDateFormat("dd-MM-yyyy");
	        if(jtfExerciseName.getText().equals("") || jtfMinit.getText().equals("")){

	            System.out.println("Must be require all fields");


	        }else{
	            System.out.println("EveryThing Is Working"+dateFormate.format(selectedDate));

	            try {
	                  String inputUserDateSQL = "UPDATE `exercise` SET `m_date`='"
	                            +dateFormate.format(selectedDate)+"', `minit`="
	                            +jtfMinit.getText()+" WHERE `id`="+userExercise.getId()
	                            +" AND user_id="+user.getUser_id()
	                            +" AND course_id="+ currentViewCourseId + ";";
	                    
	                            System.out.println(inputUserDateSQL);
	                            dbConnection.getCon();
	                            dbConnection.statement.executeUpdate(inputUserDateSQL);
	                            dbConnection.con.close();
	                            
	                           //now add row in table
	                           viewExercise(currentViewCourseId);
	                            
	                   
	            } catch (SQLException ex) {
	                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }
	    }
		
		
	}
	private void deleteExercise(int currentViewCourseId, Exercise userExercise) {
		try {
	        String SQL = "DELETE FROM `exercise` WHERE `id`="+userExercise.getId()+" AND user_id="+user.getUser_id()+" AND course_id="+currentViewCourseId+";";
	        System.out.println(SQL);
	        dbConnection.getCon();
	        dbConnection.statement.executeUpdate(SQL);
	        dbConnection.con.close();

	    } catch (SQLException ex) {
	        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	
	
	private void viewMeal(int currentViewCourseId) {
		currentAddViewTable="mealTable";
		enableButton();
		jpViewBoard.removeAll();

	    mealTable = new JTable(); 

	    // create a weightTable weightTablemModel and set a Column Identifiers to this weightTablemModel 
	    Object[] columns = {"ID", "Date","Meal Name","Gram","Cal P-Gr", "Total Cal"};
	    mealTableModel = new DefaultTableModel();
	    mealTableModel.setColumnIdentifiers(columns);

	    // set the weightTablemModel to the weightTable
	    mealTable.setModel(mealTableModel);

	    // Change A JTable Background Color, Font Size, Font Color, Row Height
	    mealTable.setBackground(Color.LIGHT_GRAY);
	    mealTable.setForeground(Color.black);
	    Font font = new Font("",1,14);
	    mealTable.setFont(font);
	    mealTable.setRowHeight(18);

	    //

	    //get database value
	    String mealSQL = "SELECT meal.id, meal.m_date, meal_calories.meal_name, meal.gram, meal_calories.calories_pg FROM meal "
	    		+ "INNER JOIN meal_calories ON(meal.meal_id=meal_calories.id) WHERE user_id ="+user.getUser_id()+" AND course_id ="+currentViewCourseId+";";
	    
	    try {
	    	
	    		dbConnection.getCon();
	    		ResultSet rsMeal = dbConnection.statement.executeQuery(mealSQL);
	    		
	        while(rsMeal.next()){
	                // create an array of objects to set the row data
	                Object[] row = new Object[6];

	                row[0] = rsMeal.getInt("id");
	                row[1] = rsMeal.getString("m_date");
	                row[2] = rsMeal.getString("meal_name");
	                row[3] = rsMeal.getFloat("gram");
	                row[4] = rsMeal.getFloat("calories_pg");
	                float totalCalories = rsMeal.getFloat("gram") * rsMeal.getFloat("calories_pg");
	                row[5] = totalCalories;
	                mealTableModel.addRow(row);

	            }
	        dbConnection.con.close();
	        
	    } catch (SQLException ex) {
	        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
	    }



	    jpViewBoard.setLayout(new BoxLayout(jpViewBoard, BoxLayout.Y_AXIS));


	    JPanel jpMeal = new JPanel();
	    jpMeal.setLayout(new BorderLayout());

	    JScrollPane jspMeal = new JScrollPane(jpMeal);
	    jpMeal.add(mealTable);
	    jspMeal.setViewportView(mealTable);

	    //set mouse listener
	    mealTable.addMouseListener(new MouseInputAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e){

	                currentTableView="mealTable";
	            }
	        });


	    //now output panel add
	    jpViewBoard.add(jspMeal);
	    jpViewBoard.revalidate();
		
	}
	private void addMeal(int currentViewCourseId) {
		JPanel myPanel = new JPanel();
        
	    Calendar today = Calendar.getInstance();

	    Date date = new Date();
	    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    UtilDateModel model = new UtilDateModel();
	    model.setDate(localDate.getYear(), localDate.getMonthValue()-1, localDate.getDayOfMonth());
	    model.setSelected(true);



	    Properties p = new Properties();
	    p.put("text.today", "Today");
	    p.put("text.month", "Month");
	    p.put("text.year", "Year");
	    JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
	    JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

	    //add jdate picker into panel
	    myPanel.add(datePicker);


	    JTextField jtfMealName = new JTextField(10);
	    JTextField jtfAmountInGram = new JTextField(5);


	    myPanel.add(new JLabel("Meal Name:"));
	    myPanel.add(jtfMealName);
	    myPanel.add(Box.createHorizontalStrut(15)); // a spacer
	    myPanel.add(new JLabel("Gram:"));
	    myPanel.add(jtfAmountInGram);



	    int result = JOptionPane.showConfirmDialog(null, myPanel, 
	             "Please Enter Meal Name and Gram Values", JOptionPane.OK_CANCEL_OPTION);
	    if (result == JOptionPane.OK_OPTION) {
	        Date selectedDate = (Date) datePicker.getModel().getValue();
	        SimpleDateFormat dateFormate = new SimpleDateFormat("dd-MM-yyyy");
	        if(jtfMealName.getText().equals("") || jtfAmountInGram.getText().equals("")){

	            System.out.println("Must be require all fields");


	        }else{
	            System.out.println("EveryThing Is Working"+dateFormate.format(selectedDate));

	            try {
	                String sql = "SELECT * FROM `meal_calories` WHERE `meal_name`='"+jtfMealName.getText()+"';";
	                dbConnection.getCon();
	                ResultSet rs = dbConnection.statement.executeQuery(sql);
	                if(rs.next()){
	                    System.out.println("Meal Found In database.");

	                    String inputMealeSQL = "INSERT INTO `meal`(`user_id`, `course_id`, `meal_id`, `m_date`, `gram`) VALUES ("
	                    				+user.getUser_id()+", "
	                    				+currentViewCourseId+", "
	                    				+rs.getInt("id")+", '"
	                    				+dateFormate.format(selectedDate)+"', "
	                    				+Float.parseFloat(jtfAmountInGram.getText())+");";
	                    System.out.println(inputMealeSQL);       
	                    dbConnection.statement.executeUpdate(inputMealeSQL);
	                    dbConnection.con.close();
	                    
	                    //add row in table
	                    viewMeal(currentViewCourseId);
	                    

	                }else{
	                    System.out.println("No meail found in dtabae");
	                    JPanel jpCaloriesInput = new JPanel();
	                    JTextField jtfCalories = new JTextField(5);
	                    jpCaloriesInput.add(new JLabel("Calories:"));
	                    jpCaloriesInput.add(jtfCalories);

	                    int caloriesResult = JOptionPane.showConfirmDialog(null, jpCaloriesInput, 
	                            "Enter how many calories this meal contain.", JOptionPane.OK_CANCEL_OPTION);
	                   if (caloriesResult == JOptionPane.OK_OPTION) {
	                       if(jtfCalories.equals("")){
	                       }else{
	                           float caloriesPerGream = Float.parseFloat(jtfCalories.getText())/Float.parseFloat(jtfAmountInGram.getText());


	                           String inputMealeSQL = "INSERT INTO `meal_calories`(`meal_name`, `calories_pg`) VALUES ('"
	                                   +jtfMealName.getText()+"', "+caloriesPerGream+");";
	                           dbConnection.statement.executeUpdate(inputMealeSQL);

	                           ResultSet rsGetMealId = dbConnection.statement.executeQuery("SELECT * FROM `meal_calories` WHERE `meal_name`='"+jtfMealName.getText()+"';");
	                           System.out.println("SELECT * FROM `meal_calories` WHERE `meal_name`='"+jtfMealName.getText()+"';");
	                           if(rsGetMealId.next()){
	                               System.out.println("meal id: "+rsGetMealId.getInt("id"));
	                               String inputUerMealeSQL = "INSERT INTO `meal`(`user_id`, `course_id`, `meal_id`, `m_date`, `gram`) VALUES ("
		                    				+user.getUser_id()+", "
		                    				+currentViewCourseId+", "
		                    				+rsGetMealId.getInt("id")+", '"
		                    				+dateFormate.format(selectedDate)+"', "
		                    				+Float.parseFloat(jtfAmountInGram.getText())+");";
	                               System.out.println(inputUerMealeSQL); 
	                               dbConnection.statement.executeUpdate(inputUerMealeSQL);
	                               
	                               //add row in table
	                                viewMeal(currentViewCourseId);

	                               
	                               
	                           }else{
	                           }



	                       }
	                   }else{

	                   }
	                }

	            } catch (SQLException ex) {
	                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
	            }

	        }
	    }
		
	}
	private void editMeal(int currentViewCourseId, Meal userMeal) {
		JPanel jpaEditMeal = new JPanel();
        
	    

	    //Date date = new Date();
	    UtilDateModel model = new UtilDateModel();
	    model.setDate(Integer.parseInt(userMeal.getM_date().substring(6, 10)), 
	            Integer.parseInt(userMeal.getM_date().substring(3, 5))-1, 
	            Integer.parseInt(userMeal.getM_date().substring(0, 2)));
	    model.setSelected(true);



	    Properties p = new Properties();
	    p.put("text.today", "Today");
	    p.put("text.month", "Month");
	    p.put("text.year", "Year");
	    JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
	    JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

	    //add jdate picker into panel
	    jpaEditMeal.add(datePicker);


	    JTextField jtfMealName = new JTextField(10);
	    jtfMealName.setText(String.valueOf(userMeal.getMeal_name()));
	    JTextField jtfMealGram = new JTextField(10);
	    jtfMealGram.setText(String.valueOf(userMeal.getGram()));


	    jpaEditMeal.add(new JLabel("Meal Name:"));
	    jpaEditMeal.add(jtfMealName);
	    jpaEditMeal.add(Box.createHorizontalStrut(15)); // a spacer
	    jpaEditMeal.add(new JLabel("Gram:"));
	    jpaEditMeal.add(jtfMealGram);

	    int result = JOptionPane.showConfirmDialog(null, jpaEditMeal, 
	             "Meal Edit.", JOptionPane.OK_CANCEL_OPTION);
	    if (result == JOptionPane.OK_OPTION) {
	        Date selectedDate = (Date) datePicker.getModel().getValue();
	        SimpleDateFormat dateFormate = new SimpleDateFormat("dd-MM-yyyy");
	        if(jtfMealName.getText().equals("") || jtfMealGram.getText().equals("")){

	            System.out.println("Must be require all fields");


	        }else{
	            System.out.println("EveryThing Is Working"+dateFormate.format(selectedDate));

	            try {
	                  String inputUerDateSQL = "UPDATE `meal` SET `m_date`='"
	                            +dateFormate.format(selectedDate)+"', `gram`="
	                            +jtfMealGram.getText()+" WHERE `id`="+userMeal.getId()+" AND "
	                            +"user_id = "+user.getUser_id()+" AND "
	                            +"course_id = "+currentViewCourseId+";";
	                    
	                            System.out.println(inputUerDateSQL);
	                            dbConnection.getCon();
	                            dbConnection.statement.executeUpdate(inputUerDateSQL);
	                            dbConnection.con.close();
	                            
	                           //now add row in table
	                           viewMeal(currentViewCourseId);
	                            
	                   
	            } catch (SQLException ex) {
	                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }
	    }
	}
	private void deleteMeal(int currentViewCourseId, Meal userMeal) {
		try {
	        String SQL = "DELETE FROM `meal` WHERE `id`="+userMeal.getId()+" AND user_id="+user.getUser_id()+" AND course_id="+currentViewCourseId+";";
	        System.out.println(SQL);
	        dbConnection.getCon();
	        dbConnection.statement.executeUpdate(SQL);
	        dbConnection.con.close();

	    } catch (SQLException ex) {
	        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
		
	private void viewAllCourse() {
		String allCourseSQL="SELECT * FROM `course` WHERE `u_id` ="+user.getUser_id();
		System.out.println(allCourseSQL);
		
	    try {
	    	dbConnection.getCon();
	    ResultSet rs = dbConnection.statement.executeQuery(allCourseSQL);
	    while(rs.next()){
	    	System.out.println(rs.getString("course_name"));
	    	
	    	int tempCourseId = rs.getInt("course_id");
	    	String tempCourseName = rs.getString("course_name");
	    	
	        JButton button = new JButton();
	        button.setOpaque(true);
	        button.setPreferredSize(new Dimension(300, 50));
	        button.setText(rs.getString("course_name"));
	        button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					enableButtonByCourseSelect();
						currentViewCourseId = tempCourseId;
						System.out.println(currentViewCourseId);
						
						JOptionPane.showMessageDialog(frame, "Your select course is "+tempCourseName+". So all changes will be make in this course.");
						
						viewMeal(currentViewCourseId);
				}
			});
	        jpCourseList.add(button);
	        
	        courseList.add(new Course(rs.getInt("course_id"), rs.getString("course_name"), rs.getString("start_date"), rs.getString("end_date")));
	        
	    }
	    
	    dbConnection.con.close();

	    } catch (SQLException ex) {
	        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	private void courseAdd() {
		JPanel myPanel = new JPanel();
        
	    Calendar today = Calendar.getInstance();

	    Date date = new Date();
	    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    UtilDateModel startDateModel = new UtilDateModel();
	    startDateModel.setDate(localDate.getYear(), localDate.getMonthValue()-1, localDate.getDayOfMonth());
	    startDateModel.setSelected(true);
	    
	    UtilDateModel endDateModel = new UtilDateModel();
	    endDateModel.setDate(localDate.getYear(), localDate.getMonthValue()-1, localDate.getDayOfMonth());
	    endDateModel.setSelected(true);



	    Properties p = new Properties();
	    p.put("text.today", "Today");
	    p.put("text.month", "Month");
	    p.put("text.year", "Year");
	    JDatePanelImpl startDatePanel = new JDatePanelImpl(startDateModel, p);
	    JDatePickerImpl startDatePicker = new JDatePickerImpl(startDatePanel, new DateLabelFormatter());
	    
	    JDatePanelImpl endDatePanel = new JDatePanelImpl(endDateModel, p);
	    JDatePickerImpl endDatePicker = new JDatePickerImpl(endDatePanel, new DateLabelFormatter());

	    //add jdate picker into panel
	    myPanel.add(startDatePicker);
	    myPanel.add(endDatePicker);


	    JTextField jtfCourseName = new JTextField(15);


	    myPanel.add(new JLabel("Course Name:"));
	    myPanel.add(jtfCourseName);
	    //myPanel.add(Box.createHorizontalStrut(15)); // a spacer



	    int result = JOptionPane.showConfirmDialog(null, myPanel, 
	             "Please Enter Courser Information", JOptionPane.OK_CANCEL_OPTION);
	    if (result == JOptionPane.OK_OPTION) {
	        Date startSelectedDate = (Date) startDatePicker.getModel().getValue();
	        Date endSelectedDate = (Date) endDatePicker.getModel().getValue();
	        SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");
	        if(jtfCourseName.getText().equals("") ){

	            System.out.println("Must be require all fields");


	        }else{
	            System.out.println("EveryThing Is Working"+dateFormate.format(startSelectedDate));

	            try {
	            		
	                String sql = "INSERT INTO `course`(`course_name`, `start_date`, `end_date`, `u_id`) VALUES ('"
	                					+jtfCourseName.getText()+"', '"
	                					+dateFormate.format(startSelectedDate)+"', '"
	                					+dateFormate.format(endSelectedDate)+"', "
	                					+user.getUser_id()+");";
	                
	                dbConnection.getCon();
	                dbConnection.statement.executeUpdate(sql);
	                dbConnection.con.close();
	                
	                this.frame.dispose();
	                new DashBoard(user);

	            } catch (SQLException ex) {
	                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
	            }

	        }
	    }
	}
	private void deleteCourse(int currentViewCourseId) {
		int courseIndex = 0;
		for(int s=0; s<courseList.size(); s++) {
			if(courseList.get(s).getCourse_id()==currentViewCourseId) {
				courseIndex=s;
			}
		}
		int result = JOptionPane.showConfirmDialog(frame, "Are you sure to delete "+courseList.get(courseIndex).course_name+" course bacause this will be apply all data related on this course?", "Delete warring", JOptionPane.YES_NO_OPTION);
		
	    if (result == JOptionPane.YES_OPTION) {
	    		String mealDeleteSQL ="DELETE FROM `meal` WHERE `user_id`="+user.getUser_id()+" AND `course_id`="+currentViewCourseId;
	    		String exerCiseDeleteSQL ="DELETE FROM `exercise` WHERE `user_id`="+user.getUser_id()+" AND `course_id`="+currentViewCourseId;
	    		String weightDeleteSQL ="DELETE FROM `weight` WHERE `user_id`="+user.getUser_id()+" AND `course_id`="+currentViewCourseId;
	    		String waistDeleteSQL ="DELETE FROM `waist` WHERE `user_id`="+user.getUser_id()+" AND `course_id`="+currentViewCourseId;
	    		String courseDeleteSQL ="DELETE FROM `course` WHERE `u_id`="+user.getUser_id()+" AND `course_id`="+currentViewCourseId;
	    		
	    		
	    		try {	
	    				dbConnection.getCon();
	    				
	    				System.out.println(mealDeleteSQL);
	    				System.out.println(exerCiseDeleteSQL);
	    				System.out.println(weightDeleteSQL);
	    				System.out.println(waistDeleteSQL);
	    				System.out.println(courseDeleteSQL);
	    				
					dbConnection.statement.executeUpdate(mealDeleteSQL);
					dbConnection.statement.executeUpdate(exerCiseDeleteSQL);
			    		dbConnection.statement.executeUpdate(weightDeleteSQL);
			    		dbConnection.statement.executeUpdate(waistDeleteSQL);
			    		dbConnection.statement.executeUpdate(courseDeleteSQL);
			    		
			    		dbConnection.con.close();
			    		
			    		frame.dispose();
			    		new DashBoard(user);
			    		
				} catch (SQLException e) {
					e.printStackTrace();
				}
	    		
	    }
	}
	
	public void maintainWeight(int currentViewCourseId) {
		String currentWeightSQL="SELECT * FROM weight WHERE user_id="+user.getUser_id()+" AND course_id="+currentViewCourseId+" ORDER BY id DESC LIMIT 1";
		float currentWeight = 0 ;
		
	    try {
	    	dbConnection.getCon();
	    ResultSet rsCurrent = dbConnection.statement.executeQuery(currentWeightSQL);
	    if(rsCurrent.next()){
	        currentWeight = rsCurrent.getFloat("mor_weight")+rsCurrent.getFloat("eve_weight");
	        
	        dbConnection.con.close();
	    }

	    } catch (SQLException ex) {
	        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
	    }



	    if(user.getGender().equals("Male")){

	        switch(user.getActivity_level()){
	            case "Inactive": 
	                JOptionPane.showMessageDialog(frame, "You Need to take "+(currentWeight/2)*5+" calories per day to maintain weight..");
	            break;

	            case "Moderately Active":
	                JOptionPane.showMessageDialog(frame, "You Need to take "+(currentWeight/2)*6+" calories per day to maintain weight..");
	            break;

	            case "Active":
	                JOptionPane.showMessageDialog(frame, "You Need to take "+(currentWeight/2)*7.5+" calories per day to maintain weight..");
	            break;

	            default:
	            break;
	        }
	    }else{
	        switch(user.getActivity_level()){
	            case "Inactive": 
	                JOptionPane.showMessageDialog(frame, "You Need to take "+(currentWeight/2)*4+" calories per day to maintain weight..");
	            break;

	            case "Moderately Active":
	                JOptionPane.showMessageDialog(frame, "You Need to take "+(currentWeight/2)*5+" calories per day to maintain weight..");
	            break;

	            case "Active":
	                JOptionPane.showMessageDialog(frame, "You Need to take "+(currentWeight/2)*6+" calories per day to maintain weight..");
	            break;

	            default:
	            break;
	        }
	    }
		
	}
	
	private void editActivityLevel() {
		JPanel jpEditActivity = new JPanel();
        
        JComboBox jcbActivity = new JComboBox();
        jcbActivity.addItem("Inactive");
        jcbActivity.addItem("Moderately Active");
        jcbActivity.addItem("Active");
        jcbActivity.setSelectedIndex(0);

        
        jpEditActivity.add(jcbActivity);
        
        int result = JOptionPane.showConfirmDialog(null, jpEditActivity, 
                 "Select Activity Level.", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            
            try {
                String inputActivitySQL = "UPDATE `user` SET `activity_level`='"+jcbActivity.getSelectedItem()+"';";
                //System.out.println(inputActivitySQL);
                
                dbConnection.getCon();
                dbConnection.statement.executeUpdate(inputActivitySQL);
                dbConnection.con.close();
                user.setActivity_level((String) jcbActivity.getSelectedItem());
                frame.dispose();
                new DashBoard(user);
                
                
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
	}
	
	private void analysis(ArrayList<String> selectedDateList, float[] mealCalories, float[] exerciseCalories) {
		jpViewBoard.removeAll();

	    analysisTable = new JTable(); 

	    // create a weightTable weightTablemModel and set a Column Identifiers to this weightTablemModel 
	    Object[] columns = {"ID", "Date","Meal Cal Take","Exercise Burn","Net Calories"};
	    analysisTableModel = new DefaultTableModel();
	    analysisTableModel.setColumnIdentifiers(columns);

	    // set the weightTablemModel to the weightTable
	    analysisTable.setModel(analysisTableModel);

	    // Change A JTable Background Color, Font Size, Font Color, Row Height
	    analysisTable.setBackground(Color.LIGHT_GRAY);
	    analysisTable.setForeground(Color.black);
	    Font font = new Font("",1,14);
	    analysisTable.setFont(font);
	    analysisTable.setRowHeight(18);

	    //

	    //get form calculate array 
	    for(int i=0; i<selectedDateList.size(); i++){
	        Object[] row = new Object[5];
	        row[0] = i+1;
	        row[1] = selectedDateList.get(i);
	        row[2] = mealCalories[i];
	        row[3] = exerciseCalories[i];
	        row[4] = mealCalories[i] - exerciseCalories[i];
	        analysisTableModel.addRow(row);
	    }




	    jpViewBoard.setLayout(new BoxLayout(jpViewBoard, BoxLayout.Y_AXIS));


	    JPanel jPanel = new JPanel();
	    jPanel.setLayout(new BorderLayout());

	    JScrollPane jScrollPane = new JScrollPane(jPanel);
	    jPanel.add(analysisTable);
	    jScrollPane.setViewportView(analysisTable);



	    //now outpu panel add
	    jpViewBoard.add(jScrollPane);
	    jpViewBoard.revalidate();
	}
	
	private class DateLabelFormatter extends AbstractFormatter {
		private String datePattern = "yyyy-MM-dd";
	    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

	    @Override
	    public Object stringToValue(String text) throws ParseException {
	        return dateFormatter.parseObject(text);
	    }

	    @Override
	    public String valueToString(Object value) throws ParseException {
	        if (value != null) {
	            Calendar cal = (Calendar) value;
	            return dateFormatter.format(cal.getTime());
	        }

	        return "";
	    }
	}
}


