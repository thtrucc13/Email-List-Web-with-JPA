package com.example.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.util.logging.Logger;

@Entity
@Table(name = "users") // Tên bảng trong PostgreSQL
public class User {
    private static final Logger LOGGER = Logger.getLogger(User.class.getName());

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Sử dụng SERIAL trong PostgreSQL
    private Long id;

    private String firstName;  // Khớp với cột firstname
    private String lastName;   // Khớp với cột lastname
    private String email;      // Khớp với cột email, UNIQUE

    public User() {}

    public User(String firstName, String lastName, String email) {
        this.firstName = (firstName != null) ? firstName.trim() : "";
        this.lastName = (lastName != null) ? lastName.trim() : "";
        this.email = (email != null) ? email.trim() : "";
        LOGGER.info("User object created with: firstName=" + this.firstName + ", lastName=" + this.lastName + ", email=" + this.email);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = (firstName != null) ? firstName.trim() : "";
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = (lastName != null) ? lastName.trim() : "";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = (email != null) ? email.trim() : "";
    }
}