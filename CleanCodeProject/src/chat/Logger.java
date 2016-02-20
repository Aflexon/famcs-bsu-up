package chat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class Logger {
    private String logFile;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public Logger(String logFile){
        this.logFile = logFile;
        new File(logFile).delete();
    }

    public void info(String message){
        add("Info", message);
    }

    public void error(String message){
        add("Error", message);
    }

    public void warning(String message){
        add("Warning", message);
    }


    private void add(String type, String message){
        try{

            FileWriter log = new FileWriter(logFile, true);
            log.write("[" + dateFormat.format(System.currentTimeMillis()) + "] " + type + ": " + message + "\r\n");
            log.close();
        } catch(IOException e){
            System.err.println("Something went wrong with logger");
        }
    }
}
