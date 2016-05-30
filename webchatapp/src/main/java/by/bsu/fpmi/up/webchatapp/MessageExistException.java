package by.bsu.fpmi.up.webchatapp;

public class MessageExistException extends RuntimeException {

    public MessageExistException(String message) {
        super(message);
    }
}
