package battleship;

import java.util.InputMismatchException;

public enum Command {

    HELP("help"),
    SAVE("save"),
    LOAD("load"),
    EXIT("exit"),
    MENU("menu");

    public final String helpText;

    Command(String helpText) {
        this.helpText = helpText;
    }

    public static Command fromString(String commandString) throws InputMismatchException {
        
        if (commandString.equalsIgnoreCase("help")) {
            return HELP;
        } else if (commandString.equalsIgnoreCase("save")) {
            return SAVE;
        } else if (commandString.equalsIgnoreCase("load")) {
            return LOAD;
        } else if (commandString.equalsIgnoreCase("exit")) {
            return EXIT;
        }else if (commandString.equalsIgnoreCase("menu")) {
            return MENU;
        }else {
            throw new InputMismatchException("Invalid Command");
        }
    }
}
