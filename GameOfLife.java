/**
 * The is the core of Conway's Game of Life. It is reported
 * to be the most-commonly implemented "game" in computer science.
 * It is not a game in the sense of "winners" and "loser". 
 * Instead, it can be described as a cellular automaton. That is,
 * it consists of group of cells which each act based on their
 * individual state, which includes the local environment, in this
 * case.
 */
public interface GameOfLife {

    /**
     * Indicates the state of the cell is alive.
     */
    public static final int ALIVE = 1;

    /**
     * Indicates the state of the cell is dead.
     */
    public static final int DEAD = 0;

    /**
     * Indicates the states of the cell is getting born
     */
    public static final int BIRTH = 2;
    public static final int BIRTH2 = 3;
    public static final int BIRTH3 = 4;
    public static final int BIRTH4 = 5;
    
    /**
     * Indicates the states of the cell is lonely
     */
    public static final int LONELY = 6;
    public static final int LONELY2 = 7;
    public static final int LONELY3 = 8;
    public static final int LONELY4 = 9;
 
    /**
     * Indicates the states of the cell is overcrowding
     */
    public static final int CROWD = 10;
    public static final int CROWD2 = 11;
    public static final int CROWD3 = 12;
    public static final int CROWD4 = 13;
    
    /**
     * Gets the current state of a given sell.
     * @param row The row number of the given cell, 1 - 19
     * @param col The column number of the given cell, 1 - 19
     * @return The current state of the given cell
     */
    public int getCellState(int row, int col);

    /**
     * Sets the state of the given sell to the given state
     * value.
     * @param row The row number of the given cell, 1 - 19
     * @param col The column number of the given cell, 1 - 19
     * @param state The new state for the given cell.
     */
    public void setCellState(int row, int col, int state);

    /**
     * Creates an ASCII representation of the current board
     * state.
     */
    public String toString();

    /**
     * Calcuates the state of the Game of Life booard for the
     * next generation and updates the board state.
     * <i>Implementation note: This method does not instantiate
     * any new array objects.</i>
     */
    public void nextGeneration();

}
