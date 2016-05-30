package by.bsu.fpmi.up.webchatapp.storages;

import by.bsu.fpmi.up.webchatapp.exceptions.UserExistsException;
import by.bsu.fpmi.up.webchatapp.models.User;
import by.bsu.fpmi.up.webchatapp.utils.Hashcode;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserStorage {

    protected static Map<String, User> users;
    private static UserStorage ourInstance = new UserStorage();

    private UserStorage() throws UserExistsException {
        users = new HashMap<>();
        try(InputStream usersStream = getClass().getClassLoader().getResourceAsStream("passwords.txt")){
            Scanner usersFile = new Scanner(usersStream);
            while (usersFile.hasNextLine()) {
                Scanner user = new Scanner(usersFile.nextLine());
                user.useDelimiter(":");
                String uid = user.next();
                String login = user.next();
                String password = user.next();
                if (users.containsKey(login)) {
                    throw new UserExistsException("User with login " + login + " already exists in database.");
                } else {
                    users.put(login, new User(uid, login, password));
                }
            }
            usersFile.close();
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static UserStorage getInstance() {
        return ourInstance;
    }

    public boolean checkUser(String login, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        User user = users.get(login);
        String hashedPassword = Hashcode.encryptPassword(password);
        return user != null && user.getPassword().equals(hashedPassword);
    }

    public User getUserByUid(String uid) {
        if(uid == null){
            return null;
        }
        for(Map.Entry<String, User> entry : users.entrySet()) {
            if (uid.equals(entry.getValue().getUid())) {
               return entry.getValue();
            }
        }
        return null;
    }

    public User getUserByLogin(String login) {
        return users.get(login);
    }

}
