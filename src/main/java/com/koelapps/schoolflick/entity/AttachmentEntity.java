package com.koelapps.schoolflick.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "attachment")
public class AttachmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer attachmentId;

    @Column(name = "attachment_type", nullable = false)
    private String attachmentType;

    @Column(name = "attachment_link", nullable = false)
    private String attachmentLink;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "updated_at", nullable = false)
    private String updatedAt;

    @Column(name = "deleted_at", nullable = false)
    private String deletedAt;

    public AttachmentEntity() {

    }

    public AttachmentEntity(Integer attachmentId, String attachmentType, String attachmentLink) {
        this.attachmentId = attachmentId;
        this.attachmentType = attachmentType;
        this.attachmentLink = attachmentLink;
    }

    public Integer getAttachmentId() {
        return attachmentId;
    }

    public void setId(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }

    public String getAttachmentLink() {
        return attachmentLink;
    }

    public void setAttachmentLink(String attachmentLink) {
        this.attachmentLink = attachmentLink;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String string) {
        this.createdAt = string;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public String toString() {
        return "Student [attachmentId=" + attachmentId + ", attachmentType=" + attachmentType + ", attachmentLink="
                + attachmentLink + ", createdAt=" + createdAt
                + "]";
    }
}
