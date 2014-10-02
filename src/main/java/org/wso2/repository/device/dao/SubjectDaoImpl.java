package org.wso2.repository.device.dao;

import org.wso2.repository.device.model.Subject;
import org.wso2.repository.device.util.DB;

import javax.ws.rs.core.UriInfo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;


public class SubjectDaoImpl implements SubjectDao{

   Subject subject;
   Connection connection;




    public String deleteSubject(String id) throws Exception {
        String strResponse="";
        Connection con = DB.getConnection();
        Statement stmt = con.createStatement();
        // String schema= con.getSchema();

        try{

            String strCount = "select count(*) cnt from UCSC.TB_SUBJECT where SUB_ID  ='" + id +"'";
            ResultSet resultSet = stmt.executeQuery(strCount);
            resultSet.next();


            if(resultSet.getInt("cnt") == 0)
            {strResponse="No Records To Be Deleted";}
            {
                String query = "delete from UCSC.TB_SUBJECT where SUB_ID ='" +id+"'";
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

    public LinkedList<Subject> getSubject(UriInfo parameters) {

        LinkedList subjectList=new LinkedList();

        try {
            String subjectId = parameters.getQueryParameters().getFirst("subjectID");
            String subjectName = parameters.getQueryParameters().getFirst("subjectName");


            String options = null;

            Connection con=DB.getConnection();
            Statement statement=con.createStatement();
            String query ="select * from UCSC.TB_SUBJECT ";

            boolean firstPara = false;

            if (subjectId !=null)
            {
                options = " SUB_ID = '" + subjectId +"' ";
                firstPara =true;
            }

            if (subjectName !=null)
            {
                if (firstPara==false) {
                    options = " SUB_NAME = '" + subjectName+ "' ";
                    firstPara = true;
                }else
                {
                    options = options +  " AND SUB_NAME = '" + subjectName + "' ";
                }

            }




            if(firstPara)
            {
                query = query + " Where " + options;
            }

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {

                Subject subject =new Subject();
                subject.setSubjectID(resultSet.getString("SUB_ID"));
                subject.setSubjectName(resultSet.getString("SUB_NAME"));

                subjectList.add(subject);
            }


            return subjectList;

        }catch (Exception e) {
            e.printStackTrace();

            return subjectList;

        }finally{
            return subjectList;
        }
    }



    public String addSubject(Subject subject) {

        String strResponse = null;
        try {

            Connection con = DB.getConnection();
            Statement stmt = con.createStatement();
            String query = "insert into UCSC.TB_SUBJECT(SUB_ID,SUB_NAME) values ('" + subject.getSubjectID() + "','"
                    + subject.getSubjectName() + "')";

            stmt.executeUpdate(query);
            strResponse="Subject Added Successfully";

        } catch (Exception ee) {
            ee.printStackTrace();
            strResponse="Error in adding the Subject";
        }finally{
            return strResponse;
        }
    }

    public String editSubject(Subject subject, String id) {
        String strResponse = null;
        try {

            Connection connection = DB.getConnection();
            Statement statement = connection.createStatement();

            String query =null;
            LinkedList<String> listColumns= new LinkedList<String>();
            LinkedList<String> listValues= new LinkedList<String>();
           
            if( subject.getSubjectName()!=null) {
                listColumns.add("SUB_NAME");
                listValues.add(subject.getSubjectName());
            }


            for (int x= 0;x<listColumns.size();x++) {

                if(x==0)
                {
                    query = "update UCSC.TB_SUBJECT set ";
                }

                if(x!=(listColumns.size()-1))
                {
                    query = query + listColumns.get(x) + " = '";
                    query = query + listValues.get(x) + "' , ";
                }
                else{
                    query = query + listColumns.get(x) + " = '";
                    query = query + listValues.get(x)+ "' WHERE SUB_ID = '" + id+"'";
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
