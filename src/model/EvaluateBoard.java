package model;

import java.util.Arrays;

public class EvaluateBoard {

	public static int[][] evaluate(int[][] boardState, int idPlayer1, int idPlayer2) {
		int[][] scoreBoard = new int[boardState.length][boardState[0].length];
		int count5B; // 5 boxes
		int count4BF2H; // 4 boxes free 2 head
		int count4B3CF2H; // 4 boxes 3 check free 2 head
		int count3BF2H; // 3 boxes 3 check free 2 head
		int count4B; // 4 boxes 4 check
		int count5B4C; // 5 boxes 4 check
		int count3B; // 3 boxes
		int count2B; // 2 boxes
		for (int i = 0; i < scoreBoard.length; i++) {
			for (int j = 0; j < scoreBoard[i].length; j++) {
				if (boardState[i][j] != 0)
					continue;
				// attack
				count5B = check5Boxes(boardState, idPlayer1, i, j);
				count4BF2H = check4BoxexFree2Head(boardState, idPlayer1, i, j);
				count4B3CF2H = check4Boxes3CheckFree2Head(boardState, idPlayer1, i, j);
				count3BF2H = check3Boxes3CheckFree2Head(boardState, idPlayer1, i, j);
				count4B = check4Boxes4Check(boardState, idPlayer1, i, j);
				count5B4C = check5Boxes4Check(boardState, idPlayer1, i, j);
				count3B = check3Boxes(boardState, idPlayer1, i, j);
				count2B = check2Boxes(boardState, idPlayer1, i, j);
				if (count5B > 0) { // win
					scoreBoard[i][j] += 999999;
					break;
				}
				if (count4BF2H > 0)
					scoreBoard[i][j] += 500000;
				if ((count4B > 0 && count4B3CF2H > 0) || (count4B > 0 && count3BF2H > 0) 
						|| (count4B + count5B4C > 1) || (count5B4C > 0 && count3BF2H > 0) || (count5B4C > 0 && count4B3CF2H > 0)) 
					scoreBoard[i][j] += 100000;
				if (count3BF2H + count4B3CF2H > 1)
					scoreBoard[i][j] += 30000;
				if (count4B > 0)
					scoreBoard[i][j] += 40000;
				if (count5B4C > 0)
					scoreBoard[i][j] += 39091;
				if (count3BF2H > 0)
					scoreBoard[i][j] += 2500;
				if (count4B3CF2H > 0)
					scoreBoard[i][j] += 1501;
				if (count3B > 0) 
					scoreBoard[i][j] += 50*count3B;
				if (count2B > 0)
					scoreBoard[i][j] += 40*count2B;
				
				// defend
				count5B = check5Boxes(boardState, idPlayer2, i, j);
				count4BF2H = check4BoxexFree2Head(boardState, idPlayer2, i, j);
				count4B3CF2H = check4Boxes3CheckFree2Head(boardState, idPlayer2, i, j);
				count3BF2H = check3Boxes3CheckFree2Head(boardState, idPlayer2, i, j);
				count4B = check4Boxes4Check(boardState, idPlayer2, i, j);
				count5B4C = check5Boxes4Check(boardState, idPlayer2, i, j);
				count3B = check3Boxes(boardState, idPlayer2, i, j);
				count2B = check2Boxes(boardState, idPlayer2, i, j);
				if (count5B > 0) // win
					scoreBoard[i][j] += 900000;
				if ((count4B > 0 && count4B3CF2H > 0) || (count4B > 0 && count3BF2H > 0) 
						|| (count4B + count5B4C > 1) || (count5B4C > 0 && count3BF2H > 0) || (count5B4C > 0 && count4B3CF2H > 0)) 
					scoreBoard[i][j] += 90000;
				if (count3BF2H + count4B3CF2H > 1)
					scoreBoard[i][j] += 8000;
				if (count4BF2H > 0)
					scoreBoard[i][j] += 38000;
				if (count4B > 0)
					scoreBoard[i][j] += 10;
				if (count5B4C > 0)
					scoreBoard[i][j] += 10;
				if (count3BF2H > 0)
					scoreBoard[i][j] += 10;
				if (count4B3CF2H > 0)
					scoreBoard[i][j] += 10;
				if (count3B > 0) 
					scoreBoard[i][j] += 10;
				if (count2B > 0)
					scoreBoard[i][j] += 10;
			}
		}
		return scoreBoard;
	}

