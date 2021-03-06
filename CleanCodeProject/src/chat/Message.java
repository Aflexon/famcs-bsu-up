package chat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

class Message {
    private String id;
    private String author;
    private String message;
    private Date timestamp;

    public Message(String id, String author, String message, long timestamp){
        this.id = id;
        this.author = author;
        this.message = message;
        this.timestamp = new Date(timestamp);
    }

    public Message(String author, String message, long timestamp){
        this(UUID.randomUUID().toString(), author, message, timestamp);
    }

    public Message(String author, String message){
        this(UUID.randomUUID().toString(), author, message, (new Date()).getTime());
    }

    public void setId(String id){
        this.id = id;
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
    public Date getDate(){
        return timestamp;
    }

    public void setTimestamp(long timestamp){
        this.timestamp.setTime(timestamp);
    }

    public String getTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return dateFormat.format(getTimestamp());
    }

    public String toString(){
        return "id: " + getId() + "\r\n"
                + "[" + getTime() + "] "
                + getAuthor() + ": " + getMessage();
    }
}
