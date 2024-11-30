package ru.console.chess.peice;

/**
 * слон
 */
public class Bishop extends ChessPiece {

	public PieceColor color;
	
	public Bishop(PieceColor color){
		this.color = color;
	}
	
	@Override
	public boolean canMoveToPosition(ChessPiece[][] board, int currentRow, int currentCol, int newRow, int newCol) {
		
		if(currentRow == newRow || currentCol == newCol){
			// нет движения по диагонали
			return false;
		}
		
		if(Math.abs(newRow - currentRow) != Math.abs(newCol - currentCol)){
			return false;
		}
		
		int rowOffset, colOffset;
		
		if(currentRow < newRow){
			rowOffset = 1;
		}else{
			rowOffset = -1;
		}
		
		if(currentCol < newCol){
			colOffset = 1;
		}else{
			colOffset = -1;
		}
		
		int y = currentCol + colOffset;
		for(int x = currentRow + rowOffset; x != newRow; x += rowOffset){
			
			if(board[x][y] != null){
				return false;
			}
			
			y += colOffset;
		}
		
		return true;
		
	}
	
	public PieceColor getColor(){
		return this.color;
	}

	@Override
	public String getSymbol() {
		return "B";
	}

}
