package ru.console.chess;

import ru.console.chess.peice.ChessPiece;

public class BoardUtil {

    public static ChessPiece getPieceByPoint(ChessPiece[][] board, Point point) {
        return board[point.getY()][point.getX()];
    }

    /**
     * Переводим комманду в координату
     */
    public static int charToInt(char ch) {
        switch (ch) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
            default:
                return 8;
        }
    }

    /**
     * Переводим из реальных координат в комманду для программы
     *
     * @param row
     * @param col
     * @param newRow
     * @param newCol
     * @return
     */
    public static String coordinatesToMoveString(int row, int col, int newRow, int newCol) {

        String returnString = "";

        switch (col) {
            case 0:
                returnString += 'a';
                break;
            case 1:
                returnString += 'b';
                break;
            case 2:
                returnString += 'c';
                break;
            case 3:
                returnString += 'd';
                break;
            case 4:
                returnString += 'e';
                break;
            case 5:
                returnString += 'f';
                break;
            case 6:
                returnString += 'g';
                break;
            case 7:
                returnString += 'h';
                break;
            default:
                returnString += 'a';
                break;
        }

        int addInt = row + 1;

        returnString += addInt + "";

        returnString += " ";

        switch (newCol) {
            case 0:
                returnString += 'a';
                break;
            case 1:
                returnString += 'b';
                break;
            case 2:
                returnString += 'c';
                break;
            case 3:
                returnString += 'd';
                break;
            case 4:
                returnString += 'e';
                break;
            case 5:
                returnString += 'f';
                break;
            case 6:
                returnString += 'g';
                break;
            case 7:
                returnString += 'h';
                break;
            default:
                returnString += 'a';
                break;
        }

        addInt = newRow + 1;

        returnString += addInt + "";
        //System.out.println(row + " " + col + " " + newRow + " " + newCol + " " + returnString);
        return returnString;
    }


}
