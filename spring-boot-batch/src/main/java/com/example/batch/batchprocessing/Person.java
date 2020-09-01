package com.example.batch.batchprocessing;

import lombok.ToString;

import java.util.Date;

/**
 * Spring Boot runs schema-@@platform@@.sql automatically during startup. -all is the default for all platforms.
 */

@ToString
public class Person {

    private String lastName;
    private String firstName;
    private Date createdAt;

    public Person() {
    }

    public Person(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.createdAt = new Date();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
