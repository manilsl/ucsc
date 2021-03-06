package org.wso2.repository.device.dao;



import org.wso2.repository.device.model.Student;

import javax.ws.rs.core.UriInfo;
import java.util.LinkedList;

public interface StudentDao {

    public String deleteStudent(String id) throws Exception;
    public LinkedList<Student> getStudent(UriInfo parameters);
    public String addStudent(Student student);
    public String editStudent(Student student, String id);

}
