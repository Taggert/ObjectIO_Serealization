package com.classwork;

public class Employee {
    @NotNull
    @PrintAnnotation(printValue = "First name")
    @Length(minValue = 5, maxValue = 6)
    private String firstName;
    @NotNull
    @PrintAnnotation(printValue = "Last name")
    private String lastName;



    public Employee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Employee() {
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


}