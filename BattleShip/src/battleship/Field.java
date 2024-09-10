package battleship;

import static battleship.Game.currentRound;
import static battleship.Game.gameFlag;
import static battleship.Game.maxRounds;
import static battleship.Game.test;
import java.util.Random;

public class Field {

    private int numRows;
    private int numCols;
    private Player player;
    private Location[][] locations;

    public Location getLocation(int r, int c) {
        return locations[r][c];
    }

    public Location getLocation(String locString) throws InvalidLocationException {

        String r, c;

        try {

            r = locString.substring(0, 1);
            c = locString.substring(1);

        } catch (IndexOutOfBoundsException e) {
            //System.out.println("Field 26");
            throw new InvalidLocationException("Invalid location");
        }
        //System.out.println("field 29 Rows:" + r + " Cols:" + c);

        int row = Conversion.toRow(r);
        int col = -1;

        try {

            col = Integer.parseInt(c);
            col--;

        } catch (NumberFormatException e) {
            //System.out.println("Field 38");
            throw new InvalidLocationException("Invalid Location Format");
        }

        //System.out.println("field 43 Rows:"+row+" Cols:"+col);
        boolean flag = false;
        String mes = "";

        if ((row >= 0) && (row < this.numRows)) {
            flag = true;
        } else {
            mes = "Out Of Bounds : Invalid Rows";
        }

        if (flag) {
            if ((col >= 0) && (col < this.numCols)) {
                return locations[row][col];
            } else {
                mes = "Out Of Bounds : Invalid Columns";
            }
        }
        //System.out.println("Field 58");
        throw new InvalidLocationException(mes);
    }

    public boolean placeShipRandomly(Ship s, int maxTries, boolean checkMarked) {
        Random rand = new Random();
        int load = 0;
        int count = maxTries;

        while (true) {

            int r = rand.nextInt(this.numRows); // [0 - 14] 
            int c = rand.nextInt(this.numCols); // [0 - 14] 

            int rD = rand.nextInt(2);
            ShipDirection dir = ShipDirection.HORIZONTAL;
            if (rD == 1) {
                dir = ShipDirection.VERTICAL;
            }

            if (dir == ShipDirection.HORIZONTAL) {

                if ((s.getLength() + c) < this.numCols) {
                    boolean flag = true;
                    for (int i = c; i < (c + s.getLength()); i++) {

                        if (!locations[r][i].isEmpty()) {
                            flag = false;
                            break;
                        }

                        if (checkMarked) {
                            if (locations[r][i].isMarked()) {
                                flag = false;
                                break;
                            }
                        }
                    }

                    if (!flag) {

                        if (maxTries == 0) {
                            continue;
                        }

                        if ((maxTries != 0) && (count == 0)) {
                            return false;
                        }

                        count--;
                        continue;
                    }

                    s.setStart(locations[r][c]);
                    s.setDir(dir);

                    for (int i = c; i < (c + s.getLength()); i++) {
                        locations[r][i].setShip(s);
                    }

                    if (test) {
                        System.out.println("Success!! The " + s.toString() + " Has Been Placed!");
                    } else {
                        System.out.println("Success!! The Ship Has Been Placed!");
                    }

                    return true;

                } else {
                    load += 5;
                    if (maxTries == 0) {
                        continue;
                    }

                    if ((maxTries != 0) && (count == 0)) {
                        return false;
                    }

                    count--;
                    continue;
                }

            } else if (dir == ShipDirection.VERTICAL) {

                if ((s.getLength() + r) < this.numRows) {
                    boolean flag = true;
                    for (int i = r; i < (r + s.getLength()); i++) {

                        if (!locations[i][c].isEmpty()) {
                            flag = false;
                            break;
                        }

                        if (checkMarked) {
                            if (locations[i][c].isMarked()) {
                                flag = false;
                                break;
                            }
                        }
                    }

                    if (!flag) {

                        if (maxTries == 0) {
                            continue;
                        }

                        if ((maxTries != 0) && (count == 0)) {
                            return false;
                        }

                        count--;
                        continue;
                    }

                    s.setStart(locations[r][c]);
                    s.setDir(dir);

                    for (int i = r; i < (r + s.getLength()); i++) {
                        locations[i][c].setShip(s);
                    }

                    if (test) {
                        System.out.println("Success!! The " + s.toString() + " Has Been Placed!");
                    } else {
                        System.out.println("Success!! The Ship Has Been Placed!");
                    }
                    return true;
                } else {

                    if (maxTries == 0) {
                        continue;
                    }

                    if ((maxTries != 0) && (count == 0)) {
                        return false;
                    }

                    count--;
                    continue;
                }
            }

        }

    }

