package com.koelapps.schoolflick.controller;

import java.sql.Timestamp;

import com.koelapps.schoolflick.dao.AttachmentRepository;
import com.koelapps.schoolflick.entity.AttachmentEntity;
import com.koelapps.schoolflick.response.ResponseHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/attachment")
public class AttachmentController {

    @Autowired
    AttachmentRepository attachmentRepository;

    // Create Attachments for Announcements
    @PostMapping("/create")
    public ResponseEntity<Object> createAttachment(@RequestBody AttachmentEntity attachment) {
        try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            AttachmentEntity data = new AttachmentEntity();
            data.setAttachmentType(attachment.getAttachmentType());
            data.setAttachmentLink(attachment.getAttachmentLink());
            data.setCreatedAt(timestamp.toString());
            AttachmentEntity savedAttachment = attachmentRepository.save(data);
            return ResponseHandler.generateResponse("Attachment Created Successfully", HttpStatus.OK, savedAttachment);

        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    // Update Attachments for Announcements
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateAttachment(@PathVariable(value = "id") Integer attachmentId,
            @RequestBody AttachmentEntity attachment) {
        try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            AttachmentEntity attachmentEntity = attachmentRepository.findById(attachmentId).get();
            attachmentEntity.setAttachmentType(attachment.getAttachmentType());
            attachmentEntity.setAttachmentLink(attachment.getAttachmentLink());
            attachmentEntity.setUpdatedAt(timestamp.toString());
            attachmentRepository.save(attachmentEntity);
            return ResponseHandler.generateResponse(
                    "Attachment with the ID" + " " + attachmentId + " " + "Updated Successfully", HttpStatus.OK,
                    attachmentEntity);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

}