	public static int check5Boxes(int[][] boardState, int idPlayer, int x, int y) {
		int[][] board = copy2DArray(boardState);
		board[x][y] = idPlayer;
		int result = 0;
		int count;
		int rowStart = (x - 4) > 0 ? x - 4 : 0;
		int rowEnd = (x + 4) < board.length ? x + 4 : board.length-1;
		int colStart = y - 4 > 0 ? y - 4 : 0;
		int colEnd = y + 4 < board[0].length ? y + 4 : board[0].length-1;

		// kiem tra hang ngang
		for (int i = colStart; i <= colEnd - 4; i++) {
			count = 0;
			for (int j = 0; j < 5; j++) {
				if (board[x][i + j] == idPlayer)
					count++;
			}
			if (count == 5)
				result++;
		}

		// kiem tra hang doc
		for (int i = rowStart; i <= rowEnd - 4; i++) {
			count = 0;
			for (int j = 0; j < 5; j++) {
				if (board[i + j][y] == idPlayer)
					count++;
			}
			if (count == 5)
				result++;
		}

		// kiem tra hang cheo len
		for (int r = x + y <= rowEnd ? x + y : rowEnd, c = colStart; r >= rowStart + 4 && c <= colEnd - 4; r--, c++) {
			count = 0;
			for (int j = 0; j < 5; j++) {
				if (board[r - j][c + j] == idPlayer)
					count++;
			}
			if (count == 5)
				result++;
		}

		// kiem tra hang cheo xuong
		for (int r = x - y >= rowStart ? x - y : rowStart, c = y - x >= colStart ? y - x : colStart; r <= rowEnd - 4 && c <= colEnd - 4; r++, c++) {
			count = 0;
			for (int j = 0; j < 5; j++) {
				if (board[r + j][c + j] == idPlayer)
					count++;
			}
			if (count == 5)
				result++;
		}
		return result;
	}

	public static int check4BoxexFree2Head(int[][] boardState, int idPlayer, int x, int y) {
		if ( x == boardState.length-1 || x == 0 || y == 0 || y == boardState[0].length-1) // danh ngay goc
			return 0;

		int[][] board = copy2DArray(boardState);
		board[x][y] = idPlayer;
		int result = 0;
		int count;
		int rowStart = (x - 4) > 0 ? x - 4 : 0;
		int rowEnd = (x + 4) < board.length ? x + 4 : board.length-1;
		int colStart = y - 4 > 0 ? y - 4 : 0;
		int colEnd = y + 4 < board[0].length ? y + 4 : board[0].length-1;

		// hang ngang
		for (int i = colStart+1; i <= colEnd - 4; i++) {
			count = 0;
			for (int j = 0; j < 4; j++) {
				if (board[x][i + j] == idPlayer)
					count++;
			}
			if (count == 4) {
				try {
					if (board[x][i-1] == 0 && board[x][i+4] == 0)
						result++;
				} catch (Exception e) {
				}
			}
		}

		// hang doc
		for (int i = rowStart+1; i <= rowEnd - 4; i++) {
			count = 0;
			for (int j = 0; j < 4; j++) {
				if (board[i + j][y] == idPlayer)
					count++;
			}
			if (count == 4) {
				try {
					if (board[i-1][y] == 0 && board[i+4][y] == 0)
						result++;
				} catch (Exception e) {
				}
			}
		}
		
		// hang cheo len
		int r = -1, c = -1;
		if ( x + 3 <= rowEnd && y - 3 >= 0) {
			r = x + 3; c = y -3;
		}
		if ( x + 3 > rowEnd && y - 3 < 0) {
			r = x + y <= rowEnd ? x + y : rowEnd;
			c = x + y - rowEnd < 0 ? 0 : x + y - rowEnd;
		}
		if ( x + 3 <= rowEnd && y - 3 < 0) {
			r = x + y; c = 0;
		}
		if ( x + 3 > rowEnd && y - 3 >= 0) {
			r = rowEnd; c = x + y - rowEnd;
		}
		for (; r >= rowStart + 4 && c <= colEnd - 4; r--, c++) {
			count = 0;
			for (int j = 0; j < 4; j++) {
				if (board[r - j][c + j] == idPlayer)
					count++;
			}
			if (count == 4) {
				try {
					if (board[r+1][c-1] == 0 && board[r-4][c+4] == 0)
						result++;
				} catch (Exception e) {
				}
			}
		}
		
		// hang cheo xuong
		if ( x - 3 >= 0 && y - 3 >= 0) {
			r = x - 3; c = y - 3;
		}
		if ( x - 3 < 0 && y - 3 < 0) {
			r = x > y ? x - y : 0; c = x > y ? 0 : y - x;
		}
		if ( x - 3 >= 0 && y - 3 < 0) {
			r = x - y; c = 0;
		}
		if ( x - 3 < 0 && y - 3 >= 0) {
			r = 0; c = y -x;
		}
		for (; r <= rowEnd - 4 && c <= colEnd - 4; r++, c++) {
			count = 0;
			for (int j = 0; j < 4; j++) {
				if (board[r + j][c + j] == idPlayer)
					count++;
			}
			if (count == 4) {
				try {
					if (board[r-1][c-1] == 0 && board[r+4][c+4] == 0)
						result++;
				} catch (Exception e) {
				}
			}
		}

		return result;
	}

