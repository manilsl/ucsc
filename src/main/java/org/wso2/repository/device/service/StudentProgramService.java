package org.wso2.repository.device.service;

import org.wso2.repository.device.dao.StudentProgramDao;
import org.wso2.repository.device.dao.StudentProgramDaoImpl;
import org.wso2.repository.device.model.StudentProgram;
import org.wso2.repository.device.model.StudentProgramDetail;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.sql.SQLException;
import java.util.LinkedList;


@Path("/studentprogram/")
public class StudentProgramService
{

    StudentProgramDao studentprogramDao;


    @DELETE
    @Path("/deletestudentprogram/")
    public String deleteStudentProgram(@Context UriInfo  id) throws Exception {

      String strResponse;
      studentprogramDao=new StudentProgramDaoImpl();
      strResponse=studentprogramDao.deleteStudentProgram(id);
      return strResponse;

    }

    @GET
    @Path("/getstudentprogram/")
    @Produces(MediaType.APPLICATION_JSON)
    public LinkedList<StudentProgram> getStudentPrograms(@Context UriInfo parameters) throws SQLException {
        LinkedList studentprogramList=new LinkedList();
        studentprogramDao=new StudentProgramDaoImpl();
        studentprogramList=studentprogramDao.getStudentProgram(parameters);
        return studentprogramList;

    }


    @GET
    @Path("/getstudentprogramdetail/")
    @Produces(MediaType.APPLICATION_JSON)
    public LinkedList<StudentProgramDetail> getStudentProgramsDetail(@Context UriInfo parameters) throws SQLException {
        LinkedList studentProgramList=new LinkedList();
        studentprogramDao=new StudentProgramDaoImpl();
        studentProgramList=studentprogramDao.getStudentProgramDetail(parameters);
        return studentProgramList;

    }



    @POST
    @Path("/addstudentprogram/")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addStudentProgram(StudentProgram studentprogram) throws SQLException {

        String strResponse="";
        studentprogramDao=new StudentProgramDaoImpl();
        strResponse=studentprogramDao.addStudentProgram(studentprogram);
        return strResponse;

    }


    @PUT
    @Path("/updatestudentprogram/")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateStudentProgram(StudentProgram studentprogram ,@Context UriInfo id ) throws SQLException {

        String strResponse="";
        studentprogramDao=new StudentProgramDaoImpl();
        strResponse=studentprogramDao.editStudentProgram(studentprogram,id);
        return strResponse;


    }



}
