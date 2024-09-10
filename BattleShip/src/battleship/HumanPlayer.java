package battleship;

import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanPlayer extends Player {

    @Override
    public boolean placeShips(Field otherField) {

        Scanner input = new Scanner(System.in);

        String mes = "\n\n -------------------------------------------\n";
        mes += Conversion.centeredLine("Player","|", 43,true);
        mes += Conversion.centeredLine(getName(),"|", 43,true);
        mes += Conversion.centeredLine("Please Choose How To Place Your Ships","|", 43,true);
        mes += " -------------------------------------------\n";

        System.out.println(mes);

        mes = "\n -----------------------\n";
        mes += "|  How to Place Ships?  |\n";
        mes += "|    1.Automatically    |\n";
        mes += "|    2.Manual           |\n";
        mes += " -----------------------\n";

        int ans = validateInput(mes, 1, 2);

        Ship air1 = new AircraftCarrier(otherField, null, null);
        Ship air2 = new AircraftCarrier(otherField, null, null);
        Ship des1 = new Destroyer(otherField, null, null);
        Ship des2 = new Destroyer(otherField, null, null);
        Ship des3 = new Destroyer(otherField, null, null);
        Ship sub1 = new Submarine(otherField, null, null);
        Ship sub2 = new Submarine(otherField, null, null);

        if (ans == 1) { // Automatically
            mes = "\n\n ------------------------------------------\n";
            mes += "|  How Many Placement Atempts to execute?  |\n";
            mes += "|              1.Infinite                  |\n";
            mes += "|              2.Give a limit              |\n";
            mes += " ------------------------------------------\n";

            ans = validateInput(mes, 1, 2);

            if (ans == 1) { // auto maxTries=0

                otherField.placeShipRandomly(air1, 0, false);
                otherField.placeShipRandomly(air2, 0, false);
                otherField.placeShipRandomly(des1, 0, false);
                otherField.placeShipRandomly(des2, 0, false);
                otherField.placeShipRandomly(des3, 0, false);
                otherField.placeShipRandomly(sub1, 0, false);
                otherField.placeShipRandomly(sub2, 0, false);

            } else { // auto maxTries>0
                mes = "\n\n --------------\n";
                mes += "|  Give limit  |\n";
                mes += "|    [1-250]   |\n";
                mes += " --------------\n";

                ans = validateInput(mes, 1, 250);

                repeatAttempts(air1, ans, otherField);
                repeatAttempts(air2, ans, otherField);
                repeatAttempts(des1, ans, otherField);
                repeatAttempts(des2, ans, otherField);
                repeatAttempts(des3, ans, otherField);
                repeatAttempts(sub1, ans, otherField);
                repeatAttempts(sub2, ans, otherField);

            }

        } else { // manually

            Boolean flag = false;

            while (true) {
                flag = repeatAttempts(air1, otherField);
                if (flag) {
                    break;
                } else {
                    return false;
                }
            }

            while (true) {
                flag = repeatAttempts(air2, otherField);
                if (flag) {
                    break;
                } else {
                    return false;
                }
            }

            while (true) {
                flag = repeatAttempts(des1, otherField);
                if (flag) {
                    break;
                } else {
                    return false;
                }
            }

            while (true) {
                flag = repeatAttempts(des2, otherField);
                if (flag) {
                    break;
                } else {
                    return false;
                }
            }

            while (true) {
                flag = repeatAttempts(des3, otherField);
                if (flag) {
                    break;
                } else {
                    return false;
                }
            }

            while (true) {
                flag = repeatAttempts(sub1, otherField);
                if (flag) {
                    break;
                } else {
                    return false;
                }
            }

            while (true) {
                flag = repeatAttempts(sub2, otherField);
                if (flag) {
                    break;
                } else {
                    return false;
                }
            }

        }
        
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
    public Location selectMove() throws MoveIsCommandException, InvalidLocationException {

        Scanner input = new Scanner(System.in);

        String ans;
        Location loc = null;
        Command com = null;

        String mes = "\n\n ----------------------\n";
        mes += "|  Please Select Move  |\n";
        mes += " ----------------------\n";
        System.out.println(mes);

        if (input.hasNextLine()) {
            ans = input.nextLine();

            try {

                com = Command.fromString(ans);

                if ((com == Command.EXIT) || (com == Command.HELP) || (com == Command.LOAD) || (com == Command.SAVE) || (com == Command.MENU)) {
                    //System.out.println("humanPlayer 147 Command: " + com.toString());
                    throw new MoveIsCommandException("Command :" + com.toString(), com);
                }

            } catch (InputMismatchException e) {
                //System.out.println("humanPlayer 125");
                loc = getField().getLocation(ans);
                //System.out.println("humanPlayer 128");
            }

        }
        return loc;
    }

    

}