	public static int check4Boxes4Check(int[][] boardState, int idPlayer, int x, int y) {
		int[][] board = copy2DArray(boardState);
		board[x][y] = idPlayer;
		int result = 0;
		int count;
		int rowStart = (x - 3) > 0 ? x - 3 : 0;
		int rowEnd = (x + 3) < board.length ? x + 3 : board.length-1;
		int colStart = y - 3 > 0 ? y - 3 : 0;
		int colEnd = y + 3 < board[0].length ? y + 3 : board[0].length-1;
		// kiem tra hang ngang
		for (int i = colStart; i <= colEnd - 3; i++) {
			count = 0;
			for (int j = 0; j < 4; j++) {
				if (board[x][i + j] == idPlayer)
					count++;
				else if (board[x][i + j] != 0) {
					count = 0;
					break;
				}
				if (count == 4) {
					try {
						if (board[x][i-1] == 0) {
							result++;
							break;
						} else if (board[x][i+4] == 0) {
							result++;
							break;
						}
					} catch (Exception e) {
					}
				}
			}
		}

		// kiem tra hang doc
		for (int i = rowStart; i <= rowEnd - 3; i++) {
			count = 0;
			for (int j = 0; j < 4; j++) {
				if (board[i + j][y] == idPlayer)
					count++;
				else if (board[i + j][y] != 0) {
					count = 0;
					break;
				}
				if (count == 4) {
					try {
						if (board[i-1][y] == 0) {
							result++;
							break;
						} else if (board[i+4][y] == 0) {
							result++;
							break;
						}
					} catch (Exception e) {
					}	
				}
			}
		}

		// kiem tra hang cheo len
		for (int r = x + y <= rowEnd ? x + y : rowEnd, c = colStart; r >= rowStart + 3 && c <= colEnd - 3; r--, c++) {
			count = 0;
			for (int j = 0; j < 4; j++) {
				if (board[r - j][c + j] == idPlayer)
					count++;
				else if (board[r - j][c + j] != 0) {
					count = 0;
					break;
				}
				if (count == 4) {
					try {
						if (board[r+1][c-1] == 0) {
							result++;
							break;
						} else if (board[r-4][c+4] == 0) {
							result++;
							break;
						}
					} catch (Exception e) {
					}
				}
			}
		}

		// kiem tra hang cheo xuong
		for (int r = x - y >= rowStart ? x - y : rowStart, c = y - x >= colStart ? y - x : colStart; r <= rowEnd - 3 && c <= colEnd - 3; r++, c++) {
			count = 0;
			for (int j = 0; j < 4; j++) {
				if (board[r + j][c + j] == idPlayer)
					count++;
				else if (board[r + j][c + j] != 0) {
					count = 0;
					break;
				}
				if (count == 4) {
					try {
						if (board[r-1][c-1] == 0) {
							result++;
							break;
						} else if (board[r+4][c+4] == 0) {
							result++;
							break;
						}
					} catch (Exception e) {
					}
				}
			}
		}
		return result;
	}
	
