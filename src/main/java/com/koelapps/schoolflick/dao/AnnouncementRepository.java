package com.koelapps.schoolflick.dao;

import com.koelapps.schoolflick.entity.AnnouncementEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<AnnouncementEntity, Integer> {

}
