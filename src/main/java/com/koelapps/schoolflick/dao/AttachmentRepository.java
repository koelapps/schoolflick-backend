package com.koelapps.schoolflick.dao;

import com.koelapps.schoolflick.entity.AttachmentEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<AttachmentEntity, Integer> {
}
