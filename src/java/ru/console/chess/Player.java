package ru.console.chess;

import ru.console.chess.peice.PieceColor;

import java.util.Scanner;

public class Player {
    private final PieceColor color;

    public Player(PieceColor pieceColor) {
        this.color = pieceColor;
    }

    public PieceColor getColor() {
        return color;
    }

    public String makeMove() {
        Scanner sc = new Scanner(System.in);
        System.out.println(color + " make a move: ");
        return sc.nextLine();

    }

    @Override
    public String toString() {
        return color.toString();
    }
}