    public boolean placeShip(Ship s, boolean checkMarked) {

        int c = s.getStart().getCol();
        int r = s.getStart().getRow();

        if (s.getDir() == ShipDirection.HORIZONTAL) {

            if ((s.getLength() + c - 1) < this.numCols) {

                for (int i = c; i < (c + s.getLength()); i++) {

                    if (!locations[r][i].isEmpty()) {
                        System.out.println("Another Ship Is On The Way");
                        return false;
                    }

                    if (checkMarked) {
                        if (locations[r][i].isMarked()) {
                            System.out.println("The Location Is Marked");
                            return false;
                        }
                    }
                }

                for (int i = c; i < (c + s.getLength()); i++) {
                    locations[r][i].setShip(s);
                }

                if (test) {
                    System.out.println("Success!! The " + s.toString() + " Has Been Placed!");
                } else {
                    System.out.println("Success!! The Ship Has Been Placed!");
                }
                return true;

            } else {
                System.out.println("The Location Is Out Of Bounds");
                return false;
            }

        } else if (s.getDir() == ShipDirection.VERTICAL) {

            if ((s.getLength() + r - 1) < this.numRows) {

                for (int i = r; i < (r + s.getLength()); i++) {

                    if (!locations[i][c].isEmpty()) {
                        System.out.println("Another Ship Is On The Way");
                        return false;
                    }

                    if (checkMarked) {
                        if (locations[i][c].isMarked()) {
                            System.out.println("The Location Is Marked");
                            return false;
                        }
                    }
                }

                for (int i = r; i < (r + s.getLength()); i++) {
                    locations[i][c].setShip(s);
                }

                if (test) {
                    System.out.println("Success!! The " + s.toString() + " Has Been Placed!");
                } else {
                    System.out.println("Success!! The Ship Has Been Placed!");
                }
                return true;
            } else {
                System.out.println("The Location Is Out Of Bounds");
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean loadShip(Ship s) throws Exception {

        int c = s.getStart().getCol();
        int r = s.getStart().getRow();

        if (s.getDir() == ShipDirection.HORIZONTAL) {

            if ((s.getLength() + c - 1) < this.numCols) {

                for (int i = c; i < (c + s.getLength()); i++) {

                    if (!locations[r][i].isEmpty()) {
                        System.out.println("Another Ship Is On The Way");
                        return false;
                    }

                }

                for (int i = c; i < (c + s.getLength()); i++) {
                    locations[r][i].setShip(s);
                }

                return true;

            } else {
                throw new Exception("");
            }

        } else if (s.getDir() == ShipDirection.VERTICAL) {

            if ((s.getLength() + r - 1) < this.numRows) {

                for (int i = r; i < (r + s.getLength()); i++) {

                    if (!locations[i][c].isEmpty()) {
                        return false;
                    }

                }

                for (int i = r; i < (r + s.getLength()); i++) {
                    locations[i][c].setShip(s);
                }

                return true;
            } else {
                throw new Exception("");
            }
        } else {
            throw new Exception("");
        }
    }

    public void removeShip(Ship s) {

        int len = s.getLength();
        int r = s.getStart().getCol();
        int c = s.getStart().getRow();
        ShipDirection dir = s.getDir();

        if (dir == ShipDirection.HORIZONTAL) {
            for (int i = c; i < (c + len); i++) {
                locations[r][i].setShip(null);
            }

        } else if (dir == ShipDirection.VERTICAL) {

            for (int i = r; i < (r + len); i++) {
                locations[i][c].setShip(null);
            }
        }
        s.setStart(null);
    }

    public boolean processValidMove(Location moveLoc) {

        if (moveLoc.isMarked()) {
            System.out.println("Location " + Conversion.toRow(moveLoc.getRow()) + (moveLoc.getCol() + 1) + " Is Already Marked!");
            return true;
        }

        if (!moveLoc.isEmpty()) {

            moveLoc.mark();
            System.out.println("Marked Location " + Conversion.toRow(moveLoc.getRow()) + (moveLoc.getCol() + 1));

            moveLoc.getShip().hit();
            moveLoc.getShip().getHitMessage();

            if (moveLoc.getShip().isSinking()) {
                player.setScore(player.getScore() + moveLoc.getShip().getPoints());
                moveLoc.getShip().getSinkMessage();
            }
        } else {
            moveLoc.mark();
            System.out.println("Marked Location " + Conversion.toRow(moveLoc.getRow()) + (moveLoc.getCol() + 1));
            System.out.println("You Missed! No Ship Was Hit");
        }

        int r = moveLoc.getRow();
        int c = moveLoc.getCol();

        if ((r - 1) > 0) {

            Location loc = getLocation(r - 1, c);
            threatenShip(loc);

        }

        if ((r + 1) < this.numRows) {

            Location loc = getLocation(r + 1, c);
            threatenShip(loc);
        }

        if ((c - 1) > 0) {
            Location loc = getLocation(r, c - 1);
            threatenShip(loc);
        }

        if ((c + 1) < this.numCols) {
            Location loc = getLocation(r, c + 1);
            threatenShip(loc);
        }

        return false;
    }

    private void threatenShip(Location loc) {
        boolean flag;
        if (!loc.isMarked()) {

            if (!loc.isEmpty()) {

                ShipDirection dir = loc.getShip().getDir();
                int length = loc.getShip().getLength();
                int rLim = loc.getShip().getStart().getRow();
                int cLim = loc.getShip().getStart().getCol();

                flag = loc.getShip().threaten();
                if (flag) {

                    if (dir == ShipDirection.HORIZONTAL) {
                        for (int i = cLim; i < (cLim + length); i++) {
                            getLocation(rLim, i).setShip(null);
                        }
                    } else if (dir == ShipDirection.VERTICAL) {
                        for (int i = rLim; i < (rLim + length); i++) {
                            getLocation(i, cLim).setShip(null);
                        }
                    }

                }
            }
        }
    }

    public void setRows(int r) {
        this.numRows = r;
    }

    public void setCols(int c) {
        this.numCols = c;
    }

    public int getRows() {
        return this.numRows;
    }

    public int getCols() {
        return this.numCols;
    }

    public void setPlayer(Player play) {
        this.player = play;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void initLocation(int r, int c) {
        this.locations = new Location[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                this.locations[i][j] = new Location();
                this.locations[i][j].setRow(i);
                this.locations[i][j].setCol(j);
            }
        }
    }

    @Override
    public String toString() {
        String temp;

        // get length of player box
        int playerBoxWidth;
        if (this.player.getName().length() > 6) {
            playerBoxWidth = this.player.getName().length() + 6;
        } else {
            playerBoxWidth = 12;
        }

        int roundsBoxWidth = 9;
        if (maxRounds != 0) {
            if (currentRound >= 10) {
                roundsBoxWidth += 1;
            } else if (currentRound >= 100) {
                roundsBoxWidth += 2;
            }
            if (maxRounds >= 10) {
                roundsBoxWidth += 1;
            } else if (maxRounds >= 100) {
                roundsBoxWidth += 2;
            }
        }

        int scoreBoxWidth = 9;

        // make rounds box , player box and score box
        // top lines
        String var = "\n\n";

        // rounds box top dashes
        temp = "";
        for (int i = 0; i < roundsBoxWidth; i++) {
            temp += "-";
        }
        var += Conversion.centeredLine(temp, " ", roundsBoxWidth, false);
        var += "     ";

        //player box top dashes
        temp = "";
        for (int i = 0; i < playerBoxWidth; i++) {
            temp += "-";
        }
        var += Conversion.centeredLine(temp, " ", playerBoxWidth, false);
        var += "     ";

        //score box top dashes
        temp = "";
        for (int i = 0; i < scoreBoxWidth; i++) {
            temp += "-";
        }
        var += Conversion.centeredLine(temp, " ", scoreBoxWidth, true);

        // first line of rounds box
        var += Conversion.centeredLine("Rounds", "|", roundsBoxWidth, false);
        var += "     ";

        // first line of player box 
        var += Conversion.centeredLine("Player", "|", playerBoxWidth, false);
        var += "     ";

        // first line of score box 
        var += Conversion.centeredLine("Score", "|", scoreBoxWidth, true);

        // second line of rounds box
        if (!gameFlag) {
            var += Conversion.centeredLine(currentRound + " of " + maxRounds, "|", roundsBoxWidth, false);
        } else {
            var += Conversion.centeredLine(currentRound + "", "|", roundsBoxWidth, false);
        }
        var += "     ";

        // second line of player name box
        var += Conversion.centeredLine(this.player.getName(), "|", playerBoxWidth, false);
        var += "     ";

        // second line of score box
        var += Conversion.centeredLine(this.player.getScore() + "", "|", scoreBoxWidth, true);

        // rounds box bottom dashes
        temp = "";
        for (int i = 0; i < roundsBoxWidth; i++) {
            temp += "-";
        }
        var += Conversion.centeredLine(temp, " ", roundsBoxWidth, false);
        var += "     ";

        //player box bottom dashes
        temp = "";
        for (int i = 0; i < playerBoxWidth; i++) {
            temp += "-";
        }
        var += Conversion.centeredLine(temp, " ", playerBoxWidth, false);
        var += "     ";

        //score box bottom dashes
        temp = "";
        for (int i = 0; i < scoreBoxWidth; i++) {
            temp += "-";
        }
        var += Conversion.centeredLine(temp, " ", scoreBoxWidth, true);
        var += "\n";

        // column numbers
        var += "\n    ";
        for (int i = 0; i < this.numCols; i++) {
            if ((i + 1) < 10) {
                var += "   " + (i + 1);
            } else {
                var += "  " + (i + 1);
            }
        }

        // line of dashes (-)
        var += "\n     ";
        for (int i = 0; i < this.numCols; i++) {
            var += "----";
        }

        // all rows
        for (int i = 0; i < this.numRows; i++) {

            //each row letter
            var += "\n " + Conversion.toRow(i) + " |";

            for (int j = 0; j < this.numCols; j++) {

                if (locations[i][j].isMarked()) {

                    if (!locations[i][j].isEmpty()) {

                        if (locations[i][j].getShip().isHit()) {

                            if (locations[i][j].getShip().isSinking()) {

                                //ship exists and has been sunk
                                var += "  " + "x" + locations[i][j].getShip().getLetter();

                            } else {

                                //ship exists and has not been sunk
                                var += "   " + "X";
                            }
                        }
                    } else {

                        //location is marked and there is no ship
                        var += "   " + "O";
                    }

                } else {

                    // location is unmarked
                    var += "   " + ".";
                }

            }

            //new row
            var += "\n";

        }

        //gaps for looks
        var += "\n\n";

        return var;
    }

    public String toStringWithShips(boolean flag) {

        String temp;
        String var = "\n\n";

        if (flag) {
            // get length of player box
            int playerBoxWidth;
            if (this.player.getName().length() > 6) {
                playerBoxWidth = this.player.getName().length() + 6;
            } else {
                playerBoxWidth = 12;
            }

            int roundsBoxWidth = 9;
            if (maxRounds != 0) {
                if (currentRound >= 10) {
                    roundsBoxWidth += 1;
                } else if (currentRound >= 100) {
                    roundsBoxWidth += 2;
                }
                if (maxRounds >= 10) {
                    roundsBoxWidth += 1;
                } else if (maxRounds >= 100) {
                    roundsBoxWidth += 2;
                }
            }

            int scoreBoxWidth = 9;

            // make rounds box , player box and score box
            // top lines
            // rounds box top dashes
            temp = "";
            for (int i = 0; i < roundsBoxWidth; i++) {
                temp += "-";
            }
            var += Conversion.centeredLine(temp, " ", roundsBoxWidth, false);
            var += "     ";

            //player box top dashes
            temp = "";
            for (int i = 0; i < playerBoxWidth; i++) {
                temp += "-";
            }
            var += Conversion.centeredLine(temp, " ", playerBoxWidth, false);
            var += "     ";

            //score box top dashes
            temp = "";
            for (int i = 0; i < scoreBoxWidth; i++) {
                temp += "-";
            }
            var += Conversion.centeredLine(temp, " ", scoreBoxWidth, true);

            // first line of rounds box
            var += Conversion.centeredLine("Rounds", "|", roundsBoxWidth, false);
            var += "     ";

            // first line of player box 
            var += Conversion.centeredLine("Player", "|", playerBoxWidth, false);
            var += "     ";

            // first line of score box 
            var += Conversion.centeredLine("Score", "|", scoreBoxWidth, true);

            // second line of rounds box
            if (!gameFlag) {
                var += Conversion.centeredLine(currentRound + " of " + maxRounds, "|", roundsBoxWidth, false);
            } else {
                var += Conversion.centeredLine(currentRound + "", "|", roundsBoxWidth, false);
            }
            var += "     ";

            // second line of player name box
            var += Conversion.centeredLine(this.player.getName(), "|", playerBoxWidth, false);
            var += "     ";

            // second line of score box
            var += Conversion.centeredLine(this.player.getScore() + "", "|", scoreBoxWidth, true);

            // rounds box bottom dashes
            temp = "";
            for (int i = 0; i < roundsBoxWidth; i++) {
                temp += "-";
            }
            var += Conversion.centeredLine(temp, " ", roundsBoxWidth, false);
            var += "     ";

            //player box bottom dashes
            temp = "";
            for (int i = 0; i < playerBoxWidth; i++) {
                temp += "-";
            }
            var += Conversion.centeredLine(temp, " ", playerBoxWidth, false);
            var += "     ";

            //score box bottom dashes
            temp = "";
            for (int i = 0; i < scoreBoxWidth; i++) {
                temp += "-";
            }
            var += Conversion.centeredLine(temp, " ", scoreBoxWidth, true);
            var += "\n";
        }

        // column numbers
        if (flag) {
            var += "\n    ";
        }
        for (int i = 0; i < this.numCols; i++) {
            if ((i + 1) < 10) {
                var += "   " + (i + 1);
            } else {
                var += "  " + (i + 1);
            }
        }

        // line of dashes (-)
        var += "\n     ";
        for (int i = 0; i < this.numCols; i++) {
            var += "----";
        }

        // all rows
        for (int i = 0; i < this.numRows; i++) {

            //each row letter
            var += "\n " + Conversion.toRow(i) + " |";

            for (int j = 0; j < this.numCols; j++) {

                if (locations[i][j].isMarked()) {

                    if (!locations[i][j].isEmpty()) {

                        if (locations[i][j].getShip().isHit()) {

                            //ship exists and has been hit or sunk
                            var += "  " + "x" + locations[i][j].getShip().getLetter();

                        } else {

                            //ship exists and has not been hit or sunk
                            var += "   " + locations[i][j].getShip().getLetter();

                        }
                    } else {

                        //location is marked and there is no ship
                        var += "   " + "O";
                    }

                } else {

                    // location is unmarked
                    var += "   " + ".";
                }

            }

            //new row
            var += "\n";

        }

        //gaps for looks
        var += "\n\n";

        return var;
    }

    public String toStringTesting(boolean showName) {

        String var = "";

        if (showName) {

            String temp;

            // get length of player box
            int playerBoxWidth;
            if (this.player.getName().length() > 6) {
                playerBoxWidth = this.player.getName().length() + 6;
            } else {
                playerBoxWidth = 12;
            }

            int roundsBoxWidth = 9;
            if (maxRounds != 0) {
                if (currentRound >= 10) {
                    roundsBoxWidth += 1;
                } else if (currentRound >= 100) {
                    roundsBoxWidth += 2;
                }
                if (maxRounds >= 10) {
                    roundsBoxWidth += 1;
                } else if (maxRounds >= 100) {
                    roundsBoxWidth += 2;
                }
            }

            int scoreBoxWidth = 9;

            // make rounds box , player box and score box
            // top lines
            var = "\n\n";

            // rounds box top dashes
            temp = "";
            for (int i = 0; i < roundsBoxWidth; i++) {
                temp += "-";
            }
            var += Conversion.centeredLine(temp, " ", roundsBoxWidth, false);
            var += "     ";

            //player box top dashes
            temp = "";
            for (int i = 0; i < playerBoxWidth; i++) {
                temp += "-";
            }
            var += Conversion.centeredLine(temp, " ", playerBoxWidth, false);
            var += "     ";

            //score box top dashes
            temp = "";
            for (int i = 0; i < scoreBoxWidth; i++) {
                temp += "-";
            }
            var += Conversion.centeredLine(temp, " ", scoreBoxWidth, true);

            // first line of rounds box
            var += Conversion.centeredLine("Rounds", "|", roundsBoxWidth, false);
            var += "     ";

            // first line of player box 
            var += Conversion.centeredLine("Player", "|", playerBoxWidth, false);
            var += "     ";

            // first line of score box 
            var += Conversion.centeredLine("Score", "|", scoreBoxWidth, true);

            // second line of rounds box
            if (!gameFlag) {
                var += Conversion.centeredLine(currentRound + " of " + maxRounds, "|", roundsBoxWidth, false);
            } else {
                var += Conversion.centeredLine(currentRound + "", "|", roundsBoxWidth, false);
            }
            var += "     ";

            // second line of player name box
            var += Conversion.centeredLine(this.player.getName(), "|", playerBoxWidth, false);
            var += "     ";

            // second line of score box
            var += Conversion.centeredLine(this.player.getScore() + "", "|", scoreBoxWidth, true);

            // rounds box bottom dashes
            temp = "";
            for (int i = 0; i < roundsBoxWidth; i++) {
                temp += "-";
            }
            var += Conversion.centeredLine(temp, " ", roundsBoxWidth, false);
            var += "     ";

            //player box bottom dashes
            temp = "";
            for (int i = 0; i < playerBoxWidth; i++) {
                temp += "-";
            }
            var += Conversion.centeredLine(temp, " ", playerBoxWidth, false);
            var += "     ";

            //score box bottom dashes
            temp = "";
            for (int i = 0; i < scoreBoxWidth; i++) {
                temp += "-";
            }
            var += Conversion.centeredLine(temp, " ", scoreBoxWidth, true);
            var += "\n";

        }

        // column numbers
        if (showName) {
            var += "\n    ";
        } else {
            var += "\n\n    ";
        }

        for (int i = 0; i < this.numCols; i++) {
            if ((i + 1) < 10) {
                var += "   " + (i + 1);
            } else {
                var += "  " + (i + 1);
            }
        }

        // line of dashes (-)
        var += "\n     ";
        for (int i = 0; i < this.numCols; i++) {
            var += "----";
        }

        // all rows
        for (int i = 0; i < this.numRows; i++) {

            //each row letter
            var += "\n " + Conversion.toRow(i) + " |";

            for (int j = 0; j < this.numCols; j++) {

                if (!locations[i][j].isEmpty()) {

                    if (locations[i][j].getShip().isHit()) {

                        if (locations[i][j].isMarked()) {

                            //ship exists and has been hit or sunk and is marked
                            var += "  " + "x" + locations[i][j].getShip().getLetter();

                        } else {

                            //ship exists and has not been hit or sunk and is not marked
                            var += "   " + locations[i][j].getShip().getLetter();

                        }
                    } else {

                        //ship exists and has not been hit or sunk
                        var += "   " + locations[i][j].getShip().getLetter();

                    }

                } else {
                    if (locations[i][j].isMarked()) {

                        // location is marked
                        var += "   " + "O";

                    } else {

                        // location is unmarked
                        var += "   " + ".";

                    }

                }

            }

            //new row
            var += "\n";

        }

        //gaps for looks
        var += "\n\n";

        return var;
    }

}
