package com.classwork;

public class LengthValidator {

    public static void validate(String s, int min, int max){

        if(s == null && min ==0){
            return;
        }
        if(s == null){
            throw new RuntimeException("Incorrect length. Length is null.");
        }
        if(s.length()>max || s.length()<min){

            throw new RuntimeException("Incorrect length. Legth is not in range.");
        }
        return;
    }


}