package chat;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;
import javax.json.*;

public class MessageContainer {
    private ArrayList<Message> messages;
    private Logger log;

    public MessageContainer(){
        messages = new ArrayList<>();
        log = new Logger("MessageContainer.log");
    }

    public void readFromJsonFile(String fileName) {
        try {
            JsonReader reader = Json.createReader(new FileReader(fileName));
            JsonArray messages = reader.readArray();
            messages.forEach(this::addFromJson);
            log.info("Add " + messages.size() + " messages from file " + fileName);
        } catch(FileNotFoundException e){
            System.err.println("File " + fileName + " not found");
            log.info("File " + fileName + " not found");
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
            log.error("Something is wrong with data " + val);
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
            log.error("Something went wrong with output to file " + fileName);
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

    public void printByPeriod(long from, long to){
        messages.stream()
                .filter(message -> message.getTimestamp() >= from && message.getTimestamp() <= to)
                .forEach(System.out::println);
    }

    public void add(String id, String author, String message, long timestamp){
        messages.add(new Message(id, author, message, timestamp));
    }

    public void addMessage(String author, String message, long timestamp){
        messages.add(new Message(author, message, timestamp));
        log.info("New message from " + author + " add");
    }

    public void newMessage(String author, String message){
        messages.add(new Message(author, message));
        log.info("New message from " + author + " add");
    }

    public void printMessages(){
        this.messages.sort((Message a, Message b) -> a.getDate().compareTo(b.getDate()));
        messages.forEach(System.out::println);
    }

    public void printByAuthor(String author){
        messages.stream().filter(message -> message.getAuthor().equals(author)).forEach(System.out::println);
    }

    public void printByWord(String word){
        messages.stream().filter(message -> message.getMessage().contains(word)).forEach(System.out::println);
    }

    public void printByRegExp(String regExp){
        messages.stream().filter(message -> Pattern.matches(regExp, message.getMessage())).forEach(System.out::println);
    }

    public void delete(String id){
        Iterator<Message> message = messages.iterator();
        while (message.hasNext()){
            if (message.next().getId().equals(id)){
                message.remove();
                log.info("Message with id:" + id + " remove");
            }
        }
    }
}
