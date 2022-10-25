package com.xxl.job.admin.core.model;

public class ATaskInfo {
    
    private int taskId;

    private int serverId;
    
    private int taskSource;
    
    private int taskSourceId;
    
    private int taskType;
    
    private String taskStatus;
    
    private String busType;
    
    private String busInitKey;
    
    private String busKey;
    
    private String busCtrlParam;

    private String busRunParam;
    
    private int busTimeout;
    
    private int busFailHanding;
    
    private int taskRunStatus;
    
    private int lastRunDate;
    
    private int lastRunTime;
    
    private int taskValid;
    
    private int taskVerNo;
    
    private String remark;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public int getTaskSource() {
        return taskSource;
    }

    public void setTaskSource(int taskSource) {
        this.taskSource = taskSource;
    }

    public int getTaskSourceId() {
        return taskSourceId;
    }

    public void setTaskSourceId(int taskSourceId) {
        this.taskSourceId = taskSourceId;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getBusKey() {
        return busKey;
    }

    public void setBusKey(String busKey) {
        this.busKey = busKey;
    }

    public String getBusCtrlParam() {
        return busCtrlParam;
    }

    public void setBusCtrlParam(String busCtrlParam) {
        this.busCtrlParam = busCtrlParam;
    }

    public String getBusRunParam() {
        return busRunParam;
    }

    public void setBusRunParam(String busRunParam) {
        this.busRunParam = busRunParam;
    }

    public int getBusTimeout() {
        return busTimeout;
    }

    public void setBusTimeout(int busTimeout) {
        this.busTimeout = busTimeout;
    }

    public int getBusFailHanding() {
        return busFailHanding;
    }

    public void setBusFailHanding(int busFailHanding) {
        this.busFailHanding = busFailHanding;
    }

    public int getTaskRunStatus() {
        return taskRunStatus;
    }

    public void setTaskRunStatus(int taskRunStatus) {
        this.taskRunStatus = taskRunStatus;
    }

    public int getLastRunDate() {
        return lastRunDate;
    }

    public void setLastRunDate(int lastRunDate) {
        this.lastRunDate = lastRunDate;
    }

    public int getLastRunTime() {
        return lastRunTime;
    }

    public void setLastRunTime(int lastRunTime) {
        this.lastRunTime = lastRunTime;
    }

    public int getTaskValid() {
        return taskValid;
    }

    public void setTaskValid(int taskValid) {
        this.taskValid = taskValid;
    }

    public int getTaskVerNo() {
        return taskVerNo;
    }

    public void setTaskVerNo(int taskVerNo) {
        this.taskVerNo = taskVerNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBusInitKey() {
        return busInitKey;
    }

    public void setBusInitKey(String busInitKey) {
        this.busInitKey = busInitKey;
    }

    @Override
    public String toString() {
        return "ATaskInfo{" +
                "taskId=" + taskId +
                ", serverId=" + serverId +
                ", taskSource=" + taskSource +
                ", taskSourceId=" + taskSourceId +
                ", taskType=" + taskType +
                ", taskStatus='" + taskStatus + '\'' +
                ", busType='" + busType + '\'' +
                ", busInitKey='" + busInitKey + '\'' +
                ", busKey='" + busKey + '\'' +
                ", busCtrlParam='" + busCtrlParam + '\'' +
                ", busRunParam='" + busRunParam + '\'' +
                ", busTimeout=" + busTimeout +
                ", busFailHanding=" + busFailHanding +
                ", taskRunStatus=" + taskRunStatus +
                ", lastRunDate=" + lastRunDate +
                ", lastRunTime=" + lastRunTime +
                ", taskValid=" + taskValid +
                ", taskVerNo=" + taskVerNo +
                ", remark='" + remark + '\'' +
                '}';
    }
}
