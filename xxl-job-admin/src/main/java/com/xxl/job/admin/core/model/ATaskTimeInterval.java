package com.xxl.job.admin.core.model;

public class ATaskTimeInterval {
    private int timeintervalid;
    private int taskid;
    private int begindate;
    private int begintime;
    private int enddate;
    private int endtime;
    private int actiontype;
    private String actiondays;
    private int runmode;
    private int interval;
    private int timeflags;
    private int timetype;

    public int getTimeintervalid() {
        return timeintervalid;
    }

    public void setTimeintervalid(int timeintervalid) {
        this.timeintervalid = timeintervalid;
    }

    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }

    public int getBegindate() {
        return begindate;
    }

    public void setBegindate(int begindate) {
        this.begindate = begindate;
    }

    public int getBegintime() {
        return begintime;
    }

    public void setBegintime(int begintime) {
        this.begintime = begintime;
    }

    public int getEnddate() {
        return enddate;
    }

    public void setEnddate(int enddate) {
        this.enddate = enddate;
    }

    public int getEndtime() {
        return endtime;
    }

    public void setEndtime(int endtime) {
        this.endtime = endtime;
    }

    public int getActiontype() {
        return actiontype;
    }

    public void setActiontype(int actiontype) {
        this.actiontype = actiontype;
    }

    public String getActiondays() {
        return actiondays;
    }

    public void setActiondays(String actiondays) {
        this.actiondays = actiondays;
    }

    public int getRunmode() {
        return runmode;
    }

    public void setRunmode(int runmode) {
        this.runmode = runmode;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getTimeflags() {
        return timeflags;
    }

    public void setTimeflags(int timeflags) {
        this.timeflags = timeflags;
    }

    public int getTimetype() {
        return timetype;
    }

    public void setTimetype(int timetype) {
        this.timetype = timetype;
    }

    @Override
    public String toString() {
        return "ATaskTimeInterval{" +
                "timeintervalid=" + timeintervalid +
                ", taskid=" + taskid +
                ", begindate=" + begindate +
                ", begintime=" + begintime +
                ", enddate=" + enddate +
                ", endtime=" + endtime +
                ", actiontype=" + actiontype +
                ", actiondays='" + actiondays + '\'' +
                ", runmode=" + runmode +
                ", interval=" + interval +
                ", timeflags=" + timeflags +
                ", timetype=" + timetype +
                '}';
    }
}
