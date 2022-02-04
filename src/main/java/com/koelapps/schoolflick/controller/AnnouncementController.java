package com.koelapps.schoolflick.controller;

import java.sql.Timestamp;

import com.koelapps.schoolflick.dao.AnnouncementRepository;
import com.koelapps.schoolflick.dao.AttachmentRepository;
import com.koelapps.schoolflick.dao.StudentRepository;
import com.koelapps.schoolflick.entity.AnnouncementEntity;
import com.koelapps.schoolflick.entity.AttachmentEntity;
import com.koelapps.schoolflick.entity.StudentEntity;
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
@RequestMapping("/api/v1/announcement")
public class AnnouncementController {

    @Autowired
    AnnouncementRepository announcementRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    // create announcements
    @PostMapping("/create")
    public ResponseEntity<Object> createAnnouncements(@RequestBody AnnouncementEntity announcement) {
        try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            StudentEntity verify = studentRepository.findById(announcement.getAuthorId()).get();
            AttachmentEntity verifyAttachment = attachmentRepository.findById(announcement.getAttachmentId()).get();
            if (verify != null && verifyAttachment != null) {
                AnnouncementEntity announcementEntity = new AnnouncementEntity();
                announcementEntity.setAnnouncementType(announcement.getAnnouncementType());
                announcementEntity.setTitle(announcement.getTitle());
                announcementEntity.setBody(announcement.getBody());
                announcementEntity.setCreatedAt(timestamp.toString());
                announcementEntity.setAuthorId(announcement.getAuthorId());
                announcementEntity.setAttachmentId(announcement.getAttachmentId());
                AnnouncementEntity data = announcementRepository.save(announcementEntity);
                return ResponseHandler.generateResponse("Announcement Created Successfully", HttpStatus.OK, data);
            } else {
                return ResponseHandler.generateResponse("Invalid Author Id or check attachments", HttpStatus.OK, null);
            }
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    // update announcements
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateAnnouncements(@PathVariable(value = "id") Integer announcementId,
            @RequestBody AnnouncementEntity data) {
        try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            AnnouncementEntity announcement = announcementRepository.findById(announcementId).get();
            AttachmentEntity verify = attachmentRepository.findById(data.getAttachmentId()).get();
            if (announcement != null && verify != null) {
                announcement.setAnnouncementType(data.getAnnouncementType());
                announcement.setTitle(data.getTitle());
                announcement.setBody(data.getBody());
                announcement.setUpdatedAt(timestamp.toString());
                announcement.setAttachmentId(data.getAttachmentId());
                announcementRepository.save(announcement);
                return ResponseHandler.generateResponse("Announcement updated successfully", HttpStatus.OK,
                        announcement);
            } else {
                return ResponseHandler.generateResponse("Attachment and announcement not found", HttpStatus.OK, null);
            }
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }

}
