package ru.console.chess.peice;

public class Rook extends ChessPiece {

	private PieceColor color;
	
	public Rook(PieceColor color){
		this.color = color;
	}
	
	public boolean canMoveToPosition(ChessPiece[][] board, int currentRow, int currentCol, int newRow, int newCol) {
		if(currentRow != newRow && currentCol != newCol){
			return false;
		}

		int offset;
		
		if(currentRow != newRow){
			if(currentRow < newRow){
				offset = 1;
			}else{
				offset = -1;
			}
			
			for(int x = currentRow + offset; x != newRow; x += offset){
				if(board[x][currentCol] != null){
					return false;
				}
			}
		}

		if(currentCol != newCol){
			if(currentCol < newCol){
				offset = 1;
			}else{
				offset = -1;
			}
			
			for(int x = currentCol + offset; x != newCol; x += offset){
				if(board[currentRow][x] != null){
					return false;
				}
			}
		}
		return true;
	}
	
	public PieceColor getColor(){
		return this.color;
	}

	@Override
	public String getSymbol() {
		return "R";
	}
}
