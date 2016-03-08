package by.bsu.up.chat.logging.impl;

import by.bsu.up.chat.logging.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class FileLogger implements Logger {

    String logFile;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("M/d/yyyy HH:mm:ss");

    public FileLogger(String logFile){
        this.logFile = logFile;
    }

    @Override
    public void info(String message){
        try(FileWriter log = new FileWriter(logFile, true)){
            String date = DATE_FORMAT.format(System.currentTimeMillis());
            log.write(date + " " + message + "\r\n");
        } catch(IOException e){
            System.err.println("[FileLogger] Error: " + e.getMessage());
        }
    }

    @Override
    public void error(String message, Throwable e){
        try(FileWriter log = new FileWriter(logFile, true)){
            String date = DATE_FORMAT.format(System.currentTimeMillis());
            log.write(date + " " + message + "\r\n");
            log.write(date + " " + e.getMessage() + "\r\n");
        } catch(IOException ex){
            System.err.println("[FileLogger] Error: " + ex.getMessage());
        }
    }
}
