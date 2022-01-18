package com.koelapps.schoolflick.controller;

import com.koelapps.schoolflick.dao.StudentRepository;
import com.koelapps.schoolflick.entity.StudentEntity;
import com.koelapps.schoolflick.response.ResponseHandler;
import com.koelapps.schoolflick.service.EmailService;
import com.koelapps.schoolflick.service.Students;
import com.koelapps.schoolflick.utility.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
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

    @Autowired
    EmailService emailService;

    //Create Students OR SIGNUP
    @PostMapping("/create")
    public ResponseEntity<Object> createStudent(@RequestBody StudentEntity student) {

        try {
            StudentEntity emailVerify = studentRepository.findByEmailId(student.getEmailId());
            StudentEntity usernameVerify = studentRepository.findByUsername(student.getUsername());
            if(emailVerify != null) {
                return ResponseHandler.generateResponse("Email Id already exists", HttpStatus.MULTI_STATUS, null);
            } else if(usernameVerify != null) {
                return ResponseHandler.generateResponse("Username already exists", HttpStatus.MULTI_STATUS, null);
            } else {
                StudentEntity savedStudents = students.saveStudents(student);
                
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(student.getEmailId());
                mailMessage.setSubject("Email Verification!");
                mailMessage.setFrom("admin@koelapps.com");
                mailMessage.setText("Paste this Link in postman with POST request : "
                +"http://localhost:8080/api/v1/student/create/verify/"+savedStudents.getStudentId());
    
                emailService.sendEmail(mailMessage);
                return ResponseHandler.generateResponse("A mail has been Sent for Verification", HttpStatus.OK, null);
            }
            
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
                 
    }

    @PostMapping("create/verify/{id}")
    public ResponseEntity<Object> verifyEmail(@PathVariable(value = "id") Integer studentId) {
        try {
            StudentEntity student = studentRepository.findById(studentId).get();
            student.setEnabled(true);
            studentRepository.save(student);
            return ResponseHandler.generateResponse("Mail Verified Successfully", HttpStatus.OK, student); 
        } catch (Exception e) {
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

    //Forgot Password
    @PostMapping("/forgot_password")
    public ResponseEntity<Object> forgotPassword(@RequestBody StudentEntity student) {
        try {
            StudentEntity findStudent = studentRepository.findByEmailId(student.getEmailId());
            if(findStudent != null) {
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(student.getEmailId());
                mailMessage.setSubject("Email Verification!");
                mailMessage.setFrom("admin@koelapps.com");
                mailMessage.setText("Paste this Link in postman with POST request to Reset Password : "
                    +"http://localhost:8080/api/v1/student/reset-password/"+findStudent.getStudentId());

                emailService.sendEmail(mailMessage);
                return ResponseHandler.generateResponse("An Password Reset Link has sent to the email ID", HttpStatus.OK, null);
             }else {
                return ResponseHandler.generateResponse("Email ID not Found", HttpStatus.OK, null);
             }
            
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
        
    }

    //Reset Password
    @PostMapping("/reset-password/{id}")
    public ResponseEntity<Object> resetPassword(@PathVariable(value = "id") Integer studentId, @RequestBody StudentEntity student) {
        try {
            StudentEntity studentEntity = studentRepository.findById(studentId).get();
            studentEntity.setPassword(Utility.encrypt(student.getPassword()));
            studentRepository.save(studentEntity);
            return ResponseHandler.generateResponse("Password Changed Successfully", HttpStatus.OK, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //Login
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody StudentEntity student) {
        try {
            StudentEntity loginUser = studentRepository.findByUsername(student.getUsername());
            String loginPassword = student.getPassword();
            String encodedPassword = loginUser.getPassword();
            Boolean password = Utility.validatePassword(loginPassword, encodedPassword);
            if(password != true) {
                return ResponseHandler.generateResponse("Invalid credentials", HttpStatus.OK, null);
            } else {
                String token = Utility.getJWTToken(student.getUsername());
                loginUser.setToken(token);
                return ResponseHandler.generateResponse("Login Successfully!", HttpStatus.OK, loginUser);
            }
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
        
    }
}
