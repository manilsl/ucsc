package org.wso2.repository.device.dao;

import org.wso2.repository.device.model.Course;

import javax.ws.rs.core.UriInfo;
import java.util.LinkedList;

public interface CourseDao {

    public String deleteCourse(String id) throws Exception;
    public LinkedList<Course> getCourse(UriInfo parameters);
    public String addCourse(Course course);
    public String editCourse(Course course, String id);

}
