package org.wso2.repository.device.dao;

import org.wso2.repository.device.model.Schedule;
import org.wso2.repository.device.util.DB;

import javax.ws.rs.core.UriInfo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;


public class ScheduleDaoImpl implements ScheduleDao{

   Schedule schedule;
   Connection connection;




    public String deleteSchedule(String id) throws Exception {
        String strResponse="";
        Connection con = DB.getConnection();
        Statement stmt = con.createStatement();
        // String schema= con.getSchema();

        try{

            String strCount = "select count(*) cnt from UCSC.TB_SCHEDULE where SCH_ID  ='" + id +"'";
            ResultSet resultSet = stmt.executeQuery(strCount);
            resultSet.next();


            if(resultSet.getInt("cnt") == 0)
            {strResponse="No Records To Be Deleted";}
            {
                String query = "delete from UCSC.TB_SCHEDULE where SCH_ID ='" +id+"'";
                stmt.execute(query);
                strResponse="Successfully Deleted";

            }


        }catch (SQLException e) {
            e.printStackTrace();
            strResponse="Failed.Try Again.";
            return strResponse;

        }finally {

            return strResponse;
        }
    }

    public LinkedList<Schedule> getSchedule(UriInfo parameters) {

        LinkedList scheduleList=new LinkedList();

        try {
            String scheduleId = parameters.getQueryParameters().getFirst("scheduleID");
            String year = parameters.getQueryParameters().getFirst("year");
            String batch = parameters.getQueryParameters().getFirst("batch");

            String options = null;

            Connection con=DB.getConnection();
            Statement statement=con.createStatement();
            String query ="select * from UCSC.TB_SCHEDULE ";

            boolean firstPara = false;

            if (scheduleId !=null)
            {
                options = " SCH_ID = '" + scheduleId +"' ";
                firstPara =true;
            }

            if (year !=null)
            {
                if (firstPara==false) {
                    options = " YEAR = '" + year+ "' ";
                    firstPara = true;
                }else
                {
                    options = options +  " AND YEAR = '" + year + "' ";
                }

            }

            if (batch !=null)
            {
                if (firstPara==false) {
                    options = " BATCH = '" + batch+ "' ";
                    firstPara = true;
                }else
                {
                    options = options +  " AND BATCH = '" + batch + "' ";
                }

            }


            if(firstPara)
            {
                query = query + " Where " + options;
            }

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {

                Schedule schedule =new Schedule();
                schedule.setScheduleID(resultSet.getString("SCH_ID"));
                schedule.setYear(resultSet.getString("YEAR"));
                schedule.setBatch(resultSet.getString("BATCH"));
                scheduleList.add(schedule);
            }


            return scheduleList;

        }catch (Exception e) {
            e.printStackTrace();

            return scheduleList;

        }finally{
            return scheduleList;
        }
    }



    public String addSchedule(Schedule schedule) {

        String strResponse = null;
        try {

            Connection con = DB.getConnection();
            Statement stmt = con.createStatement();
            String query = "insert into UCSC.TB_SCHEDULE(SCH_ID,YEAR,BATCH) values ('" + schedule.getScheduleID() + "','"
                    + schedule.getYear()  + "','" + schedule.getBatch() + "')";

            stmt.executeUpdate(query);
            strResponse="Schedule Added Successfully";

        } catch (Exception ee) {
            ee.printStackTrace();
            strResponse="Error in adding the Schedule";
        }finally{
            return strResponse;
        }
    }

    public String editSchedule(Schedule schedule, String id) {
        String strResponse = null;
        try {

            Connection connection = DB.getConnection();
            Statement statement = connection.createStatement();

            String query =null;
            LinkedList<String> listColumns= new LinkedList<String>();
            LinkedList<String> listValues= new LinkedList<String>();
           
            if( schedule.getYear()!=null) {
                listColumns.add("YEAR");
                listValues.add(schedule.getYear());
            }
            if( schedule.getBatch()!=null) {
                listColumns.add("BATCH");
                listValues.add(schedule.getBatch());
            }


            for (int x= 0;x<listColumns.size();x++) {

                if(x==0)
                {
                    query = "update UCSC.TB_SCHEDULE set ";
                }

                if(x!=(listColumns.size()-1))
                {
                    query = query + listColumns.get(x) + " = '";
                    query = query + listValues.get(x) + "' , ";
                }
                else{
                    query = query + listColumns.get(x) + " = '";
                    query = query + listValues.get(x)+ "' WHERE SCH_ID = '" + id+"'";
                }

            }

            if (listColumns.size()!=0) {
                statement.execute(query);
            }

            strResponse="Record Successfully Updated";
            return strResponse;

        } catch (SQLException e) {
            e.printStackTrace();
            strResponse="Error In Updating The Record";
            return strResponse;

        } catch (Exception e) {
            e.printStackTrace();
        }finally {

            return strResponse;
        }
    }
}
