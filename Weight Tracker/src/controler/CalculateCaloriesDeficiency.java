package controler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalculateCaloriesDeficiency {
	private ArrayList<String> selectedDateList = new ArrayList<String>();
    private float[] mealCalories;
    private float[] exerciseCalories;
    
    private DbConnection dbConnection =  new DbConnection();

 

    public CalculateCaloriesDeficiency(int userID, int courseID) {
    		dbConnection.getCon();
    		
        String mealCalIntakSQL = "SELECT meal.m_date, meal.gram, meal_calories.calories_pg FROM `meal` "
        		+ "INNER JOIN meal_calories ON(meal.meal_id=meal_calories.id) WHERE user_id="+userID+" AND course_id="+courseID;
        String excerciseCalBurn = "SELECT exercise.m_date, exercise.minit, exercise_calories.calories_burn_pm FROM `exercise` INNER JOIN "
        		+ "exercise_calories ON (exercise.exercise_id=exercise_calories.id) WHERE user_id="+userID+" AND course_id="+courseID;
        
        try {
            ResultSet rsMeal = dbConnection.statement.executeQuery(mealCalIntakSQL);
            while(rsMeal.next()){
                boolean foundDate=false;
                m: for(int a=0; a<selectedDateList.size(); a++){
                   if(selectedDateList.get(a).equals(rsMeal.getString("m_date"))){
                       foundDate=true;
                       break m;
                   }
                   
               }
               
               if(foundDate==false){
                   selectedDateList.add(rsMeal.getString("m_date"));
               }
                 
            }
            
           ResultSet rsExercise = dbConnection.statement.executeQuery(excerciseCalBurn);
           while(rsExercise.next()){
               boolean foundDate=false;
               e: for(int a=0; a<selectedDateList.size(); a++){
                   if(selectedDateList.get(a).equals(rsExercise.getString("m_date"))){
                       foundDate=true;
                       break e;
                   }
                   
               }
               
               if(foundDate==false){
                   selectedDateList.add(rsExercise.getString("m_date"));
               }
               
               
            }
           
           //now set array for calories calculate
           mealCalories = new float[selectedDateList.size()];
           exerciseCalories = new float[selectedDateList.size()];
           
           
           for(int c=0; c<selectedDateList.size(); c++){
               String newMealCalIntakSQL = "SELECT meal.m_date, meal.gram, meal_calories.calories_pg FROM `meal` "
              		+ "INNER JOIN meal_calories ON(meal.meal_id=meal_calories.id) WHERE user_id="+userID+" AND course_id="+courseID
            		  	+" AND `m_date`='"+selectedDateList.get(c)+"' ORDER BY m_date ASC";
               System.out.println(newMealCalIntakSQL);
               ResultSet newRsMeal = dbConnection.statement.executeQuery(newMealCalIntakSQL);
               while (newRsMeal.next()) {
                   mealCalories[c] += newRsMeal.getFloat("gram")*newRsMeal.getFloat("calories_pg");
                   
               }
               
               
               
               
               String neweExerciseCalIntakSQL = "SELECT exercise.m_date, exercise.minit, exercise_calories.calories_burn_pm "
              		+ "FROM `exercise` INNER JOIN exercise_calories ON (exercise.exercise_id=exercise_calories.id) "
             		+ "WHERE user_id=" + userID + " AND course_id="+ courseID+" AND `m_date`='"+selectedDateList.get(c)
             		+"' ORDER BY m_date ASC";
               
               System.out.println(neweExerciseCalIntakSQL);
               ResultSet newRsExercise = dbConnection.statement.executeQuery(neweExerciseCalIntakSQL);
               while (newRsExercise.next()) {
                   exerciseCalories[c] += newRsExercise.getFloat("minit")*newRsExercise.getInt("calories_burn_pm");
                   
               }
               
           }
           
           
//           for(int c=0; c<selectedDateList.size(); c++){
//               String newMealCalIntakSQL = "SELECT meal.m_date, SUM(meal.gram) AS gram, SUM(meal_calories.calories_pg) AS calories_pg FROM `meal` "
//               		+ "INNER JOIN meal_calories ON(meal.meal_id=meal_calories.id) WHERE user_id="+userID+" AND course_id="+courseID
//               		+" AND `m_date`='"+selectedDateList.get(c)+"' GROUP BY m_date ORDER BY m_date ASC";
//               System.out.println(newMealCalIntakSQL);
//               
//               ResultSet newRsMeal = dbConnection.statement.executeQuery(newMealCalIntakSQL);
//               if (newRsMeal.next()) {
//                   mealCalories[c] = newRsMeal.getFloat("gram")*newRsMeal.getFloat("calories_pg");
//                   
//               }
//               
//               
//               
//               
//               String neweExerciseCalIntakSQL = "SELECT exercise.m_date, SUM(exercise.minit) AS minit, SUM(exercise_calories.calories_burn_pm) AS "
//               		+ "calories_burn_pm FROM `exercise` INNER JOIN exercise_calories ON (exercise.exercise_id=exercise_calories.id) "
//               		+ "WHERE user_id=" + userID + " AND course_id="+ courseID+" AND `m_date`='"+selectedDateList.get(c)
//               		+"' GROUP BY m_date ORDER BY m_date ASC";
//               
//               System.out.println(neweExerciseCalIntakSQL);
//               ResultSet newRsExercise = dbConnection.statement.executeQuery(neweExerciseCalIntakSQL);
//               
//               
//               if (newRsExercise.next()) {
//                   exerciseCalories[c] = newRsExercise.getFloat("minit")*newRsExercise.getInt("calories_burn_pm");
//                   
//               }
//               
//               
//               
//           }
           
           dbConnection.con.close();
           
            
            
        } catch (SQLException ex) {
            Logger.getLogger(CalculateCaloriesDeficiency.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    
    public float[] getMealCalories() {
        return mealCalories;
    }

    public float[] getExerciseCalories() {
        return exerciseCalories;
    }
    
    public ArrayList<String> getSelectedDateList() {
        return selectedDateList;
    }

}
