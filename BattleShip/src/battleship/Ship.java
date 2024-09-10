package battleship;

import static battleship.Game.test;

public abstract class Ship {

    private final int length;
    private final int points;
    private final Field field;
    private final String letter;
    private Location start;
    private ShipDirection dir;
    private int hitCounter = 0;

    public Ship(int length, int points, Field field, String letter, Location start, ShipDirection dir) {
        this.length = length;
        this.points = points;
        this.field = field;
        this.letter = letter;
        this.start = start;
        this.dir = dir;
    }

    public abstract void getSinkMessage();

    public abstract boolean threaten();

    public void hit() {
        this.hitCounter++;
    }

    public boolean isHit() {
        if (this.hitCounter > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isSinking() {
        if (this.hitCounter == this.length) {
            return true;
        } else {
            return false;
        }
    }

    public void getHitMessage() {
        
        String name;
        
        if (test) {
            if("A".equals(this.getLetter())){
                name = "Aircraft Carrier";
            }else if("D".equals(this.getLetter())){
                name = "Destroyer";
            }else if("S".equals(this.getLetter())){
                name = "Submarine";
            }else{
                name = "Ship";
            }
            
        } else {
            name = "Ship";
        }
        
        System.out.println("You Just Hit A "+ name);
    }

    public int getLength() {
        return this.length;
    }

    public int getPoints() {
        return this.points;
    }

    public Field getField() {
        return this.field;
    }

    public String getLetter() {
        return this.letter;
    }

    public Location getStart() {
        return this.start;
    }

    public void setStart(Location start) {
        this.start = start;
    }

    public ShipDirection getDir() {
        return this.dir;
    }

    public void setDir(ShipDirection dir) {
        this.dir = dir;
    }

}
