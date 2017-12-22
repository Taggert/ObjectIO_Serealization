package com.homework;

import com.homework.Annotations.DisplayName;
import com.homework.Annotations.Length;
import com.homework.Annotations.NumberLength;
import com.homework.Annotations.DisplayName;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class InputData {

    private static final String FILENAME = "user.dta";

    public static void main(String[] args) throws IllegalAccessException, IOException {
        User u = new User();
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        Class c = User.class;
        Field[] allFields = c.getDeclaredFields();
        Field id = null;
        for (Field field : allFields) {
            if (field.getType().isPrimitive()) {
                id = field;
            } else {
                boolean f = true;
                String str = "";
                String err = "";
                while (f) {
                    System.out.println("Input " + getFieldName(field)[0] + ". " + getFieldName(field)[1] + "\nor type Exit to exit:");
                    field.setAccessible(true);
                    str = br.readLine();
                    if (str.equalsIgnoreCase("exit")) {
                        isr.close();
                        br.close();
                        return;
                    }
                    field.set(u, str);
                    f = false;
                    err = Validator.validateProcessing(field, u);
                    if (!err.equals("")) {
                        System.out.println("\n"+err);
                        f = true;
                    }
                }
            }


        }

        isr.close();
        br.close();
        boolean flag = true;
        File f = new File(FILENAME);
        int size = 0;
        List<User> list = new ArrayList<>();
        if (f.exists())

        {
            list = deserialize(FILENAME);
            size = list.size();
            for (User user : list) {
                if (user.getUsername().equalsIgnoreCase(u.getUsername())) {
                    flag = false;
                    System.out.println("User " + u.getUsername() + " exists");
                }
            }
        }
        if (flag)

        {
            id.setAccessible(true);
            id.setInt(u, (size + 1));
            list.add(u);
            serialize(list);
        }
        //list.forEach(System.out::println);
    }


    private static void serialize(List<User> listUsers) {
        try {
            FileOutputStream fos = new FileOutputStream(FILENAME);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(listUsers);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<User> deserialize(String file) {
        List<User> list = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            list = (List<User>) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("User class not found...");
            e.printStackTrace();
        }
        System.out.println();
        return list;
    }

    private static String[] getFieldName(Field field) {
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
        }
        return res;
    }
}



