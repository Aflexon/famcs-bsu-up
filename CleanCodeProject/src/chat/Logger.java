package chat;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by ������� on 14.02.2016.
 */
public class Logger {
    public static final Logger INSTANCE = new Logger("chat.log");

    private FileWriter log;
    public Logger(String fileName){
        try {
            log = new FileWriter(fileName);
        } catch(IOException e){
            System.err.println("Something went wrong with logger");
        }
    }

    public void add(String type, String message){
        try{
            log.write(type + ": " + message + "\r\n");
            log.flush();
        } catch(IOException e){
            System.err.println("Something went wrong with logger" + e.getMessage());
        }
    }
}