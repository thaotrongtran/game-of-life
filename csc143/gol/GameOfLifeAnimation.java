package csc143.gol;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 * PA5: Game of Life, Animation Class to provide an animation user interface for
 * the Game of Life
 * 
 * @author Thao T Tran
 * @version Challenge
 */
public class GameOfLifeAnimation {
	private MyGameOfLife model;
	private GameOfLifeBoard view;
	private int gensPerMin; // Generations per minute
	private boolean running; // Flag to stop thread running when necessary

	/**
	 * Constructor to create a GameOfLifeAnimation object
	 */
	public GameOfLifeAnimation() {
		model = new MyGameOfLife();
		view = new GameOfLifeBoard(model);
		model.addObserver(view);
		gensPerMin = 120;
		running = false;
	}

	/**
	 * Application method to run
	 * 
	 * @param args argument for the application method
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Animated Game of Life");
		frame.setLocation(100, 100); // Set window location
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // End application when closing

		GameOfLifeAnimation animation = new GameOfLifeAnimation();

		// Puts the board into a JPanel to get a little space around
		JPanel container = new JPanel();
		container.add(animation.view);

		// Add the container to center of the JFrame
		frame.add(container, BorderLayout.CENTER);

		// Creates the Start Animation button
		JButton animate = new JButton("Start Animation");
		animate.addActionListener(new ActionListener() {
			/**
			 * Inner class to override with new action for Start Animation
			 */
			public void actionPerformed(ActionEvent e) {
				animation.running = !animation.running; // Toggle between Start and Stop Animation
				if (animation.running) {
					animate.setText("Stop Animation"); // Set the label accordingly
				} else {
					animate.setText("Start Animation");
				}
				int temp = 60000 / animation.gensPerMin; //Get the time for thread.sleep()
				// Timer object to implement mini-animations for 4 frames with half of the time of no change
				Timer timer = new Timer(temp / 8, new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						animation.model.nextGeneration(); //All of the transition states are processed in model
						animation.view.updateBoard();
					}
				});
				timer.setInitialDelay(temp / 2);  //Time of no change
				// Create a Thread to run
				Thread action = new Thread(new Runnable() {
					public void run() {
						timer.start();
						while (animation.running) {
							try {
								Thread.sleep(temp); // Run thread in the proper interval
							} catch (InterruptedException e) {
							}
						}
						timer.stop();
					}
				});
				action.start();
			}
		});

		// Creates Starting Point button
		JButton startingPoint = new JButton("Starting Point");
		startingPoint.addActionListener(new ActionListener() {
			/**
			 * Inner class to override with new action for Starting Point
			 */
			public void actionPerformed(ActionEvent e) {
				animation.running = false;
				animation.model.clear();
				animation.model.startingPoint();
				animation.view.updateBoard(); // Updates the display
				animate.setText("Start Animation");
			}
		});

		// Creates Clear Board button
		JButton clearBoard = new JButton("Clear Board");
		clearBoard.addActionListener(new ActionListener() {
			/**
			 * Inner class to override with new action for Clear Board
			 */
			public void actionPerformed(ActionEvent e) {
				animation.running = false;
				animation.model.clear();
				animation.view.updateBoard(); // Updates the display
				animate.setText("Start Animation");
			}
		});

		// Creates label and field to change number of generations per minute
		JLabel genMin = new JLabel("Generations per minute");
		JTextField genMinAdjust = new JTextField(animation.gensPerMin + "", 3);
		Font f = new Font("Helvetica", Font.PLAIN, 18);
		genMinAdjust.setFont(f);

		// Changes generations per minute using text field
		genMinAdjust.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int temp = java.lang.Integer.parseInt(genMinAdjust.getText());
				if (temp >= 60 && temp <= 500) {
					animation.gensPerMin = temp;
					System.out.println(animation.gensPerMin + "");

				} else {
					JOptionPane.showMessageDialog(frame, "Generations per minute muste be within 60 and 500");
				}
			}
		});

		// Changes generations per minute using increase button
		JButton incr = new JButton("+");
		incr.addActionListener(new ActionListener() {
			/**
			 * Inner class to override with new action for Clear Board
			 */
			public void actionPerformed(ActionEvent e) {
				if (animation.gensPerMin <= 480) {
					animation.gensPerMin += 20;
					genMinAdjust.setText(animation.gensPerMin + "");
				} else {
					Toolkit t = Toolkit.getDefaultToolkit();
					t.beep();  //Evoking the beeping sound
				}
			}
		});

		// Changes generations per minute using decrease button
		JButton decr = new JButton("-");
		decr.addActionListener(new ActionListener() {
			/**
			 * Inner class to override with new action for Clear Board
			 */
			public void actionPerformed(ActionEvent e) {
				if (animation.gensPerMin >= 80) {
					animation.gensPerMin -= 20;
					genMinAdjust.setText(animation.gensPerMin + "");
				} else {
					Toolkit t = Toolkit.getDefaultToolkit();
					t.beep(); //Evoking the beeping sound
				}
			}
		});

		// Adds all the button to the menu
		// Top menu
		JPanel menuTop = new JPanel();
		menuTop.add(animate);
		menuTop.add(genMin);
		menuTop.add(genMinAdjust);
		menuTop.add(incr);
		menuTop.add(decr);

		// Bottom menu
		JPanel menuBtn = new JPanel();
		menuBtn.add(startingPoint);
		menuBtn.add(clearBoard);

		// Add both menus to one toolbar
		JPanel toolbar = new JPanel();
		toolbar.setLayout(new BorderLayout());
		toolbar.add(menuTop, BorderLayout.NORTH);
		toolbar.add(menuBtn, BorderLayout.SOUTH);

		// Adds the toolbar to main window
		frame.add(toolbar, BorderLayout.SOUTH);

		// Adds MouseListener to toggle the state of each cell clicked
		animation.view.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = e.getY() / GameOfLifeBoard.cellSize + 1; // Get the row based on y coordinate
				int col = e.getX() / GameOfLifeBoard.cellSize + 1; // Get the column based on x coordinate
				if (animation.model.getCellState(row, col) == MyGameOfLife.ALIVE) {
					animation.model.setCellState(row, col, MyGameOfLife.DEAD);
				} else {
					animation.model.setCellState(row, col, MyGameOfLife.ALIVE);
				}
				animation.view.updateBoard(); // Updates the display
			}
		});

		// Stop threads when closing windows
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				frame.dispose();
				System.exit(0);
			}
		});
		frame.pack(); // Sets the size of the JFrame to contain elements properly
		frame.setVisible(true); // Shows the window
		frame.validate(); // Forces the new layout manager to work
	}
}
