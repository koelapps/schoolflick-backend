package com.koelapps.schoolflick.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long commentId;

    public String comment;
    public Integer fromUserId;

    public Comment() {
    }

    public Comment(String comment, Integer fromUserId) {
        this.comment = comment;
        this.fromUserId = fromUserId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setId(Long commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    @Override
    public String toString() {
        return "Student [commentId=" + commentId + ", comment=" + comment + ", fromUserId="
                + fromUserId
                + "]";
    }
}
