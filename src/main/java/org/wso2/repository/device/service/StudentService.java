package org.wso2.repository.device.service;

import org.wso2.repository.device.dao.StudentDao;
import org.wso2.repository.device.dao.StudentDaoImpl;
import org.wso2.repository.device.model.Student;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.sql.SQLException;
import java.util.LinkedList;


@Path("/student/")
public class StudentService
{

    StudentDao studentDao;


    @DELETE
    @Path("/deletestudent/{id}/")
    public String deleteStudent(@PathParam("id") String id) throws Exception {

      String strResponse;
      studentDao=new StudentDaoImpl();
      strResponse=studentDao.deleteStudent(id);
      return strResponse;

    }

    @GET
    @Path("/getstudent/")
    @Produces(MediaType.APPLICATION_JSON)
    public LinkedList<Student> getStudents(@Context UriInfo parameters) throws SQLException {
        LinkedList studentList=new LinkedList();
        studentDao=new StudentDaoImpl();
        studentList=studentDao.getStudent(parameters);
        return studentList;

    }



    @POST
    @Path("/addstudent/")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addStudent(Student student) throws SQLException {

        String strResponse="";
        studentDao=new StudentDaoImpl();
        strResponse=studentDao.addStudent(student);
        return strResponse;

    }


    @PUT
    @Path("/updatestudent/{id}/")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateStudent(Student student ,@PathParam("id") String id ) throws SQLException {

        String strResponse="";
        studentDao=new StudentDaoImpl();
        strResponse=studentDao.editStudent(student,id);
        return strResponse;


    }



}
