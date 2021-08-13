package com.fosung.eduapi.bean;

import com.haier.cellarette.libretrofit.common.ResponseSlbBean1;

import java.io.Serializable;
import java.util.List;

public class EduResourceDetailBean extends ResponseSlbBean1 implements Serializable {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        private Object externalLink;
        private String reportOrg;
        private String reportTime;
        private String material_status;
        private Object routerFlag;
        private String type;
        private boolean lock;
        private String id;
        private Object downloadImage;
        private float studyHour;
        private String materialId;
        private String coverUrl;
        private Object createOrgId;
        private Object size;
        private Object contentTypeIds;
        private String subtitle;
        private Object terminalParentName;
        private String status_dict;
        private Object resourceManual;
        private Object terminalIds;
        private String status;
        private Object shared;
        private Object orgResourceId;
        private String durationDisplay;
        private Object reportUserName;
        private Object orgLevel;
        private String terminalId;
        private String resourceAvType;
        private int delFlag;
        private String articleUrl;
        private String orgId;
        private Object lockUserName;
        private Object terminalName;
        private Object lockTime;
        private Object processId;
        private Object outId;
        private String processType;
        private String aiStatus_dict;
        private String editor;
        private String uploadCoverUrl;
        private boolean forbidden;
        private String words;
        private String material_status_dict;
        private String url;
        private String createDatetime;
        private String lastUpdateDatetime;
        private String articleSource;
        private Object downloadAuditStatus;
        private String type_dict;
        private Object sharedTime;
        private Object zshgdId;
        private Object copyrightNotice;
        private Object resourceGrade;
        private Object auditStatus;
        private Object subjectIntroduction;
        private String subject;
        private Object materialList;
        private String sign;
        private boolean used;
        private String shortTitle;
        private Object source;
        private Object subjectProducer;
        private Object masterUserName;
        private Object articleType;
        private Object materialNum;
        private Object courseId;
        private String producerTime;
        private Object orgName;
        private Object audioNumList;
        private int level;
        private String author;
        private Object mediaType;
        private String contentTitle;
        private Object wmv9Status;
        private ExtendInfoBean extendInfo;
        private Object replaceStaus;
        private String name;
        private String producer;
        private Object ids;
        private String auditId;
        private Object replaceMaterialId;
        private boolean batchUpdate;
        private String aiStatus;
        private String remark;
        private String content;
        private Object duration;
        private Object lockUserId;
        private Object reportUserId;
        private String introduction;
        private Object mp4Status;
        private Object subjectName;
        private boolean editFlag;
        private String summary;
        private Object terminalOrgName;
        private String videoImg;
        private Object tagList;
        private String assigneeStatus;
        private Object sourceName;
        private String contentTypeNames;
        private List<FbzdListBean> fbzdList;
        private List<?> participantList;
        private List<?> zshgd;
        private List<AttachmentBean> attachment;
        private List<WordsListBean> wordsList;

        public Object getExternalLink() {
            return externalLink;
        }

        public void setExternalLink(Object externalLink) {
            this.externalLink = externalLink;
        }

        public String getMaterial_status() {
            return material_status;
        }

        public void setMaterial_status(String material_status) {
            this.material_status = material_status;
        }

        public Object getRouterFlag() {
            return routerFlag;
        }

        public void setRouterFlag(Object routerFlag) {
            this.routerFlag = routerFlag;
        }

        public String getReportOrg() {
            return reportOrg;
        }

        public void setReportOrg(String reportOrg) {
            this.reportOrg = reportOrg;
        }

        public void setReportTime(String reportTime) {
            this.reportTime = reportTime;
        }

