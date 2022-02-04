package com.koelapps.schoolflick.controller;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.koelapps.schoolflick.entity.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/announcement")
public class CommentController {

    @PostMapping("/comment")
    public ResponseEntity<List<Comment>> addComment(@RequestBody Comment comment) {

        List<Comment> al = new ArrayList<>();
        al.add(new Comment(comment.getComment(), comment.getFromUserId()));
        return ResponseEntity.ok(al);
    }

}
