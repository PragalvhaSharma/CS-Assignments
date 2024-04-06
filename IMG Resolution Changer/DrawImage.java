import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics;
import java.io.File;

/* =============================================== */
/* This class contains methods to display an image */
/* =============================================== */
public class DrawImage extends JComponent {
	private final int SIZE_CANVAS = 512;      // Size of the window where images are displayed
	private final int SMALLEST_IMAGE = 256;
	private final int FINAL_RESOLUTION = 9;   // Full image resolution
	public static QuadrantTree imageTree;    // Quadrant tree storing the image
	private static BufferedImage im;          // Image container
	private static int imageType;
	public static int size;                         // Image size
	public static int initialResolution;
	public static QTreeNode startingNode;
	
	/* Display the image stored in the fiven file with the specified resolution */
	public DrawImage(String imagefile, int resolution) {
		int[][] pixels;
		try {
			// Open the image file
			im = ImageIO.read(new File(imagefile));
			imageType = im.getType();
			int width = im.getWidth();
			int height = im.getHeight();
			if (width < height) size = width; else size = height;
			if (size < SMALLEST_IMAGE) {
				System.out.println("Image must be of size at least "+SMALLEST_IMAGE+"x"+SMALLEST_IMAGE);
				return;
			}

			if (size <= SIZE_CANVAS) size = SIZE_CANVAS;
			else if (size <= 2*SIZE_CANVAS) size = 2*SIZE_CANVAS;
			else if (size <= 4*SIZE_CANVAS) size = 4*SIZE_CANVAS;
			else {
				System.out.println("Image must be of size at most "+(4*SIZE_CANVAS)+"x"+(4*SIZE_CANVAS));
				return;
			}
			pixels = new int[size][size];
	
			// Store the image pixels in the 2-dimensional array "pixels"
			for (int xm = 0; xm < size; ++xm)
				for (int ym = 0; ym < size; ++ym) 
					if (xm < width && ym < height)
						pixels[ym][xm] = im.getRGB(xm, ym);
					else pixels[ym][xm] = 0;

			imageTree = new QuadrantTree(pixels);
			startingNode = imageTree.getRoot();
			ListNode<QTreeNode> head = imageTree.getPixels(imageTree.getRoot(),resolution);storePixels(head);			
		} catch (IOException e) {
			System.out.println("Error opening file. " + e.getMessage());
			System.exit(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}				
	}
	
	/* Draw the image with the given resolution with its upper left corner
	   defined by the node storing the coordinates (x,y)                  */
	public DrawImage(int resolution, int x, int y) {
		ListNode<QTreeNode> head;
		initialResolution = resolution;
		if (resolution <= FINAL_RESOLUTION) {
			startingNode = imageTree.getRoot();
			head = imageTree.getPixels(startingNode,resolution);
		}
		else {
			startingNode = imageTree.findNode(imageTree.getRoot(),resolution - FINAL_RESOLUTION,x,y);
			if (startingNode == null) {
				System.out.println("Error. There is no node containing the position ("+x+","+y+")");
				System.exit(0);
			}
			head = imageTree.getPixels(startingNode,FINAL_RESOLUTION);
		}
		storePixels(head);
	}
	
	/* Draw the image with the given resolution, marking those pixels that have
       the same color as the color of the pixel at position (x,y)           	*/
	public DrawImage(int resolution, int x, int y, boolean mark) {
		ListNode<QTreeNode> headAll;  // List with nodes of the same color as pixel at position (x,y)
		Duple headColor;
		initialResolution = resolution;
			
		// Find the node at position (x,y)
		QTreeNode colorNode;
		if (resolution > FINAL_RESOLUTION) resolution = FINAL_RESOLUTION;
		colorNode = imageTree.findNode(startingNode,resolution,x,y);
		if (colorNode == null) return;
		int color = colorNode.getColor();
		
		// Find the nodes with the same color as the node as position (x,y)
		headColor = imageTree.findMatching(startingNode,color,resolution);
		headAll = imageTree.getPixels(startingNode,resolution);	

		// Store the image pixels
		storePixels(headAll);
		Gui.markPixels(headColor,resolution);
		System.out.println("Number of pixels with the same color as pixel ("+x+","+y+"): "+headColor.getCount());
	}
	
	/* Draw the image on the screen */
	public void paint(Graphics g) {
		int[] pix = new int[SIZE_CANVAS*SIZE_CANVAS];
		int[][] matrixOfPixels = Gui.getPixelsMatrix();
		int i = 0;
		for (int xm = 0; xm < SIZE_CANVAS; ++xm)
			for (int ym = 0; ym < SIZE_CANVAS; ++ym)
				pix[i++] = matrixOfPixels[xm][ym];
		im = new BufferedImage(SIZE_CANVAS,SIZE_CANVAS,imageType);
		im.setRGB(0,0,SIZE_CANVAS,SIZE_CANVAS,pix,0,SIZE_CANVAS);	
		g.drawImage(im,0,0,null);
	}
	
	public int getImageSize() {
		return size;
	}
	
	/* Save the pixels stored in the singly linked list referenced by head,
	   so they can be displayed in the screen.                              */
	private void storePixels(ListNode<QTreeNode> head) {
		while (head != null) {
			Gui.storePixel(head.getData());
			head = head.getNext();
		}
	}
}