        public String getReportTime() {
            return reportTime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isLock() {
            return lock;
        }

        public void setLock(boolean lock) {
            this.lock = lock;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getDownloadImage() {
            return downloadImage;
        }

        public void setDownloadImage(Object downloadImage) {
            this.downloadImage = downloadImage;
        }

        public float getStudyHour() {
            return studyHour;
        }

        public void setStudyHour(int studyHour) {
            this.studyHour = studyHour;
        }

        public String getMaterialId() {
            return materialId;
        }

        public void setMaterialId(String materialId) {
            this.materialId = materialId;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }

        public Object getCreateOrgId() {
            return createOrgId;
        }

        public void setCreateOrgId(Object createOrgId) {
            this.createOrgId = createOrgId;
        }

        public Object getSize() {
            return size;
        }

        public void setSize(Object size) {
            this.size = size;
        }

        public Object getContentTypeIds() {
            return contentTypeIds;
        }

        public void setContentTypeIds(Object contentTypeIds) {
            this.contentTypeIds = contentTypeIds;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public Object getTerminalParentName() {
            return terminalParentName;
        }

        public void setTerminalParentName(Object terminalParentName) {
            this.terminalParentName = terminalParentName;
        }

        public String getStatus_dict() {
            return status_dict;
        }

        public void setStatus_dict(String status_dict) {
            this.status_dict = status_dict;
        }

        public Object getResourceManual() {
            return resourceManual;
        }

        public void setResourceManual(Object resourceManual) {
            this.resourceManual = resourceManual;
        }

        public Object getTerminalIds() {
            return terminalIds;
        }

        public void setTerminalIds(Object terminalIds) {
            this.terminalIds = terminalIds;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getShared() {
            return shared;
        }

        public void setShared(Object shared) {
            this.shared = shared;
        }

        public Object getOrgResourceId() {
            return orgResourceId;
        }

        public void setOrgResourceId(Object orgResourceId) {
            this.orgResourceId = orgResourceId;
        }

        public String getDurationDisplay() {
            return durationDisplay;
        }

        public void setDurationDisplay(String durationDisplay) {
            this.durationDisplay = durationDisplay;
        }

        public Object getReportUserName() {
            return reportUserName;
        }

        public void setReportUserName(Object reportUserName) {
            this.reportUserName = reportUserName;
        }

        public Object getOrgLevel() {
            return orgLevel;
        }

        public void setOrgLevel(Object orgLevel) {
            this.orgLevel = orgLevel;
        }

        public String getTerminalId() {
            return terminalId;
        }

        public void setTerminalId(String terminalId) {
            this.terminalId = terminalId;
        }

        public String getResourceAvType() {
            return resourceAvType;
        }

        public void setResourceAvType(String resourceAvType) {
            this.resourceAvType = resourceAvType;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public String getArticleUrl() {
            return articleUrl;
        }

        public void setArticleUrl(String articleUrl) {
            this.articleUrl = articleUrl;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public Object getLockUserName() {
            return lockUserName;
        }

        public void setLockUserName(Object lockUserName) {
            this.lockUserName = lockUserName;
        }

        public Object getTerminalName() {
            return terminalName;
        }

        public void setTerminalName(Object terminalName) {
            this.terminalName = terminalName;
        }

        public Object getLockTime() {
            return lockTime;
        }

        public void setLockTime(Object lockTime) {
            this.lockTime = lockTime;
        }

        public Object getProcessId() {
            return processId;
        }

        public void setProcessId(Object processId) {
            this.processId = processId;
        }

        public Object getOutId() {
            return outId;
        }

        public void setOutId(Object outId) {
            this.outId = outId;
        }

        public String getProcessType() {
            return processType;
        }

        public void setProcessType(String processType) {
            this.processType = processType;
        }

        public String getAiStatus_dict() {
            return aiStatus_dict;
        }

        public void setAiStatus_dict(String aiStatus_dict) {
            this.aiStatus_dict = aiStatus_dict;
        }

        public String getEditor() {
            return editor;
        }

        public void setEditor(String editor) {
            this.editor = editor;
        }

        public String getUploadCoverUrl() {
            return uploadCoverUrl;
        }

        public void setUploadCoverUrl(String uploadCoverUrl) {
            this.uploadCoverUrl = uploadCoverUrl;
        }

        public boolean isForbidden() {
            return forbidden;
        }

        public void setForbidden(boolean forbidden) {
            this.forbidden = forbidden;
        }

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }

        public String getMaterial_status_dict() {
            return material_status_dict;
        }

        public void setMaterial_status_dict(String material_status_dict) {
            this.material_status_dict = material_status_dict;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
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

        public String getArticleSource() {
            return articleSource;
        }

        public void setArticleSource(String articleSource) {
            this.articleSource = articleSource;
        }

        public Object getDownloadAuditStatus() {
            return downloadAuditStatus;
        }

        public void setDownloadAuditStatus(Object downloadAuditStatus) {
            this.downloadAuditStatus = downloadAuditStatus;
        }

        public String getType_dict() {
            return type_dict;
        }

        public void setType_dict(String type_dict) {
            this.type_dict = type_dict;
        }

        public Object getSharedTime() {
            return sharedTime;
        }

        public void setSharedTime(Object sharedTime) {
            this.sharedTime = sharedTime;
        }

        public Object getZshgdId() {
            return zshgdId;
        }

        public void setZshgdId(Object zshgdId) {
            this.zshgdId = zshgdId;
        }

        public Object getCopyrightNotice() {
            return copyrightNotice;
        }

        public void setCopyrightNotice(Object copyrightNotice) {
            this.copyrightNotice = copyrightNotice;
        }

        public Object getResourceGrade() {
            return resourceGrade;
        }

        public void setResourceGrade(Object resourceGrade) {
            this.resourceGrade = resourceGrade;
        }

        public Object getAuditStatus() {
            return auditStatus;
        }

        public void setAuditStatus(Object auditStatus) {
            this.auditStatus = auditStatus;
        }

        public Object getSubjectIntroduction() {
            return subjectIntroduction;
        }

        public void setSubjectIntroduction(Object subjectIntroduction) {
            this.subjectIntroduction = subjectIntroduction;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public Object getMaterialList() {
            return materialList;
        }

        public void setMaterialList(Object materialList) {
            this.materialList = materialList;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getShortTitle() {
            return shortTitle;
        }

        public void setShortTitle(String shortTitle) {
            this.shortTitle = shortTitle;
        }

        public Object getSource() {
            return source;
        }

        public void setSource(Object source) {
            this.source = source;
        }

        public Object getSubjectProducer() {
            return subjectProducer;
        }

        public void setSubjectProducer(Object subjectProducer) {
            this.subjectProducer = subjectProducer;
        }

        public Object getMasterUserName() {
            return masterUserName;
        }

        public void setMasterUserName(Object masterUserName) {
            this.masterUserName = masterUserName;
        }

        public Object getArticleType() {
            return articleType;
        }

        public void setArticleType(Object articleType) {
            this.articleType = articleType;
        }

        public Object getMaterialNum() {
            return materialNum;
        }

        public void setMaterialNum(Object materialNum) {
            this.materialNum = materialNum;
        }

        public Object getCourseId() {
            return courseId;
        }

        public void setCourseId(Object courseId) {
            this.courseId = courseId;
        }

        public String getProducerTime() {
            return producerTime;
        }

        public void setProducerTime(String producerTime) {
            this.producerTime = producerTime;
        }

        public Object getOrgName() {
            return orgName;
        }

        public void setOrgName(Object orgName) {
            this.orgName = orgName;
        }

        public Object getAudioNumList() {
            return audioNumList;
        }

        public void setAudioNumList(Object audioNumList) {
            this.audioNumList = audioNumList;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public Object getMediaType() {
            return mediaType;
        }

        public void setMediaType(Object mediaType) {
            this.mediaType = mediaType;
        }

        public String getContentTitle() {
            return contentTitle;
        }

        public void setContentTitle(String contentTitle) {
            this.contentTitle = contentTitle;
        }

        public Object getWmv9Status() {
            return wmv9Status;
        }

        public void setWmv9Status(Object wmv9Status) {
            this.wmv9Status = wmv9Status;
        }

        public ExtendInfoBean getExtendInfo() {
            return extendInfo;
        }

        public void setExtendInfo(ExtendInfoBean extendInfo) {
            this.extendInfo = extendInfo;
        }

        public Object getReplaceStaus() {
            return replaceStaus;
        }

        public void setReplaceStaus(Object replaceStaus) {
            this.replaceStaus = replaceStaus;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProducer() {
            return producer;
        }

        public void setProducer(String producer) {
            this.producer = producer;
        }

        public Object getIds() {
            return ids;
        }

        public void setIds(Object ids) {
            this.ids = ids;
        }

        public String getAuditId() {
            return auditId;
        }

        public void setAuditId(String auditId) {
            this.auditId = auditId;
        }

        public Object getReplaceMaterialId() {
            return replaceMaterialId;
        }

        public void setReplaceMaterialId(Object replaceMaterialId) {
            this.replaceMaterialId = replaceMaterialId;
        }

        public boolean isBatchUpdate() {
            return batchUpdate;
        }

        public void setBatchUpdate(boolean batchUpdate) {
            this.batchUpdate = batchUpdate;
        }

        public String getAiStatus() {
            return aiStatus;
        }

        public void setAiStatus(String aiStatus) {
            this.aiStatus = aiStatus;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getDuration() {
            return duration;
        }

        public void setDuration(Object duration) {
            this.duration = duration;
        }

        public Object getLockUserId() {
            return lockUserId;
        }

        public void setLockUserId(Object lockUserId) {
            this.lockUserId = lockUserId;
        }

        public Object getReportUserId() {
            return reportUserId;
        }

        public void setReportUserId(Object reportUserId) {
            this.reportUserId = reportUserId;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public Object getMp4Status() {
            return mp4Status;
        }

        public void setMp4Status(Object mp4Status) {
            this.mp4Status = mp4Status;
        }

        public Object getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(Object subjectName) {
            this.subjectName = subjectName;
        }

        public boolean isEditFlag() {
            return editFlag;
        }

        public void setEditFlag(boolean editFlag) {
            this.editFlag = editFlag;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public Object getTerminalOrgName() {
            return terminalOrgName;
        }

        public void setTerminalOrgName(Object terminalOrgName) {
            this.terminalOrgName = terminalOrgName;
        }

        public String getVideoImg() {
            return videoImg;
        }

        public void setVideoImg(String videoImg) {
            this.videoImg = videoImg;
        }

        public Object getTagList() {
            return tagList;
        }

        public void setTagList(Object tagList) {
            this.tagList = tagList;
        }

        public String getAssigneeStatus() {
            return assigneeStatus;
        }

        public void setAssigneeStatus(String assigneeStatus) {
            this.assigneeStatus = assigneeStatus;
        }

        public Object getSourceName() {
            return sourceName;
        }

        public void setSourceName(Object sourceName) {
            this.sourceName = sourceName;
        }

        public String getContentTypeNames() {
            return contentTypeNames;
        }

        public void setContentTypeNames(String contentTypeNames) {
            this.contentTypeNames = contentTypeNames;
        }

        public List<FbzdListBean> getFbzdList() {
            return fbzdList;
        }

        public void setFbzdList(List<FbzdListBean> fbzdList) {
            this.fbzdList = fbzdList;
        }

        public List<?> getParticipantList() {
            return participantList;
        }

        public void setParticipantList(List<?> participantList) {
            this.participantList = participantList;
        }

        public List<?> getZshgd() {
            return zshgd;
        }

        public void setZshgd(List<?> zshgd) {
            this.zshgd = zshgd;
        }

        public List<AttachmentBean> getAttachment() {
            return attachment;
        }

        public void setAttachment(List<AttachmentBean> attachment) {
            this.attachment = attachment;
        }

        public List<WordsListBean> getWordsList() {
            return wordsList;
        }

        public void setWordsList(List<WordsListBean> wordsList) {
            this.wordsList = wordsList;
        }

        public static class ExtendInfoBean implements Serializable {
            private Model3488270594699436401Bean model_3488270594699436401;

            public Model3488270594699436401Bean getModel_3488270594699436401() {
                return model_3488270594699436401;
            }

            public void setModel_3488270594699436401(Model3488270594699436401Bean model_3488270594699436401) {
                this.model_3488270594699436401 = model_3488270594699436401;
            }

            public static class Model3488270594699436401Bean implements Serializable {
                private String teacher;
                private String teacherProfile;
                private String category;
                private List<String> terminalIds;

                public String getTeacher() {
                    return teacher;
                }

                public void setTeacher(String teacher) {
                    this.teacher = teacher;
                }

                public String getTeacherProfile() {
                    return teacherProfile;
                }

                public void setTeacherProfile(String teacherProfile) {
                    this.teacherProfile = teacherProfile;
                }

                public String getCategory() {
                    return category;
                }

                public void setCategory(String category) {
                    this.category = category;
                }

                public List<String> getTerminalIds() {
                    return terminalIds;
                }

                public void setTerminalIds(List<String> terminalIds) {
                    this.terminalIds = terminalIds;
                }
            }
        }

        public static class FbzdListBean implements Serializable {
            private String parentName;
            private String orgName;
            private String terminalLevel;
            private String modelId;
            private String shortOrgName;
            private String name;
            private String orgLevel;
            private String id;
            private String publishTerminalId;
            private String parentId;

            public String getParentName() {
                return parentName;
            }

            public void setParentName(String parentName) {
                this.parentName = parentName;
            }

            public String getOrgName() {
                return orgName;
            }

            public void setOrgName(String orgName) {
                this.orgName = orgName;
            }

            public String getTerminalLevel() {
                return terminalLevel;
            }

            public void setTerminalLevel(String terminalLevel) {
                this.terminalLevel = terminalLevel;
            }

            public String getModelId() {
                return modelId;
            }

            public void setModelId(String modelId) {
                this.modelId = modelId;
            }

            public String getShortOrgName() {
                return shortOrgName;
            }

            public void setShortOrgName(String shortOrgName) {
                this.shortOrgName = shortOrgName;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOrgLevel() {
                return orgLevel;
            }

            public void setOrgLevel(String orgLevel) {
                this.orgLevel = orgLevel;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPublishTerminalId() {
                return publishTerminalId;
            }

            public void setPublishTerminalId(String publishTerminalId) {
                this.publishTerminalId = publishTerminalId;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }
        }

        public static class AttachmentBean implements Serializable {
            private String id;
            private String resourceId;
            private String type;
            private String name;
            private String url;
            private String orgId;
            private String orgName;
            private String uploadtime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getResourceId() {
                return resourceId;
            }

            public void setResourceId(String resourceId) {
                this.resourceId = resourceId;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getOrgId() {
                return orgId;
            }

            public void setOrgId(String orgId) {
                this.orgId = orgId;
            }

            public String getOrgName() {
                return orgName;
            }

            public void setOrgName(String orgName) {
                this.orgName = orgName;
            }

            public String getUploadtime() {
                return uploadtime;
            }

            public void setUploadtime(String uploadtime) {
                this.uploadtime = uploadtime;
            }
        }

        public static class WordsListBean implements Serializable {
            private String word;

            public String getWord() {
                return word;
            }

            public void setWord(String word) {
                this.word = word;
            }
        }
    }
}
