package com.fosung.eduapi.bean;

import com.haier.cellarette.libretrofit.common.ResponseSlbBean1;

import java.io.Serializable;
import java.util.List;

public class EduResourceListBean extends ResponseSlbBean1 implements Serializable {

    private int totalelements;
    private int pagesize;
    private int pagerealsize;
    private int totalpages;
    private int pagenum;
    private List<DatalistBean> datalist;

    public int getTotalelements() {
        return totalelements;
    }

    public void setTotalelements(int totalelements) {
        this.totalelements = totalelements;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getPagerealsize() {
        return pagerealsize;
    }

    public void setPagerealsize(int pagerealsize) {
        this.pagerealsize = pagerealsize;
    }

    public int getTotalpages() {
        return totalpages;
    }

    public void setTotalpages(int totalpages) {
        this.totalpages = totalpages;
    }

    public int getPagenum() {
        return pagenum;
    }

    public void setPagenum(int pagenum) {
        this.pagenum = pagenum;
    }

    public List<DatalistBean> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<DatalistBean> datalist) {
        this.datalist = datalist;
    }

    public static class DatalistBean implements Serializable {
        private String resourceId;
        private Object externalLink;
        private String sign;
        private Object shortTitle;
        private String type;
        private String orgId;
        private int duration;
        private String terminalName;
        private boolean readonly;
        private String outId;
        private String id;
        private String mp4Status;
        private String createOrgName;
        private String owner;
        private boolean editFlag;
        private String processInstanceId;
        private double studyHour;
        private String orgName;
        private String lastOrgName;
        private int level;
        private String auditStatus_dict;
        private String mediaType;
        private String materialId;
        private String wmv9Status;
        private String url;
        private String createOrgId;
        private String resourceStatus;
        private String assigneeStatus;
        private Object downloadAuditStatus;
        private String createTime;
        private String type_dict;
        private String lastOrgId;
        private String name;
        private String auditStatus;
        private String resourceStatus_dict;
        private String assignee;
        private String lastUpdateTime;
        private String processNodeName;

        public String getResourceId() {
            return resourceId;
        }

        public void setResourceId(String resourceId) {
            this.resourceId = resourceId;
        }

        public Object getExternalLink() {
            return externalLink;
        }

        public void setExternalLink(Object externalLink) {
            this.externalLink = externalLink;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public Object getShortTitle() {
            return shortTitle;
        }

        public void setShortTitle(Object shortTitle) {
            this.shortTitle = shortTitle;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getTerminalName() {
            return terminalName;
        }

        public void setTerminalName(String terminalName) {
            this.terminalName = terminalName;
        }

        public boolean isReadonly() {
            return readonly;
        }

        public void setReadonly(boolean readonly) {
            this.readonly = readonly;
        }

        public String getOutId() {
            return outId;
        }

        public void setOutId(String outId) {
            this.outId = outId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMp4Status() {
            return mp4Status;
        }

        public void setMp4Status(String mp4Status) {
            this.mp4Status = mp4Status;
        }

        public String getCreateOrgName() {
            return createOrgName;
        }

        public void setCreateOrgName(String createOrgName) {
            this.createOrgName = createOrgName;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public boolean isEditFlag() {
            return editFlag;
        }

        public void setEditFlag(boolean editFlag) {
            this.editFlag = editFlag;
        }

        public String getProcessInstanceId() {
            return processInstanceId;
        }

        public void setProcessInstanceId(String processInstanceId) {
            this.processInstanceId = processInstanceId;
        }

        public double getStudyHour() {
            return studyHour;
        }

        public void setStudyHour(double studyHour) {
            this.studyHour = studyHour;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getLastOrgName() {
            return lastOrgName;
        }

        public void setLastOrgName(String lastOrgName) {
            this.lastOrgName = lastOrgName;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getAuditStatus_dict() {
            return auditStatus_dict;
        }

        public void setAuditStatus_dict(String auditStatus_dict) {
            this.auditStatus_dict = auditStatus_dict;
        }

        public String getMediaType() {
            return mediaType;
        }

        public void setMediaType(String mediaType) {
            this.mediaType = mediaType;
        }

        public String getMaterialId() {
            return materialId;
        }

        public void setMaterialId(String materialId) {
            this.materialId = materialId;
        }

        public String getWmv9Status() {
            return wmv9Status;
        }

        public void setWmv9Status(String wmv9Status) {
            this.wmv9Status = wmv9Status;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCreateOrgId() {
            return createOrgId;
        }

        public void setCreateOrgId(String createOrgId) {
            this.createOrgId = createOrgId;
        }

        public String getResourceStatus() {
            return resourceStatus;
        }

        public void setResourceStatus(String resourceStatus) {
            this.resourceStatus = resourceStatus;
        }

        public String getAssigneeStatus() {
            return assigneeStatus;
        }

        public void setAssigneeStatus(String assigneeStatus) {
            this.assigneeStatus = assigneeStatus;
        }

        public Object getDownloadAuditStatus() {
            return downloadAuditStatus;
        }

        public void setDownloadAuditStatus(Object downloadAuditStatus) {
            this.downloadAuditStatus = downloadAuditStatus;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getType_dict() {
            return type_dict;
        }

        public void setType_dict(String type_dict) {
            this.type_dict = type_dict;
        }

        public String getLastOrgId() {
            return lastOrgId;
        }

        public void setLastOrgId(String lastOrgId) {
            this.lastOrgId = lastOrgId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAuditStatus() {
            return auditStatus;
        }

        public void setAuditStatus(String auditStatus) {
            this.auditStatus = auditStatus;
        }

        public String getResourceStatus_dict() {
            return resourceStatus_dict;
        }

        public void setResourceStatus_dict(String resourceStatus_dict) {
            this.resourceStatus_dict = resourceStatus_dict;
        }

        public String getAssignee() {
            return assignee;
        }

        public void setAssignee(String assignee) {
            this.assignee = assignee;
        }

        public String getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(String lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }

        public String getProcessNodeName() {
            return processNodeName;
        }

        public void setProcessNodeName(String processNodeName) {
            this.processNodeName = processNodeName;
        }
    }
}
