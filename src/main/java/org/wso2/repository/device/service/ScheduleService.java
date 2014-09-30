package org.wso2.repository.device.service;

import org.wso2.repository.device.dao.ScheduleDao;
import org.wso2.repository.device.dao.ScheduleDaoImpl;
import org.wso2.repository.device.model.Schedule;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.sql.SQLException;
import java.util.LinkedList;


@Path("/schedule/")
public class ScheduleService
{

    ScheduleDao scheduleDao;


    @DELETE
    @Path("/deleteschedule/{id}/")
    public String deleteSchedule(@PathParam("id") String id) throws Exception {

      String strResponse;
      scheduleDao=new ScheduleDaoImpl();
      strResponse=scheduleDao.deleteSchedule(id);
      return strResponse;

    }

    @GET
    @Path("/getschedule/")
    @Produces(MediaType.APPLICATION_JSON)
    public LinkedList<Schedule> getSchedules(@Context UriInfo parameters) throws SQLException {
        LinkedList scheduleList=new LinkedList();
        scheduleDao=new ScheduleDaoImpl();
        scheduleList=scheduleDao.getSchedule(parameters);
        return scheduleList;

    }



    @POST
    @Path("/addschedule/")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addSchedule(Schedule schedule) throws SQLException {

        String strResponse="";
        scheduleDao=new ScheduleDaoImpl();
        strResponse=scheduleDao.addSchedule(schedule);
        return strResponse;

    }


    @PUT
    @Path("/updateschedule/{id}/")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateSchedule(Schedule schedule ,@PathParam("id") String id ) throws SQLException {

        String strResponse="";
        scheduleDao=new ScheduleDaoImpl();
        strResponse=scheduleDao.editSchedule(schedule,id);
        return strResponse;


    }



}
