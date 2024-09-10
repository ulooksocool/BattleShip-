package battleship;

import static battleship.Game.checkBounds;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class Player {

    private String name;
    private int score;
    private Field field;
    public ArrayList<Ship> ships = new ArrayList<>();

    public abstract boolean placeShips(Field otherField);

    public abstract Location selectMove() throws MoveIsCommandException, InvalidLocationException;

    public void initField(int r, int c) {
        this.field.initLocation(r, c);
        this.field.setRows(r);
        this.field.setCols(c);
    }

    public boolean hasWon() {
        boolean flagShip;
        boolean flagSink;

        for (int i = 0; i < this.field.getRows(); i++) {
            for (int j = 0; j < this.field.getCols(); j++) {
                flagShip = this.field.getLocation(i, j).isEmpty();

                if (!flagShip) {
                    flagSink = this.field.getLocation(i, j).getShip().isSinking();

                    if (!flagSink) {
                        return false;
                    }
                }

            }
        }
        return true;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }

    public Field getField() {
        return this.field;
    }

    public static int validateInput(String mes, int min, int max) {
        Scanner input = new Scanner(System.in);

        int ans;
        while (true) {
            System.out.println(mes);
            if (input.hasNextInt()) {
                ans = input.nextInt();
                if (checkBounds(ans, min, max)) {
                    break;
                }
            }
            System.out.println("Wrong Input! Please Try Again [" + min + " - " + max + "]");
            input.nextLine();
        }
        return ans;
    }

    public Location validateLocation(Field otherField) {

        Scanner input = new Scanner(System.in);

        Location loc;
        String ans, mes;
        while (true) {
            try {
                mes = "\n\n -----------------------\n";
                mes += "|    Give Location     |\n";
                mes += "|   [A10 - RowLine]    |\n";
                mes += " ----------------------\n";
                System.out.println(mes);
                ans = input.nextLine();
                loc = otherField.getLocation(ans);
                return loc;
            } catch (InvalidLocationException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public ShipDirection validateDirection() {

        Scanner input = new Scanner(System.in);

        String ans, mes;
        ShipDirection dir;
        while (true) {

            try {
                mes = "\n\n ----------------------\n";
                mes += "|    Give Direction    |\n";
                mes += "|    Horizontal [h]    |\n";
                mes += "|    Vertical   [v]    |\n";
                mes += " ----------------------\n";

                System.out.println(mes);
                ans = input.nextLine();
                dir = ShipDirection.fromString(ans);
                return dir;

            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            }

        }

    }

    public Ship initShip(Ship s, Field otherField) {
        Location loc = validateLocation(otherField);
        s.setStart(loc);
        ShipDirection dir = validateDirection();
        s.setDir(dir);
        return s;
    }

    public void repeatAttempts(Ship s, int ans, Field otherField) {

        String mes;
        boolean flag;
        while (true) {
            flag = otherField.placeShipRandomly(s, ans, false);
            if (!flag) {
                mes = "Failed to Place Ship " + s + " at " + ans + " attemps\nAttemping to Place Again";
                System.out.println(mes);
                continue;
            }
            break;
        }

    }

    public boolean repeatAttempts(Ship s, Field otherField) {

        Scanner input = new Scanner(System.in);

        String mes;
        boolean flag;

        while (true) {
            printPlaceShipRequest(s, 40);
            System.out.println(otherField.toStringTesting(false));
            s = initShip(s, otherField);
            flag = otherField.placeShip(s, false);

            if (!flag) {

                System.out.println("Failed To Place Ship " + s);
                while (true) {

                    mes = "\n\n -------------------------------\n";
                    mes += "|  Do You Want To Place Again?  |\n";
                    mes += "|          [yes - no]           |\n";
                    mes += " -------------------------------\n";
                    System.out.println(mes);

                    if (input.hasNextLine()) {
                        mes = input.nextLine();
                        if (mes.equalsIgnoreCase("yes")) {
                            break;
                        } else if (mes.equalsIgnoreCase("no")) {
                            return false;
                        } else {
                            System.out.println("Wrong Input Try Again!");
                        }
                    }

                }
            } else {
                break;
            }
        }
        return true;
    }

    public void printPlaceShipRequest(Ship s, int width) {
        String mes;
        mes = "\n\n ----------------------------------------\n";
        mes += Conversion.centeredLine("Player","|", width, true);
        mes += Conversion.centeredLine(getName(),"|", width, true);
        mes += Conversion.centeredLine("Please Place Your","|", width, true);
        mes += Conversion.centeredLine(s.toString(),"|", width, true);
        mes += " ----------------------------------------\n";
        System.out.println(mes);
    }

}
