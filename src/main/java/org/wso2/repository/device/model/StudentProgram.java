package org.wso2.repository.device.model;

/**
 * Created by manilsl on 7/10/14.
 */
public class StudentProgram {

    String studentID;
    String programID;
    String subjectID;
    long finalMark;

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getProgramID() {
        return programID;
    }

    public void setProgramID(String programID) {
        this.programID = programID;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public long getFinalMark() {
        return finalMark;
    }

    public void setFinalMark(long finalMark) {
        this.finalMark = finalMark;
    }
}
