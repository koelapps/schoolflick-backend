package com.koelapps.schoolflickbackend.Student;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Service
@RequestMapping("/api/v1/student")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @GetMapping("/students")
    public List<StudentEntity> getAllStudent(){
        List<StudentEntity> allEmployeelist = studentRepository.findAll();
        return allEmployeelist;

    }

    @GetMapping("/students/{id}")
    public StudentEntity getEmployeebyId(@PathVariable(value = "id") Integer studentId)

    {
        StudentEntity studentEntity = studentRepository.findById(studentId).get();

        return studentEntity;
    }

    @PostMapping("/create")
    public StudentEntity createEmployee(@RequestBody StudentEntity student) {
        StudentEntity savedStudent = studentRepository.save(student);

        return savedStudent;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StudentEntity> updateStudent(@PathVariable(value = "id") Integer studentId,
                                                       @RequestBody StudentEntity studentDetails) {
        StudentEntity student = studentRepository.findById(studentId).get();

        student.setEmailId(studentDetails.getEmailId());
        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        student.setGender(studentDetails.getGender());
        student.setUserType(studentDetails.getUserType());
        final StudentEntity updatedStudent = studentRepository.save(student);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Boolean> deleteStudent(@PathVariable(value = "id") Integer studentId)
    {
        StudentEntity student = studentRepository.findById(studentId).get();

        studentRepository.delete(student);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
