package org.wso2.repository.device.dao;

import org.wso2.repository.device.model.Program;
import org.wso2.repository.device.util.DB;

import javax.ws.rs.core.UriInfo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;


public class ProgramDaoImpl implements ProgramDao{

   Program program;
   Connection connection;




    public String deleteProgram(String id) throws Exception {
        String strResponse="";
        Connection con = DB.getConnection();
        Statement stmt = con.createStatement();
        // String schema= con.getSchema();

        try{

            String strCount = "select count(*) cnt from UCSC.TB_PROGRAM where PG_ID  ='" + id +"'";
            ResultSet resultSet = stmt.executeQuery(strCount);
            resultSet.next();


            if(resultSet.getInt("cnt") == 0)
            {strResponse="No Records To Be Deleted";}
            {
                String query = "delete from UCSC.TB_PROGRAM where PG_ID ='" +id+"'";
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

    public LinkedList<Program> getProgram(UriInfo parameters) {

        LinkedList programList=new LinkedList();

        try {
            String programId = parameters.getQueryParameters().getFirst("programID");
            String courseID = parameters.getQueryParameters().getFirst("courseID");
            String scheduleID = parameters.getQueryParameters().getFirst("scheduleID");

            String options = null;

            Connection con=DB.getConnection();
            Statement statement=con.createStatement();
            String query ="select * from UCSC.TB_PROGRAM ";

            boolean firstPara = false;

            if (programId !=null)
            {
                options = " PG_ID = '" + programId +"' ";
                firstPara =true;
            }

            if (courseID !=null)
            {
                if (firstPara==false) {
                    options = " CO_ID = '" + courseID+ "' ";
                    firstPara = true;
                }else
                {
                    options = options +  " AND CO_ID = '" + courseID + "' ";
                }

            }

            if (scheduleID !=null)
            {
                if (firstPara==false) {
                    options = " SCH_ID = '" + scheduleID+ "' ";
                    firstPara = true;
                }else
                {
                    options = options +  " AND SCH_ID = '" + scheduleID + "' ";
                }

            }


            if(firstPara)
            {
                query = query + " Where " + options;
            }

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {

                Program program =new Program();
                program.setProgramID(resultSet.getString("PG_ID"));
                program.setCourseID(resultSet.getString("CO_ID"));
                program.setScheduleID(resultSet.getString("SCH_ID"));
                programList.add(program);
            }


            return programList;

        }catch (Exception e) {
            e.printStackTrace();

            return programList;

        }finally{
            return programList;
        }
    }



    public String addProgram(Program program) {

        String strResponse = null;
        try {

            Connection con = DB.getConnection();
            Statement stmt = con.createStatement();
            String query = "insert into UCSC.TB_PROGRAM(PG_ID,CO_ID,SCH_ID) values ('" + program.getProgramID() + "','"
                    + program.getCourseID()  + "','" + program.getScheduleID() + "')";

            stmt.executeUpdate(query);
            strResponse="Program Added Successfully";

        } catch (Exception ee) {
            ee.printStackTrace();
            strResponse="Error in adding the Program";
        }finally{
            return strResponse;
        }
    }

    public String editProgram(Program program, String id) {
        String strResponse = null;
        try {

            Connection connection = DB.getConnection();
            Statement statement = connection.createStatement();

            String query =null;
            LinkedList<String> listColumns= new LinkedList<String>();
            LinkedList<String> listValues= new LinkedList<String>();
           
            if( program.getCourseID()!=null) {
                listColumns.add("CO_ID");
                listValues.add(program.getCourseID());
            }
            if( program.getScheduleID()!=null) {
                listColumns.add("SCH_ID");
                listValues.add(program.getScheduleID());
            }


            for (int x= 0;x<listColumns.size();x++) {

                if(x==0)
                {
                    query = "update UCSC.TB_PROGRAM set ";
                }

                if(x!=(listColumns.size()-1))
                {
                    query = query + listColumns.get(x) + " = '";
                    query = query + listValues.get(x) + "' , ";
                }
                else{
                    query = query + listColumns.get(x) + " = '";
                    query = query + listValues.get(x)+ "' WHERE PG_ID = '" + id+"'";
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
