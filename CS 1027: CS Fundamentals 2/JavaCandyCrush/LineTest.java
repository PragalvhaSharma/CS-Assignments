
public class LineTest {
	private static int passed = 0;
	private static int tested = 0;

	public static void main(String[] args) {
		Line line1 = new Line(0,0,false,4);
		Line line2 = new Line(1,1,true,3);

		// ********** Line: toString()
		test(1,"Line: toString()",line1.toString().equals("Line:[0,0]->[3,0]"));


		// ********** Line: length()
		test(2,"Line: length() & isHorizontal()",(line1.length()+" "+line1.isHorizontal()+" "+line2.length()+" "+line2.isHorizontal()).equals("4 false 3 true"));


		// ********** Line: inLine()
		test(3,"Line: inLine()",(line2.inLine(5,1)+" "+line2.inLine(1,1)+" "+line2.inLine(1,4)+" "+line2.inLine(3,1)).equals("false true false false"));


		// ********** Line: getStart()
		test(4,"Line: getStart()",(line1.getStart()[0]+" "+line2.getStart()[1]).equals("0 1"));
		
		
		System.out.println("Your code scored: " + passed + " / " + tested);
	}

	public static void test(int testNumber, String message, boolean testStatus) {
		tested++;
		System.out.println("Test " + testNumber + " (" + message + ") " + (testStatus ? "passed" : "failed")+"\n");
		if (testStatus)
			passed++;
	}

}
