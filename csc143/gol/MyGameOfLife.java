package csc143.gol;

/**
 * LA4: Learning Activity: Game of Life, Various Enhancements Provides the
 * functionality of a Life game board such as set/get cell state, next
 * generation with Adaptive Implementation
 * 
 * @author Thao T Tran
 * @version Plus
 */

public class MyGameOfLife extends java.util.Observable implements GameOfLife, GoBoardConstants {
	private int[][] board; // An integer array to hold state of each game board cell

	/**
	 * Constructor to create a game board with specified dimension
	 */
	public MyGameOfLife() {
		board = new int[ROW_COUNT][COLUMN_COUNT];
	}

	/**
	 * Retrieves the state of each cell with specified row and column
	 * 
	 * @param row
	 *            The row of the given cell
	 * @param col
	 *            The column of the given cell
	 * @return the state of each cell
	 * @throws IllegalArgumentException
	 *             if row or column is not within valid range
	 */
	public int getCellState(int row, int col) {
		if (row < FIRST_ROW || row > LAST_ROW) {
			throw new IllegalArgumentException("Invalid row, must be within first and last row range");
		} else if (col < FIRST_COLUMN || col > LAST_COLUMN) {
			throw new IllegalArgumentException("Invalid column, must be within first and last column range");
		}
		return board[row - FIRST_ROW][col - FIRST_COLUMN];
	}

	/**
	 * Sets the state of a cell with specified row and column
	 * 
	 * @param row
	 *            The row of the given cell
	 * @param col
	 *            The column of the given cell
	 * @throws IllegalArgumentException
	 *             if row or column is not within valid range
	 * @throws IllegalArgumentException
	 *             if state to set is not ALIVE or DEAD
	 */
	public void setCellState(int row, int col, int state) {
		if (row < FIRST_ROW || row > LAST_ROW) {
			throw new IllegalArgumentException("Invalid row, must be within first and last row range");
		} else if (col < FIRST_COLUMN || col > LAST_COLUMN) {
			throw new IllegalArgumentException("Invalid column, must be within first and last column range");
		} else if (!(state == ALIVE || state == DEAD)) {
			throw new IllegalArgumentException("Invalid state to set");
		}
		board[row - FIRST_ROW][col - FIRST_COLUMN] = state;
	}

	/**
	 * Defines next generation of the game board with rules defined by Conway
	 * 
	 * @throws IllegalArgumentException
	 *             when board is null
	 */
	public void nextGeneration() {
		if (board == null) {
			throw new IllegalArgumentException("Bad value for the board");
		}
		//Set the state for the beginning of Animation
		if (!isAnimating()) {
			for (int row = FIRST_ROW; row <= LAST_ROW; row++) {
				for (int col = FIRST_COLUMN; col < LAST_COLUMN; col++) {
					if (board[row - FIRST_ROW][col - FIRST_COLUMN] == DEAD && getNeighbors(row, col) == 3) {
						board[row - FIRST_ROW][col - FIRST_COLUMN] = BIRTH;
					} else if (board[row - FIRST_ROW][col - FIRST_COLUMN] == ALIVE && (getNeighbors(row, col) < 2)) {
						board[row - FIRST_ROW][col - FIRST_COLUMN] = LONELY;
					} else if (board[row - FIRST_ROW][col - FIRST_COLUMN] == ALIVE && (getNeighbors(row, col) > 3)) {
						board[row - FIRST_ROW][col - FIRST_COLUMN] = CROWD;
					}
				}
			}
		} else {  //Set the state for all the mini frames within one Animation, frames are numbered with their appearances
			for (int row = FIRST_ROW; row <= LAST_ROW; row++) {
				for (int col = FIRST_COLUMN; col < LAST_COLUMN; col++) {
					if (board[row - FIRST_ROW][col - FIRST_COLUMN] >= BIRTH 
							&& board[row - FIRST_ROW][col - FIRST_COLUMN] <= BIRTH3) {
						board[row - FIRST_ROW][col - FIRST_COLUMN]++; //Increment to the next frame
					} else if (board[row - FIRST_ROW][col - FIRST_COLUMN] >= LONELY
							&& board[row - FIRST_ROW][col - FIRST_COLUMN] <= LONELY3) {
						board[row - FIRST_ROW][col - FIRST_COLUMN]++;
					} else if (board[row - FIRST_ROW][col - FIRST_COLUMN] >= CROWD
							&& board[row - FIRST_ROW][col - FIRST_COLUMN] <= CROWD3) {
						board[row - FIRST_ROW][col - FIRST_COLUMN]++;
					} else if (board[row - FIRST_ROW][col - FIRST_COLUMN] == BIRTH4) {
						board[row - FIRST_ROW][col - FIRST_COLUMN] = ALIVE; //Become alive when all frames passed
					} else if (board[row - FIRST_ROW][col - FIRST_COLUMN] == LONELY4 
							|| board[row - FIRST_ROW][col - FIRST_COLUMN] == CROWD4) {
						board[row - FIRST_ROW][col - FIRST_COLUMN] = DEAD;//Become dead when all frames passed
					}
				}
			}
		}
	}

