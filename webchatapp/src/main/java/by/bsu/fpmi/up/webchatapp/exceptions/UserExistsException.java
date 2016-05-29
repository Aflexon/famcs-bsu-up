package by.bsu.fpmi.up.webchatapp.exceptions;

public class UserExistsException extends RuntimeException {
    public UserExistsException(String s) {
        super(s);
    }
}
