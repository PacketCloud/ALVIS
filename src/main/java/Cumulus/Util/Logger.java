package Cumulus.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    public Logger(String string) {
        log(string);
    }

    public static void log(String string) {
        String timestamp = new SimpleDateFormat("yyyy:MM:dd|HH:mm:ss").format(new Date());
        String output = String.format("[%s] %s", timestamp, string);
        System.out.println(output);
    }

    public static void logContext(Class c, String string){
        String context = c.getName();
        String timestamp = new SimpleDateFormat("yyyy:MM:dd|HH:mm:ss").format(new Date());
        String output = String.format("[%s][%s] %s", timestamp, context, string);
        System.out.println(output);
    }

    public static void error(String string){
        String timestamp = new SimpleDateFormat("yyyy:MM:dd|HH:mm:ss").format(new Date());
        String output = String.format("[%s] ERROR: %s", timestamp, string);
        System.out.println(output);
    }

    public static void errorContext(Class c, String string){
        String context = c.getName();
        String timestamp = new SimpleDateFormat("yyyy:MM:dd|HH:mm:ss").format(new Date());
        String output = String.format("[%s][%s] ERROR: %s", timestamp, context, string);
        System.out.println(output);
    }

}
