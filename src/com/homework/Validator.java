package com.homework;

import com.homework.Annotations.Email;
import com.homework.Annotations.Length;
import com.homework.Annotations.NotBlank;
import com.homework.Annotations.NotNull;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static void validateNotNull(Object o) {

        if (o == null) {
            throw new RuntimeException("Field is null");
        }
    }

    private static void validateNotBlank(Object o) {

        String string = (String) o;
        if (string == null || string.trim().equals("")) {
            throw new RuntimeException("Field is blank");
        }
    }

    private static void validateLength(Object o, int min, int max){
        String s = (String) o;
        if(s == null && min ==0){
            return;
        }
        if(s == null){
            throw new RuntimeException("Incorrect length. Length is null.");
        }
        if(s.length()>max || s.length()<min){

            throw new RuntimeException("Incorrect length. Legth is not in range: " + min + " - "+max+".");
        }
        return;
    }

    private static void validateEmail(Object o){
        String s = (String) o;
        Pattern p =Pattern.compile("\\w+\\@[0-9a-zA-Z]+\\.[a-zA-Z]+");
        Matcher m = p.matcher(s);
        if(!m.matches()){
            throw new RuntimeException("Incorrect eMail. Please input eMail, equals example@example.com");
        }

    }
    public static void validateProcessing(Field field, Object user) throws IllegalAccessException {

        boolean annotationPresent = field.isAnnotationPresent(NotNull.class);
        if(annotationPresent){
            validateNotNull(field.get(user));
        }
        annotationPresent = field.isAnnotationPresent(NotBlank.class);
        if (annotationPresent) {
            validateNotBlank(field.get(user));
        }
        annotationPresent = field.isAnnotationPresent(Length.class);
        if (annotationPresent) {
            Length length = field.getAnnotation(Length.class);
            Object o = field.get(user);
            validateLength((String) o, length.minValue(), length.maxValue());
        }
        annotationPresent = field.isAnnotationPresent(Email.class);
        if(annotationPresent){
            Validator.validateEmail(field.get(user));
        }

    }


}