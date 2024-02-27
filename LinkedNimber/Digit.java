
public class Digit {
	
	private char digit;
	
	public Digit (char d) {
		digit = d;
	}

	public int getValue () {
		switch (digit) {
			case 'A':
				return 10;
			case 'B':
				return 11;
			case 'C':
				return 12;
			case 'D':
				return 13;
			case 'E':
				return 14;
			case 'F':
				return 15;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				return Integer.parseInt(String.valueOf(digit));
			default:
				return -1;
			}
	}
	
	public String toString () {
		return String.valueOf(digit);
	}
	
	public boolean equals (Digit other) {
		return digit == other.digit;
	}
	
}
