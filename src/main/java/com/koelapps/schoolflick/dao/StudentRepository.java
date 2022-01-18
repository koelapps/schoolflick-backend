package com.koelapps.schoolflick.dao;

import com.koelapps.schoolflick.entity.StudentEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

    @Repository
   public interface StudentRepository extends JpaRepository<StudentEntity, Integer>{

    StudentEntity findByEmailId(String emailId);
    StudentEntity findByUsername(String username);

    Boolean existsByUsername(String username);
    Boolean existsByEmailId(String emailId);
}
