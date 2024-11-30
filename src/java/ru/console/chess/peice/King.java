package ru.console.chess.peice;

/**
 * Король
 */
public class King extends ChessPiece {
	
	private PieceColor color;
	public boolean hasMoved;
	public boolean castled;
	
	public King(PieceColor color){
		this.color = color;
		this.hasMoved = false;
		this.castled = false;
	}

	@Override
	public boolean canMoveToPosition(ChessPiece[][] board, int currentRow, int currentCol, int newRow, int newCol) {
		
		if(Math.abs(newRow - currentRow) > 1 || Math.abs(newCol - currentCol) > 1){
			
			if(hasMoved){
				return false;
			}
			
			//ракировка
			if(newCol - currentCol == 2 && currentRow == newRow){
				if(board[newRow][currentCol + 1] != null || board[newRow][currentCol + 2] != null){
					castled = false;
					return false;
				}
				
			}else if(currentCol - newCol == 3 && currentRow == newRow){
				if(board[newRow][currentCol - 1] != null || board[newRow][currentCol - 2] != null || board[newRow][currentCol - 3] != null){
					castled = false;
					return false;
				}
				
			}else{
				castled = false;
				return false;
			}
			
			castled = true;
			
		}

		return true;
	}
	
	public PieceColor getColor(){
		return this.color;
	}

	@Override
	public String getSymbol() {
		return "K";
	}
}
