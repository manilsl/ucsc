package org.wso2.repository.device.dao;

import org.wso2.repository.device.model.StudentProgram;
import org.wso2.repository.device.model.StudentProgramDetail;
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
                    options = " SP_FINAL_MARKS = '" + finalMark+ "' ";
                    firstPara = true;
                }else
                {
                    options = options +  " AND SP_FINAL_MARKS = '" + finalMark + "' ";
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
                studentProgram.setFinalMark(resultSet.getInt("SP_FINAL_MARKS"));
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

    public LinkedList<StudentProgramDetail> getStudentProgramDetail(UriInfo parameters) {
        LinkedList studentProgramList=new LinkedList();

        try {
            String studentID = parameters.getQueryParameters().getFirst("studentID");
            String programID = parameters.getQueryParameters().getFirst("programID");
            String subjectID = parameters.getQueryParameters().getFirst("subjectID");
            String finalMark = parameters.getQueryParameters().getFirst("finalMark");
            String studentName= parameters.getQueryParameters().getFirst("studentName");
            String programName = parameters.getQueryParameters().getFirst("programName");
            String subjectName = parameters.getQueryParameters().getFirst("subjectName");


            String options = null;

            Connection con=DB.getConnection();
            Statement statement=con.createStatement();
            String query ="select * from UCSC.VW_STUDENT_PROGRAM_DETAIL ";

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

            if (studentName !=null)
            {
                if (firstPara==false) {
                    options = " ST_NAME = '" + studentName+ "' ";
                    firstPara = true;
                }else
                {
                    options = options +  " AND ST_NAME = '%" + studentName + "%' ";
                }

            }

            if (programName !=null)
            {
                if (firstPara==false) {
                    options = " PG_NAME = '" + programName+ "' ";
                    firstPara = true;
                }else
                {
                    options = options +  " AND PG_NAME = '%" + programName + "%' ";
                }

            }

            if (subjectName !=null)
            {
                if (firstPara==false) {
                    options = " SUB_NAME = '" + subjectName+ "%' ";
                    firstPara = true;
                }else
                {
                    options = options +  " AND SUB_NAME = '" + subjectName + "' ";
                }

            }

            if (finalMark !=null)
            {
                if (firstPara==false) {
                    options = " SP_FINAL_MARKS = '" + finalMark+ "' ";
                    firstPara = true;
                }else
                {
                    options = options +  " AND SP_FINAL_MARKS = '" + finalMark + "' ";
                }

            }


            if(firstPara)
            {
                query = query + " Where " + options;
            }

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {

                StudentProgramDetail studentProgram =new StudentProgramDetail();
                studentProgram.setStudentID(resultSet.getString("ST_ID"));
                studentProgram.setProgramID(resultSet.getString("PG_ID"));
                studentProgram.setSubjectID(resultSet.getString("SUB_ID"));
                studentProgram.setStudentName(resultSet.getString("ST_NAME"));
                studentProgram.setProgramName(resultSet.getString("PG_NAME"));
                studentProgram.setSubjectName(resultSet.getString("SUB_NAME"));
                studentProgram.setFinalMark(resultSet.getInt("SP_FINAL_MARKS"));
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
            String query = "insert into UCSC.TB_STUDENT_PROGRAM(ST_ID,PG_ID,SUB_ID,SP_FINAL_MARKS) values ('"
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
                listColumns.add("SP_FINAL_MARKS");
                listValues.add(Double.valueOf(studentProgram.getFinalMark()).toString());
            }
            else
            {
                System.out.println(" marks null");
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
                    					+"' AND PG_ID = '" + id.getQueryParameters().getFirst("programID")
                                        +"' AND SUB_ID = '" + id.getQueryParameters().getFirst("subjectID") +"'";
                }

            }
            System.out.println("update statement " + query);

            if (listColumns.size()!=0) {
                statement.execute(query);
            }

            strResponse="Record Successfully Updated";
            return strResponse;

        } catch (Exception e) {
            e.printStackTrace();
            strResponse="Error In Updating The Record";
            return strResponse;

        } finally {

            return strResponse;
        }
    }
}
