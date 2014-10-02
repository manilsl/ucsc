package org.wso2.repository.device.service;

import org.wso2.repository.device.dao.StudentProgramDao;
import org.wso2.repository.device.dao.StudentProgramDaoImpl;
import org.wso2.repository.device.model.StudentProgram;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.sql.SQLException;
import java.util.LinkedList;


@Path("/studentProgram/")
public class StudentProgramService
{

    StudentProgramDao studentProgramDao;


    @DELETE
    @Path("/deletestudentProgram/")
    public String deleteStudentProgram(@Context UriInfo  id) throws Exception {

      String strResponse;
      studentProgramDao=new StudentProgramDaoImpl();
      strResponse=studentProgramDao.deleteStudentProgram(id);
      return strResponse;

    }

    @GET
    @Path("/getstudentProgram/")
    @Produces(MediaType.APPLICATION_JSON)
    public LinkedList<StudentProgram> getStudentPrograms(@Context UriInfo parameters) throws SQLException {
        LinkedList studentProgramList=new LinkedList();
        studentProgramDao=new StudentProgramDaoImpl();
        studentProgramList=studentProgramDao.getStudentProgram(parameters);
        return studentProgramList;

    }



    @POST
    @Path("/addstudentProgram/")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addStudentProgram(StudentProgram studentProgram) throws SQLException {

        String strResponse="";
        studentProgramDao=new StudentProgramDaoImpl();
        strResponse=studentProgramDao.addStudentProgram(studentProgram);
        return strResponse;

    }


    @PUT
    @Path("/updatestudentProgram/")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateStudentProgram(StudentProgram studentProgram ,@Context UriInfo id ) throws SQLException {

        String strResponse="";
        studentProgramDao=new StudentProgramDaoImpl();
        strResponse=studentProgramDao.editStudentProgram(studentProgram,id);
        return strResponse;


    }



}