	public static int check5Boxes4Check(int[][] boardState, int idPlayer, int x, int y) {
		int[][] board = copy2DArray(boardState);
		board[x][y] = idPlayer;
		int result = 0;
		int count;
		int rowStart = (x - 4) > 0 ? x - 4 : 0;
		int rowEnd = (x + 4) < board.length ? x + 4 : board.length-1;
		int colStart = y - 4 > 0 ? y - 4 : 0;
		int colEnd = y + 4 < board[0].length ? y + 4 : board[0].length-1;
		// kiem tra hang ngang
		for (int i = colStart; i <= colEnd - 4; i++) {
			count = 0;
			for (int j = 0; j < 5; j++) {
				if (board[x][i + j] == idPlayer)
					count++;
				else if (board[x][i + j] != 0) {
					count = 0;
					break;
				}
				if (count == 4 && board[x][i + 4] == idPlayer && board[x][i] == idPlayer)
						result++;
			}
		}

		// kiem tra hang doc
		for (int i = rowStart; i <= rowEnd - 4; i++) {
			count = 0;
			for (int j = 0; j < 5; j++) {
				if (board[i + j][y] == idPlayer)
					count++;
				else if (board[i + j][y] != 0) {
					count = 0;
					break;
				}
				if (count == 4 && board[i + 4][y] == idPlayer && board[i][y] == idPlayer)
						result++;
			}
		}

		// kiem tra hang cheo len
		for (int r = x + y <= rowEnd ? x + y : rowEnd, c = colStart; r >= rowStart + 4 && c <= colEnd - 4; r--, c++) {
			count = 0;
			for (int j = 0; j < 5; j++) {
				if (board[r - j][c + j] == idPlayer)
					count++;
				else if (board[r - j][c + j] != 0) {
					count = 0;
					break;
				}
				if (count == 4 && board[r - 4][c + 4] == idPlayer && board[r][c] == idPlayer)
						result++;
			}
		}

		// kiem tra hang cheo xuong
		for (int r = x - y >= rowStart ? x - y : rowStart, c = y - x >= colStart ? y - x : colStart; r <= rowEnd - 4 && c <= colEnd - 4; r++, c++) {
			count = 0;
			for (int j = 0; j < 5; j++) {
				if (board[r + j][c + j] == idPlayer)
					count++;
				else if (board[r + j][c + j] != 0) {
					count = 0;
					break;
				}
				if (count == 4 && board[r + 4][c + 4] == idPlayer && board[r][c] == idPlayer)
						result++;
			}
		}
		return result;
	}
	
