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
    private static UserStorage ourInstance;

    static {
        try {
            ourInstance = new UserStorage();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private UserStorage() throws UserExistsException, IOException {
        users = new HashMap<String, User>();
        InputStream usersStream = getClass().getClassLoader().getResourceAsStream("passwords.txt");
        Scanner usersFile = new Scanner(usersStream);
        while (usersFile.hasNextLine()) {
            Scanner user = new Scanner(usersFile.nextLine());
            user.useDelimiter(":");
            String login = user.next();
            String password = user.next();
            if (users.containsKey(login)) {
                throw new UserExistsException("User with login " + login + " already exists in database.");
            } else {
                users.put(login, new User(login, password));
            }
        }
        usersFile.close();
        usersStream.close();
    }

    public static UserStorage getInstance() {
        return ourInstance;
    }

    public boolean checkUser(String login, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        User user = users.get(login);
        String hashedPassword = Hashcode.encryptPassword(password);
        return user != null && user.getPassword().equals(hashedPassword);
    }

}
