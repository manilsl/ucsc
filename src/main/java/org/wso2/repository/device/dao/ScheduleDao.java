package org.wso2.repository.device.dao;

import org.wso2.repository.device.model.Schedule;

import javax.ws.rs.core.UriInfo;
import java.util.LinkedList;

public interface ScheduleDao {

    public String deleteSchedule(String id) throws Exception;
    public LinkedList<Schedule> getSchedule(UriInfo parameters);
    public String addSchedule(Schedule schedule);
    public String editSchedule(Schedule schedule, String id);

}