	public static int check3Boxes3CheckFree2Head(int[][] boardState, int idPlayer, int x, int y) {
		if ( x == boardState.length-1 || x == 0 || y == 0 || y == boardState[0].length-1) // danh ngay goc
			return 0;
		
		int[][] board = copy2DArray(boardState);
		board[x][y] = idPlayer;
		int result = 0;
		int count;
		int rowStart = (x - 2) > 0 ? x - 2 : 0;
		int rowEnd = (x + 2) < board.length ? x + 2 : board.length-1;
		int colStart = y - 2 > 0 ? y - 2 : 0;
		int colEnd = y + 2 < board[0].length ? y + 2 : board[0].length-1;
		// hang ngang
		int first, last;
		for (int i = colStart; i <= colEnd - 2; i++) {
			count = 0;
			first = 0; last = 0;
			for (int j = 0; j < 3; j++) {
				if (board[x][i + j] == idPlayer)
					count++;
				else if (board[x][i + j] != 0) {
					last = 0;
					break;
				} else
					continue;
				if (count == 1) {
					first = i + j;
				}
				if (count == 3) {
					last = i + j;
				}
			}
			if (count == 3) {
				try {
					if (board[x][first-1] == 0 && board[x][last+1] == 0 && board[x][first-2] == 0)
						result++;
					else if (board[x][first-1] == 0 && board[x][last+1] == 0 && board[x][last+2] == 0)
						result++;
				} catch (Exception e) {
				}
			break;
			}
		}
		// hang doc
		for (int i = rowStart; i <= rowEnd - 2; i++) {
			count = 0;
			first = 0; last = 0;
			for (int j = 0; j < 3; j++) {
				if (board[i + j][y] == idPlayer)
					count++;
				else if (board[i + j][y] != 0) {
					last = 0;
					break;
				} else
					continue;
				if (count == 1) {
					first = i + j;
				}
				if (count == 3) {
					last = i + j;
				}
			}
			if (count == 3) {
				try {
					if (board[first-1][y] == 0 && board[last+1][y] == 0 && board[first-2][y] == 0)
						result++;
					else if (board[first-1][y] == 0 && board[last+1][y] == 0 && board[last+2][y] == 0)
						result++;	
				} catch (Exception e) {
				}
				break;
			}
		}
		// hang cheo len
		int r = -1, c = -1;
		if ( x + 2 < board.length && y - 2 >= 0) {
			r = x + 2; c = y - 2;
		}
		if ( x + 2 >= board.length && y - 2 < 0) {
			r = x + y < board.length ? x + y : rowEnd;
			c = x + y - rowEnd < 0 ? 0 : x + y - rowEnd;
		}
		if ( x + 2 < board.length && y - 2 < 0) {
			r = x + y; c = 0;
		}
		if ( x + 2 >= board.length && y - 2 >= 0) {
			r = rowEnd; c = x + y - rowEnd;
		}
		for (; r >= rowStart + 2 && c <= colEnd - 2; r--, c++) {
			count = 0;
			first = 0; last = 0;
			for (int j = 0; j < 3; j++) {
				if (board[r - j][c + j] == idPlayer)
					count++;
				else if (board[r - j][c + j] != 0) {
					last = 0;
					break;
				} else
					continue;
				if (count == 1) {
					first = c + j;
				}
				if (count == 3) {
					last = c + j;
				}
			}
			if (count == 3) {
				try {
					if (board[r+1][c-1] == 0 && board[r+2][c-2] == 0 && board[r-3][c+3] == 0)
						result++;
					else if (board[r-3][c+3] == 0 && board[r-4][c+4] == 0 && board[r+1][c-1] == 0)
						result++;
				} catch (Exception e) {
				}
				break;
			}
		}
		
		// hang cheo xuong
		if ( x - 2 >= 0 && y - 2 >= 0) {
			r = x - 2; c = y - 2;
		}
		if ( x - 2 < 0 && y - 2 < 0) {
			r = x > y ? x - y : 0; c = x > y ? 0 : y - x;
		}
		if ( x - 2 >= 0 && y - 2 < 0) {
			r = x - y; c = 0;
		}
		if ( x - 2 < 0 && y - 2 >= 0) {
			r = 0; c = y -x;
		}
		for (; r <= rowEnd - 2 && c <= colEnd - 2; r++, c++) {
			count = 0;
			first = 0; last = 0;
			for (int j = 0; j < 3; j++) {
				if (board[r + j][c + j] == idPlayer)
					count++;
				else if (board[r + j][c + j] != 0) {
					last = 0;
					break;
				} else
					continue;
				if (count == 1) {
					first = c + j;
				}
				if (count == 3) {
					last = c + j;
				}
			}
			if (count == 3) {
				try {
					if (board[r-1][c-1] == 0 && board[r-2][c-2] == 0 && board[r+3][c+3] == 0)
						result++;
					else if (board[r+3][c+3] == 0 && board[r+4][c+4] == 0 && board[r-1][c-1] == 0)
						result++;
				} catch (Exception e) {
				}
				break;
			}
		}

		return result;
	}

