package chat;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.json.*;

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
            System.err.println("Something is wrong with data: ");
            System.err.println(val);
        }
    }

    public void writeToJsonFile(String fileName){
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for(Message message : messages){
            builder.add(buildJsonObject(message));
        }
        JsonArray jsonArray = builder.build();
        try {
            JsonWriter writer = Json.createWriter(new FileWriter(fileName));
            writer.write(jsonArray);
            writer.close();
        }
        catch(IOException e){
            System.err.println("Something went wrong with output to file " + fileName);
        }
    }

    private JsonObject buildJsonObject(Message message){
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("id", message.getId());
        objectBuilder.add("author", message.getAuthor());
        objectBuilder.add("message", message.getMessage());
        objectBuilder.add("timestamp", message.getTimestamp());
        return objectBuilder.build();
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

    public void delete(String id){
        Iterator<Message> message = messages.iterator();
        while (message.hasNext()){
            if (message.next().getId().equals(id)){
                message.remove();
            }
        }
    }
}
