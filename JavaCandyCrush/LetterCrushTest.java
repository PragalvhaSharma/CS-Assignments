
public class LetterCrushTest {
	private static int passed = 0;
	private static int tested = 0;

	public static void main(String[] args) {
		String lcString = new LetterCrush(4,3,"AAAABBBBCDDC").toString();
		// *** LetterCrush: toString()
		test(1,"LetterCrush: toString()",lcString.contains("LetterCrush") &&
		lcString.contains("|AAAA|0") &&
		lcString.contains("|BBBB|1") &&
		lcString.contains("|CDDC|2") &&
		lcString.contains("+0123+") );
		// ********** 

		LetterCrush lc  = new LetterCrush(5,4,"AAAABBBBCDDCC");
		lcString = lc.toString();
		while(!lc.isStable()) lc.applyGravity();
		lcString = lc.toString();
		// *** LetterCrush: applyGravity() & isStable()
		test(2,"LetterCrush: applyGravity() & isStable()",lcString.contains("LetterCrush") &&
		lcString.contains("|     |0") &&
		lcString.contains("|AAA  |1") &&
		lcString.contains("|BBBAB|2") &&
		lcString.contains("|DCCCD|3") &&
		lcString.contains("+01234+") );
		// ********** 

		LetterCrush lc2 = new LetterCrush(6, 5, "BCAABBBACABCABCCCCAAACCCACCABC");
		Line longest = lc2.longestLine();
		LetterCrush lc3 = new LetterCrush(5, 5, "CCEABBCBACEBDCDBCEBEADAECAADBA");
		Line longest2 = lc3.longestLine();
		// *** LetterCrush: longestLine()
		test(3,"LetterCrush: longestLine()",longest.toString() .equals("Line:[2,2]->[2,5]"));
		// ********** 

		// *** LetterCrush: longestLine() 
		test(4,"LetterCrush: longestLine() ",(longest+" "+longest.length()+" "+longest2).equals("Line:[2,2]->[2,5] 4 null"));
		// ********** 

		lc2 = new LetterCrush(5, 6, "BCAABBBACABCABCCCCAAACCCACCABC");
		lcString =lc2.toString(lc2.longestLine());
		// *** LetterCrush: toString(Line)
		test(5,"LetterCrush: toString(Line)",lcString.contains("CrushLine") &&
		lcString.contains("|BCAAB|0") &&
		lcString.contains("|BBACA|1") &&
		lcString.contains("|BcABC|2") &&
		lcString.contains("|CcCAA|3") &&
		lcString.contains("|AcCCA|4") &&
		lcString.contains("|CcABC|5") &&
		lcString.contains("+01234+") );
		
		LetterCrush demo = new LetterCrush(4,3,"AACADBBDCD");
				System.out.print(demo.toString(new Line(0,0,true,2)));
		// ********** 

		Line line1 = new Line(1,1,false,4);
		Line line2 = new Line(3,3,true,2);
		lc2.remove(line1);
		lc2.remove(line2);
		lcString = lc2.toString();
		// *** LetterCrush: remove(Line)
		test(6,"LetterCrush: remove(Line)",lcString.contains("LetterCrush") &&
		lcString.contains("|BCAAB|0") &&
		lcString.contains("|B ACA|1") &&
		lcString.contains("|B ABC|2") &&
		lcString.contains("|C C  |3") &&
		lcString.contains("|A CCA|4") &&
		lcString.contains("|CCABC|5") &&
		lcString.contains("+01234+") );
		// ********** 

		LetterCrush lc4 = new LetterCrush(7, 4, "BCBABBCCCBBBAABBBAAAACCBBCCBBC");
		lc4.cascade();
		lcString = lc4.toString();
		// *** LetterCrush: cascade()
		test(7,"LetterCrush: cascade()",lcString.contains("LetterCrush") &&
		lcString.contains("|       |0") &&
		lcString.contains("|B    BC|1") &&
		lcString.contains("|C  ABAA|2") &&
		lcString.contains("|C  BCCB|3") &&
		lcString.contains("+0123456+") );
		// ********** 

		System.out.println("Your code scored: " + passed + " / " + tested);
	}

	public static void test(int testNumber, String message, boolean testStatus) {
		tested++;
		System.out.println("Test " + testNumber + " (" + message + ") " + (testStatus ? "passed" : "failed")+"\n");
		if (testStatus)
			passed++;
	}

}
