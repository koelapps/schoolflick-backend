package com.koelapps.schoolflick.controller;

import com.koelapps.schoolflick.dao.StudentRepository;
import com.koelapps.schoolflick.entity.StudentEntity;
import com.koelapps.schoolflick.response.ResponseHandler;
import com.koelapps.schoolflick.service.EmailService;
import com.koelapps.schoolflick.service.Students;
import com.koelapps.schoolflick.utility.Utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    @Autowired
	Students students;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    EmailService emailService;

    Logger logger = LoggerFactory.getLogger(StudentController.class);

    //Create Students OR SIGNUP
    @PostMapping("/create")
    public ResponseEntity<Object> createStudent(@RequestBody StudentEntity student) {
        logger.info("Executing API to perform signup for student");
        try {
            StudentEntity emailVerify = studentRepository.findByEmailId(student.getEmailId());
            logger.info("Fetching Email ID from database to check whether the given Email Id is already existed or not!");
            StudentEntity usernameVerify = studentRepository.findByUsername(student.getUsername());
            logger.info("Fetching username from database to check whether the given username is already existed or not!");
            if(emailVerify != null) {
                logger.warn("Email Id already exists!");
                return ResponseHandler.generateResponse("Email Id already exists", HttpStatus.MULTI_STATUS, null);
            } else if(usernameVerify != null) {
                logger.warn("username already exists!");
                return ResponseHandler.generateResponse("Username already exists", HttpStatus.MULTI_STATUS, null);
            } else {   
                String token = UUID.randomUUID().toString();
                student.setVerificationToken(token);
                StudentEntity data = new StudentEntity();
                data.setFirstName(student.getFirstName());
                data.setLastName(student.getLastName());
                data.setEmailId(student.getEmailId());
                data.setGender(student.getGender());
                data.setUserType(student.getUserType());
                data.setPassword(Utility.encrypt(student.getPassword()));
                data.setUsername(student.getUsername());
                data.setVerificationToken(token);
                StudentEntity savedStudent  = studentRepository.save(data);
                logger.info("Creating and saving the student details into the database");
                String ip = InetAddress.getLoopbackAddress().getHostName();
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                logger.info("Creating an email to send for verification");
                mailMessage.setTo(student.getEmailId());
                mailMessage.setSubject("Email Verification!");
                mailMessage.setFrom("md.asad@koelapps.com");
                mailMessage.setText("paste this Link in Postman to verify your Email : "
                +ip+":8080/api/v1/student/create/verify/"+savedStudent.getStudentId()+"/"+token);
    
                emailService.sendEmail(mailMessage);
                logger.info("An email has been sent to the Email ID of the student for verification");
                return ResponseHandler.generateResponse("A mail has been Sent for Verification", HttpStatus.OK, null);
            }
            
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
                 
    }

    @PostMapping("create/verify/{id}/{token}")
    public ResponseEntity<Object> verifyEmail(@PathVariable(value = "id") Integer studentId, @PathVariable(value = "token") String verifyToken) {
        logger.info("Executing API to verify EmailID");
        try {
            StudentEntity student = studentRepository.findByVerificationToken(verifyToken);
            String verify = student.getVerificationToken();
            if (verify != null && student.isEnabled == false) {
                logger.info("Fetching Email Id from the database");
                student.setEnabled(true);
                logger.info("The profile is set to Enabled");
                studentRepository.save(student);
                logger.info("Email Id has been verified and Account has been created Successfully");
                return ResponseHandler.generateResponse("Mail Verified Successfully", HttpStatus.OK, student);
            } else {
                return ResponseHandler.generateResponse("Mail is Already Verified and Account created successfully", HttpStatus.OK, null);
            }
           
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        } 
        
    }

    @PostMapping("/create/verify/resend/{id}")
    public ResponseEntity<Object> resendEmail(@PathVariable(value = "id") Integer studentId) {
        StudentEntity student = studentRepository.findById(studentId).get();
        String token = student.getVerificationToken();
        if (student.isEnabled == true) {
            return ResponseHandler.generateResponse("Mail Already verified", HttpStatus.OK, null);
        } else {
            String ip = InetAddress.getLoopbackAddress().getHostName();
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(student.getEmailId());
            mailMessage.setSubject("Email Verification!");
            mailMessage.setFrom("md.asad@koelapps.com");
            mailMessage.setText("paste this Link in Postman to verify your Email : "
            +ip+":8080/api/v1/student/create/verify/"+student.getStudentId()+"/"+token);

            emailService.sendEmail(mailMessage);
            return ResponseHandler.generateResponse("A mail has resent successfully", HttpStatus.OK, null);
        }
    }

    //Get All Students List from the Database
    @GetMapping("/students")
    public ResponseEntity<Object> getAllStudent(){
        logger.info("Executing API to get all student details in the database");
        try {
            List<StudentEntity> allStudentList = studentRepository.findAll();
            logger.info("Fetching all Students in the database");
            logger.info("Got all Students list from the database");
            return ResponseHandler.generateResponse("Executed Successfully", HttpStatus.OK, allStudentList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //Get Students Details By Using their Id's
    @GetMapping("/students/{id}")
    public ResponseEntity<Object> getStudentById(@PathVariable(value = "id") Integer studentId) {
        logger.info("Executing API fo find student details using student ID");
        try {
            StudentEntity studentEntity = studentRepository.findById(studentId).get();
            logger.info("Fetching student details from the database");
            return ResponseHandler.generateResponse("Executed Successfully", HttpStatus.OK, studentEntity);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //Update Students Details Using their Id's
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateStudent(@PathVariable(value = "id") Integer studentId,
                                                       @RequestBody StudentEntity studentDetails) {
        logger.info("Executing API to update student details");
        try {
        StudentEntity student = studentRepository.findById(studentId).get();
        logger.info("Fetching student details from the database");
        student.setEmailId(studentDetails.getEmailId());
        logger.info("Email ID successfully updated");
        student.setFirstName(studentDetails.getFirstName());
        logger.info("firstName successfully updated");
        student.setLastName(studentDetails.getLastName());
        logger.info("lastName successfully updated");
        student.setGender(studentDetails.getGender());
        logger.info("Gender successfully updated");
        student.setUserType(studentDetails.getUserType());
        logger.info("userType successfully updated");
        final StudentEntity updatedStudent = studentRepository.save(student);
        logger.info("successfully updated.");
        return ResponseHandler.generateResponse("Student with the ID" + " "+ studentId +" " + "Updated Successfully", HttpStatus.OK, updatedStudent);    
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //Delete OR Remove Student's Data from Database using their ID's
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable(value = "id") Integer studentId) {
        logger.info("Executing API to Delete or remove student from the database");
        try {
        StudentEntity student = studentRepository.findById(studentId).get();
        logger.info("Fetching Student Details...");
        studentRepository.delete(student);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        logger.info("Deleted Successfully");
        return ResponseHandler.generateResponse("Student with the ID" + " "+ studentId +" " + "Deleted Successfully", HttpStatus.OK, response);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //Forgot Password
    @PostMapping("/forgot_password")
    public ResponseEntity<Object> forgotPassword(@RequestBody StudentEntity student) {
        logger.info("Executing API to change student password");
        try {
            StudentEntity findStudent = studentRepository.findByEmailId(student.getEmailId());
            logger.info("Fetching Student Details...");
            if(findStudent != null) {
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                logger.info("Preparing to send Email to change password");
                String ip = InetAddress.getLoopbackAddress().getHostName();
                mailMessage.setTo(student.getEmailId());
                mailMessage.setSubject("Email Verification!");
                mailMessage.setFrom("md.asad@koelapps.com");
                mailMessage.setText("paste this Link in Postman to reset your password : "
                +ip+":8080/api/v1/student/reset-password/"+findStudent.getStudentId());

                emailService.sendEmail(mailMessage);
                logger.info("An Email has been sent to verify and reset password");
                return ResponseHandler.generateResponse("An Password Reset Link has sent to the email ID", HttpStatus.OK, null);
             }else {
                logger.error("Invalid Email ID" +student.getEmailId());
                return ResponseHandler.generateResponse("Email ID not Found", HttpStatus.OK, null);
             }
            
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
        
    }

    //Reset Password
    @PostMapping("/reset-password/{id}")
    public ResponseEntity<Object> resetPassword(@PathVariable(value = "id") Integer studentId, @RequestBody StudentEntity student) {
        logger.info("Executing API to reset student password");
        try {
            StudentEntity studentEntity = studentRepository.findById(studentId).get();
            logger.info("Fetching Student Details...");
            studentEntity.setPassword(Utility.encrypt(student.getPassword()));
            studentRepository.save(studentEntity);
            logger.info("Password changed Successfully");
            return ResponseHandler.generateResponse("Password Changed Successfully", HttpStatus.OK, null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error(e.getMessage());
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //Login
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody StudentEntity student) {
        logger.info("Executing API to login into student account");
        try {
            StudentEntity loginUser = studentRepository.findByUsername(student.getUsername());
            logger.info("Fetching Student Details...");
            String loginPassword = student.getPassword();
            String encodedPassword = loginUser.getPassword();
            Boolean password = Utility.validatePassword(loginPassword, encodedPassword);
            logger.info("matching password");
            if(password != true) {
                logger.error("password doesn't matches");
                return ResponseHandler.generateResponse("Invalid credentials", HttpStatus.OK, null);
            } else if(loginUser.isEnabled == false) {
                return ResponseHandler.generateResponse("Email not verified", HttpStatus.OK, null);
            } else {
                String token = Utility.getJWTToken(student.getUsername());
                logger.info("generating tokens for authentication");
                loginUser.setToken(token);
                logger.info("Login successfully...");
                return ResponseHandler.generateResponse("Login Successfully!", HttpStatus.OK, loginUser);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
        
    }
}