	public static int check4Boxes3CheckFree2Head(int[][] boardState, int idPlayer, int x, int y) {
		if ( x == boardState.length-1 || x == 0 || y == 0 || y == boardState[0].length-1) // danh ngay goc
			return 0;
		
		int[][] board = copy2DArray(boardState);
		board[x][y] = idPlayer;
		int result = 0;
		int count;
		int rowStart = (x - 3) > 0 ? x - 3 : 0;
		int rowEnd = (x + 3) < board.length ? x + 3 : board.length-1;
		int colStart = y - 3 > 0 ? y - 3 : 0;
		int colEnd = y + 3 < board[0].length ? y + 3 : board[0].length-1;
		// hang ngang
		for (int i = colStart; i <= colEnd - 3; i++) {
			count = 0;
			for (int j = 0; j < 4; j++) {
				if (board[x][i + j] == idPlayer)
					count++;
				else if (board[x][i + j] != 0)
					break;
			}
			if (count == 3 && board[x][i + 3] == idPlayer && board[x][i] == idPlayer) {
				try {
					if (board[x][i-1] == 0 && board[x][i+4] == 0)
						result++;
				} catch (Exception e) {
				}
				break;
			}
		}
		// hang doc
		for (int i = rowStart; i <= rowEnd - 3; i++) {
			count = 0;
			for (int j = 0; j < 4; j++) {
				if (board[i + j][y] == idPlayer)
					count++;
				else if (board[i + j][y] != 0) 
					break;
			}
			if (count == 3 && board[i+3][y] == idPlayer && board[i][y] == idPlayer) {
				try {
					if (board[i-1][y] == 0 && board[i+4][y] == 0)
						result++;
				} catch (Exception e) {
				}
				break;
			}
		}
		// hang cheo len
		int r = -1, c = -1;
		if ( x + 3 < board.length && y - 3 >= 0) {
			r = x + 3; c = y - 3;
		}
		if ( x + 3 >= board.length && y - 3 < 0) {
			r = x + y < board.length ? x + y : rowEnd;
			c = x + y - rowEnd < 0 ? 0 : x + y - rowEnd;
		}
		if ( x + 3 < board.length && y - 3 < 0) {
			r = x + y; c = 0;
		}
		if ( x + 3 >= board.length && y - 3 >= 0) {
			r = rowEnd; c = x + y - rowEnd;
		}
		for (; r >= rowStart + 3 && c <= colEnd - 3; r--, c++) {
			count = 0;
			for (int j = 0; j < 4; j++) {
				if (board[r - j][c + j] == idPlayer)
					count++;
				else if (board[r - j][c + j] != 0) 
					break;
			}
			if (count == 3 && board[r][c] == idPlayer && board[r-3][c+3] == idPlayer) {
				try {
					if (board[r-4][c+4] == 0 && board[r + 1][c-1] == 0)
						result++;
				} catch (Exception e) {
				}
				break;
			}
		}
		
		// hang cheo xuong
		if ( x - 3 >= 0 && y - 3 >= 0) {
			r = x - 3; c = y - 3;
		}
		if ( x - 3 < 0 && y - 3 < 0) {
			r = x > y ? x - y : 0; c = x > y ? 0 : y - x;
		}
		if ( x - 3 >= 0 && y - 3 < 0) {
			r = x - y; c = 0;
		}
		if ( x - 3 < 0 && y - 3 >= 0) {
			r = 0; c = y -x;
		}
		for (; r <= rowEnd - 3 && c <= colEnd - 3; r++, c++) {
			count = 0;
			for (int j = 0; j < 4; j++) {
				if (board[r + j][c + j] == idPlayer)
					count++;
				else if (board[r + j][c + j] != 0) 
					break;
			}
			if (count == 3 && board[r][c] == idPlayer && board[r+3][c+3] == idPlayer) {
				try {
					if (board[r-1][c-1] == 0 && board[r+4][c+4] == 0)
						result++;
				} catch (Exception e) {
				}
				break;
			}
		}

		return result;
	}
	
