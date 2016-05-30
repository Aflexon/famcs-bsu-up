package by.bsu.fpmi.up.webchatapp.models;

public class User {
    protected String uid;
    protected String login;
    protected String password;

    public User(String uid, String login, String password) {
        this.uid = uid;
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getUid() {
        return uid;
    }

    public String toString() {
        return login + ":" + password;
    }
}
