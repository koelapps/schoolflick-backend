package com.koelapps.schoolflick.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.apache.catalina.User;

@Entity
@Table(name = "students")
public class StudentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer studentId;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "email", nullable = false)
	@Email
	@NotBlank
	private String emailId;

	@Column(name = "gender", nullable = false)
	private String gender;

	@Column(name = "user_type", nullable = false)
	private String userType;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "enabled", nullable = false)
	public boolean isEnabled;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "token", nullable = false)
	private String token;

	@Column(name = "verification_token", nullable = false)
	private String verificationToken;

	public StudentEntity() {

	}

	public StudentEntity(String firstName, String lastName, String emailId, String gender, String userType, String password, String username) {
		this.firstName = firstName;
		this.lastName= lastName;
		this.emailId = emailId;
		this.gender = gender;
		this.userType = userType;
		this.password = password;
		this.username = username;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public boolean getEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	public String getVerificationToken(){
		return verificationToken;
	}
	public void setVerificationToken(String verificationToken){
		this.verificationToken = verificationToken;
	}



	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId
				+ "]";
	}

	public User orElseThrow(Object object) {
		return null;
	}
}