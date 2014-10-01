package org.wso2.repository.device.dao;

import org.wso2.repository.device.model.Student;
import org.wso2.repository.device.util.DB;

import javax.ws.rs.core.UriInfo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;


public class StudentDaoImpl implements StudentDao{

   Student student;
   Connection connection;




    public String deleteStudent(String id) throws Exception {
        String strResponse="";
        Connection con = DB.getConnection();
        Statement stmt = con.createStatement();
        // String schema= con.getSchema();

        try{

            String strCount = "select count(*) cnt from UCSC.TB_STUDENT where ST_ID  ='" + id +"'";
            ResultSet resultSet = stmt.executeQuery(strCount);
            resultSet.next();


            if(resultSet.getInt("cnt") == 0)
            {strResponse="No Records To Be Deleted";}
            {
                String query = "delete from UCSC.TB_STUDENT where ST_ID ='" +id+"'";
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

    public LinkedList<Student> getStudent(UriInfo parameters) {

        LinkedList studentList=new LinkedList();

        try {
            String studentId = parameters.getQueryParameters().getFirst("studentID");
            String firstName = parameters.getQueryParameters().getFirst("firstName");
            String lastName = parameters.getQueryParameters().getFirst("lastName");
			String nicNo = parameters.getQueryParameters().getFirst("nicNo");

			
            String options = null;

            Connection con=DB.getConnection();
            Statement statement=con.createStatement();
            String query ="select * from UCSC.TB_STUDENT ";

            boolean firstPara = false;

            if (studentId !=null)
            {
                options = " ST_ID = '" + studentId +"' ";
                firstPara =true;
            }

            if (firstName !=null)
            {
                if (firstPara==false) {
                    options = " ST_FIRST_NAME = '" + firstName+ "' ";
                    firstPara = true;
                }else
                {
                    options = options +  " AND ST_FIRST_NAME = '" + firstName + "' ";
                }

            }

            if (lastName !=null)
            {
                if (firstPara==false) {
                    options = " ST_LAST_NAME = '" + lastName+ "' ";
                    firstPara = true;
                }else
                {
                    options = options +  " AND ST_LAST_NAME = '" + lastName + "' ";
                }

            }
			
			if (nicNo !=null)
            {
                if (firstPara==false) {
                    options = " ST_NIC = '" + nicNo+ "' ";
                    firstPara = true;
                }else
                {
                    options = options +  " AND ST_NIC = '" + nicNo + "' ";
                }

            }


            if(firstPara)
            {
                query = query + " Where " + options;
            }

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {

                Student student =new Student();
                student.setStudentID(resultSet.getString("ST_ID"));
                student.setFirstName(resultSet.getString("ST_FIRST_NAME"));
                student.setLastName(resultSet.getString("ST_LAST_NAME"));
				student.setNicNo(resultSet.getString("ST_NIC"));
                studentList.add(student);
            }


            return studentList;

        }catch (Exception e) {
            e.printStackTrace();

            return studentList;

        }finally{
            return studentList;
        }
    }



    public String addStudent(Student student) {

        String strResponse = null;
        try {

            Connection con = DB.getConnection();
            Statement stmt = con.createStatement();
            String query = "insert into UCSC.TB_STUDENT(ST_ID,ST_FIRST_NAME,ST_LAST_NAME,ST_NIC) values ('" + student.getStudentID() + "','"
                    + student.getFirstName()  + "','" + student.getLastName()  + "','" + student.getNicNo() + "')";

            stmt.executeUpdate(query);
            strResponse="Student Added Successfully";

        } catch (Exception ee) {
            ee.printStackTrace();
            strResponse="Error in adding the Student";
        }finally{
            return strResponse;
        }
    }

    public String editStudent(Student student, String id) {
        String strResponse = null;
        try {

            Connection connection = DB.getConnection();
            Statement statement = connection.createStatement();

            String query =null;
            LinkedList<String> listColumns= new LinkedList<String>();
            LinkedList<String> listValues= new LinkedList<String>();
           
            if( student.getCourseID()!=null) {
                listColumns.add("CO_ID");
                listValues.add(student.getCourseID());
            }
            if( student.getScheduleID()!=null) {
                listColumns.add("SCH_ID");
                listValues.add(student.getScheduleID());
            }


            for (int x= 0;x<listColumns.size();x++) {

                if(x==0)
                {
                    query = "update UCSC.TB_STUDENT set ";
                }

                if(x!=(listColumns.size()-1))
                {
                    query = query + listColumns.get(x) + " = '";
                    query = query + listValues.get(x) + "' , ";
                }
                else{
                    query = query + listColumns.get(x) + " = '";
                    query = query + listValues.get(x)+ "' WHERE ST_ID = '" + id+"'";
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
