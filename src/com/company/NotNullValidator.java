package com.company;

public class NotNullValidator {

    public static void validate(Object o){

        if(o == null){
            throw new RuntimeException("Field is null");
        }
    }
}