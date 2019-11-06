package com.groupseven.voicestamp.db.bean;

public class Record {

    private String userId;
    private String recordTitle;
    private String recordDate;
    private String duration;
    private String recordId;
    private String localPath;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRecordTitle() {
        return recordTitle;
    }

    public void setRecordTitle(String recordTitle) {
        this.recordTitle = recordTitle;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    @Override
    public String toString() {
        return "Record{" +
                "userId='" + userId + '\'' +
                ", recordTitle='" + recordTitle + '\'' +
                ", recordDate='" + recordDate + '\'' +
                ", duration='" + duration + '\'' +
                ", recordId='" + recordId + '\'' +
                ", localPath='" + localPath + '\'' +
                '}';
    }
}
