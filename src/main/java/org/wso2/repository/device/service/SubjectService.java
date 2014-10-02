package org.wso2.repository.device.service;

import org.wso2.repository.device.dao.SubjectDao;
import org.wso2.repository.device.dao.SubjectDaoImpl;
import org.wso2.repository.device.model.Subject;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.sql.SQLException;
import java.util.LinkedList;


@Path("/subject/")
public class SubjectService
{

    SubjectDao subjectDao;


    @DELETE
    @Path("/deletesubject/{id}/")
    public String deleteSubject(@PathParam("id") String id) throws Exception {

      String strResponse;
      subjectDao=new SubjectDaoImpl();
      strResponse=subjectDao.deleteSubject(id);
      return strResponse;

    }

    @GET
    @Path("/getsubject/")
    @Produces(MediaType.APPLICATION_JSON)
    public LinkedList<Subject> getSubjects(@Context UriInfo parameters) throws SQLException {
        LinkedList subjectList=new LinkedList();
        subjectDao=new SubjectDaoImpl();
        subjectList=subjectDao.getSubject(parameters);
        return subjectList;

    }



    @POST
    @Path("/addsubject/")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addSubject(Subject subject) throws SQLException {

        String strResponse="";
        subjectDao=new SubjectDaoImpl();
        strResponse=subjectDao.addSubject(subject);
        return strResponse;

    }


    @PUT
    @Path("/updatesubject/{id}/")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateSubject(Subject subject ,@PathParam("id") String id ) throws SQLException {

        String strResponse="";
        subjectDao=new SubjectDaoImpl();
        strResponse=subjectDao.editSubject(subject,id);
        return strResponse;


    }



}
