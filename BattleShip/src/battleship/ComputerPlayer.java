package battleship;

import java.util.InputMismatchException;
import java.util.Random;

public class ComputerPlayer extends Player {

    @Override
    public boolean placeShips(Field otherField) {

        Ship air1 = new AircraftCarrier(otherField, null, null);
        Ship air2 = new AircraftCarrier(otherField, null, null);
        Ship des1 = new Destroyer(otherField, null, null);
        Ship des2 = new Destroyer(otherField, null, null);
        Ship des3 = new Destroyer(otherField, null, null);
        Ship sub1 = new Submarine(otherField, null, null);
        Ship sub2 = new Submarine(otherField, null, null);

        otherField.placeShipRandomly(air1, 0, false);
        otherField.placeShipRandomly(air2, 0, false);
        otherField.placeShipRandomly(des1, 0, false);
        otherField.placeShipRandomly(des2, 0, false);
        otherField.placeShipRandomly(des3, 0, false);
        otherField.placeShipRandomly(sub1, 0, false);
        otherField.placeShipRandomly(sub2, 0, false);

        // adds ships to arraylist for saving later
        this.ships.add(air1);
        this.ships.add(air2);
        this.ships.add(des1);
        this.ships.add(des2);
        this.ships.add(des3);
        this.ships.add(sub1);
        this.ships.add(sub2);
        
        return true;

    }

    @Override
    public Location selectMove()  {

        Random rand = new Random();
        
        String ans;

        // randomly choose a move
        int r = rand.nextInt(getField().getRows()); // [0 - 14] 
        int c = rand.nextInt(getField().getCols()); // [0 - 14] 
        
        Location loc = getField().getLocation(r,c);

        return loc;
        
    }

}
