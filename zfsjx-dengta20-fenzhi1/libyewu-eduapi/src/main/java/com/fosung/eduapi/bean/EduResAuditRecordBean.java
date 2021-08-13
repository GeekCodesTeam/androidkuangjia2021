package com.fosung.eduapi.bean;

import com.haier.cellarette.libretrofit.common.ResponseSlbBean1;

import java.io.Serializable;
import java.util.List;

public class EduResAuditRecordBean extends ResponseSlbBean1 implements Serializable {

    private List<DatalistBean> datalist;

    public List<DatalistBean> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<DatalistBean> datalist) {
        this.datalist = datalist;
    }

    public static class DatalistBean implements Serializable {
        private String processInstanceId;
        private String resourceId;
        private String orgName;
        private String suggestion;
        private Object remark;
        private String userName;
        private String userId;
        private String orgId;
        private String createDatetime;
        private String lastUpdateDatetime;
        private String operate;
        private String auditTime;
        private String resourceGrade;
        private String status_dict;
        private Object processNodeId;
        private String id;
        private String status;
        private Object processNodeName;

        public String getProcessInstanceId() {
            return processInstanceId;
        }

        public void setProcessInstanceId(String processInstanceId) {
            this.processInstanceId = processInstanceId;
        }

        public String getResourceId() {
            return resourceId;
        }

        public void setResourceId(String resourceId) {
            this.resourceId = resourceId;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getSuggestion() {
            return suggestion;
        }

        public void setSuggestion(String suggestion) {
            this.suggestion = suggestion;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getCreateDatetime() {
            return createDatetime;
        }

        public void setCreateDatetime(String createDatetime) {
            this.createDatetime = createDatetime;
        }

        public String getLastUpdateDatetime() {
            return lastUpdateDatetime;
        }

        public void setLastUpdateDatetime(String lastUpdateDatetime) {
            this.lastUpdateDatetime = lastUpdateDatetime;
        }

        public String getOperate() {
            return operate;
        }

        public void setOperate(String operate) {
            this.operate = operate;
        }

        public String getAuditTime() {
            return auditTime;
        }

        public void setAuditTime(String auditTime) {
            this.auditTime = auditTime;
        }

        public String getResourceGrade() {
            return resourceGrade;
        }

        public void setResourceGrade(String resourceGrade) {
            this.resourceGrade = resourceGrade;
        }

        public String getStatus_dict() {
            return status_dict;
        }

        public void setStatus_dict(String status_dict) {
            this.status_dict = status_dict;
        }

        public Object getProcessNodeId() {
            return processNodeId;
        }

        public void setProcessNodeId(Object processNodeId) {
            this.processNodeId = processNodeId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getProcessNodeName() {
            return processNodeName;
        }

        public void setProcessNodeName(Object processNodeName) {
            this.processNodeName = processNodeName;
        }
    }
}
