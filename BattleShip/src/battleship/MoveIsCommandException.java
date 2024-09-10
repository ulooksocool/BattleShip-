
package battleship;

public class MoveIsCommandException extends InvalidLocationException{
    
    Command cmd;

    public MoveIsCommandException(String message) {
        super(message);
        // maybe something more?
    }
    
    public  MoveIsCommandException(String mes,Command cmd)
    {
        super(mes);
        this.cmd = cmd;
    }
    
    public Command getCommand()
    {
        return cmd;
    }

}
