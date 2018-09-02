package csc143.gol;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;

/**
 * PA2: Game of Life, Graphical Interface Provides graphical representation of a
 * Game of Life with Java GUI
 * 
 * @author Thao T Tran
 * @version Challenge
 */
public class GameOfLifeBoard extends JPanel implements java.util.Observer {
	private MyGameOfLife game; // Reference to a MyGameOfLife object
	public static final int sideRow = MyGameOfLife.ROW_COUNT; // Stores number of rows
	public static final int sideCol = MyGameOfLife.COLUMN_COUNT; // Stores number of columns
	public static final int cellSize = 25; // Stores the size of each cell

	/**
	 * Creates a board that represent the initial state of a MyGameOfLife object
	 * 
	 * @param game
	 *            MyGameOfLife object
	 */
	public GameOfLifeBoard(MyGameOfLife game) {
		super();
		this.game = game;
		setLayout(new GridLayout(sideRow, sideCol, 0, 0));
		setPreferredSize(new Dimension(sideCol * cellSize + 1, sideRow * cellSize + 1)); // Sets the preferred size so
																							// that each cell is 25
																							// pixels
	}

	/**
	 * Draw lines to create borders at edges and around cells
	 */
	@Override
	public void paintComponent(java.awt.Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i <= this.getWidth(); i += this.getWidth() / sideCol) {
			g.drawLine(i, 0, i, this.getHeight());
		}
		for (int i = 0; i <= this.getHeight(); i += this.getHeight() / sideRow) {
			g.drawLine(0, i, this.getWidth(), i);
		}
	}

	/**
	 * Updates the geographical representation of all cells within the board
	 * 
	 * @param game
	 *            MyGameOfLife object linked to a board
	 * @param board
	 *            GameOfLifeBoard represents a MyGameOfLife object
	 */
	protected void updateBoard() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				removeAll();
				for (int i = 1; i <= sideRow; i++) {
					for (int k = 1; k <= sideCol; k++) {
						if (game.getCellState(i, k) == GameOfLife.ALIVE) {
							add(new dot()); // Adds dot when a cell is live
						} else if (game.getCellState(i, k) == GameOfLife.BIRTH) {
							add(new dot(GameOfLife.BIRTH));
						} else if (game.getCellState(i, k) == GameOfLife.BIRTH2) {
							add(new dot(GameOfLife.BIRTH2));
						} else if (game.getCellState(i, k) == GameOfLife.BIRTH3) {
							add(new dot(GameOfLife.BIRTH3));
						} else if (game.getCellState(i, k) == GameOfLife.BIRTH4) {
							add(new dot(GameOfLife.BIRTH4));
						} else if (game.getCellState(i, k) == GameOfLife.LONELY) {
							add(new dot(GameOfLife.LONELY));
						} else if (game.getCellState(i, k) == GameOfLife.LONELY2) {
							add(new dot(GameOfLife.LONELY2));
						} else if (game.getCellState(i, k) == GameOfLife.LONELY3) {
							add(new dot(GameOfLife.LONELY3));
						} else if (game.getCellState(i, k) == GameOfLife.LONELY4) {
							add(new dot(GameOfLife.LONELY4));
						} else if (game.getCellState(i, k) == GameOfLife.CROWD) {
							add(new dot(GameOfLife.CROWD));
						} else if (game.getCellState(i, k) == GameOfLife.CROWD2) {
							add(new dot(GameOfLife.CROWD2));
						} else if (game.getCellState(i, k) == GameOfLife.CROWD3) {
							add(new dot(GameOfLife.CROWD3));
						} else if (game.getCellState(i, k) == GameOfLife.CROWD4) {
							add(new dot(GameOfLife.CROWD4));
						} else {
							add(new blank()); // Adds blank when a cell is dead
						}
					}
				}
				validate();
			}
		});

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		repaint();
	}
}

/**
 * Helper class to represent frames of animation
 */
class dot extends javax.swing.JComponent {
	private Color fc;
	private int w = 15;
	private int h = 15;
	private int x = 5;
	private int y = 5;

	/**
	 * Constructor, sets the preferred size and colors
	 */
	public dot() {
		fc = Color.blue;
	}

	public dot(int n) {
		if (n == 10) {
			fc = new Color(17, 17, 17);
		} else if (n == 11) {
			fc = new Color(51, 51, 51);
		} else if (n == 12) {
			fc = new Color(85, 85, 85);
		} else if (n == 13) {
			fc = new Color(119, 119, 119);
		} else if (n == 2) {
			fc = Color.green;
			h = 4;
		} else if (n == 3) {
			fc = Color.green;
			h = 8;
		} else if (n == 4) {
			fc = Color.green;
			h = 12;
		} else if (n == 5) {
			fc = Color.green;
			h = 15;
		} else if (n == 6) {
			fc = Color.red;
			h = 15;
			w = 15;
		} else if (n == 7) {
			fc = Color.red;
			h = 12;
			w = 12;
		} else if (n == 8) {
			fc = Color.red;
			h = 8;
			w = 8;
		} else if (n == 9) {
			fc = Color.red;
			h = 4;
			w = 4;
		}

	}

	/**
	 * Renders the component.
	 * 
	 * @param g
	 *            the Graphics object use to render
	 */
	@Override
	public void paintComponent(java.awt.Graphics g) {
		// Paint the component
		super.paintComponent(g);
		// Set the fill color
		g.setColor(fc);
		g.fillOval(5, 5, w, h); // Creates a circle with 15px diameter and proper offset to center
	}
}

/**
 * Helper class to represent dead cells
 */
class blank extends javax.swing.JComponent {
}