package org.wso2.repository.device.service;

import org.wso2.repository.device.dao.CourseDao;
import org.wso2.repository.device.dao.CourseDaoImpl;
import org.wso2.repository.device.model.Course;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.sql.SQLException;
import java.util.LinkedList;


@Path("/course/")
public class CourseService
{

    CourseDao courseDao;


    @DELETE
    @Path("/deletecourse/{id}/")
    public String deleteCourse(@PathParam("id") String id) throws Exception {

      String strResponse;
      courseDao=new CourseDaoImpl();
      strResponse=courseDao.deleteCourse(id);
      return strResponse;

    }

    @GET
    @Path("/getcourse/")
    @Produces(MediaType.APPLICATION_JSON)
    public LinkedList<Course> getCourses(@Context UriInfo parameters) throws SQLException {
        LinkedList courseList=new LinkedList();
        courseDao=new CourseDaoImpl();
        courseList=courseDao.getCourse(parameters);
        return courseList;

    }



    @POST
    @Path("/addcourse/")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addCourse(Course course) throws SQLException {

        String strResponse="";
        courseDao=new CourseDaoImpl();
        strResponse=courseDao.addCourse(course);
        return strResponse;

    }


    @PUT
    @Path("/updatecourse/{id}/")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateCourse(Course course ,@PathParam("id") String id ) throws SQLException {

        String strResponse="";
        courseDao=new CourseDaoImpl();
        strResponse=courseDao.editCourse(course,id);
        return strResponse;


    }



}
