package battleship;

import static battleship.Game.test;

public class AircraftCarrier extends Ship {

    public AircraftCarrier(Field field, Location start, ShipDirection dir) {
        super(5, 5, field, "A", start, dir);
    }

    @Override
    public void getSinkMessage() {
        System.out.println("Ship Is Going Down: Aircraft Carrier"); 
    }

    @Override
    public boolean threaten() {
        
        if (test) {
            System.out.println("Aircraft Carrier Is Threatened! Be Carefull..");
        } else {
            System.out.println("Ship Is Threatened!\nShip Has Failed To Move Away From Danger");
        }
        return false;
    }

    public String toString() {
        return "Aircraft Carrier";
    }

}
