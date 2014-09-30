package org.wso2.repository.device.dao;



import org.wso2.repository.device.model.Program;

import javax.ws.rs.core.UriInfo;
import java.util.LinkedList;

public interface ProgramDao {

    public String deleteProgram(String id) throws Exception;
    public LinkedList<Program> getProgram(UriInfo parameters);
    public String addProgram(Program program);
    public String editProgram(Program program, String id);

}
