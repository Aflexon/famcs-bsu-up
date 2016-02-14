package ui;

import chat.Messages;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Алексей on 14.02.2016.
 */
public class UI {
    private static final String operationDescriptions[] = {
            "Change name",
            "Read messages from file",
            "Write messages to file",
            "Write message",
            "Delete message",
            "Print all messages",
            "Print messages by author",
            "Print messages by key word",
            "Print messages by regular expression",
            "Print messages by period",
            "About",
            "Exit",
    };

    private String name;
    private Scanner input;
    private Messages chat;

    public UI(){
        input = new Scanner(System.in);
        chat = new Messages();

        System.out.println("Welcome to our chat!");
        System.out.println("What's your name?");
        setName(input.nextLine());
        while(true){
            int mode = chooseOperation();
            if(mode == operationDescriptions.length){
                break;
            }
            run(mode);
            System.out.println("Press Enter to continue...");
            try
            {
                System.in.read();
            }
            catch(IOException e)
            {}
        }

    }

    public void run(int mode){
        switch(mode){
            case 1:
                changeName();
                break;
            case 2:
                readFromFile();
                break;
            case 3:
                writeToFile();
                break;
            case 4:
                addMessage();
                break;
            case 5:
                deleteMessage();
                break;
            case 6:
                printAll();
                break;
            case 7:
                printByAuthor();
                break;
            case 8:
                printByWord();
                break;
            case 9:
                printByRegExp();
                break;
            case 10:
                printByPeriod();
                break;
            case 11:
                about();
                break;
            default:
                System.err.println("Operation must be in [1.." + (operationDescriptions.length - 1) + "]");
                break;
        }
    }

    public void changeName(){
        System.out.println("Please enter a new name: ");
        setName(input.nextLine());
    }

    public void readFromFile(){
        System.out.println("Please enter a file name: ");
        chat.readFromJsonFile(input.nextLine());
    }

    public void printAll(){
        chat.printMessages();
    }

    public void writeToFile(){
        System.out.println("Please enter a file name:");
        chat.writeToJsonFile(input.nextLine());
    }

    public void addMessage(){
        System.out.println("Please enter a message:");
        chat.newMessage(getName(), input.nextLine());
    }

    public void deleteMessage(){
        System.out.println("Please enter a id:");
        chat.delete(input.nextLine());
    }

    public void printByAuthor(){
        System.out.println("Please enter a author:");
        chat.printByAuthor(input.nextLine());
    }

    public void printByRegExp(){
        System.out.println("Please enter a regExp:");
        chat.printByRegExp(input.nextLine());
    }

    public void printByWord(){
        System.out.println("Please enter a word:");
        chat.printByWord(input.nextLine());
    }

    public void printByPeriod(){
        System.out.println("Please enter timestamp from:");
        long from = input.nextLong();
        System.out.println("Please enter timestamp to:");
        long to = input.nextLong();
        chat.printByPeriod(from, to);
    }

    public void about(){
        System.out.println("=================================");
        System.out.println(" _____ _           _   \n" +
                "/  __ \\ |         | |  \n" +
                "| /  \\/ |__   __ _| |_ \n" +
                "| |   | '_ \\ / _` | __|\n" +
                "| \\__/\\ | | | (_| | |_ \n" +
                " \\____/_| |_|\\__,_|\\__|");
        System.out.println("version: 0.1(beta)");
        System.out.println("author: Aleksey Afanasenko");
        System.out.println("FAMCS BSU 2016");
        System.out.println("=================================");
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public int chooseOperation(){
        System.out.println("Please choose operation ");
        for(int i = 0; i < operationDescriptions.length; i++){
            System.out.println((i + 1) + " - " + operationDescriptions[i]);
        }
        try {
            String stringMode  = input.nextLine();
            int mode = new Integer(stringMode);
            if (mode <= 0 || mode > operationDescriptions.length){
                System.err.println("Operation must be in [1.." + operationDescriptions.length + "]");
            }
            return mode;
        } catch(NumberFormatException e){
            System.err.println("Operation must be a integer");
            return 0;
        }

    }
}
