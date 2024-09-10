package battleship;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Game {

    public static int rows;
    public static int cols;
    public static int currentRound;
    public static int maxRounds;
    public static Player player1;
    public static Player player2;
    public static boolean gameFlag; // true for deathmatch false for n-rounds
    private static boolean flagGameExists; // true if a game is already loaded false if there is no game currently loaded
    private static boolean flagPlayerTurn; // alternates from true and false to switch player (always starts with true)
    private static boolean flagPlayerOrder; // specifies if player1 plays first (true) or second (false)
    public static boolean test; // specifies if the game is started on normal mode or development mode

    public static void main(String[] args) {

        Command com = null;

        NormalOrTest();

        while (true) {

            if (flagGameExists) {
                try {

                    play();

                } catch (MoveIsCommandException e) {

                    try {
                        com = e.getCommand();
                        //System.out.println("Game 39 Command : " + com.toString());
                        if (com == Command.EXIT) {

                            if (gameExit()) {
                                return;
                            }
                            continue;
                        } else if (com == Command.HELP) {
                            help();
                            continue;
                        } else if (com == Command.LOAD) {
                            load();
                            continue;
                        } else if (com == Command.SAVE) {
                            save();
                            continue;
                        } else if (com == Command.MENU) {
                            checkMenu();
                        }

                    } catch (InputMismatchException s) {

                    }
                }

            } else {
                checkMenu();
            }

        }
    }

    public static void init() {

        Scanner input = new Scanner(System.in);

        String mes = "\n\n -----------------------\n";
        mes += "|  Give Number of Rows  |\n";
        mes += "|        [10-15]        |\n";
        mes += " -----------------------\n";
        while (true) {
            System.out.println(mes);
            if (input.hasNextInt()) {
                rows = input.nextInt();
                if (checkBounds(rows, 10, 15)) {
                    break;
                }
            }
            System.out.println("Wrong Input! Please Try Again");
            input.nextLine();
        }

        mes = "\n\n --------------------------\n";
        mes += "|  Give Number of Columns  |\n";
        mes += "|          [10-15]         |\n";
        mes += " --------------------------\n";

        while (true) {
            System.out.println(mes);
            if (input.hasNextInt()) {
                cols = input.nextInt();
                if (checkBounds(cols, 10, 15)) {
                    break;
                }
            }
            System.out.println("Wrong Input! Please Try Again");
            input.nextLine();

        }

        mes = "\n\n ---------------------\n";
        mes += "|      Player 1       |\n";
        mes += "|      1.Human        |\n";
        mes += "|      2.Computer     |\n";
        mes += " ---------------------\n";

        int p1;
        while (true) {
            System.out.println(mes);
            if (input.hasNextInt()) {
                p1 = input.nextInt();
                if (checkBounds(p1, 1, 2)) {
                    break;
                }
            }
            System.out.println("Wrong Input! Please Try Again");
            input.nextLine();
        }

        mes = "\n\n -------------------\n";
        mes += "|     Player 1      |\n";
        mes += "|    [Type Name]    |\n";
        mes += " -------------------\n";

        String p1name;
        if (p1 == 1) {
            input.nextLine();
            while (true) {
                System.out.println(mes);
                if (input.hasNextLine()) {
                    p1name = input.nextLine();
                    if (p1name.matches("[a-zA-Z0-9]{1,20}")) {
                        break;
                    }
                }
                System.out.println("Wrong Input! Please Try Again");
                input.nextLine();
            }
        } else {
            p1name = "Computer";
        }

        mes = "\n\n ---------------------\n";
        mes += "|      Player 2       |\n";
        mes += "|      1.Human        |\n";
        mes += "|      2.Computer     |\n";
        mes += " ---------------------\n";

        int p2;
        while (true) {
            System.out.println(mes);
            if (input.hasNextInt()) {
                p2 = input.nextInt();
                if (checkBounds(p2, 1, 2)) {
                    break;
                }
            }
            System.out.println("Wrong Input! Please Try Again");
            input.nextLine();
        }

        mes = "\n\n -------------------\n";
        mes += "|     Player 2      |\n";
        mes += "|    [Type Name]    |\n";
        mes += " -------------------\n";

        String p2name;
        if (p2 == 1) {
            input.nextLine();
            while (true) {
                System.out.println(mes);
                if (input.hasNextLine()) {
                    p2name = input.nextLine();
                    if (p2name.matches("[a-zA-Z0-9]{1,20}")) {
                        break;
                    }
                }
                System.out.println("Wrong Input! Please Try Again");
                input.nextLine();
            }
        } else {
            p2name = "Computer";
        }

        mes = "\n\n ---------------------\n";
        mes += "|      Game Type      |\n";
        mes += "|     1.Deathmatch    |\n";
        mes += "|     2.Max Rounds    |\n";
        mes += " ---------------------\n";

        int gameType;
        while (true) {
            System.out.println(mes);
            if (input.hasNextInt()) {
                gameType = input.nextInt();
                if (checkBounds(gameType, 1, 2)) {
                    break;
                }
            }
            System.out.println("Wrong Input! Please Try Again");
            input.nextLine();
        }

        if (gameType == 2) {

            int max = (rows * cols) - 1;

            mes = "\n\n ---------------------------\n";
            mes += "|   Give Number of Rounds   |\n";
            mes += Conversion.centeredLine("[1 - " + max + "]", "|", 27, true);
            mes += " ---------------------------\n";

            while (true) {
                System.out.println(mes);
                if (input.hasNextInt()) {
                    maxRounds = input.nextInt();
                    if (checkBounds(maxRounds, 1, max)) {
                        break;
                    }
                }
                System.out.println("Wrong Input! Please Try Again");
                input.nextLine();
            }
        }

        if (gameType == 1) {
            gameFlag = true;
        } else {
            gameFlag = false;
        }

        Random rand = new Random();
        int t = rand.nextInt(2);
        //System.out.println(t);
        if (t == 0) {
            flagPlayerOrder = true;
        } else {
            flagPlayerOrder = false;
        }

        flagGameExists = true;
        flagPlayerTurn = true;

        currentRound = 1;

        if (p1 == 1) {
            player1 = new HumanPlayer();
        } else {
            player1 = new ComputerPlayer();
        }
        player1.setName(p1name);
        player1.setField(new Field());
        player1.getField().setPlayer(player1);
        player1.initField(rows, cols);
        player1.setScore(0);

        if (p2 == 1) {
            player2 = new HumanPlayer();
        } else {
            player2 = new ComputerPlayer();
        }
        player2.setName(p2name);
        player2.setField(new Field());
        player2.getField().setPlayer(player2);
        player2.initField(rows, cols);
        player2.setScore(0);
    }

    public static boolean placeShips() {

        boolean flag;
        String mes;
        Player p1;
        Player p2;

        if (flagPlayerOrder) {
            p1 = player1;
            p2 = player2;
        } else {
            p1 = player2;
            p2 = player1;
        }

        //first player
        while (true) {
            flag = p1.placeShips(p2.getField());

            if (!flag) {

                mes = "\n\n ---------------------------------------------\n";
                mes += "|   You Have Been Returned To The Main Menu   |\n";
                mes += " ---------------------------------------------\n";
                System.out.println(mes);

                return false;
            } else {
                
                mes = "\n\n ----------------------------------\n";
                mes += "|             Ships For            |\n";
                mes += Conversion.centeredLine(p1.getName(), "|", 34, true);
                mes += "|   Has Been Placed Successfully   |\n";
                mes += " ----------------------------------\n";
                System.out.println(mes);
                break;
            }

        }

        //second player
        while (true) {
            flag = p2.placeShips(p1.getField());

            if (!flag) {

                mes = "\n\n ---------------------------------------------\n";
                mes += "|   You Have Been Returned To The Main Menu   |\n";
                mes += " ---------------------------------------------\n";
                System.out.println(mes);

                return false;
            } else {
                
                mes = "\n\n ----------------------------------\n";
                mes += "|             Ships For            |\n";
                mes += Conversion.centeredLine(p2.getName(), "|", 34, true);
                mes += "|   Has Been Placed Successfully   |\n";
                mes += " ----------------------------------\n";
                System.out.println(mes);
                break;
            }

        }

        return true;
    }

    public static void play() throws MoveIsCommandException {

        Location loc;
        Player p1 = null;
        Player p2 = null;
        boolean flag = false;
        boolean marked = true;

        if (flagPlayerOrder) {
            p1 = player1;
            p2 = player2;
        } else {
            p1 = player2;
            p2 = player1;
        }

        while (true) {
            try {
                if (flagPlayerTurn) {

                    if (test) {
                        System.out.println(p1.getField().toStringTesting(true));
                    } else {
                        printField(p1);
                    }

                    if (flag) {
                        System.out.println("Please enter a valid move or command!");
                        flag = false;
                    }

                    loc = p1.selectMove();
                    marked = p1.getField().processValidMove(loc);

                    if (marked) {
                        flag = true;
                        continue;
                    }

                    if (showResult()) {
                        clear();
                        throw new MoveIsCommandException("", Command.MENU);
                    }

                    flagPlayerTurn = false;
                    break;

                } else {

                    if (test) {
                        System.out.println(p2.getField().toStringTesting(true));
                    } else {
                        printField(p2);
                    }

                    if (flag) {
                        System.out.println("Please enter a valid move or command!");
                        flag = false;
                    }

                    loc = p2.selectMove();
                    marked = p2.getField().processValidMove(loc);

                    if (marked) {
                        flag = true;
                        continue;
                    }

                    if (showResult()) {
                        clear();
                        throw new MoveIsCommandException("", Command.MENU);
                    }

                    flagPlayerTurn = true;
                    currentRound++;
                    break;
                }

            } catch (InvalidLocationException ex) {

                if (ex instanceof MoveIsCommandException) {
                    throw new MoveIsCommandException("", ((MoveIsCommandException) ex).getCommand());
                }

                flag = true;
            }
        }

    }

    public static boolean showResult() {

        if (!gameFlag) {
            if (maxRounds == currentRound) {
                if (!flagPlayerTurn) {
                    if (player1.getScore() > player2.getScore()) {
                        printWin(player1, player2);
                    } else if (player2.getScore() > player1.getScore()) {
                        printWin(player2, player1);
                    } else {
                        printDraw();
                    }
                    return true;
                }
            }
        } else {
            if (player1.hasWon()) {
                printWin(player1, player2);
                return true;
            } else if (player2.hasWon()) {
                printWin(player2, player1);
                return true;
            }

        }
        return false;
    }

    public static void printWin(Player won, Player lost) {
        String mes;

        mes = "\n\n                                                  _                         \n";
        mes += "                                            .----'/|                        \n";
        mes += "                                           |  .--.\\|                       \n";
        mes += "                                          .'--'. '-'                        \n";
        mes += "                             _.----------'.____.'----.__                  \n";
        mes += "                 ,-.       .'            You Win!       `--.__            \n";
        mes += "                |=| `..__.'    _          Player          _   ``.         \n";
        mes += "_.-.__.-.__.-._ |=|           / \\";
        mes += Conversion.centeredLine(won.getName(), "", 24, false);
        mes += "/ \\     \\ _.-.__.-.__.-._     \n";
        mes += "                |=|    __     \\_/         Score          \\_/     /      \n";
        mes += "                |=|  ,'  `.                ";
        if (won.getScore() <= 9) {
            mes += " ";
        }
        mes += won.getScore();
        mes += "                 _.'     \n";
        mes += "                 `-.'      '._                            __( ). '. - '. ~'  \n";
        mes += "                              `.______________________.--'      ' - . ~ ` ~  \n";
        mes += "                                                                  `' ~ `. ~  \n";
        mes += "                                                                      `. ~   \n";

        System.out.println(mes);
        lost.getField().toStringWithShips(false);
    }

    public static void printDraw() {
        String mes;

        mes = "\n\n";
        mes += "      It's a Draw! \n";
        mes += "   Have Some Ice Cream \n";
        mes += "\n";
        mes += "            @     \n";
        mes += "          (' .)   \n";
        mes += "         (*.`. )  \n";
        mes += "        (*.~.*. ) \n";
        mes += "        {\\#####/} \n";
        mes += "          \\###/   \n";
        mes += "           \\#/    \n";
        mes += "            V     \n";
        System.out.println(mes);

        player2.getField().toStringWithShips(false);
        player1.getField().toStringWithShips(false);

    }

    public static void clear() {
        rows = 0;
        cols = 0;
        currentRound = 0;
        maxRounds = 0;
        player1 = null;
        player2 = null;
        gameFlag = false;
        flagGameExists = false; // true if a game is already loaded false if there is no game currently loaded
        flagPlayerTurn = false; // alternates from true and false to switch player 
        flagPlayerOrder = false; // specifies if player1 plays first or second etc
    }

    public static boolean checkBounds(int given, int min, int max) {
        if ((given >= min) && (given <= max)) {
            return true;
        }
        return false;
    }

    public static boolean checkStringBounds(String mes, String correct, String wrong) {
        Scanner input = new Scanner(System.in);
        String ans;
        while (true) {
            System.out.println(mes);
            ans = input.nextLine();
            if (ans.equalsIgnoreCase(correct)) {
                return true;
            } else if (ans.equalsIgnoreCase(wrong)) {
                return false;
            }

            System.out.println("Wrong Input! Try Again!");
        }

    }

    public static void printField(Player pl) {
        System.out.println(pl.getField());
    }

    public static void menu() {
        if (!flagGameExists) {
            System.out.println("\n ------------------------");
            System.out.println("|          MENU          |");
            System.out.println("|                        |");
            System.out.println("|         PLAY           |");
            System.out.println("|         SAVE           |");
            System.out.println("|         LOAD           |");
            System.out.println("|         HELP           |");
            System.out.println("|         EXIT           |");
            System.out.println("|                        |");
            System.out.println(" ------------------------");
            System.out.println(" Please Enter Choise:");
        } else {
            System.out.println("\n ------------------------");
            System.out.println("|          MENU          |");
            System.out.println("|                        |");
            System.out.println("|         RESUME         |");
            System.out.println("|          SAVE          |");
            System.out.println("|          LOAD          |");
            System.out.println("|          HELP          |");
            System.out.println("|          EXIT          |");
            System.out.println("|                        |");
            System.out.println(" ------------------------");
            System.out.println(" Please Enter Choise:");
        }
    }

    public static void checkMenu() {

        Scanner input = new Scanner(System.in);
        Command com = null;
        boolean flag = false;

        while (true) {
            menu();
            if (flag) {
                System.out.println("\nPlease Enter a Valid Option");
                flag = false;
            }
            String ans = input.nextLine();

            if (ans.equalsIgnoreCase("play")) {

                if (!flagGameExists) {
                    init();
                    if (!placeShips()) {
                        clear();
                        continue;
                    }
                    return;
                }
                flag = true;

            } else if (ans.equalsIgnoreCase("resume")) {

                if (flagGameExists) {
                    //go back to game
                    return;
                }
                flag = true;

            } else {
                try {
                    com = Command.fromString(ans);

                    if (com == Command.EXIT) {

                        if (gameExit()) {
                            System.exit(0);
                        }

                    } else if (com == Command.HELP) {
                        help();

                    } else if (com == Command.LOAD) {
                        load();
                        return;
                    } else if (com == Command.SAVE) {
                        save();

                    }

                } catch (InputMismatchException s) {
                    flag = true;
                }

            }
        }

    }

    public static void help() {

        String mes = "\n\n";
        mes += Conversion.fillLine("-----", " ", 102, true);
        mes += Conversion.centeredLine(" ", "|", 100, true);
        mes += Conversion.centeredLine("~~~~~~~~~~~~~~~~", "|", 100, true);
        mes += Conversion.centeredLine("~ HELP SECTION ~", "|", 100, true);
        mes += Conversion.centeredLine("~~~~~~~~~~~~~~~~", "|", 100, true);

        // Game Goal Section
        mes += Conversion.centeredLine(" ", "|", 100, true);
        mes += Conversion.leftLine(".-----------.", "|", 100, 10, true);
        mes += Conversion.leftLine("| Game Goal |", "|", 100, 10, true);
        mes += Conversion.leftLine("'-----------'", "|", 100, 10, true);

        mes += Conversion.centeredLine(" ", "|", 100, true);
        mes += Conversion.centeredLine("The goal of the game is to try and sink all of the other players ships", "|", 100, true);
        mes += Conversion.centeredLine("before they sink all of your ships. All of the other players ships are", "|", 100, true);
        mes += Conversion.centeredLine("somewhere on their board. Each turn you try and hit them by typing the", "|", 100, true);
        mes += Conversion.centeredLine("coordinates of one of the squares on their board.", "|", 100, true);

        // Controls Section
        mes += Conversion.centeredLine(" ", "|", 100, true);
        mes += Conversion.leftLine(".----------.", "|", 100, 10, true);
        mes += Conversion.leftLine("| Controls |", "|", 100, 10, true);
        mes += Conversion.leftLine("'----------'", "|", 100, 10, true);

        mes += Conversion.centeredLine(" ", "|", 100, true);
        mes += Conversion.centeredLine("~~ Commands ~~", "|", 100, true);

        mes += Conversion.centeredLine(" ", "|", 100, true);
        mes += Conversion.centeredLine("[Play]", "|", 100, true);
        mes += Conversion.centeredLine(" ", "|", 100, true);
        mes += Conversion.centeredLine("The command play is executed by typing the word \"play\", case ", "|", 100, true);
        mes += Conversion.centeredLine("independent. The player/players are then introduced to a series of", "|", 100, true);
        mes += Conversion.centeredLine("questions with the purpose of configuring the rules of the game for", "|", 100, true);
        mes += Conversion.centeredLine("this run.", "|", 100, true);
        mes += Conversion.leftLine("In particular the following options must be set for a game to be", "|", 100, 20, true);
        mes += Conversion.leftLine("started:", "|", 100, 20, true);
        mes += Conversion.leftLine("1. The dimensions of the board.", "|", 100, 20, true);
        mes += Conversion.leftLine("A) The number of Rows.", "|", 100, 24, true);
        mes += Conversion.leftLine("B) The number of Cols.", "|", 100, 24, true);
        mes += Conversion.leftLine("2. Who is controlling the Player. *For each player seperately", "|", 100, 20, true);
        mes += Conversion.leftLine("A) A human person.", "|", 100, 24, true);
        mes += Conversion.leftLine("B) The computer.", "|", 100, 24, true);
        mes += Conversion.leftLine("3. The name of the Player. *For each player seperately", "|", 100, 20, true);
        mes += Conversion.leftLine("4. The game mode.", "|", 100, 20, true);
        mes += Conversion.leftLine("A) Deathmatch.", "|", 100, 24, true);
        mes += Conversion.leftLine("B) Max rounds.", "|", 100, 24, true);

        mes += Conversion.centeredLine(" ", "|", 100, true);
        mes += Conversion.centeredLine("[Save]", "|", 100, true);
        mes += Conversion.centeredLine(" ", "|", 100, true);
        mes += Conversion.centeredLine("The command save is executed by typing the word \"save\", case ", "|", 100, true);
        mes += Conversion.centeredLine("independent. The player/players are then asked whether they truly wish", "|", 100, true);
        mes += Conversion.centeredLine("to save the current running game, if there is one, in which case they.", "|", 100, true);
        mes += Conversion.centeredLine("they are asked to enter a valid label for their game save.", "|", 100, true);

        mes += Conversion.centeredLine(" ", "|", 100, true);
        mes += Conversion.centeredLine("[Load]", "|", 100, true);
        mes += Conversion.centeredLine(" ", "|", 100, true);
        mes += Conversion.centeredLine("The command load is executed by typing the word \"load\", case ", "|", 100, true);
        mes += Conversion.centeredLine("independent. The player/players are given a list of all available", "|", 100, true);
        mes += Conversion.centeredLine("game saves and are prompted to select a game save to load. The", "|", 100, true);
        mes += Conversion.centeredLine("game save is then loaded in the state it was when it was last saved.", "|", 100, true);

        mes += Conversion.centeredLine(" ", "|", 100, true);
        mes += Conversion.centeredLine("[Help]", "|", 100, true);
        mes += Conversion.centeredLine(" ", "|", 100, true);
        mes += Conversion.centeredLine("The command help is executed by typing the word \"help\", case ", "|", 100, true);
        mes += Conversion.centeredLine("independent. Regardless from where this command was executed a ", "|", 100, true);
        mes += Conversion.centeredLine("this help section is printed. If or when the player/players choose", "|", 100, true);
        mes += Conversion.centeredLine("to exit the help section, they are returned to the point they were", "|", 100, true);
        mes += Conversion.centeredLine("when they executed the \"help\" command.", "|", 100, true);

        mes += Conversion.centeredLine(" ", "|", 100, true);
        mes += Conversion.centeredLine("[Exit]", "|", 100, true);
        mes += Conversion.centeredLine(" ", "|", 100, true);
        mes += Conversion.centeredLine("The command help is executed by typing the word \"help\", case ", "|", 100, true);
        mes += Conversion.centeredLine("independent. The player/players are given the three following options:", "|", 100, true);
        mes += Conversion.leftLine("1. To attempt to save the game, if one exists,", "|", 100, 27, true);
        mes += Conversion.leftLine("and then exit the game.", "|", 100, 31, true);
        mes += Conversion.leftLine("2. To exit the game without attempting to save", "|", 100, 27, true);
        mes += Conversion.leftLine("the current game.", "|", 100, 31, true);
        mes += Conversion.leftLine("3. To go back to the point from which the \"help\"", "|", 100, 27, true);
        mes += Conversion.leftLine("command was executed.", "|", 100, 31, true);

        mes += Conversion.centeredLine(" ", "|", 100, true);
        mes += Conversion.centeredLine("~~ Special Mentions ~~", "|", 100, true);

        mes += Conversion.centeredLine(" ", "|", 100, true);
        mes += Conversion.centeredLine("[Resume]", "|", 100, true);
        mes += Conversion.centeredLine(" ", "|", 100, true);
        mes += Conversion.centeredLine("The command Resume is executed by typing the word \"Resume\", case ", "|", 100, true);
        mes += Conversion.centeredLine("independent. When the \"menu\" command has been executed previously", "|", 100, true);
        mes += Conversion.centeredLine("and there is a game currently running, the \"Resume\" command is shown", "|", 100, true);
        mes += Conversion.centeredLine("instead of the \"Play\" command. When executed it exits the main menu", "|", 100, true);
        mes += Conversion.centeredLine("and returns the player/players back to the game.", "|", 100, true);

        mes += Conversion.centeredLine(" ", "|", 100, true);
        mes += Conversion.centeredLine("[Menu]", "|", 100, true);
        mes += Conversion.centeredLine(" ", "|", 100, true);
        mes += Conversion.centeredLine("The command menu is executed by typing the word \"menu\", case ", "|", 100, true);
        mes += Conversion.centeredLine("independent. The main menu with the commands mentioned above is", "|", 100, true);
        mes += Conversion.centeredLine("printed to revision the available commands the player/players have", "|", 100, true);
        mes += Conversion.centeredLine("at any given point in the game.", "|", 100, true);

        // How to play section
        mes += Conversion.centeredLine(" ", "|", 100, true);
        mes += Conversion.leftLine(".-------------.", "|", 100, 10, true);
        mes += Conversion.leftLine("| How To Play |", "|", 100, 10, true);
        mes += Conversion.leftLine("'-------------'", "|", 100, 10, true);

        mes += Conversion.centeredLine(" ", "|", 100, true);
        mes += Conversion.centeredLine("When a new game is started and the player/players have gone through", "|", 100, true);
        mes += Conversion.centeredLine("the configuration process, they are then asked to specify ", "|", 100, true);
        mes += Conversion.centeredLine("how they want to place their ships.", "|", 100, true);
        mes += Conversion.leftLine("1. Automatically.", "|", 100, 32, true);
        mes += Conversion.leftLine("A) Infinite placement attempts.", "|", 100, 36, true);
        mes += Conversion.leftLine("B) Specify the number of attempts.", "|", 100, 36, true);
        mes += Conversion.leftLine("2. Manually.", "|", 100, 32, true);
        mes += Conversion.centeredLine("For option 2 the user is required to enter the location and direction", "|", 100, true);
        mes += Conversion.centeredLine("every ship until all the ships have been placed. The location is given", "|", 100, true);
        mes += Conversion.centeredLine("in the \"A10\" format where \"A\" is the letter of the row and ", "|", 100, true);
        mes += Conversion.centeredLine("\"10\" is the number of the column where the player wishes to place", "|", 100, true);
        mes += Conversion.centeredLine("their ship. The direction is given in the \"h\" or \"v\" format for", "|", 100, true);
        mes += Conversion.centeredLine("horizontal or vertical placement. The player repeats this process for", "|", 100, true);
        mes += Conversion.centeredLine("every ship until all ships have been placed. The player is also given", "|", 100, true);
        mes += Conversion.centeredLine("the choise to not place a ship in which case the game gets deleted and", "|", 100, true);
        mes += Conversion.centeredLine("the player is returned to the main menu. The ships to be placed are in", "|", 100, true);
        mes += Conversion.centeredLine("this order:", "|", 100, true);
        mes += Conversion.leftLine("1. Aircraft Carrier (x2)", "|", 100, 37, true);
        mes += Conversion.leftLine("2. Destroyer (x3)", "|", 100, 37, true);
        mes += Conversion.leftLine("3. Submarine (x2)", "|", 100, 37, true);
        mes += Conversion.centeredLine("After all the ships have been placed the game starts with the starting", "|", 100, true);
        mes += Conversion.centeredLine("player chosen randomly. The players then can make a move on the enemy ", "|", 100, true);
        mes += Conversion.centeredLine("players board, in their coresponding turn, by giving coordinates in the", "|", 100, true);
        mes += Conversion.centeredLine("\"A10\" format, where \"A\" is the letter of the row and", "|", 100, true);
        mes += Conversion.centeredLine("\"10\" is the number of the column the player attacks.", "|", 100, true);

        // Ships information section
        mes += Conversion.centeredLine(" ", "|", 100, true);
        mes += Conversion.leftLine(".-------.", "|", 100, 10, true);
        mes += Conversion.leftLine("| Ships |", "|", 100, 10, true);
        mes += Conversion.leftLine("'-------'", "|", 100, 10, true);

        //headers line
        mes += "|";
        mes += Conversion.fillLine("     ", "", 20, false);
        mes += Conversion.centeredLine("Ship", " ", 12, false);
        mes += "|";
        mes += Conversion.centeredLine("Fleet", " ", 12, false);
        mes += "|";
        mes += Conversion.centeredLine("Length", " ", 12, false);
        mes += "|";
        mes += Conversion.centeredLine("Points", " ", 12, false);
        mes += "|";
        mes += Conversion.centeredLine("Letter", " ", 12, false);
        mes += Conversion.fillLine("     ", "", 6, false);
        mes += "|\n";

        // seperator
        mes += "|";
        mes += Conversion.fillLine("     ", "", 14, false);
        mes += Conversion.fillLine("--------------------", "", 18, false);
        mes += Conversion.fillLine("+--------------", "", 60, false);
        mes += Conversion.fillLine("     ", "", 6, false);
        mes += "|\n";

        //  Aircraft Carrier information
        mes += "|";
        mes += Conversion.fillLine("     ", "", 14, false);
        mes += Conversion.centeredLine("Aircraft Carrier", " ", 18, false);
        mes += "|";
        mes += Conversion.centeredLine("2", " ", 12, false);
        mes += "|";
        mes += Conversion.centeredLine("5", " ", 12, false);
        mes += "|";
        mes += Conversion.centeredLine("5", " ", 12, false);
        mes += "|";
        mes += Conversion.centeredLine("A", " ", 12, false);
        mes += Conversion.fillLine("     ", "", 6, false);
        mes += "|\n";

        // seperator
        mes += "|";
        mes += Conversion.fillLine("     ", "", 14, false);
        mes += Conversion.fillLine("--------------------", "", 18, false);
        mes += Conversion.fillLine("+--------------", "", 60, false);
        mes += Conversion.fillLine("     ", "", 6, false);
        mes += "|\n";

        //  Destroyer information
        mes += "|";
        mes += Conversion.fillLine("     ", "", 14, false);
        mes += Conversion.centeredLine("Destroyer", " ", 18, false);
        mes += "|";
        mes += Conversion.centeredLine("3", " ", 12, false);
        mes += "|";
        mes += Conversion.centeredLine("3", " ", 12, false);
        mes += "|";
        mes += Conversion.centeredLine("2", " ", 12, false);
        mes += "|";
        mes += Conversion.centeredLine("D", " ", 12, false);
        mes += Conversion.fillLine("     ", "", 6, false);
        mes += "|\n";

        // seperator
        mes += "|";
        mes += Conversion.fillLine("     ", "", 14, false);
        mes += Conversion.fillLine("--------------------", "", 18, false);
        mes += Conversion.fillLine("+--------------", "", 60, false);
        mes += Conversion.fillLine("     ", "", 6, false);
        mes += "|\n";

        //  Submarine information
        mes += "|";
        mes += Conversion.fillLine("     ", "", 14, false);
        mes += Conversion.centeredLine("Submarine", " ", 18, false);
        mes += "|";
        mes += Conversion.centeredLine("2", " ", 12, false);
        mes += "|";
        mes += Conversion.centeredLine("1", " ", 12, false);
        mes += "|";
        mes += Conversion.centeredLine("3", " ", 12, false);
        mes += "|";
        mes += Conversion.centeredLine("S", " ", 12, false);
        mes += Conversion.fillLine("     ", "", 6, false);
        mes += "|\n";

        mes += Conversion.centeredLine(" ", "|", 100, true);
        mes += Conversion.fillLine("-----", " ", 102, true);

        while (true) {
            System.out.println(mes);
            if (checkStringBounds("Do You Want To Go Back? [Yes - No]", "yes", "no")) {
                break;
            }
        }
    }

    private static boolean gameExit() {

        String mes;
        mes = "\n\n ------------------------\n";
        mes += "|          EXIT          |\n";
        mes += "|                        |\n";
        mes += "|     1. SAVE & ΕΧΙΤ     |\n";
        mes += "|     2. EXIT            |\n";
        mes += "|     3. BACK            |\n";
        mes += "|                        |\n";
        mes += " ------------------------\n";
        mes += " Please Enter Choise:  [1 - 3]";

        int ans = Player.validateInput(mes, 1, 3);
        if (ans == 1) {
            //save();
            return true;
        } else if (ans == 2) {
            return true;
        } else {
            return false;
        }
    }

    private static void NormalOrTest() {
        String mes = "\n\n --------------------------------------------\n";
        mes += "|   Do You Wish To Enter Development Mode?   |\n";
        mes += "|           [Play]   or  [Develop]           |\n";
        mes += " --------------------------------------------\n";
        boolean flag = checkStringBounds(mes, "Play", "Develop");
        if (flag) {
            test = false;
        } else {
            test = true;
        }
    }

    private static void save() {

        if (!flagGameExists) {
            System.out.println("There Is No Game Running");
            return;
        }

        Scanner input = new Scanner(System.in);
        String mes = Conversion.fillLine("----", " ", 46, true);
        mes += Conversion.centeredLine("Are You Sure You Want To Save The Game", "|", 44, true);
        mes += Conversion.centeredLine("[Yes]", "|", 44, true);
        mes += Conversion.centeredLine("[No]", "|", 44, true);
        mes += Conversion.fillLine("----", " ", 46, true);

        if (!checkStringBounds(mes, "yes", "no")) {
            System.out.println("Game Was Not Saved!");
            return;
        }

        String fileName;
        File file;

        while (true) {
            mes = Conversion.fillLine("---", " ", 18, true);
            mes += Conversion.centeredLine("Save As...", "|", 16, true);
            mes += Conversion.fillLine("---", " ", 18, true);

            System.out.println(mes);
            fileName = input.nextLine();

            if (fileName.matches("[a-zA-Z0-9_!-@]{1,30}")) {

                File directory = new File("\\battleship\\saves");
                if (!directory.exists()) {
                    directory.mkdir();
                }

                file = new File(directory.getName() + "\\" + fileName + ".txt");
                if (file.exists()) {

                    mes = Conversion.fillLine("----", " ", 48, true);
                    mes += Conversion.centeredLine("A Save With The Same Name Already Exists", "|", 46, true);
                    mes += Conversion.centeredLine("Do you Want to Override it?", "|", 46, true);
                    mes += Conversion.centeredLine("[Yes]", "|", 46, true);
                    mes += Conversion.centeredLine("[No]", "|", 46, true);
                    mes += Conversion.fillLine("----", " ", 48, true);

                    if (checkStringBounds(mes, "yes", "no")) {
                        break;
                    } else {
                        System.out.println("Please Give Another Name");
                    }
                } else {
                    break;
                }
            } else {
                System.out.println("Invalid Name");
            }
        }

        try {

            PrintWriter wr = new PrintWriter(file);
            mes = "";
            String sep = ",";

            // Overall Game Setup Info
            wr.write(rows + sep + cols + sep);

            if (gameFlag) {
                mes = "infinite";
            } else {
                mes = "n-rounds";
            }
            wr.write(mes + sep + currentRound + sep + maxRounds + sep);

            int order = 1;
            if (!flagPlayerOrder) {
                order = 2;
            }
            wr.write(order + sep);

            int turn = 1;
            if (!flagPlayerTurn) {
                turn = 2;
            }
            wr.write(turn + sep + "\n");

            // Player 1 Info
            if (player1 instanceof HumanPlayer) {
                mes = "Human";
            } else {
                mes = "Computer";
            }
            wr.write(mes + sep + player1.getName() + sep + player1.getScore() + sep + "\n");

            // Player 2 Info
            if (player2 instanceof HumanPlayer) {
                mes = "Human";
            } else {
                mes = "Computer";
            }
            wr.write(mes + sep + player2.getName() + sep + player2.getScore() + sep + "\n");

            for (Ship k : player1.ships) {
                wr.write(k.getLetter() + sep + k.getStart().getRow() + sep + k.getStart().getCol() + sep + k.getDir().toString() + sep + "\n");
            }

            for (Ship k : player2.ships) {
                wr.write(k.getLetter() + sep + k.getStart().getRow() + sep + k.getStart().getCol() + sep + k.getDir().toString() + sep + "\n");
            }

            // save marked location of p1
            Location loc = null;
            int num = 0;
            // check how many locations are marked
            for (int i = 0; i < player1.getField().getRows(); i++) {

                for (int j = 0; j < player1.getField().getCols(); j++) {
                    loc = player1.getField().getLocation(i, j);

                    if (loc.isMarked()) {
                        num++;
                    }

                }
            }
            wr.write(num + sep + "\n");

            loc = null;
            // save marked location 
            for (int i = 0; i < player1.getField().getRows(); i++) {

                for (int j = 0; j < player1.getField().getCols(); j++) {
                    loc = player1.getField().getLocation(i, j);

                    if (loc.isMarked()) {
                        wr.write(i + sep + j + "\n");
                    }

                }
            }

            // save marked location of p2
            loc = null;
            num = 0;
            // check how many locations are marked
            for (int i = 0; i < player2.getField().getRows(); i++) {

                for (int j = 0; j < player2.getField().getCols(); j++) {
                    loc = player2.getField().getLocation(i, j);

                    if (loc.isMarked()) {
                        num++;
                    }

                }
            }
            wr.write(num + sep + "\n");

            loc = null;
            // save marked location
            for (int i = 0; i < player2.getField().getRows(); i++) {

                for (int j = 0; j < player2.getField().getCols(); j++) {
                    loc = player2.getField().getLocation(i, j);

                    if (loc.isMarked()) {
                        wr.write(i + sep + j + "\n");
                    }

                }
            }

            wr.close();
            System.out.println("Game Saved Successfully");

        } catch (IOException ex) {
            System.out.println("There Was a Problem Saving Your Game!");
        }

    }

    private static void load() {

        String mes = Conversion.fillLine("----", " ", 50, true);
        mes += Conversion.centeredLine("!Warning!", "|", 48, true);
        mes += Conversion.centeredLine(" ~ A Game Is Already Running ~", "|", 48, true);
        mes += Conversion.centeredLine("-Everything That Is Not Saved Will Be Lost-", "|", 48, true);
        mes += Conversion.centeredLine("Do You Want to Save This Game?", "|", 48, true);
        mes += Conversion.centeredLine("[Yes]", "|", 48, true);
        mes += Conversion.centeredLine("[No]", "|", 48, true);
        mes += Conversion.fillLine("----", " ", 50, true);

        if (checkStringBounds(mes, "yes", "no")) {
            save();
        }

        Scanner input = new Scanner(System.in);
        mes = "\n";

        File directory = new File("\\battleship\\saves");
        if (!directory.exists()) {
            System.out.println("No Saves Found");
            return;
        }

        // get all files in the saves directory
        File[] files = directory.listFiles();

        int width = 5;
        int size = 0;

        for (File file : files) {

            if (!file.isDirectory() && !file.isHidden() && file.exists() && file.canRead()) {

                size = file.getName().length();

                if (size > width) {
                    width = size;
                }
            }
        }

        if (size == 5) {
            size += 6;
        } else {
            size += 8;
        }

        // printing saves names on screen
        mes += Conversion.fillLine("-----", " ", size + 2, true);
        mes += Conversion.centeredLine("Saved Games", "|", size, true);
        for (File file : files) {

            if (!file.isDirectory() && !file.isHidden() && file.exists() && file.canRead() && file.getName().endsWith(".txt")) {

                mes += Conversion.centeredLine("[" + file.getName().replace(".txt", "") + "]", "|", size, true);

            }
        }
        mes += Conversion.fillLine("-----", " ", size + 2, true);

        String fileName;
        File loadFile = null;
        while (true) {

            System.out.println(mes);

            System.out.println("Please Type the Save Name");
            fileName = input.nextLine();

            for (File file : files) {

                if (!file.isDirectory() && !file.isHidden() && file.exists() && file.canRead() && file.getName().endsWith(".txt")) {

                    if (file.getName().equals(fileName.concat(".txt"))) {

                        loadFile = file;

                    }
                }
            }

            if (loadFile == null) {
                System.out.println("Please Give Another Name");
                continue;
            }

            break;
        }

        try {

            Scanner read = new Scanner(loadFile);

            String[] line = read.nextLine().split(",");

            // read field size
            int tempRows = Integer.parseInt(line[0]);
            int tempCols = Integer.parseInt(line[1]);

            // read game type
            boolean tempGameFlag;
            if (line[2].equals("infinity")) {
                tempGameFlag = true;

            } else if (line[2].equals("n-rounds")) {
                tempGameFlag = false;
            } else {
                throw new Exception("");
            }

            // read current round and the max rounds
            int tempCurrentRound = Integer.parseInt(line[3]);
            int tempMaxRounds = Integer.parseInt(line[4]);

            // read player order
            int tempPOrder = Integer.parseInt(line[5]);
            boolean tempPlayerOrder;
            if (tempPOrder == 1) {
                tempPlayerOrder = true;

            } else if (tempPOrder == 2) {
                tempPlayerOrder = false;
            } else {
                throw new Exception("");
            }

            // read player turn
            int tempPTurn = Integer.parseInt(line[6]);
            boolean tempPlayerTurn;
            if (tempPTurn == 1) {
                tempPlayerTurn = true;

            } else if (tempPTurn == 2) {
                tempPlayerTurn = false;
            } else {
                throw new Exception("");
            }

            // start construction player 1
            Player p1;

            line = read.nextLine().split(",");

            // get type of player
            if (line[0].equals("Human")) {
                p1 = new HumanPlayer();

            } else if (line[0].equals("Computer")) {
                p1 = new ComputerPlayer();
            } else {
                throw new Exception("");
            }

            // get player name
            if (line[1].matches("[a-zA-Z0-9]{1,20}")) {
                p1.setName(line[1]);
            } else {
                throw new Exception("");
            }

            // get player score
            int tempP1Score = Integer.parseInt(line[2]);
            p1.setScore(tempP1Score);
            p1.setField(new Field());
            p1.getField().setPlayer(p1);
            p1.initField(tempRows, tempCols);

            // start construction player 2
            Player p2;

            line = read.nextLine().split(",");

            // get type of player
            if (line[0].equals("Human")) {
                p2 = new HumanPlayer();

            } else if (line[0].equals("Computer")) {
                p2 = new ComputerPlayer();
            } else {
                throw new Exception("");
            }

            // get player name
            if (line[1].matches("[a-zA-Z0-9]{1,20}")) {
                p2.setName(line[1]);
            } else {
                throw new Exception("");
            }

            // get player score
            int tempP2Score = Integer.parseInt(line[2]);

            p2.setScore(tempP2Score);
            p2.setField(new Field());
            p2.getField().setPlayer(p2);
            p2.initField(tempRows, tempCols);

            // place player 2 ships on p1 field
            for (int i = 0; i < 7; i++) {

                line = read.nextLine().split(",");

                int r = Integer.parseInt(line[1]);
                int c = Integer.parseInt(line[2]);

                Location loc = p1.getField().getLocation(r, c);

                ShipDirection dir = ShipDirection.fromString(line[3]);

                size = p1.ships.size();
                if (line[0].equals("A")) {

                    p1.ships.add(new AircraftCarrier(p1.getField(), loc, dir));

                    Ship s = p1.ships.get(size);

                    p1.getField().loadShip(s);

                } else if (line[0].equals("D")) {
                    p1.ships.add(new Destroyer(p1.getField(), loc, dir));

                    Ship s = p1.ships.get(size);

                    p1.getField().loadShip(s);

                } else if (line[0].equals("S")) {
                    p1.ships.add(new Submarine(p1.getField(), loc, dir));

                    Ship s = p1.ships.get(size);

                    p1.getField().loadShip(s);

                }
            }
            // place player 1 ships on p2 field
            for (int i = 0; i < 7; i++) {

                line = read.nextLine().split(",");
                int r = Integer.parseInt(line[1]);
                int c = Integer.parseInt(line[2]);

                Location loc = p2.getField().getLocation(r, c);
                ShipDirection dir = ShipDirection.fromString(line[3]);

                size = p2.ships.size();
                if (line[0].equals("A")) {
                    p2.ships.add(new AircraftCarrier(p2.getField(), loc, dir));

                    Ship s = p2.ships.get(size);

                    p2.getField().loadShip(s);

                } else if (line[0].equals("D")) {
                    p2.ships.add(new Destroyer(p2.getField(), loc, dir));

                    Ship s = p2.ships.get(size);

                    p2.getField().loadShip(s);

                } else if (line[0].equals("S")) {
                    p2.ships.add(new Submarine(p2.getField(), loc, dir));

                    Ship s = p2.ships.get(size);

                    p2.getField().loadShip(s);

                }
            }

            // mark locations and raise hitCounter on ships that are hit
            line = read.nextLine().split(",");
            int num = Integer.parseInt(line[0]);

            for (int i = 0; i < num; i++) {

                line = read.nextLine().split(",");

                int r = Integer.parseInt(line[0]);
                int c = Integer.parseInt(line[1]);

                Location loc = p1.getField().getLocation(r, c);

                loc.mark();

                if (!loc.isEmpty()) {
                    loc.getShip().hit();
                }
            }

            // mark locations and raise hitCounter on ships that are hit
            line = read.nextLine().split(",");
            num = Integer.parseInt(line[0]);

            for (int i = 0; i < num; i++) {

                line = read.nextLine().split(",");

                int r = Integer.parseInt(line[0]);
                int c = Integer.parseInt(line[1]);

                Location loc = p2.getField().getLocation(r, c);

                loc.mark();
                if (!loc.isEmpty()) {
                    loc.getShip().hit();
                }
            }

            // everything was read and initiated successfully
            flagGameExists = true;
            rows = tempRows;
            cols = tempCols;
            gameFlag = tempGameFlag;
            currentRound = tempCurrentRound;
            maxRounds = tempMaxRounds;
            flagPlayerOrder = tempPlayerOrder;
            flagPlayerTurn = tempPlayerTurn;
            player1 = p1;
            player2 = p2;

            System.out.println("Save Loaded Successfully");

        } catch (Exception e) {
            System.out.println("Failed to Load Requested Save");
        }

    }

}
