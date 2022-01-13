package com.koelapps.schoolflick.controller;
import com.koelapps.schoolflick.dao.StudentRepository;
import com.koelapps.schoolflick.entity.StudentEntity;
import com.koelapps.schoolflick.response.ResponseHandler;
import com.koelapps.schoolflick.service.Students;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    @Autowired
	Students students;

    @Autowired
    StudentRepository studentRepository;

    //Create Students OR SIGNUP
    @PostMapping("/create")
    public ResponseEntity<Object> createStudent(@RequestBody StudentEntity student) {

        try {
            StudentEntity savedStudent = students.saveStudents(student);

            return ResponseHandler.generateResponse("Student Created Successfully", HttpStatus.OK, savedStudent);
        }catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //Get All Students List from the Database
    @GetMapping("/students")
    public ResponseEntity<Object> getAllStudent(){
        try {
            List<StudentEntity> allStudentList = studentRepository.findAll();

            return ResponseHandler.generateResponse("Executed Successfully", HttpStatus.OK, allStudentList);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //Get Students Details By Using their Id's
    @GetMapping("/students/{id}")
    public ResponseEntity<Object> getStudentById(@PathVariable(value = "id") Integer studentId) {
        try {
            StudentEntity studentEntity = studentRepository.findById(studentId).get();

            return ResponseHandler.generateResponse("Executed Successfully", HttpStatus.OK, studentEntity);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //Update Students Details Using their Id's
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateStudent(@PathVariable(value = "id") Integer studentId,
                                                       @RequestBody StudentEntity studentDetails) {

        try {
        StudentEntity student = studentRepository.findById(studentId).get();

        student.setEmailId(studentDetails.getEmailId());
        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        student.setGender(studentDetails.getGender());
        student.setUserType(studentDetails.getUserType());
        final StudentEntity updatedStudent = studentRepository.save(student);

        return ResponseHandler.generateResponse("Student with the ID" + " "+ studentId +" " + "Updated Successfully", HttpStatus.OK, updatedStudent);    
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //Delete OR Remove Student's Data from Database using their ID's
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable(value = "id") Integer studentId) {
        try {
        StudentEntity student = studentRepository.findById(studentId).get();

        studentRepository.delete(student);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return ResponseHandler.generateResponse("Student with the ID" + " "+ studentId +" " + "Deleted Successfully", HttpStatus.OK, response);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
}
