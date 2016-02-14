package chat;

import java.lang.reflect.Array;
import java.util.ArrayList;

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

    public void readFromJsonFile(String fileName){

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
