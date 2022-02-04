package com.koelapps.schoolflick.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "announcement")
public class AnnouncementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer announcementId;

    @Column(name = "announcement_type", nullable = false)
    private String announcementType;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "updated_at", nullable = false)
    private String updatedAt;

    @Column(name = "deleted_at", nullable = false)
    private String deletedAt;

    @Column(name = "author_id", nullable = false)
    private Integer authorId;

    @Column(name = "attachment_id", nullable = false)
    private Integer attachmentId;

    @Column(name = "comment_id", nullable = false)
    private Integer commentId;

    @Column(name = "claps_id", nullable = false)
    private Integer clapsId;

    public AnnouncementEntity() {
    }

    public AnnouncementEntity(String announcementType, String title, String body, String createdAt, String updatedAt,
            String deletedAt) {
        this.announcementType = announcementType;
        this.title = title;
        this.body = body;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public Integer getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(Integer announcementId) {
        this.announcementId = announcementId;
    }

    public String getAnnouncementType() {
        return announcementType;
    }

    public void setAnnouncementType(String announcementType) {
        this.announcementType = announcementType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getClapsId() {
        return clapsId;
    }

    public void setClapsId(Integer clapsId) {
        this.clapsId = clapsId;
    }
}
