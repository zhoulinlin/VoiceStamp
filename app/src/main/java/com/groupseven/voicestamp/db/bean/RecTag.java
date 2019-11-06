package com.groupseven.voicestamp.db.bean;

public class RecTag {

    private String recordId;
    private String tagType;
    private String tagTitle;
    private String tagDate;
    private String tagContent;
    private String extInfo;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public String getTagTitle() {
        return tagTitle;
    }

    public void setTagTitle(String tagTitle) {
        this.tagTitle = tagTitle;
    }

    public String getTagDate() {
        return tagDate;
    }

    public void setTagDate(String tagDate) {
        this.tagDate = tagDate;
    }

    public String getTagContent() {
        return tagContent;
    }

    public void setTagContent(String tagContent) {
        this.tagContent = tagContent;
    }

    public String getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "recordId='" + recordId + '\'' +
                ", tagType='" + tagType + '\'' +
                ", tagTitle='" + tagTitle + '\'' +
                ", tagDate='" + tagDate + '\'' +
                ", tagContent='" + tagContent + '\'' +
                ", extInfo='" + extInfo + '\'' +
                '}';
    }
}
