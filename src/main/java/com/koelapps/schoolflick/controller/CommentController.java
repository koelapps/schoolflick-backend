package com.koelapps.schoolflick.controller;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.koelapps.schoolflick.dao.CommentRepository;
import com.koelapps.schoolflick.entity.Comment;
import com.koelapps.schoolflick.entity.CommentEntity;
import com.koelapps.schoolflick.response.ResponseHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/announcement")
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @PostMapping("/comment")
    public ResponseEntity<Object> addComment(@RequestBody Comment comment) {
        List<Comment> al = new ArrayList<>();
        al.add(new Comment(comment.getComment(), comment.getFromUserId()));
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setComment(al);
        System.out.println(ResponseEntity.ok(al));
        System.out.println(al);
        System.out.println(commentEntity.toString());
        CommentEntity result = commentRepository.save(commentEntity);
        return ResponseHandler.generateResponse("ok", HttpStatus.OK, result);
    }

}