	/**
	 * Checks if an animation is going on within the cells
	 * @return true if animation is going on
	 */
	private boolean isAnimating() {
		for (int row = FIRST_ROW; row <= LAST_ROW; row++) {
			for (int col = FIRST_COLUMN; col < LAST_COLUMN; col++) {
				if (board[row - FIRST_ROW][col - FIRST_COLUMN] >= 2) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Retrieves the number of alive neighbors around a cell
	 * 
	 * @param row
	 *            row of the current cell
	 * @param col
	 *            column of the current cell
	 * @return the number of alive neighbors around a cell specified
	 */
	private int getNeighbors(int row, int col) {
		int neighborAlive = 0;
		// Looping through all neighbors and the cell itself
		for (int i = row - 1; i <= row + 1; i++) {
			for (int k = col - 1; k <= col + 1; k++) {
				if (i < FIRST_ROW || i > LAST_ROW || k < FIRST_COLUMN || k > LAST_COLUMN) {
					neighborAlive += 0; // Account for those cells at edges and corners
				} else if (board[i - FIRST_ROW][k - FIRST_COLUMN] == ALIVE
						|| (board[i - FIRST_ROW][k - FIRST_COLUMN] >= LONELY
								&& board[i - FIRST_ROW][k - FIRST_COLUMN] <= CROWD4)) {
					neighborAlive++; // Add 1 when a neighbor to alive or dying (still alive at the moment)
				}
			}
		}
		// Decrease the number of neighbors alive by 1 if the current cell evaluated is
		// also alive
		if (board[row - FIRST_ROW][col - FIRST_COLUMN] == ALIVE) {
			neighborAlive--;
		}
		return neighborAlive;
	}

	protected void startingPoint() {
		// the two alive cells in the upper left
		setCellState(3, 4, GameOfLife.ALIVE);
		setCellState(4, 4, GameOfLife.ALIVE);
		// the block in the upper right
		setCellState(3, 13, GameOfLife.ALIVE);
		setCellState(3, 14, GameOfLife.ALIVE);
		setCellState(4, 13, GameOfLife.ALIVE);
		setCellState(4, 14, GameOfLife.ALIVE);
		// the beehive in the center
		setCellState(8, 7, GameOfLife.ALIVE);
		setCellState(8, 8, GameOfLife.ALIVE);
		setCellState(9, 6, GameOfLife.ALIVE);
		setCellState(9, 9, GameOfLife.ALIVE);
		setCellState(10, 7, GameOfLife.ALIVE);
		setCellState(10, 8, GameOfLife.ALIVE);
		// the glider in the lower l
		setCellState(15, 6, GameOfLife.ALIVE);
		setCellState(16, 4, GameOfLife.ALIVE);
		setCellState(16, 6, GameOfLife.ALIVE);
		setCellState(17, 5, GameOfLife.ALIVE);
		setCellState(17, 6, GameOfLife.ALIVE);
		// the blinker in the lower right
		setCellState(13, 13, GameOfLife.ALIVE);
		setCellState(13, 14, GameOfLife.ALIVE);
		setCellState(13, 15, GameOfLife.ALIVE);
	}

	protected void clear() {
		for (int row = FIRST_ROW; row <= LAST_ROW; row++) {
			for (int col = FIRST_COLUMN; col < LAST_COLUMN; col++) {
				this.setCellState(row, col, GameOfLife.DEAD);
			}
		}
	}

	/**
	 * Provides a string representation of the current board state
	 * 
	 * @return a string representation of the current board state
	 * @throws IllegalArgumentException
	 *             when board is null
	 */
	public String toString() {
		if (board == null) {
			throw new IllegalArgumentException("Bad value for the board");
		}
		String temp = "";
		for (int row = FIRST_ROW; row <= LAST_ROW; row++) {
			for (int col = FIRST_COLUMN; col <= LAST_COLUMN; col++) {
				if (board[row - FIRST_ROW][col - FIRST_COLUMN] == 1) {
					temp += "O ";
				} else if (board[row - FIRST_ROW][col - FIRST_COLUMN] == 0) {
					temp += ". ";
				} else {
					temp += "x ";
				}
			}
			temp += "\n";
		}
		return temp;
	}
}
