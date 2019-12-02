package com.tanveer.data;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String password;
    private String imageUrl;
    private String attendenceGrade;

    public User(String firstName, String lastName, String gender, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.password = password;
    }

    public User(int id, String firstName, String lastName, String gender, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.password = password;
    }

    public User(String firstName, String lastName, String email, String imageUrl) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.imageUrl = imageUrl;
    }
    public User(String firstName, String lastName, String gender, String email,String attendenceGrade, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.attendenceGrade = attendenceGrade;
        this.password = password;
    }



    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAttendenceGrade() {
        return attendenceGrade;
    }
}
