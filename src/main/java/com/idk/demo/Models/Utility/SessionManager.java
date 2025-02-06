package com.idk.demo.Models.Utility;

import java.io.*;
import java.util.Properties;

public class SessionManager {
    private static final String FILE_NAME = "session.properties";

    public static void saveSession(String username, String position) {
        Properties prop = new Properties();
        prop.setProperty("username", username);
        prop.setProperty("position", position);
        try (FileOutputStream out = new FileOutputStream(FILE_NAME)) {
            prop.store(out, "User Session");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String loadSession() {
        Properties prop = new Properties();
        try (FileInputStream in = new FileInputStream(FILE_NAME)) {
            prop.load(in);
            return prop.getProperty("username");
        } catch (IOException e) {
            return null;
        }
    }
    public static String loadSessionPosition() {
        Properties prop = new Properties();
        try (FileInputStream in = new FileInputStream(FILE_NAME)) {
            prop.load(in);
            return prop.getProperty("position");
        } catch (IOException e) {
            return null;
        }
    }

    public static void clearSession() {
        new File(FILE_NAME).delete();
    }
}