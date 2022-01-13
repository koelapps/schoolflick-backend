package com.koelapps.schoolflick.serviceImpl;

import com.koelapps.schoolflick.dao.StudentRepository;
import com.koelapps.schoolflick.entity.StudentEntity;
import com.koelapps.schoolflick.service.Students;
import com.koelapps.schoolflick.utility.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentImpl implements Students {

    @Autowired
    StudentRepository studentRepository;

    @Override
    public StudentEntity saveStudents(StudentEntity student) {

        StudentEntity data = new StudentEntity();
        data.setFirstName(student.getFirstName());
        data.setLastName(student.getLastName());
        data.setEmailId(student.getEmailId());
        data.setGender(student.getGender());
        data.setUserType(student.getUserType());
        data.setPassword(Utility.encrypt(student.getPassword()));

        StudentEntity savedStudent  = studentRepository.save(data);

        return savedStudent;
    }
    
}
