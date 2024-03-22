import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Pond extends JFrame {

	private Hexagon startCell;
	private int numNeighbours = 6;
	//public static final Color WATER = new Color(20, 75, 200); 


	public Pond (String mapFile) throws InvalidMapException, FileNotFoundException, IOException {
		super("Leap Frog");

		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel p = new JPanel();


		p.setBackground(new Color(20, 75, 200));

		// Get monitor resolution
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenHeight = screenSize.height;

		// set up the file reader and skip the first line
		BufferedReader in;
		String line = "";
		in = new BufferedReader(new FileReader(mapFile));
		line = in.readLine(); // Ignore first line
		line = in.readLine();

		// Tokenize the first line to get the row and column
		StringTokenizer lineTokens = new StringTokenizer(line);

		// First line is the number of rows then the number of columns
		int row = Integer.parseInt(lineTokens.nextToken());
		int col = Integer.parseInt(lineTokens.nextToken());

		int cellSize = screenHeight / (row + 2);

		if (lineTokens.hasMoreTokens()) {

			if (lineTokens.hasMoreTokens()) {
				cellSize = Integer.parseInt(lineTokens.nextToken());
				if (cellSize > (screenHeight / (row + 2)))
					cellSize = screenHeight / (row + 2);
			}

		}


		// To build the Map we will make temporary use of a 2D array
		// Once built, the hexagons themselves know all of their neighbors, so
		// we do not need the 2D array anymore.
		// Add a row and col of nulls around the "edges" of the builder matrix
		// (+2's)
		// This will greatly simplify the neighbor building process below
		Hexagon[][] mapBuilder = new Hexagon[row + 2][col + 2];

		// HexLayout will arrange the Hexagons in the window
		p.setLayout(new HexLayout(row, col, 2)); // 2

		int i = 0;
		for (int r = 1; r < row + 1; r++) {
			line = in.readLine();
			lineTokens = new StringTokenizer(line);
			// for each token on the line (col in the Map)
			for (int c = 1; c < col + 1; c++) {
				// Read the token and generate the cell type
				char token = lineTokens.nextToken().charAt(0);
				switch (token) {
				case 'W': // water
					mapBuilder[r][c] = new Hexagon(i, Hexagon.CellType.WATER);
					break;
				case 'L': // lilypad
					mapBuilder[r][c] = new Hexagon(i, Hexagon.CellType.LILYPAD);
					break;
				case 'R': // reeds
					mapBuilder[r][c] = new Hexagon(i, Hexagon.CellType.REEDS);
					break;
				case 'M': // mud
					mapBuilder[r][c] = new Hexagon(i, Hexagon.CellType.MUD);
					break;
				case 'S': // start
					mapBuilder[r][c] = new Hexagon(i, Hexagon.CellType.START);
					startCell = mapBuilder[r][c];
					break;
				case 'E': // exit
					mapBuilder[r][c] = new Hexagon(i, Hexagon.CellType.END);
					break;
				case 'A': // alligator
					mapBuilder[r][c] = new Hexagon(i, Hexagon.CellType.GATOR);
					break;
				case '1': // 1 fly
					mapBuilder[r][c] = new FoodHexagon(i, 1);
					break;
				case '2': // 2 flies
					mapBuilder[r][c] = new FoodHexagon(i, 2);
					break;
				case '3': // 3 flies
					mapBuilder[r][c] = new FoodHexagon(i, 3);
					break;
				default:
					throw new InvalidMapException(token);
				}

				// add to the GUI layout
				p.add(mapBuilder[r][c]);
				i++;
			} // end for cols
		} // end for rows

		// go through the 2D matrix again and build the neighbors
		int offset = 0;
		for (int r = 1; r < row + 1; r++) {
			for (int c = 1; c < col + 1; c++) {
				// on even rows(insert from left side) need to add one to the
				// upper and lower neighbors
				// on odd, do not add anything (offset should be 0)
				offset = 1 - r % 2;

				// set the neighbors for this hexagon in the builder
				mapBuilder[r][c].setNeighbour(mapBuilder[r - 1][c + offset], 0);
				mapBuilder[r][c].setNeighbour(mapBuilder[r][c + 1], 1);
				mapBuilder[r][c].setNeighbour(mapBuilder[r + 1][c + offset], 2);
				mapBuilder[r][c].setNeighbour(mapBuilder[r + 1][c - 1 + offset], 3);
				mapBuilder[r][c].setNeighbour(mapBuilder[r][c - 1], 4);
				mapBuilder[r][c].setNeighbour(mapBuilder[r - 1][c - 1 + offset], 5);
			} // end for cols
		} // end for rows

		// close the file
		in.close();

		// set up the GUI window
		this.add(p);
		this.pack();
		this.setSize(cellSize * col, cellSize * row);
		this.setVisible(true);
		setResizable(false); // Prevent users from changing the window size.
	}

	public Hexagon getStart() {
		return startCell;
	}

}
