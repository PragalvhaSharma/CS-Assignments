import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class CellComponent extends JComponent  {

	public static enum CellType {
		WATER, LILYPAD, REEDS, MUD, START, END, GATOR, FLY1, FLIES2, FLIES3
	};
	
	private CellType type;
	private Color hexColour;
	private Color borderColour = Color.white;
	private int borderWidth = 0;
	private Polygon hexagon;
	public static final Color IN_STACK_BORDER = Color.green;
	public static final Color OUT_STACK_BORDER = Color.red;
	
	
	@Override
	public void setSize(Dimension d) {
		super.setSize(d);
		calculateCoords();
	}

	@Override
	public void setSize(int w, int h) {
		super.setSize(w, h);
		calculateCoords();
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		calculateCoords();
	}

	@Override
	public void setBounds(Rectangle r) {
		super.setBounds(r);
		calculateCoords();
	}
	

	public void calculateCoords() {

		int w = getWidth() - 1;
		int h = getHeight() - 1;

		int ratio = (int) (h * .25);

		int[] hexX = new int[6];
		int[] hexY = new int[6];
		
		agressiveCoords(hexX, hexY, w, h, ratio);

		hexagon = new Polygon(hexX, hexY, 6);
	}

	private void agressiveCoords(int[] hexX, int[] hexY, int w, int h, int ratio) {
		hexX[0] = w / 2;
		hexY[0] = 0;

		hexX[1] = w;
		hexY[1] = ratio;

		hexX[2] = w;
		hexY[2] = h - ratio;

		hexX[3] = w / 2;
		hexY[3] = h;

		hexX[4] = 0;
		hexY[4] = h - ratio;

		hexX[5] = 0;
		hexY[5] = ratio;
	}
	
	public void setType(CellType type) {
		this.type = type;
		repaint();
	}
	
	
	public void markInStack () {
		borderColour = IN_STACK_BORDER;
		borderWidth = 7;
	}
	
	public void markOutStack () {
		borderColour = OUT_STACK_BORDER;
		borderWidth = 7;
	}
	
	
	
	
	/**
	 * Draws the different types of map cells on the screen
	 * 
	 * @param g
	 *            Graphics object used to draw the cells on the screen
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int width = getWidth();
		int height = getHeight();
		
		String imgFile = null;
		if (type == CellType.WATER) {
			imgFile = "water.png";
		} else if (type == CellType.LILYPAD) {
			imgFile = "lilypad.png";
		} else if (type == CellType.REEDS) {
			imgFile = "reeds.png";
		} else if (type == CellType.MUD) {
			imgFile = "mud.png";
		} else if (type == CellType.START) {
			imgFile = "frog.png";
		} else if (type == CellType.END) {
			imgFile = "mate.png";
		} else if (type == CellType.GATOR) {
			imgFile = "gator.png";
		} else if (type == CellType.FLY1) {
			imgFile = "flies1.png";
		} else if (type == CellType.FLIES2) {
			imgFile = "flies2.png";
		} else if (type == CellType.FLIES3) {
			imgFile = "flies3.png";
		}
		g2d.setClip(hexagon);
		
		try {
			if (imgFile != null) {
				Image img = new ImageIcon(imgFile).getImage();
				g2d.drawImage(img, 0, 0, width, height, null);
				
			} else {
				g2d.setColor(hexColour);
				g2d.fill(hexagon);
			}
			g2d.setColor(borderColour);
			g2d.setStroke(new BasicStroke(borderWidth));
            g2d.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.draw(hexagon);
			
			g2d.setColor(Color.black);
			int x, y;
			if (width > 100) {
				x = width/2 - 1;
				y = height/6;
			}
			else if (width > 60) {
				x = width/2 - 8;
				y = height/5 + 4;
			}
			else {
				if (toString().length() < 3) x = width/2 - 6;
				else x = width/2-10;
				y = height/4;
			}
			g2d.drawString(toString(), x, y); //Show ID on hexagons.
		} catch (Exception e) {
			System.out.println("Error opening file " + imgFile);
		}

	}
	
}
