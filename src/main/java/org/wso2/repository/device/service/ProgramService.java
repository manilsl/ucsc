package org.wso2.repository.device.service;

import org.wso2.repository.device.dao.ProgramDao;
import org.wso2.repository.device.dao.ProgramDaoImpl;
import org.wso2.repository.device.model.Program;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.sql.SQLException;
import java.util.LinkedList;


@Path("/program/")
public class ProgramService
{

    ProgramDao programDao;


    @DELETE
    @Path("/deleteprogram/{id}/")
    public String deleteProgram(@PathParam("id") String id) throws Exception {

      String strResponse;
      programDao=new ProgramDaoImpl();
      strResponse=programDao.deleteProgram(id);
      return strResponse;

    }

    @GET
    @Path("/getprogram/")
    @Produces(MediaType.APPLICATION_JSON)
    public LinkedList<Program> getPrograms(@Context UriInfo parameters) throws SQLException {
        LinkedList programList=new LinkedList();
        programDao=new ProgramDaoImpl();
        programList=programDao.getProgram(parameters);
        return programList;

    }



    @POST
    @Path("/addprogram/")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addProgram(Program program) throws SQLException {

        String strResponse="";
        programDao=new ProgramDaoImpl();
        strResponse=programDao.addProgram(program);
        return strResponse;

    }


    @PUT
    @Path("/updateprogram/{id}/")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateProgram(Program program ,@PathParam("id") String id ) throws SQLException {

        String strResponse="";
        programDao=new ProgramDaoImpl();
        strResponse=programDao.editProgram(program,id);
        return strResponse;


    }



}
