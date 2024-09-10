package battleship;

import static battleship.Game.test;

public class Submarine extends Ship {

    public Submarine(Field field, Location start, ShipDirection dir) {
        super(1, 3, field, "S", start, dir);
    }

    @Override
    public void getSinkMessage() {
        System.out.println("Ship Is Going Down: Submarine"); ;
    }

    @Override
    public boolean threaten() {

        String name;

        if (test) {
            name = "Submarine";
        } else {
            name = "Ship";
        }

        System.out.println(name + " Is Threatened!");
        if (isHit()) {
            System.out.println(name + " Is Damaged! Can't Be Moved :(");
            return false;
        }

        boolean flag = getField().placeShipRandomly(this, 0, true);
        if (flag) {
            System.out.println(name + " Has Succesfully Moved Away From Danger");
            return true;
        } else {
            System.out.println(name + " Has Failed To Move Away From Danger");
        }
        return false;
    }

    public String toString() {
        return "Submarine";
    }
}
