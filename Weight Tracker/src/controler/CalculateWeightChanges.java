package controler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalculateWeightChanges {
	public ArrayList<Float> averageWeightList = new ArrayList<Float>();
    public ArrayList<String> dateList = new ArrayList<String>();
    

    public CalculateWeightChanges(int userID, int courseID) {
    	
    		String sql="SELECT * FROM `weight` WHERE user_id="+userID+" AND course_id="+courseID+" LIMIT 15";
    	
        try {
        		DbConnection dbConnection = new DbConnection();
        		dbConnection.getCon();
            ResultSet rs = dbConnection.statement.executeQuery(sql);
            while(rs.next()){
              averageWeightList.add((rs.getFloat("mor_weight")+rs.getFloat("eve_weight"))/2);
              dateList.add(rs.getString("m_date"));
              
              
                System.out.println((rs.getFloat("mor_weight")+rs.getFloat("eve_weight"))/2);
                
            }
            dbConnection.con.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(CalculateWeightChanges.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
