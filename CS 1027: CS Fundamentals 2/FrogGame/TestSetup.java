import java.io.FileReader;
import java.io.FileNotFoundException;

/* Check that the image and txt files are in the correct location. Follow a random path from
the starting cell to the end cell, or until 60 steps have been performed */
public class TestSetup {

	private static void runTest (int num) {
		Pond pond=null;
		try {
			FileReader in;
			for (int i = 1; i < 10; ++i)
				in = new FileReader("pond"+i+".txt");
			String[] images = {"water","flies1","flies2","flies3","frog","gator","lilypad","mud","reeds","mate"};
			for (int i = 0; i < images.length; ++i)
				in = new FileReader(images[i]+".png");
		} catch (FileNotFoundException e) {
			System.out.println("You program has not been set up correctly. If you are running the");
			System.out.println("program from the terminal, put the your java, png, and txt files");
			System.out.println("in the same folder; then compile the program by typing");
			System.out.println("     javac TestSetup.java");
			System.out.println("then run the program by typing");
			System.out.println("     java TestSetup");
			System.out.println("If you are running the program  from Eclipse, put all the  png and");
			System.out.println("text files in the root folder of your project (the folder where the");
			System.out.println("src folder is located) and then set the \"Run Configurations\" as is");
			System.out.println("specified in the assignment instructions.");
		}
		try {
			pond = new Pond("pond" + num + ".txt");
		} catch (Exception e) {
			System.out.println("Cannot run the program. The following exceptio is thrown:");
			System.out.println(e.getMessage());
		} 
		try {
			//test
			Thread.sleep(2000); //2 seconds pause

			Hexagon location = pond.getStart();
			int step=0;
			//for (int i=0;i<4;i++) {  // take four steps
			while (!location.isEnd() && step < 60) {  // go until arrives at end 
				System.out.println ("step :"+(step++)+" ("+location.getID()+")");
				location.markInStack();

				Hexagon newLocation = location.getNeighbour(any(6));
				Thread.sleep(500); //0.5 seconds pause
				location.markOutStack();
				if (newLocation != null) location = newLocation;
			}
			System.out.println ("!!step :"+(step++)+" ("+location.getID()+")");
			
			Thread.sleep(4000); //4 seconds pause
			pond.dispose();
		} catch (Exception e) {
			System.out.println("test:"+e);
		}
		System.out.println("done");
	}
	
	public static int any(int range) {
		return (int)(Math.floor(Math.random()*range));
	}
	public static void main(String[] args) {		
		Hexagon.TIME_DELAY = 500;  //half second

		runTest(0);		// pond0.txt is just for testing in this TestSetup program.
	}
}