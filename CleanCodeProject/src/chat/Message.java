package chat;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Алексей on 14.02.2016.
 */
public class Message {
    private String id;
    private String author;
    private String message;
    private Date timestamp;

    public Message(){
        this.id = UUID.randomUUID().toString();
    }

    public String getId(){
        return id;
    }

    public String getAuthor(){
        return author;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public long getTimestamp(){
        return timestamp.getTime();
    }

    public void setTimestamp(long timestamp){
        this.timestamp.setTime(timestamp);
    }
}
