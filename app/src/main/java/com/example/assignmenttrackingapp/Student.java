package com.example.assignmenttrackingapp;

public class Student {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private Double submissionRate;

    public String getStudentNumber() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Double getSubmissionRate() {
        return submissionRate;
    }

    public void setSubmissionRate(Double submissionRate) {
        this.submissionRate = submissionRate;
    }
}
