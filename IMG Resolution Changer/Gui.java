import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JComponent;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;


/* Create the graphical user interface */
public class Gui extends JFrame implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1;

	private JPanel buttonPanel;
	private JButton buttons[];

	private Graphics display; // Graphics object needed to draw
	private Board panel; // Panel containing map
	private Container contentPane;

	private Color backgroundColor = new Color(225, 225, 255);
	private final int MENU_HEIGHT = 74; 	// Height of buttons toolbar
	private final int BORDER = 9; 		// Border width
	private final int INITIAL_RESOLUTION = 6;
	private static final int FINAL_RESOLUTION = 9;   // Full image resolution
	private final int MAX_RESOLUTION = 11;
	private final int MIN_RESOLUTION = 1;
	private static final int CANVAS_SIZE = 512;     // Size of window where images are displayed

	private int windowWidth; 		// Dimensions of  GUI window
	private int windowHeight;

	private int xcoord, ycoord;          	// Coordinates entered in the graphical user interface
	private static int[][] pixelsMatrix; 	// Pixels of image to draw
	private int resolution = INITIAL_RESOLUTION;
	private DrawImage dim;
	private static int imageSize;  		// Size of image being displayed

	/* ============================================== */
	public Gui(int width, int height, String fileName) {
	/* ============================================== */

		try {
			windowWidth = width;
			windowHeight = height;
			pixelsMatrix = new int[CANVAS_SIZE][CANVAS_SIZE];

			panel = new Board();
			contentPane = getContentPane();
			contentPane.add(panel);
			setSize(width, height);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			setVisible(true);

			display = panel.getGraphics();

			contentPane.setBackground(backgroundColor);
			display.setColor(backgroundColor);

			/* Create buttons and mouse event handlers */

			buttonPanel = new JPanel();
			buttons = new JButton[4];

			buttons[0] = makeButton("++Res", backgroundColor);
			buttons[1] = makeButton("--Res", backgroundColor);
			buttons[3] = makeButton("Exit",backgroundColor);
			buttons[2] = makeButton("Find",backgroundColor);

			for (int i = 0; i < buttons.length; i++)
				buttonPanel.add(buttons[i]);

			//x = new JTextField("x: ",5);
			//y = new JTextField("y: ",5);
			
			//buttonPanel.add(x);
			//buttonPanel.add(y);
			buttonPanel.add(buttons[2]);
			buttonPanel.add(buttons[3]);
			
			/* Place buttons at the bottom of the Window */
			contentPane.add(buttonPanel, BorderLayout.SOUTH);

			for (int i = 0; i < buttons.length; ++i)
				buttons[i].addActionListener(this);
			//x.addActionListener(this);
			//y.addActionListener(this);

			contentPane.setFocusable(true);
			contentPane.requestFocusInWindow();

			System.out.println("Initializing ...");
			Thread.sleep(2000);

			resolution = INITIAL_RESOLUTION;
			
			dim = new DrawImage(fileName, resolution);
			contentPane.add(dim);
			dim.addMouseListener(this);
			setVisible(true);
			System.out.println("Ready.");
		} catch (Exception e) {
			System.out.println("Error starting program: " + e.getMessage());
		}
	}

	/* Returns true if the given color are similar; returns false otherwise */
	public static boolean similarColor(int color1, int color2) {
		int simred = 0xf << 16;
		int simgreen = 0xf << 8;
		int simblue = 15;
		if ((Math.abs((color1 & 0xff0000) - (color2 & 0xff0000)) < simred) &&
		    (Math.abs((color1 & 0xff00) - (color2 & 0xff00)) < simgreen) &&
			(Math.abs((color1 & 0xff) - (color2 & 0xff)) < simblue)) return true;
		else return false;
	}
	
	
	public static int[][] getPixelsMatrix() {
		return pixelsMatrix;
	}
	
	/* Computes the average color of the square region of the image 
	   "pixels" with upper left corner at (x,y) and size given by 
	   the last parameter.                                   */
	public static int averageColor(int[][] pixels, int x, int y, int size) {
		int red, green, blue;
		red = green = blue = 0;
		for (int i = x; i < x + size; ++i)
			for (int j = y; j < y + size; ++j) {
				// Compute red, green, and blue component of each pixel in the square region
				red = red + ((pixels[j][i] >> 16) & 0xFF);
				green = green + ((pixels[j][i] >> 8) & 0xFF);
				blue = blue + (pixels[j][i] & 0xFF);
			}

		// Average the components and convert them back to a color
		int n = size * size;		
		red = red / n;
		green = green / n;
		blue = blue / n;
		return ((red & 0xFF) << 16) + ((green & 0xFF) << 8) + (blue & 0xFF); 
	}
	
	/* Stores the pixels represented by r in matrixPixels so the image can be     
	   displayed */
	public static void storePixel (QTreeNode r) {
		int x = r.getx();
		int y = r.gety();
		int size = 0, i, j;
		int deltax = 0, deltay = 0, scale = 1;
		QTreeNode startingNode = DrawImage.startingNode;
		QTreeNode root = DrawImage.imageTree.getRoot();
		imageSize = DrawImage.size;
		try {
			if (imageSize == CANVAS_SIZE) {
		    		size = r.getSize();
		    		for (i = x; i < x + size; ++i)
		    			for (j = y; j < y + size; ++j)
		    				pixelsMatrix[j][i] = r.getColor();	
				return;
			}
		
			deltax = startingNode.getx();
			deltay = startingNode.gety();
			x = x - deltax; // Translate the coordinates of the node with respect
						// to the starting node
			y = y - deltay;
							
			if (imageSize == 2*CANVAS_SIZE) {
				if (startingNode != root) 
					pixelsMatrix[y][x] = r.getColor();			
				else {
					scale = 2;
					x = x/scale;
					y = y/scale;
					size = r.getSize()/scale;
		    			for (i = x; i < x + size; ++i)
		    				for (j = y; j < y + size; ++j)
		    					pixelsMatrix[j][i] = r.getColor();								
				}
			}
			else {
				if (getLevel(startingNode) == 2) 
					pixelsMatrix[y][x] = r.getColor();
				else if (startingNode == root)
					scale = 4;
				else if (startingNode.getParent() == root)
					scale = 2;
				x = x/scale;
				y = y/scale;
				size = r.getSize()/scale;
				//pixelsMatrix[y/scale][x/scale] = r.getColor();
		    		for (i = x; i < x + size; ++i)
		    			for (j = y; j < y + size; ++j)
		    				pixelsMatrix[j][i] = r.getColor();			
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}
	
	/* Mark the pixels in the list stored in head */
	public static void markPixels(Duple head, int resolution) {
		QTreeNode r;
		int deltax = 0, deltay = 0, scale = 1;
		int x, y, size = 0;
		QTreeNode startingNode = DrawImage.startingNode;
		QTreeNode root = DrawImage.imageTree.getRoot();
		imageSize = DrawImage.size;
		
		if (DrawImage.size == 2*CANVAS_SIZE) scale = 2;
		else if (DrawImage.size == 4*CANVAS_SIZE) scale = 4;
		ListNode<QTreeNode> p = head.getFront();
		while (p != null) {
			r = (QTreeNode)p.getData();
			x = r.getx();
			y = r.gety();
			if (imageSize == CANVAS_SIZE) {
		    		size = r.getSize();
		    		for (int i = x; i < x + size; ++i)
		    			for (int j = y; j < y + size; ++j)
		    				pixelsMatrix[j][i] = 0xff0000;	
			}	
			else {
				deltax = startingNode.getx();
				deltay = startingNode.gety();
				x = x - deltax; 	// Translate the coordinates of the node with respect
							// to the starting node
				y = y - deltay;
							
				if (imageSize == 2*CANVAS_SIZE) {
					if (startingNode != root) {
						pixelsMatrix[y][x] = 0xff0000;	
					}		
					else {
						scale = 2;
						x = x/scale;
						y = y/scale;
						size = r.getSize()/scale;
						for (int i = x; i < x + size; ++i)
							for (int j = y; j < y + size; ++j)
								pixelsMatrix[j][i] = 0xff0000;								
					}
				}
				else {
					if (getLevel(startingNode) == 2) 
						pixelsMatrix[y][x] = 0xff0000;
					else if (startingNode == root)
						scale = 4;
					else if (startingNode.getParent() == root)
						scale = 2;
					x = x/scale;
					y = y/scale;
					size = r.getSize()/scale;
					//pixelsMatrix[y/scale][x/scale] = r.getColor();
		    			for (int i = x; i < x + size; ++i)
		    				for (int j = y; j < y + size; ++j)
		    					pixelsMatrix[j][i] = 0xff0000;			
				}

			}
			p = p.getNext();			
		}
	}
	
	/* Compute the level of the node r */
	/* ========================================*/
	private static int getLevel(QTreeNode r) {
	/* ========================================*/		
		int level = 0;
		while (r.getParent() != null) {
			r = r.getParent();
			++level;
		}
		return level;
	}
	/* ==================== */
	public int displayWidth() {
	/* ==================== */
		return windowWidth - BORDER;
	}

	/* ==================== */
	public int displayHeight() {
	/* ==================== */
		return windowHeight - MENU_HEIGHT;
	}

	/* =================================================== */
	public Graphics getDisplay() {
	/* =================================================== */
		return display;
	}

	/* =================================================== */
	private JButton makeButton(String label, Color color)
	/* =================================================== */
	{
		JButton b = new JButton(label);
		b.setBackground(color);
		return b;
	}

	/* ======================================= */
	public void actionPerformed(ActionEvent e)
	/* ======================================= */
	{
		int scale = 1, deltax = 0, deltay = 0;
		String s = (String) e.getActionCommand();
		int imgSize = DrawImage.size;
		
		if (s.equals("Find")) {
			if ((imgSize > CANVAS_SIZE) && (resolution > FINAL_RESOLUTION)) {
				deltax = DrawImage.startingNode.getx();
				deltay = DrawImage.startingNode.gety();				
			}
			if ((imgSize == 2*CANVAS_SIZE) && (resolution <= FINAL_RESOLUTION)) scale = 2;
			else if (imgSize == 4*CANVAS_SIZE) 
				if (resolution <= FINAL_RESOLUTION) scale = 4;
				else if (resolution == (FINAL_RESOLUTION + 1)) scale = 2;

			xcoord = (xcoord * scale) + deltax;
			ycoord = (ycoord * scale) + deltay;
			
			contentPane.remove(dim);			
			dim = new DrawImage(resolution,xcoord,ycoord,true);
			contentPane.add(dim);
			dim.addMouseListener(this);			
			setVisible(true);				
		} else if (s.equals("++Res")) {

			if (((imgSize <= CANVAS_SIZE) && (resolution < FINAL_RESOLUTION)) ||
			    ((imgSize == 2*CANVAS_SIZE) && (resolution < FINAL_RESOLUTION + 1)) ||
				((imgSize == 4*CANVAS_SIZE) && (resolution < MAX_RESOLUTION))) {
				++resolution;	
				if (imgSize == 2*CANVAS_SIZE) scale = 2;
				else if (imgSize == 4*CANVAS_SIZE) {
					if (resolution == FINAL_RESOLUTION + 1) scale = 4;
					else {
						deltax = DrawImage.startingNode.getx();
						deltay = DrawImage.startingNode.gety();
						scale = 2;
					}
				}
				xcoord = xcoord * scale + deltax;
				ycoord = ycoord * scale + deltay;
				if (xcoord >= imgSize || ycoord >= imgSize) {
					xcoord = 0;
					ycoord = 0;
				}
				contentPane.remove(dim);							
				dim = new DrawImage(resolution,xcoord,ycoord);
				contentPane.add(dim);
				dim.addMouseListener(this);				
				setVisible(true);
			}
		} else if (s.equals("--Res")) {
				if (resolution >= MIN_RESOLUTION) {
					if ((imgSize == 4*CANVAS_SIZE) && (resolution == MAX_RESOLUTION)) --resolution;
					--resolution;
					if (xcoord == 0 && ycoord == 0) xcoord = ycoord = imgSize / 2;					
					contentPane.remove(dim);																
					dim = new DrawImage(resolution,xcoord,ycoord);
					contentPane.add(dim);
					dim.addMouseListener(this);					
					setVisible(true);	
				}					
		}else if (s.equals("Exit")) {
			dispose();
			System.exit(0);
		}

		contentPane.requestFocusInWindow();
	}
	
	public void mouseClicked(MouseEvent e) {
		xcoord = e.getX();
		ycoord = e.getY();
	}
	
	public void mouseEntered(MouseEvent e) {
	}
	
	public void mouseExited(MouseEvent e) {
	}
	
	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}
	
	/* ====================================== */
	public static void main(String args[]) {
		/* ====================================== */
		Gui window;
		if (args.length == 1) 
			window = new Gui(530, 590, args[0]); // Set up drawing environment;
		else if (args.length == 2) ;
	}		
}


