/**
 *  Lop luu lai trang thai cua ban co
 */
package model;

public class BoardState {
	public int[][] boardArr;
	public int width;
	public int height;
	public int count = 0;
	public Chessman lastMove = null;

	public BoardState(int width, int height) {
		boardArr = new int[width][height];
		this.height = height;
		this.width = width;
	}

	public void resetBoard() {
		count = 0;
		boardArr = new int[width][height];
	}

	/**
	public int checkEnd() {
		boolean player1, player2;
		boolean runOutOfChess = true;
		// Check hang ngang
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width - 4; c++) {
				if (boardArr[r][c] == 0) {
					runOutOfChess = false;
					continue;
				}
				player1 = true;
				player2 = true;
				for (int i = 0; i < 5; i++) {
					if (boardArr[r][c + i] != 1)
						player1 = false;
					if (boardArr[r][c + i] != 2)
						player2 = false;
					if (boardArr[r][c + i] == 0) {
						runOutOfChess = false;
						break;
					}
				}
				if (player1)
					return 1;
				if (player2)
					return 2;
			}
		}

		// Check hang doc
		for (int c = 0; c < width; c++) {
			for (int r = 0; r < height - 4; r++) {
				if (boardArr[r][c] == 0)
					continue;
				player1 = true;
				player2 = true;
				for (int i = 0; i < 5; i++) {
					if (boardArr[r + i][c] != 1)
						player1 = false;
					if (boardArr[r + i][c] != 2)
						player2 = false;
					if (boardArr[r + i][c] == 0)
						break;
				}
				if (player1)
					return 1;
				if (player2)
					return 2;
			}
		}

		// Check duong cheo xuong
		for (int r = 0; r < height - 4; r++) {
			for (int c = 0; c < width - 4; c++) {
				if (boardArr[r][c] == 0)
					continue;
				player1 = true;
				player2 = true;
				for (int i = 0; i < 5; i++) {
					if (boardArr[r + i][c + i] != 1)
						player1 = false;
					if (boardArr[r + i][c + i] != 2)
						player2 = false;
					if (boardArr[r + i][c + i] == 0)
						break;
				}
				if (player1)
					return 1;
				if (player2)
					return 2;
			}
		}

		// Check duong cheo len
		for (int r = height - 1; r > 3; r--) {
			for (int c = 0; c < width - 4; c++) {
				if (boardArr[r][c] == 0)
					continue;
				player1 = true;
				player2 = true;
				for (int i = 0; i < 5; i++) {
					if (boardArr[r - i][c + i] != 1)
						player1 = false;
					if (boardArr[r - i][c + i] != 2)
						player2 = false;
					if (boardArr[r - i][c + i] == 0)
						break;
				}
				if (player1)
					return 1;
				if (player2)
					return 2;
			}
		}
		return runOutOfChess ? -1 : 0;
	}
	*/
	
	// kiem tra 1 player nao da thang hay chua?
	public boolean checkEndForOnePlayer(IPlayer player, Chessman chessman) {
		boolean isWin;
		int row = chessman.x;
		int col = chessman.y;

		int rowStart = (row - 5) >= 0 ? row - 5 : 0;
		int rowEnd = (row + 5) < height ? row + 5 : height;
		int colStart = (col - 5) >= 0 ? col - 5 : 0;
		int colEnd = (col + 5) < width ? col + 5 : width;

		// kiem tra hang doc
		for (int r = rowStart; r < rowEnd - 4; r++) {
			for (int c = colStart; c < colEnd; c++) {
				if (boardArr[r][c] == 0) {
					continue;
				}
				isWin = true;
				for (int i = 0; i < 5; i++) {
					if (boardArr[r + i][c] != chessman.playerID) {
						isWin = false;
						break;
					}
				}
				if (isWin)
					return true;
			}
		}

		// kiem tra hang ngang
		for (int r = rowStart; r < rowEnd; r++) {
			for (int c = colStart; c < colEnd - 4; c++) {
				if (boardArr[r][c] == 0) {
					continue;
				}
				isWin = true;
				for (int i = 0; i < 5; i++) {
					if (boardArr[r][c + i] != chessman.playerID) {
						isWin = false;
						break;
					}
				}
				if (isWin)
					return true;
			}
		}

		// kiem tra hang cheo xuong
		for (int r = rowStart; r < rowEnd - 4; r++) {
			for (int c = colStart; c < colEnd - 4; c++) {
				if (boardArr[r][c] == 0) {
					continue;
				}
				isWin = true;
				for (int i = 0; i < 5; i++) {
					if (boardArr[r + i][c + i] != chessman.playerID) {
						isWin = false;
						break;
					}
				}
				if (isWin)
					return true;
			}
		}

		// kiem tra hang cheo len
		for (int r = rowEnd-1; r > rowStart + 3; r--) {
			for (int c = colStart; c < colEnd - 4; c++) {
				if (boardArr[r][c] == 0) {
					continue;
				}
				isWin = true;
				for (int i = 0; i < 5; i++) {
					if (boardArr[r - i][c + i] != chessman.playerID) {
						isWin = false;
						break;
					}
				}
				if (isWin)
					return true;
			}
		}
		return false;
	}

	// kiem tra het ban co
	public boolean isOver() {
		return count == width * height;
	}

	// set trang thai cho 1 quan co xac dinh
	public void setPosition(Chessman chessman) {
		boardArr[chessman.x][chessman.y] = chessman.playerID;
	}

	// Nuoc co hop le?
	public boolean isValid(Chessman chessman) {
		if (chessman.x >= width || chessman.y >= height) {
			return false;
		}
		return boardArr[chessman.x][chessman.y] == 0;
	}
}
