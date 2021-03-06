package org.wso2.repository.device.dao;

import org.wso2.repository.device.model.Subject;

import javax.ws.rs.core.UriInfo;
import java.util.LinkedList;

public interface SubjectDao {

    public String deleteSubject(String id) throws Exception;
    public LinkedList<Subject> getSubject(UriInfo parameters);
    public String addSubject(Subject subject);
    public String editSubject(Subject subject, String id);

}
