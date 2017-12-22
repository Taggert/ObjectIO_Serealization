package com.homework;

import com.homework.Annotations.Email;
import com.homework.Annotations.Length;
import com.homework.Annotations.NotBlank;
import com.homework.Annotations.PrintAnnotation;

import java.io.Serializable;

public class User implements Serializable {


private int id = 0;
@PrintAnnotation(printValue = "Username")
@NotBlank
@Length(minValue = 3, maxValue = 15)
private String username = "";
@PrintAnnotation(printValue = "Password")
@NotBlank
@Length(minValue = 6, maxValue = 15)
private String password;
@PrintAnnotation(printValue = "First name")
@NotBlank
@Length(minValue = 1, maxValue = 15)
private String firstName;
@PrintAnnotation(printValue = "Last name")
@NotBlank
@Length(minValue = 1, maxValue = 15)
private String lastName;
@PrintAnnotation(printValue = "E-mail")
@NotBlank
@Email
private String email;

public User() {
        }

public User(int id, String username, String password, String firstName, String lastName, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        }

public int getId() {
        return id;
        }

public String getUsername() {
        return username;
        }

public String getPassword() {
        return password;
        }

public String getFirstName() {
        return firstName;
        }

public String getLastName() {
        return lastName;
        }

public String getEmail() {
        return email;
        }

@Override
public String toString() {
        return "User{" +
        "id=" + id +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", email='" + email + '\'' +
        '}';
        }
        }
