package battleship;

public class Conversion {
    


    public static String centeredLine(String mes, String edge, int width, boolean flag) {

        int lenName = mes.length();
        int lenEdge = edge.length();
        int lenSpace;
        String result;

        if (lenName < width) {

            lenSpace = ((width - lenName) / 2);

            result = edge;

            result += String.format("%" + lenSpace + "s", "");
            result += mes;

            if (((width - lenName) % 2) == 0) {
                result += String.format("%-" + lenSpace + "s", "");
            } else {
                result += String.format("%-" + (lenSpace + 1) + "s", "");
            }

            if (flag) {
                result += edge + "\n";
            } else {
                result += edge;
            }

        } else {

            if (flag) {
                result = edge + mes + edge + "\n";
            } else {
                result = edge + mes + edge;
            }

        }

        return result;
    }

    public static String leftLine(String mes, String edge, int width, int leftMargin, boolean flag) {

        int lenName = mes.length();
        int lenSpace;
        int lenL;
        String result;

        if (lenName < width) {

            lenSpace = width - lenName; //total spaces

            if ((lenSpace - leftMargin) > 0) {

                lenL = lenSpace - leftMargin;

                result = edge;
                
                if (leftMargin != 0) {
                    result += String.format("%" + leftMargin + "s", "");
                }
                
                result += mes;

                result += String.format("%-" + lenL + "s", "");

                if (flag) {
                    result += edge + "\n";
                } else {
                    result += edge;
                }

            } else {

                if (flag) {
                    result = edge + mes + edge + "\n";
                } else {
                    result = edge + mes + edge;
                }

            }

        } else {

            if (flag) {
                result = edge + mes + edge + "\n";
            } else {
                result = edge + mes + edge;
            }

        }

        return result;
    }

    public static String fillLine(String fill, String edge, int width, boolean flag) {
        int lenName = fill.length();
        int lenEdge = edge.length();
        int lenSpace = (width - (lenEdge * 2));
        String result;

        if (lenSpace > lenName) {

            int repeat = lenSpace / lenName;
            int remaining = lenSpace % lenName;

            result = edge;

            for (int i = 0; i < repeat; i++) {
                result += fill;
            }

            if (remaining > 0) {
                result += fill.substring(0, remaining);
            }

            if (flag) {
                result += edge + "\n";
            } else {
                result += edge;
            }

        } else {
            if (flag) {
                result = edge + fill + edge + "\n";
            } else {
                result = edge + fill + edge;
            }
        }

        return result;
    }

    public static String toRow(int row) {

        if (row == 0) {
            return "A";
        } else if (row == 1) {
            return "B";
        } else if (row == 2) {
            return "C";
        } else if (row == 3) {
            return "D";
        } else if (row == 4) {
            return "E";
        } else if (row == 5) {
            return "F";
        } else if (row == 6) {
            return "G";
        } else if (row == 7) {
            return "H";
        } else if (row == 8) {
            return "I";
        } else if (row == 9) {
            return "J";
        } else if (row == 10) {
            return "K";
        } else if (row == 11) {
            return "L";
        } else if (row == 12) {
            return "M";
        } else if (row == 13) {
            return "N";
        } else if (row == 14) {
            return "O";
        }
        return "";
    }

    public static int toRow(String row) {

        if (row.equalsIgnoreCase("A")) {
            return 0;
        } else if (row.equalsIgnoreCase("B")) {
            return 1;
        } else if (row.equalsIgnoreCase("C")) {
            return 2;
        } else if (row.equalsIgnoreCase("D")) {
            return 3;
        } else if (row.equalsIgnoreCase("E")) {
            return 4;
        } else if (row.equalsIgnoreCase("F")) {
            return 5;
        } else if (row.equalsIgnoreCase("G")) {
            return 6;
        } else if (row.equalsIgnoreCase("H")) {
            return 7;
        } else if (row.equalsIgnoreCase("I")) {
            return 8;
        } else if (row.equalsIgnoreCase("J")) {
            return 9;
        } else if (row.equalsIgnoreCase("K")) {
            return 10;
        } else if (row.equalsIgnoreCase("L")) {
            return 11;
        } else if (row.equalsIgnoreCase("M")) {
            return 12;
        } else if (row.equalsIgnoreCase("N")) {
            return 13;
        } else if (row.equalsIgnoreCase("O")) {
            return 14;
        }
        return -1;
    }

}
