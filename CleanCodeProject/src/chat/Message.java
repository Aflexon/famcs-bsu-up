package chat;

import java.util.UUID;

/**
 * Created by Алексей on 14.02.2016.
 */
public class Message {
    private String id;

    public Message(){
        this.id = UUID.randomUUID().toString();
    }

    public String getId(){
        return id;
    }


}
