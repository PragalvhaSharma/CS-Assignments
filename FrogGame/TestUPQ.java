
public class TestUPQ {

	private static boolean test01 () {
		ArrayUniquePriorityQueue<String> pq = new ArrayUniquePriorityQueue<String>();
		return pq.size() == 0 && pq.isEmpty() && pq.getLength() == 10;
	}
	
	private static boolean test02 () {
		ArrayUniquePriorityQueue<String> pq = new ArrayUniquePriorityQueue<String>();
		pq.add("green", 8.0);
		pq.add("yellow", 2.0);
		pq.add("blue", 3.0);
		pq.add("purple", 9.0);
		pq.add("red", 5.0);
		return pq.size() == 5 && !pq.isEmpty() && pq.getLength() == 10;
	}
	
	private static boolean test03 () {
		ArrayUniquePriorityQueue<Double> pq = new ArrayUniquePriorityQueue<Double>();
		
		for (double i = 2.0; i < 4.999; i+=0.1) {
			pq.add(i, i);
		}
		for (double i = 11.0; i >= 0.0; i--) {
			pq.add(i, i);
		}
		return pq.size() == 41 && !pq.isEmpty() && pq.getLength() == 45;
	}
	
	private static boolean test04 () {
		ArrayUniquePriorityQueue<String> pq = new ArrayUniquePriorityQueue<String>();
		pq.add("green", 8.0);
		pq.add("yellow", 2.0);
		pq.add("blue", 3.0);
		pq.add("purple", 9.0);
		pq.add("red", 5.0);
		return pq.contains("blue") && pq.contains("purple") && !pq.contains("orange");
	}
	
	private static boolean test05 () {
		boolean b1 = false, b2 = false;
		ArrayUniquePriorityQueue<String> pq = new ArrayUniquePriorityQueue<String>();
		try {
			pq.peek();
		} catch (CollectionException e) {
			b1 = e.getMessage().equals("CollectionException: PQ is empty");
		}
		pq.add("green", 8.0);
		b2 = pq.peek().equals("green");
		pq.add("yellow", 2.0);
		pq.add("blue", 3.0);
		pq.add("purple", 9.0);
		pq.add("red", 5.0);
		return b1 && b2 && pq.peek().equals("yellow");
	}
	
	private static boolean test06 () {
		boolean b1 = false, b2 = false;
		ArrayUniquePriorityQueue<String> pq = new ArrayUniquePriorityQueue<String>();
		try {
			pq.removeMin();
		} catch (CollectionException e) {
			b1 = e.getMessage().equals("CollectionException: PQ is empty");
		}
		pq.add("green", 8.0);
		pq.add("yellow", 2.0);
		pq.add("blue", 3.0);
		pq.add("purple", 9.0);
		pq.add("red", 5.0);
		b2 = pq.removeMin().equals("yellow");
		return b1 && b2 && pq.removeMin().equals("blue");
	}
	
	private static boolean test07 () {
		boolean b1 = false;
		ArrayUniquePriorityQueue<String> pq = new ArrayUniquePriorityQueue<String>();
		b1 = pq.toString().equals("The PQ is empty");
		pq.add("green", 8.0);
		pq.add("yellow", 2.0);
		pq.add("blue", 3.0);
		pq.add("purple", 9.0);
		pq.add("red", 7.0);
		pq.add("black", 2.0);
		pq.add("white", 1.0);
		pq.add("orange", 8.0);
		pq.add("pink", 9.0);
		pq.add("cyan", 1.0);
		return b1 && pq.toString().equals("white [1.0], cyan [1.0], yellow [2.0], black [2.0], blue [3.0], red [7.0], green [8.0], orange [8.0], purple [9.0], pink [9.0]");
	}
	
	private static boolean test08 () {
		boolean b1 = false;
		ArrayUniquePriorityQueue<String> pq = new ArrayUniquePriorityQueue<String>();
		pq.add("green", 8.0);
		pq.add("yellow", 2.0);
		pq.add("blue", 3.0);
		pq.add("purple", 9.0);
		pq.add("red", 7.0);
		pq.add("black", 2.0);
		pq.add("white", 1.0);
		pq.add("orange", 8.0);
		pq.add("pink", 9.0);
		pq.add("cyan", 5.0);
		try {
			pq.updatePriority("magenta", 4.0);
		} catch (CollectionException e) {
			b1 = e.getMessage().equals("CollectionException: Item not found in PQ");
		}
		pq.updatePriority("pink", 3.0);
		pq.updatePriority("white", 5.0);
		String s = pq.toString();
		return b1 && s.equals("yellow [2.0], black [2.0], blue [3.0], pink [3.0], cyan [5.0], white [5.0], red [7.0], green [8.0], orange [8.0], purple [9.0]");
	}
	
	
	
	
	public static void main(String[] args) {

		// constructor, size, isEmpty, getLength
		try {
			if (test01()) System.out.println("Test 1 Passed");
			else System.out.println("Test 1 Failed");
		} catch (Exception e) { System.out.println("Test 1 Failed (exception)"); }
		
		// add
		try {
			if (test02()) System.out.println("Test 2 Passed");
			else System.out.println("Test 2 Failed");
		} catch (Exception e) { System.out.println("Test 2 Failed (exception)"); }
		
		// expandCapacity
		try {
			if (test03()) System.out.println("Test 3 Passed");
			else System.out.println("Test 3 Failed");
		} catch (Exception e) { System.out.println("Test 3 Failed (exception)"); }
		
		// contains
		try {
			if (test04()) System.out.println("Test 4 Passed");
			else System.out.println("Test 4 Failed");
		} catch (Exception e) { System.out.println("Test 4 Failed (exception)"); }
		
		// peek
		try {
			if (test05()) System.out.println("Test 5 Passed");
			else System.out.println("Test 5 Failed");
		} catch (Exception e) { System.out.println("Test 5 Failed (exception)"); }
		
		// removeMin
		try {
			if (test06()) System.out.println("Test 6 Passed");
			else System.out.println("Test 6 Failed");
		} catch (Exception e) { System.out.println("Test 6 Failed (exception)"); }
		
		// toString
		try {
			if (test07()) System.out.println("Test 7 Passed");
			else System.out.println("Test 7 Failed");
		} catch (Exception e) { System.out.println("Test 7 Failed (exception)"); }
		
		// updatePriority
		try {
			if (test08()) System.out.println("Test 8 Passed");
			else System.out.println("Test 8 Failed");
		} catch (Exception e) { System.out.println("Test 8 Failed (exception)"); }

	}

}
