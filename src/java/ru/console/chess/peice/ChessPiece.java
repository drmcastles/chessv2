package ru.console.chess.peice;

public abstract class ChessPiece {
	
	public PieceColor color;
	public boolean hasMoved;
	public boolean ep_able;

	public abstract PieceColor getColor();

	public abstract String getSymbol();

	public abstract boolean canMoveToPosition(ChessPiece[][] board, int currentRow, int currentCol, int newRow, int newCol);

	@Override
	public String toString() {
		return getSymbol()+getColor().getShortName();
	}
}
