package ru.console.chess.peice;

/**
 * Ладья
 */
public class Horse extends ChessPiece {

	private PieceColor color;
	
	public Horse(PieceColor color){
		this.color = color;
	}
	
	@Override
	public boolean canMoveToPosition(ChessPiece[][] board, int currentRow, int currentCol, int newRow, int newCol) {

		/**
		 * По вертикали смещение на 2 клетки вверх или вниз, по горизонтале на 1 вверх или вниз
		 */
		if(Math.abs(newRow - currentRow) == 2 && Math.abs(newCol - currentCol) == 1){
			return true;
		}
		
		if(Math.abs(newRow - currentRow) == 1 && Math.abs(newCol - currentCol) == 2){
			return true;
		}
		
		return false;
	}
	
	public PieceColor getColor(){
		return this.color;
	}

	@Override
	public String getSymbol() {
		return "H";
	}

}
