package ru.console.chess;

import ru.console.chess.peice.ChessPiece;
import ru.console.chess.peice.PieceColor;

import java.io.IOException;

public class ChessBoard {

    static Board gameBoard = new Board();
    private static final Player whitePlayer = new Player(PieceColor.WHITE);
    private static final Player blackPlayer = new Player(PieceColor.BLACK);
    private static Player nowPlayer = whitePlayer;

    public static void main(String[] args) {

        boolean drawAvailable = false;

        while (true) {

            printBoard();
            String move = nowPlayer.makeMove();
            if (checkDrawCommand(drawAvailable, move)) {
                return;
            }
            drawAvailable = false;

            if (checkResignCommand(move)) return;

            try {
                gameBoard.move(move, nowPlayer.getColor(), true);
            } catch (IOException e) {
                // повторить ввод
                System.out.println("Invalid input! " + e.getMessage());
                continue;
            }

            ChessPiece[][] oldBoard = gameBoard.board.clone();

            if (checkMate()) return;

            gameBoard.board = oldBoard;

            if (gameBoard.isInCheck(playerToggle(nowPlayer).getColor())) {
                System.out.println(playerToggle(nowPlayer) + " is in check.");
            }

            if (move.contains("draw?")) {
                drawAvailable = true;
            }




            nowPlayer = playerToggle(nowPlayer);

        }

    }

    private static boolean checkMate() {
        if (!gameBoard.canAnyPieceMakeAnyMove(playerToggle(nowPlayer).getColor())) {
            if (gameBoard.isInCheck(playerToggle(nowPlayer).getColor())) {
                System.out.println("Checkmate. " + nowPlayer + " wins");
                System.out.println("Game over!");
            } else {
                System.out.println("Stalemate!");
            }
            return true;
        }
        return false;
    }

    private static boolean checkResignCommand(String move) {
        if (move.contains("resign")) {
            System.out.println(nowPlayer + " resigns");
            System.out.println(playerToggle(nowPlayer) + " wins the game!");
            return true;
        }
        return false;
    }

    private static boolean checkDrawCommand(boolean drawAvailable, String move) {
        if (drawAvailable && move.contains("draw")) {
            System.out.println("The game is a draw.");
            return true;
        }
        return false;
    }

    private static void printBoard() {
        System.out.println(gameBoard);
    }

    public static Player playerToggle(Player player) {
        if (player.getColor().equals(PieceColor.WHITE)) {
            return blackPlayer;
        }
        return whitePlayer;
    }

}
