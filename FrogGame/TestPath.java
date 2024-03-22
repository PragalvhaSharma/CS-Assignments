import javax.swing.JOptionPane;

public class TestPath {

	private static String[] expOutputs = new String[] {
		"12 1 2 3 4 3 7 3 2 11 16 22 24 ate 2 flies",
		"12 17 23 24 ate 0 flies",
		"0 5 1 5 11 16 17 18 24 ate 3 flies",
		"12 11 6 7 13 17 16 22 21 20 ate 12 flies",
		"48 19 21 23 25 27 29 28 14 13 12 26 42 43 57 73 87 118 147 176 207 205 203 201 ate 0 flies",
		"No solution",
		"2 11 12 8 9 14 19 24 ate 2 flies",
		"77 75 94 92 71 50 41 43 24 14 13 ate 3 flies",
		"48 19 35 50 51 53 69 84 85 71 86 102 117 118 119 134 133 147 163 178 179 194 209 224 223 208 207 193 192 177 176 191 206 222 221 220 204 190 175 161 145 131 132 131 130 129 143 128 143 129 130 131 145 161 175 174 189 188 173 172 171 157 156 140 126 111 96 81 82 98 82 81 80 79 65 79 78 93 107 123 137 153 167 182 197 212 197 182 166 165 180 195 210 195 180 165 166 182 167 153 137 123 124 109 125 139 155 170 185 199 215 201 ate 4 flies",
	};

	private static void runTest (int num) {
		
		boolean debugging = false;
		
		try {
			FrogPath prog = new FrogPath("pond" + num + ".txt");
			String res = prog.findPath();

			if (res.equals(expOutputs[num-1])) {
				System.out.println("TestPath - Test " + num + " Passed");
				System.out.println(res);
				if (debugging) JOptionPane.showMessageDialog(null,"TestPath - Test " + num + " Passed");
			} else {
				System.out.println("TestPath - Test " + num + " Failed");
				if (debugging) JOptionPane.showMessageDialog(null,"TestPath - Test " + num + " Failed");
				System.out.println(res);
			}
		
		} catch (Exception e) {
			System.out.println("TestPath - Test " + num + " Failed (exception)");
			if (debugging) JOptionPane.showMessageDialog(null,"TestPath - Test " + num + " Failed (exception)");
		}
	}
	
	public static void main(String[] args) {
		
		Hexagon.TIME_DELAY = 0;

		// Run all tests sequentially.
		for (int i = 1; i <= 9; i++) {
			runTest(i);
		}
		
	}

}
