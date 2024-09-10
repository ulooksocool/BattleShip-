
package battleship;

import java.util.InputMismatchException;

public enum ShipDirection {
    
    HORIZONTAL,VERTICAL;
    
    public static ShipDirection fromString(String dirString) throws InputMismatchException{
        if(dirString.equalsIgnoreCase("h") || dirString.equalsIgnoreCase("horizontal")){
            return HORIZONTAL;
        }else if (dirString.equals("v") || dirString.equalsIgnoreCase("vertical")){
            return VERTICAL;
        }else {
            throw new InputMismatchException("Invalid Direction");
        }
    }
    
}
