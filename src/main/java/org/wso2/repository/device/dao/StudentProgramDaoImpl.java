package org.wso2.repository.device.dao;

import org.wso2.repository.device.model.StudentProgram;
import org.wso2.repository.device.util.DB;

import javax.ws.rs.core.UriInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;


public class StudentProgramDaoImpl implements StudentProgramDao{

   StudentProgram studentProgram;
   Connection connection;




    public String deleteStudentProgram(UriInfo id  ) throws Exception {
        String strResponse="";
        Connection con = DB.getConnection();
        Statement stmt = con.createStatement();
        // String schema= con.getSchema();

        try{

            String strCount = "select count(*) cnt from UCSC.TB_STUDENT_PROGRAM where ST_ID  ='" 
            + id.getQueryParameters().getFirst("studentID") +"' AND PG_ID = '" + id.getQueryParameters().getFirst("programID") +"'";
            ResultSet resultSet = stmt.executeQuery(strCount);
            resultSet.next();


            if(resultSet.getInt("cnt") == 0)
            {strResponse="No Records To Be Deleted";}
            {
                String query = "delete from UCSC.TB_STUDENT_PROGRAM where ST_ID  ='" 
            + id.getQueryParameters().getFirst("studentID") +"' AND PG_ID = '" + id.getQueryParameters().getFirst("programID") +"'";
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

    public LinkedList<StudentProgram> getStudentProgram(UriInfo parameters) {

        LinkedList studentProgramList=new LinkedList();

        try {
            String studentID = parameters.getQueryParameters().getFirst("studentID");
            String programID = parameters.getQueryParameters().getFirst("programID");
            String subjectID = parameters.getQueryParameters().getFirst("subjectID");
            String finalMark = parameters.getQueryParameters().getFirst("finalMark");
            

            String options = null;

            Connection con=DB.getConnection();
            Statement statement=con.createStatement();
            String query ="select * from UCSC.TB_STUDENT_PROGRAM ";

            boolean firstPara = false;

            if (studentID !=null)
            {
                options = " ST_ID = '" + studentID +"' ";
                firstPara =true;
            }
            
            if (programID !=null)
            {
                if (firstPara==false) {
                    options = " PG_ID = '" + programID+ "' ";
                    firstPara = true;
                }else
                {
                    options = options +  " AND PG_ID = '" + programID + "' ";
                }

            }
            
            if (subjectID !=null)
            {
                if (firstPara==false) {
                    options = " SUB_ID = '" + subjectID+ "' ";
                    firstPara = true;
                }else
                {
                    options = options +  " AND SUB_ID = '" + subjectID + "' ";
                }

            }

            if (finalMark !=null)
            {
                if (firstPara==false) {
                    options = " FINAL_MARK = '" + finalMark+ "' ";
                    firstPara = true;
                }else
                {
                    options = options +  " AND FINAL_MARK = '" + finalMark + "' ";
                }

            }


            if(firstPara)
            {
                query = query + " Where " + options;
            }

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {

                StudentProgram studentProgram =new StudentProgram();
                studentProgram.setStudentID(resultSet.getString("ST_ID"));
                studentProgram.setProgramID(resultSet.getString("PG_ID"));
                studentProgram.setSubjectID(resultSet.getString("SUB_ID"));
                studentProgram.setFinalMark(resultSet.getInt("FINAL_MARK"));
                studentProgramList.add(studentProgram);
            }


            return studentProgramList;

        }catch (Exception e) {
            e.printStackTrace();

            return studentProgramList;

        }finally{
            return studentProgramList;
        }
    }



    public String addStudentProgram(StudentProgram studentProgram) {

        String strResponse = null;
        try {

            Connection con = DB.getConnection();
            Statement stmt = con.createStatement();
            String query = "insert into UCSC.TB_STUDENT_PROGRAM(ST_ID,PG_ID,SUB_ID,FINAL_MARK) values ('" 
            + studentProgram.getStudentID() + "','" + studentProgram.getProgramID() 
            + "','" + studentProgram.getSubjectID()   + "','" + studentProgram.getFinalMark() + "')";

            stmt.executeUpdate(query);
            strResponse="Student Program Added Successfully";

        } catch (Exception ee) {
            ee.printStackTrace();
            strResponse="Error in adding the Student Program";
        }finally{
            return strResponse;
        }
    }

    public String editStudentProgram(StudentProgram studentProgram, UriInfo id) {
        String strResponse = null;
        try {

        
        	
            Connection connection = DB.getConnection();
            Statement statement = connection.createStatement();

            String query =null;
            LinkedList<String> listColumns= new LinkedList<String>();
            LinkedList<String> listValues= new LinkedList<String>();
           

            
            if(  Double.valueOf(studentProgram.getFinalMark()) !=null) {
                listColumns.add("FINAL_MARK");
                listValues.add(Double.valueOf(studentProgram.getFinalMark()).toString());
            }
            
            



            for (int x= 0;x<listColumns.size();x++) {

                if(x==0)
                {
                    query = "update UCSC.TB_STUDENT_PROGRAM set ";
                }

                if(x!=(listColumns.size()-1))
                {
                    query = query + listColumns.get(x) + " = '";
                    query = query + listValues.get(x) + "' , ";
                }
                else{
                    query = query + listColumns.get(x) + " = '";
                    query = query + listValues.get(x)+ "' where ST_ID  ='" 
                    					+ id.getQueryParameters().getFirst("studentID") 
                    					+"' AND PG_ID = '" + id.getQueryParameters().getFirst("programID") +"'";
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
