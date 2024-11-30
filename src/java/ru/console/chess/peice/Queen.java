package ru.console.chess.peice;

public class Queen extends ChessPiece {

	private PieceColor color;
	
	public Queen(PieceColor color){
		this.color = color;
	}

	@Override
	public boolean canMoveToPosition(ChessPiece[][] board, int currentRow, int currentCol, int newRow, int newCol) {
		//Может двигаться и как слон и как ладья
		return new Rook(color).canMoveToPosition(board, currentRow, currentCol, newRow, newCol) || new Bishop(color).canMoveToPosition(board, currentRow, currentCol, newRow, newCol);
	}
	
	public PieceColor getColor(){
		return this.color;
	}

	@Override
	public String getSymbol() {
		return "Q";
	}
}