	public static int check3Boxes(int[][] boardState, int idPlayer, int x, int y) {
		int[][] board = copy2DArray(boardState);
		board[x][y] = idPlayer;
		int result = 0;
		int count;
		int rowStart = (x - 2) > 0 ? x - 2 : 0;
		int rowEnd = (x + 2) < board.length ? x + 2 : board.length-1;
		int colStart = y - 2 > 0 ? y - 2 : 0;
		int colEnd = y + 2 < board[0].length ? y + 2 : board[0].length-1;

		// kiem tra hang ngang
		for (int i = colStart; i <= colEnd - 2; i++) {
			count = 0; 
			for (int j = 0; j < 3; j++) {
				if (board[x][i + j] == idPlayer)
					count++;
				else if (board[x][i + j] != 0)  
					break;
				 
			}
			if (count == 3) {
				try {
					if ((board[x][i-1] == 0 && board[x][i-2]==0) || (board[x][i+3] == 0 && board[x][i+4] == 0)) 
						result++;
				} catch (Exception e) {
				}
				break;
			}
		}

		// kiem tra hang doc
		for (int i = rowStart; i <= rowEnd - 2; i++) {
			count = 0; 
			for (int j = 0; j < 3; j++) {
				if (board[i + j][y] == idPlayer)
					count++;
				else if (board[i + j][y] != 0) 
					break;
			}
			if (count == 3) {
				try {
					if ((board[i-1][y] == 0 && board[i-2][y]==0) || (board[i+3][y] == 0 && board[i+4][y] == 0)) 
						result++;
				} catch (Exception e) {
				}
				break;
			}
		}

		// kiem tra hang cheo len
		for (int r = x + y <= rowEnd ? x + y : rowEnd, c = colStart; r >= rowStart + 2 && c <= colEnd - 2; r--, c++) {
			count = 0; 
			for (int j = 0; j < 3; j++) {
				if (board[r - j][c + j] == idPlayer)
					count++;
				else if (board[r - j][c + j] != 0)  
					break;
			}
			if (count == 3) {
				try {
					if ((board[r +1 ][c - 1] == 0 && board[r + 2][c - 2]==0) || (board[r - 3][c + 3] == 0 && board[r - 4][c + 4] == 0)) 
						result++;
				} catch (Exception e) {
				}
				break;
			}
		}

		// kiem tra hang cheo xuong
		for (int r = x - y >= rowStart ? x - y : rowStart, c = y - x >= colStart ? y - x : colStart; r <= rowEnd - 2 && c <= colEnd - 2; r++, c++) {
			count = 0; 
			for (int j = 0; j < 3; j++) {
				if (board[r + j][c + j] == idPlayer)
					count++;
				else if (board[r + j][c + j] != 0) 
					break;
			}
			if (count == 3) {
				try {
					if ((board[r - 1][c - 1] == 0 && board[r - 2][c - 2]==0) || (board[r + 3][c + 3] == 0 && board[r + 4][c + 4] == 0)) 
						result++;
				} catch (Exception e) {
				}
				break;
			}
		}
		return result;
	}
	
