package com.koelapps.schoolflick.dao;

import com.koelapps.schoolflick.entity.CommentEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

}
