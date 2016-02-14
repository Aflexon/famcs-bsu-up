package chat;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

/**
 * Created with IntelliJ IDEA.
 * User: fpm.afanasenAA
 * Date: 2/9/16
 * Time: 12:24 PM
 */
public class Messages {
    private ArrayList<Message> messages;

    public Messages(){
        messages = new ArrayList<>();
    }

    public void readFromJsonFile(String fileName) {
        try {
            JsonReader reader = Json.createReader(new FileReader(fileName));
            JsonArray messages = reader.readArray();
            messages.forEach(this::addFromJson);
            this.messages.sort((Message a, Message b) -> a.getDate().compareTo(b.getDate()));
        } catch(FileNotFoundException e){
            System.err.println("File " + fileName + " not found");
        }
    }

    private void addFromJson(JsonValue val){
        try {
            JsonObject message = (JsonObject) val;
            String id = message.getString("id");
            String author = message.getString("author");
            String text = message.getString("message");
            long timestamp = message.getJsonNumber("timestamp").longValue();
            add(id, author, text, timestamp);
        }
        catch(Exception e){
            System.err.println("Something is wrong with your data");
        }
    }

    public void writeToJsonFile(String fileName){
        
    }

    private void add(String id, String author, String message, long timestamp){
        messages.add(new Message(id, author, message, timestamp));
    }

    public void newMessage(String author, String message){
        messages.add(new Message(author, message));
    }

    public void printMessages(){
        messages.forEach(System.out::println);
    }


}
