package org.wso2.repository.device.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by manilsl on 6/12/14.
 */
@XmlRootElement(name="Student")
public class Student {

    String studentID;
    String firstName;
    String lastName;
    String nicNo;

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNicNo() {
        return nicNo;
    }

    public void setNicNo(String nicNo) {
        this.nicNo = nicNo;
    }
}
