package org.wso2.repository.device.dao;

import org.wso2.repository.device.model.StudentProgram;

import javax.ws.rs.core.UriInfo;
import java.util.LinkedList;

public interface StudentProgramDao {

    public String deleteStudentProgram(String id) throws Exception;
    public LinkedList<StudentProgram> getStudentProgram(UriInfo parameters);
    public String addStudentProgram(StudentProgram studentProgram);
    public String editStudentProgram(StudentProgram studentProgram, String id);

}
