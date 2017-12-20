package com.company;

import java.lang.reflect.Field;

public class OneMoreMain {

    public static void main(String[] args) throws IllegalAccessException {
        Employee employee = new Employee("John", "Smith");
        Field[] declaredFields = employee.getClass().getDeclaredFields();

        for (Field field : declaredFields) {
            field.setAccessible(true);
            notNullProcessing(field, employee);
            lengthProcessing(field,employee);
            System.out.println(String.format("%s : %s",
                    getFieldName(field),
                    field.get(employee)));
        }

    }

    private static void notNullProcessing(Field field , Object employee) throws IllegalAccessException {

        if(field.getAnnotation(NotNull.class) != null){
            NotNullValidator.validate(field.get(employee));
        }
    }

    private static void lengthProcessing(Field field , Object employee) throws IllegalAccessException {

        boolean annotationPresent = field.isAnnotationPresent(Length.class);
        if(annotationPresent){
            Length length = field.getAnnotation(Length.class);
            Object o = field.get(employee);
            LengthValidator.validate((String) o, length.minValue(), length.maxValue());
        }
    }

    private static String getFieldName(Field field){
        boolean annotationPresent = field.isAnnotationPresent(PrintAnnotation.class);
        if(annotationPresent){
           PrintAnnotation annotation = field.getAnnotation(PrintAnnotation.class);
           return annotation.printValue();
        }
        return field.getName();
    }

}


