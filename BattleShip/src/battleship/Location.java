 package battleship;

public class Location {

    private int col;
    private int row;
    private Ship ship;
    private boolean marked = false;

    public void mark() {

        this.marked = true;

        if (ship == null) {
        }
    }

    public boolean isMarked() {
        if (marked) {
            return true;

        }

        return false;
    }

    public boolean isEmpty() {
        if (ship == null) {
            return true;
        }
        return false;
    }

    public boolean isHit() {
        if (!isEmpty() && isMarked()) {
            return true;
        }
        return false;
    }

    public int getCol() {
        return this.col;
    }

    public int getRow() {
        return this.row;
    }

    public Ship getShip() {
        return this.ship;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

}
