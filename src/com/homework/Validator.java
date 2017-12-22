package com.homework;

import com.homework.Annotations.*;

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

        String string = o.toString();
        if (string == null || string.trim().equals("")) {
            throw new RuntimeException("Field is blank");
        }
    }

    private static void validateLength(Object o, int min, int max) {
        String s = (String) o;
        if (s == null && min == 0) {
            return;
        }
        if (s == null) {
            throw new RuntimeException("Incorrect length. Length is null.");
        }
        if (s.length() > max || s.length() < min) {

            throw new RuntimeException("Incorrect length. Legth is not in range: " + min + " - " + max + ".");
        }
        return;
    }

    private static void validateEmail(Object o) {
        String s = (String) o;
        Pattern p = Pattern.compile("\\w+\\@[0-9a-zA-Z]+\\.[a-zA-Z]+");
        Matcher m = p.matcher(s);
        if (!m.matches()) {
            throw new RuntimeException("Incorrect eMail. Please input eMail, equals example@example.com");
        }

    }

    private static void validateNumber(Object o, int min, int max) {
        String str = (String) o;
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(str);
        if (!m.matches()) {
            throw new RuntimeException("Not a number.");
        }
        int s = Integer.parseInt(str);
        if (s == 0 && min == 0) {
            return;
        }
        if (s == 0) {
            throw new RuntimeException("Incorrect number. It is null.");
        }
        if (s > max || s < min) {

            throw new RuntimeException("Incorrect number. Number is not in range: " + min + " - " + max + ".");
        }
        return;
    }

    public static String validateProcessing(Field field, Object user) throws IllegalAccessException {
        String res = "";
        boolean annotationPresent = field.isAnnotationPresent(NotNull.class);
        if (annotationPresent) {
            try {
                validateNotNull(field.get(user));
            }catch (RuntimeException e){
                res = res + e.getMessage() +"\n";
            }
        }
        annotationPresent = field.isAnnotationPresent(NotBlank.class);
        if (annotationPresent) {
            try {
                validateNotBlank(field.get(user));
            }catch (RuntimeException e){
                res = res + e.getMessage() +"\n";
            }
        }
        annotationPresent = field.isAnnotationPresent(Length.class);
        if (annotationPresent) {
            Length length = field.getAnnotation(Length.class);
            Object o = field.get(user);
            try {
                validateLength(o, length.minValue(), length.maxValue());
            }catch (RuntimeException e){
                res = res + e.getMessage() +"\n";
            }
        }
        annotationPresent = field.isAnnotationPresent(NumberLength.class);
        if (annotationPresent) {
            NumberLength numberLength = field.getAnnotation(NumberLength.class);
            Object o = field.get(user);
            try {
                validateNumber(o, numberLength.minValue(), numberLength.maxValue());
            }catch (RuntimeException e){
                res = res + e.getMessage() +"\n";
            }
        }
        annotationPresent = field.isAnnotationPresent(Email.class);
        if (annotationPresent) {
            try {
                Validator.validateEmail(field.get(user));
            }catch (RuntimeException e){
                res = res + e.getMessage() +"\n";
            }
        }
        return res;
    }

    public static String[] getFieldName(Field field) {
        String[] res = new String[2];
        res[0] = "";
        res[1] = "";

        boolean annotationPresent = field.isAnnotationPresent(DisplayName.class);
        if (annotationPresent) {
            DisplayName annotation = field.getAnnotation(DisplayName.class);
            res[0] = annotation.printValue();
            annotationPresent = field.isAnnotationPresent(Length.class);

            if (annotationPresent) {
                Length size = field.getAnnotation(Length.class);
                res[1] = res[0] + " is " + size.minValue() + " - " + size.maxValue() + " symbols";
            }
            annotationPresent = field.isAnnotationPresent(NumberLength.class);
            if(annotationPresent){
                NumberLength size = field.getAnnotation(NumberLength.class);
                res[1] = res[0] + " is " + size.minValue() + " - " + size.maxValue();
            }
        }
        return res;
    }


}