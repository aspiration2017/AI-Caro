package model;

public class EvaluateBoard {

	int[] attactScore = {};
	int[] defendScore = {};
	
	
	public static int[][] evaluate(){
		
		return null;
	}
	
	public static int check4Boxes(int[][] boardState, int idPlayer, int x, int y) {
		int result = 0;
		int count;
		int rowStart = (x - 5) > 0 ? x - 5 : 0;
		int rowEnd = (x + 5) < boardState.length ? x + 5 : boardState.length;
		int colStart = y - 5 > 0 ? y - 5 : 0;
		int colEnd = y + 5 < boardState[0].length ? y + 5 : boardState[0].length;
		
		// kiem tra hang ngang
		for (int i = colStart; i <= colEnd - 5; i++) {
			count = 0;
			for (int j = 0; j < 5; j++) {
				if (boardState[x][i+j] == idPlayer)
					count++;
			}
			if (count == 4)
				result++;
		}
		
		// kiem tra hang doc
		for (int i = rowStart; i <= rowEnd - 5; i++) {
			count = 0;
			for (int j = 0; j < 5; j++) {
				if (boardState[i+j][y] == idPlayer)
					count++;
			}
			if (count == 4)
				result++;
		}
		
		// kiem tra hang cheo len
		for (int r = x + y, c = colStart; r > 3 && r < rowEnd ; r--, c++) {
			count = 0;
			for (int j = 0; j < 5; j++) {
				if (boardState[r-j][c+j] == idPlayer)
					count++;
			}
			if (count == 4)
				result++;
		}
		
		// kiem tra hang cheo xuong
		for (int r = x - y, c = colStart; r >= 0 && r < rowEnd - 4; r++, c++) {
			count = 0;
			for (int j = 0; j < 5; j++) {
				if (boardState[r+j][c+j] == idPlayer)
					count++;
			}
			if (count == 4)
				result++;
		}
		return result;
	}
	
	public static Chessman getMax(int[][] scoreBoard) {
		Chessman result = null;
		int max = -1;
		for (int i = 0; i < scoreBoard.length; i++) {
			for (int j = 0; j < scoreBoard[i].length; j++) {
				if (scoreBoard[i][j] > max) {
					max = scoreBoard[i][j];
					result = new Chessman(i, j);
				}
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		int[][] boardState = {{1,1,0,1,1},
							  {1,1,1,0,0},
							  {1,0,0,1,0},
							  {1,1,1,0,1},
							  {0,1,1,1,1}};
		System.out.println(check4Boxes(boardState, 1, 1, 2));
	}
	
}
