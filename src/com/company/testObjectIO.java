package com.company;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class testObjectIO {

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

    private static User checkUser(List<User> list) throws IOException, IllegalAccessException {
        if (list != null) {
            User uTest = new User();
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            Class c = User.class;
            Field[] allFields = c.getDeclaredFields();
            for (int i = 1; i < 3; i++) {
                System.out.println("Input " + allFields[i].getName() + ":");
                allFields[i].setAccessible(true);
                allFields[i].set(uTest, br.readLine());
            }
            for (User user : list) {
                if (user.getUsername().equalsIgnoreCase(uTest.getUsername()) && user.getPassword().equals(uTest.getPassword())) {
                    return user;
                }
            }
            System.out.println("There is no user "+ uTest.getUsername());
            return null;
        }
        System.out.println("No file!");
        return null;
    }
}