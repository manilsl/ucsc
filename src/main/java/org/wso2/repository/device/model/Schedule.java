package org.wso2.repository.device.model;

/**
 * Created by manilsl on 7/10/14.
 */
public class Schedule {

    String scheduleID;
    String year;
    String batch;

    public String getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }
}
