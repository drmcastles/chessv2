package ru.console.chess.peice;

public enum PieceColor {
    WHITE("W"),
    BLACK("B");
    private final String shortName;

    PieceColor(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }
}
