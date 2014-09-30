package org.wso2.repository.device.dao;

import org.wso2.repository.device.model.Course;
import org.wso2.repository.device.util.DB;

import javax.ws.rs.core.UriInfo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;


public class CourseDaoImpl implements CourseDao{

   Course course;
   Connection connection;




    public String deleteCourse(String id) throws Exception {
        String strResponse="";
        Connection con = DB.getConnection();
        Statement stmt = con.createStatement();
        // String schema= con.getSchema();

        try{

            String strCount = "select count(*) cnt from UCSC.TB_COURSE where CO_ID  ='" + id +"'";
            ResultSet resultSet = stmt.executeQuery(strCount);
            resultSet.next();


            if(resultSet.getInt("cnt") == 0)
            {strResponse="No Records To Be Deleted";}
            {
                String query = "delete from UCSC.TB_COURSE where CO_ID ='" + id+"'";
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

    public LinkedList<Course> getCourse(UriInfo parameters) {

        LinkedList courseList=new LinkedList();

        try {
            String courseId = parameters.getQueryParameters().getFirst("courseID");
            String courseName = parameters.getQueryParameters().getFirst("courseName");

            String options = null;

            Connection con=DB.getConnection();
            Statement statement=con.createStatement();
            String query ="select * from UCSC.TB_COURSE ";

            boolean firstPara = false;

            if (courseId !=null)
            {
                options = " CO_ID = '" + courseId +"' ";
                firstPara =true;
            }

            if (courseName !=null)
            {
                if (firstPara==false) {
                    options = " CO_NAME = '" + courseName+ "' ";
                    firstPara = true;
                }else
                {
                    options = options +  " AND CO_NAME = '" + courseName + "' ";
                }

            }


            if(firstPara)
            {
                query = query + " Where " + options;
            }

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {

                Course course =new Course();
                course.setCourseID(resultSet.getString("CO_ID"));
                course.setCourseName(resultSet.getString("CO_NAME"));
                courseList.add(course);
            }


            return courseList;

        }catch (Exception e) {
            e.printStackTrace();

            return courseList;

        }finally{
            return courseList;
        }
    }



    public String addCourse(Course course) {

        String strResponse = null;
        try {

            Connection con = DB.getConnection();
            Statement stmt = con.createStatement();
            String query = "insert into UCSC.TB_COURSE(CO_ID,CO_NAME) values ('" + course.getCourseID() + "','"
                    + course.getCourseName() +"')";

            stmt.executeUpdate(query);
            strResponse="Course Added Successfully";

        } catch (Exception ee) {
            ee.printStackTrace();
            strResponse="Error in adding the Course";
        }finally{
            return strResponse;
        }
    }

    public String editCourse(Course course, String id) {
        String strResponse = null;
        try {

            Connection connection = DB.getConnection();
            Statement statement = connection.createStatement();

            String query =null;

            if( course.getCourseName()!=null)
            {
                query = "update UCSC.TB_COURSE set CO_NAME ='"+course.getCourseName() +"' WHERE CO_ID ='" + id+"'";
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
