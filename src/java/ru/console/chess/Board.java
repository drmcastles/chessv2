package ru.console.chess;

import ru.console.chess.peice.*;

import java.io.IOException;

import static ru.console.chess.BoardUtil.charToInt;
import static ru.console.chess.BoardUtil.coordinatesToMoveString;

public class Board {

    public ChessPiece[][] board = new ChessPiece[8][8];

    public Board() {
        this.initialize();
    }

    /**
     * Размещаем фигуры на доске
     */
    private void initialize() {
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                board[x][y] = null;
            }
        }

        // Белые пешки
        for (int x = 0; x < 8; x++) {
            board[1][x] = new Pawn(PieceColor.WHITE);
        }

        // Черные пешки
        for (int x = 0; x < 8; x++) {
            board[6][x] = new Pawn(PieceColor.BLACK);
        }

        //Ладья
        board[0][0] = new Rook(PieceColor.WHITE);
        board[0][7] = new Rook(PieceColor.WHITE);
        board[7][7] = new Rook(PieceColor.BLACK);
        board[7][0] = new Rook(PieceColor.BLACK);

        //Конь
        board[0][1] = new Horse(PieceColor.WHITE);
        board[0][6] = new Horse(PieceColor.WHITE);
        board[7][6] = new Horse(PieceColor.BLACK);
        board[7][1] = new Horse(PieceColor.BLACK);

        //слон
        board[0][2] = new Bishop(PieceColor.WHITE);
        board[0][5] = new Bishop(PieceColor.WHITE);
        board[7][2] = new Bishop(PieceColor.BLACK);
        board[7][5] = new Bishop(PieceColor.BLACK);

        //Королевы
        board[0][3] = new Queen(PieceColor.WHITE);
        board[7][3] = new Queen(PieceColor.BLACK);

        //Король
        board[0][4] = new King(PieceColor.WHITE);
        board[7][4] = new King(PieceColor.BLACK);

    }

    /**
     * Проверка находится ли король под шахом
     *
     * @param color цвет проверяемого короля
     * @return true - король указанного цвета под шахом, false - нет
     */
    public boolean isInCheck(PieceColor color) {
        Point kingPos = getKingPos(color);
        int row = kingPos.getX();
        int col = kingPos.getY();

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                ChessPiece currectPeice = board[x][y];
                if (currectPeice != null) {
                    if (currectPeice.canMoveToPosition(board, x, y, row, col) && !currectPeice.getColor().equals(color)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void move(String move, PieceColor playerColor, boolean actuallyMove) throws IOException {
        Move moveArray = parseInput(move);
        ChessPiece from = BoardUtil.getPieceByPoint(board, moveArray.getFrom());
        ChessPiece to = BoardUtil.getPieceByPoint(board, moveArray.getTo());

        checkMove(playerColor, from, to, moveArray);


        if (actuallyMove) {
            board[moveArray.getTo().getY()][moveArray.getTo().getX()] = board[moveArray.getFrom().getY()][moveArray.getFrom().getX()];
            board[moveArray.getFrom().getY()][moveArray.getFrom().getX()] = null;
        }
        castle(actuallyMove, moveArray);

        //Отдельно для пешек
        if (actuallyMove) {
            ChessPiece chessPiece = from;
            chessPiece.ep_able = false;
            if (chessPiece != null) {

                if (chessPiece.getClass().isInstance(new Pawn(PieceColor.WHITE))) {
                    chessPiece.hasMoved = true;

                    // Пешка дошла до конца - через комманду мы могли передать
                    // N для замены на коня, B - для замены на слона
                    // И во всех остальных случаях будет замена на королеву
                    ChessPiece replacement;
                    if (move.split(" ").length < 3) {
                        move += " s";
                    }
                    if (chessPiece.getColor().equals(PieceColor.WHITE)) {
                        if (moveArray.getTo().getY() == 7) {
                            switch (move.split(" ")[2].charAt(0)) {
                                case 'N':
                                    replacement = new Horse(PieceColor.WHITE);
                                    break;
                                case 'B':
                                    replacement = new Bishop(PieceColor.WHITE);
                                    break;
                                default:
                                    replacement = new Queen(PieceColor.WHITE);
                                    break;
                            }
                            board[moveArray.getTo().getY()][moveArray.getTo().getX()] = replacement;
                        }
                    } else {
                        if (moveArray.getTo().getY() == 0) {
                            switch (move.split(" ")[2].charAt(0)) {
                                case 'N':
                                    replacement = new Horse(PieceColor.BLACK);
                                    break;
                                case 'B':
                                    replacement = new Bishop(PieceColor.BLACK);
                                    break;
                                default:
                                    replacement = new Queen(PieceColor.BLACK);
                                    break;
                            }
                            board[moveArray.getTo().getY()][moveArray.getTo().getX()] = replacement;
                        }
                    }
                }
            }
        }
    }

    private void checkMove(PieceColor playerColor, ChessPiece from, ChessPiece to, Move moveArray) throws IOException {
        if (from == null) {
            throw new IOException("From point is empty");
        }

        if (!from.getColor().equals(playerColor)) {
            throw new IOException("Its not your figure");
        }

        if (to != null) {
            if (to.getColor().equals(playerColor)) {
                throw new IOException("There is your figure on the destination");
            }
        }

        if (!from.canMoveToPosition(board, moveArray.getFrom().getY(), moveArray.getFrom().getX(),
                moveArray.getTo().getY(), moveArray.getTo().getX())) {
            throw new IOException("Incorrect move");
        }
    }

    private void castle(boolean actuallyMove, Move moveArray) {
        ChessPiece to;
        to = board[moveArray.getTo().getY()][moveArray.getTo().getX()];

        if (to != null) {
            if (to.getClass().isInstance(new King(PieceColor.WHITE))) {
                King king = (King) board[moveArray.getTo().getY()][moveArray.getTo().getX()];
                if (actuallyMove) {
                    king.hasMoved = true;
                }
                //ракировка
                if (king.castled) {
                    if (moveArray.getTo().getX() - moveArray.getFrom().getX() == 2) {
                        board[moveArray.getTo().getY()][moveArray.getTo().getX() - 1] = board[moveArray.getTo().getY()][moveArray.getTo().getX() + 1];
                        board[moveArray.getTo().getY()][moveArray.getTo().getX() + 1] = null;
                    } else {
                        board[moveArray.getTo().getY()][moveArray.getTo().getX() + 1] = board[moveArray.getTo().getY()][moveArray.getTo().getX() - 1];
                        board[moveArray.getTo().getY()][moveArray.getTo().getX() - 1] = null;
                    }
                    king.castled = false;
                }
            }
        }
    }

    /**
     * Получаем из консоли данные по движению и переводим это в команду
     *
     * @param move
     * @return
     */
    public static Move parseInput(String move) {
        String[] split = move.split(" ");
        int xFrom = charToInt(Character.toLowerCase(split[0].charAt(0)));
        int yFrom = Integer.parseInt(move.charAt(1) + "") - 1;

        int xTo = charToInt(Character.toLowerCase(split[1].charAt(0)));
        int yTo = Integer.parseInt(split[1].charAt(1) + "") - 1;
        return new Move(xFrom, yFrom, xTo, yTo);
    }



    /**
     * Находим короля определенного цвета на доске
     *
     * @param color цвет короля, которого ищем
     * @return Местоположение короля
     */
    private Point getKingPos(PieceColor color) {
        int row = 0, col = 0;

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if (board[x][y] != null) {
                    if (board[x][y].getClass().isInstance(new King(PieceColor.WHITE)) && board[x][y].getColor().equals(color)) {
                        row = x;
                        col = y;
                    }
                }
            }
        }
        return new Point(row, col);
    }

    /**
     * Проверяет может ли какая-нибудь фигура сделать ход или у нас шах, пад, мат
     *
     * @return
     */
    public boolean canAnyPieceMakeAnyMove(PieceColor color) {

        ChessPiece[][] oldBoard = board.clone();

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                //Check this piece against every other piece...
                for (int w = 0; w < board.length; w++) {
                    for (int z = 0; z < board[0].length; z++) {
                        try {
                            if (board[x][y] != null) {
                                if (board[x][y].getColor().equals(color)) {
                                    //System.out.println(coordinatesToMoveString(x, y, w, z));
                                    move(coordinatesToMoveString(x, y, w, z), board[x][y].getColor(), false);
                                    board = oldBoard;
                                    return true;
                                }
                            }
                            board = oldBoard;
                        } catch (Exception e) {
                            board = oldBoard;
                        }
                    }
                }
            }
        }

        board = oldBoard;
        return false;
    }


    /**
     * Возращаем строку для расспечатывания доски
     *
     * @return
     */
    public String toString() {
        String string = "";
        int fileCount = 0;
        for (ChessPiece[] chessPieces : board) {
            int rankCount = 0;
            for (ChessPiece chessPiece : chessPieces) {
                if (chessPiece == null) {
                    string += "[  ]";
                } else {
                    string += "[" + chessPiece + "]";
                }
                string += " ";
                rankCount++;
            }
            fileCount++;
            string += "\n";
        }

        String reverseString = "";

        reverseString += "   A    B    C    D    E    F    G    H \n";
        String[] stringSplit = string.split("\n");
        for (int x = stringSplit.length - 1; x >= 0; x--) {
            reverseString += x + 1 + "|" + stringSplit[x] + "|\n";
        }

        return reverseString;
    }

}