	public static int check2Boxes(int[][] boardState, int idPlayer, int x, int y) {
		if ( x == boardState.length || x == 0 || y == 0 || y == boardState[0].length) // danh ngay goc
			return 0;
		
		int[][] board = copy2DArray(boardState);
		board[x][y] = idPlayer;
		int result = 0;
		int count;
		int rowStart = (x - 1) > 0 ? x - 1 : 0;
		int rowEnd = (x +1) < board.length ? x + 1 : board.length-1;
		int colStart = y - 1 > 0 ? y - 1 : 0;
		int colEnd = y + 1 < board[0].length ? y + 1 : board[0].length-1;

		// kiem tra hang ngang
		for (int i = colStart; i <= colEnd - 1; i++) {
			count = 0;
			for (int j = 0; j < 2; j++) {
				if (board[x][i + j] == idPlayer)
					count++;
				else if (board[x][i + j] != 0) 
					break;
			}
			if (count == 2) {
				try {
					if (board[x][i-1] == 0 && board[x][i+2] == 0)
						result++;
				} catch (Exception e) {
				}
				break;
			}
		}

		// kiem tra hang doc
		for (int i = rowStart; i <= rowEnd - 1; i++) {
			count = 0;
			for (int j = 0; j < 2; j++) {
				if (board[i + j][y] == idPlayer)
					count++;
				else if (board[i + j][y] != 0)
					break;
			}
			if (count == 2) {
				try {
					if (board[i-1][y] == 0 && board[i+2][y] == 0)
						result++;
				} catch (Exception e) {
				}
				break;
			}
		}

		// kiem tra hang cheo len
		int r = -1, c = -1;
		if ( x + 1 <= rowEnd && y - 1 >= 0) {
			r = x + 1; c = y -1;
		}
		if ( x + 1 > rowEnd && y - 1 < 0) {
			r = x + y <= rowEnd ? x + y : rowEnd;
			c = x + y - rowEnd < 0 ? 0 : x + y - rowEnd;
		}
		if ( x + 1 <= rowEnd && y - 1 < 0) {
			r = x + y; c = 0;
		}
		if ( x + 1 > rowEnd && y - 1 >= 0) {
			r = rowEnd; c = x + y - rowEnd;
		}
		for (; r >= rowStart + 1 && c <= colEnd - 1; r--, c++) {
			count = 0; 
			for (int j = 0; j < 2; j++) {
				if (board[r - j][c + j] == idPlayer)
					count++;
				else if (board[r - j][c + j] != 0) 
					break;
			}
			if (count == 2) {
				try {
					if (board[r+1][c-1] == 0 && board[r-2][c+2] == 0)
						result++;
				} catch (Exception e) {
				}
				break;
			}
		}

		// kiem tra hang cheo xuong
		if ( x - 1 >= 0 && y - 1 >= 0) {
			r = x - 1; c = y - 1;
		}
		if ( x - 1 < 0 && y - 1 < 0) {
			r = x > y ? x - y : 0; c = x > y ? 0 : y - x;
		}
		if ( x - 3 >= 1 && y - 1 < 0) {
			r = x - y; c = 0;
		}
		if ( x - 1 < 0 && y - 1 >= 0) {
			r = 0; c = y -x;
		}
		for (; r <= rowEnd - 1 && c <= colEnd - 1; r++, c++) {
			count = 0; 
			for (int j = 0; j < 2; j++) {
				if (board[r + j][c + j] == idPlayer)
					count++;
				else if (board[r + j][c + j] != 0)  
					break;
			}
			if (count == 2) {
				try {
					if (board[r-1][c-1] == 0 && board[r+2][c+2] == 0)
						result++;
				} catch (Exception e) {
				}
				break;
			}
		}
		
		return result;
	}
	
	public static int[][] copy2DArray(int[][] input) {
		int[][] result = new int[input.length][input[0].length];
		for (int i = 0; i < input.length; i++) {
			result[i] = Arrays.copyOf(input[i], input[i].length);
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
		int[][] boardState = {  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
								{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
		
//		System.out.println(check4BoxexFree2Head(boardState, 1, 3, 11)); 
//		System.out.println(check5Boxes(boardState, 1, 3, 3));
//		System.out.println(check4Boxes4Check(boardState, 1, 4, 12));
//		System.out.println(check5Boxes4Check(boardState, 1, 4, 7));
//		System.out.println(check4Boxes3CheckFree2Head(boardState, 1, 1, 2));
//		System.out.println(check3Boxes3CheckFree2Head(boardState, 1, 6, 8));
//		System.out.println(check2Boxes(boardState, 1, 8, 4));
	}

}
