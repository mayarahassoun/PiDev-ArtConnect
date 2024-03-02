/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import services.CurrentTime;

/**
 *
 * @author oXCToo
 */
public class User {

    private int id;

    private String email;

    private String dob;

    private String gender;

    private String lastname;

    private String firstname;



    private String Password;

    private String image;

    private String role;


    public User(int id, String email, String dob, String gender, String lastname, String firstname, String password, String image, String role) {
        this.id = id;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
        this.lastname = lastname;
        this.firstname = firstname;
        this.Password = password;
        this.image = image;
        this.role = role;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getDob ()
    {
        return dob;
    }

    public void setDob (String dob)
    {
        this.dob = dob;
    }

    public String getGender ()
    {
        return gender;
    }

    public void setGender (String gender)
    {
        this.gender = gender;
    }

    public String getLastname ()
    {
        return lastname;
    }

    public void setLastname (String lastname)
    {
        this.lastname = lastname;
    }

    public String getFirstname ()
    {
        return firstname;
    }

    public void setFirstname (String firstname)
    {
        this.firstname = firstname;
    }


    public String getPassword() {return Password;}

    public void setPassword(String password) {Password = password;}

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }



    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", Password='" + Password + '\'' +
                ", image='" + image + '\'' +
                ", role='" + role + '\'' +
                '}';
    }


}