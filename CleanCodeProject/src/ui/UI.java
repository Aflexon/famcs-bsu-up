package ui;

import chat.Logger;
import chat.MessageContainer;
import java.io.IOException;
import java.util.Scanner;

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
            "Add message",
            "About",
            "Exit",
    };

    private String name;
    private Scanner input;
    private MessageContainer chat;
    private Logger log;

    public UI(){
        input = new Scanner(System.in);
        chat = new MessageContainer();
        log = new Logger("UI.log");
        log.info("Start UI");
        System.out.println("Welcome to our chat!");
        System.out.println("What's your name?");
        setName(input.nextLine());

        operationHandling();
    }

    private void operationHandling(){
        while(true){
            int mode = chooseOperation();
            if(mode == operationDescriptions.length){
                log.info("Stop UI");
                break;
            }
            if (mode <= 0 || mode > operationDescriptions.length){
                System.out.println("Warning: Operation must be in [1.." + operationDescriptions.length + "]");
                log.warning("Operation must be in [1.." + operationDescriptions.length + "]");
                continue;
            }

            log.info("Start " + mode + " operation(" + operationDescriptions[mode - 1] + ")");
            run(mode);
            log.info("Stop " + mode + " operation");

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
                writeMessage();
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
                addMessage();
                break;
            case 12:
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

    public void writeMessage(){
        System.out.println("Please enter a message:");
        chat.newMessage(getName(), input.nextLine());
    }

    public void addMessage() {
        try {
            System.out.println("Please enter a author:");
            String author = input.nextLine();
            System.out.println("Please enter a message:");
            String message = input.nextLine();
            System.out.println("Please enter a timestamp:");
            long timestamp = new Long(input.nextLine());
            if(timestamp > 0){
                throw new NumberFormatException("Timestamp is a positive number");
            }
            chat.addMessage(author, message, timestamp);
        } catch (NumberFormatException e) {
            System.err.println("Timestamp must be a positive integer");
            log.error("Timestamp must be a positive integer");
        }
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
        try {
            System.out.println("Please enter timestamp from:");
            long from = new Long(input.nextLine());
            System.out.println("Please enter timestamp to:");
            long to = new Long(input.nextLine());
            chat.printByPeriod(from, to);
        } catch(NumberFormatException e){
            System.err.println("Timestamp must be a integer");
            log.error("Timestamp must be a integer");
        }

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
            return new Integer(stringMode);
        } catch(NumberFormatException e){
            System.out.println("Operation must be a integer");
            log.warning("Operation must be a integer");
            return 0;
        }

    }
}
