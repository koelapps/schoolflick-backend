package com.koelapps.schoolflick.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "comments")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer commentId;

    @Column(name = "comment")
    @ElementCollection
    @OneToMany(targetEntity = Comment.class, fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    private List<Comment> comment;

    public CommentEntity() {

    }

    public CommentEntity(List<Comment> comment) {
        this.comment = comment;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setId(Integer commentId) {
        this.commentId = commentId;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

}
