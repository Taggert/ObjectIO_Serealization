package com.homework;

import com.homework.Annotations.DisplayName;
import com.homework.Annotations.Length;
import com.homework.Annotations.NumberLength;

import java.io.*;
import java.lang.reflect.Field;
import java.util.List;

public class OutputData {

    private static final String FILENAME = "user.dta";


    public static void main(String[] args) throws IOException, IllegalAccessException, ClassNotFoundException {

        System.out.println(checkUser(deserialize(FILENAME)));
    }

    private static List<User> deserialize(String filename) throws IOException, ClassNotFoundException {
        List<User> list;
        File f = new File(filename);
        if (f.exists()) {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            list = (List<User>) ois.readObject();
            ois.close();
            fis.close();
        } else {
            list = null;
        }
        return list;
    }

    private static String checkUser(List<User> list) throws IOException, IllegalAccessException {
        if (list != null) {
            User uTest = new User();
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            Class c = User.class;
            Field[] allFields = c.getDeclaredFields();
            for (int i = 1; i < 3; i++) {
                boolean f = true;
                String str = "";
                while (f) {
                    try {
                        System.out.println("Input " + getFieldName(allFields[i])[0] + " " + getFieldName(allFields[i])[1] + "\nor type Exit to exit:");

                        allFields[i].setAccessible(true);
                        str = br.readLine();
                        if (str.equalsIgnoreCase("exit")) {
                            isr.close();
                            br.close();
                            return "";
                        }
                        if (str.equalsIgnoreCase("print all")) {
                            list.forEach(System.out::println);
                            isr.close();
                            br.close();
                            return "";
                        }
                        allFields[i].set(uTest, str);
                        f = false;
                        Validator.validateProcessing(allFields[i], uTest);
                    } catch (RuntimeException e) {
                        System.err.println(e.getMessage());
                        f = true;
                    }
                }


            }
            for (User user : list) {
                if (user.getUsername().equalsIgnoreCase(uTest.getUsername()) && user.getPassword().equals(uTest.getPassword())) {
                    return user.toString();
                }
            }
            System.out.println("There is no user " + uTest.getUsername());
            return "";
        }
        System.out.println("No file!");
        return "";
    }


    private static String[] getFieldName(Field field) {
        String[] res = new String[2];
        res[0] = "";
        res[1] = "";

        boolean annotationPresent = field.isAnnotationPresent(DisplayName.class);
        if (annotationPresent) {
            DisplayName annotation = field.getAnnotation(DisplayName.class);
            res[0] =  annotation.printValue();
            annotationPresent = field.isAnnotationPresent(Length.class);
            boolean annotationPresent2 = field.isAnnotationPresent(NumberLength.class);

            if(annotationPresent && annotationPresent2){
                Length size = field.getAnnotation(Length.class);
                NumberLength ann = field.getAnnotation(NumberLength.class);
                res[1] = res[0] + " is " +size.minValue()+ " - " +size.maxValue()+ " symbols";
            }
        }
        return res;
    }
}