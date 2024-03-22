// Class FrogPath determine the path that Freddy the Frog must follow 
public class FrogPath {
	// Represents the environment
	private Pond pond;
	
	//Constructor takes filename as input
	public FrogPath(String filename) {
		try {
			this.pond = new Pond(filename);
		} catch (Exception e) {
			//Was not specified by the instructions on how to handle the exception so I handled it on my own
			System.err.println("Error initializing Pond with file: " + filename);
		}
	}
	
	// Find the best spot for Freddy to jump to
	public Hexagon findBest(Hexagon currCell) {
		// Initialize priority queue to store hexagons/cells
		ArrayUniquePriorityQueue<Hexagon> queue = new ArrayUniquePriorityQueue<Hexagon>();
		
		// Place holder value for priority calculation
		double currentPriority = -1;
		
		// Check adjacent neighbours and iterate through them
		for (int i = 0; i < 6; i++) {
			Hexagon currentNeighbour = currCell.getNeighbour(i);
			// Check if the neighbour of current cell doesnt exist
			if (currentNeighbour != null) {
				// Check if the neighbour is not already on the stack and if the neighbour is safe to move into
				if(isSafeToMove(currentNeighbour) && !currentNeighbour.isMarked()) {
					// Add Hexagon and priority score
					currentPriority = calcPriority(currentNeighbour);
					queue.add(currentNeighbour, currentPriority);
				}
			}
		}
	
		// If the current cell is a lilypad do this additional step
		if (currCell.isLilyPadCell()) {
	    	// Iterate through neighbours
	        for (int i = 0; i < 6; i++) {
	            Hexagon currentNeighbour = currCell.getNeighbour(i);
	            if (currentNeighbour != null) { 
	            	// Iterate through neighbour's surrounding valid neighbours (2 steps from Lilypad)
	                for (int j = 0; j < 6; j++) {
	                    Hexagon tempNeighbour = currentNeighbour.getNeighbour(j);
	                    // Ensure tempNeighbour is not null, has not already been in the stack and is safe to move into
	                    if (tempNeighbour != null && !tempNeighbour.isMarked() && isSafeToMove(tempNeighbour)) {
	                    	// Calculate priority
	                        currentPriority = calcPriority(tempNeighbour);
	                        // If the 2 step neighbour is in a straight line
	                        if (i == j) {
	                            currentPriority += 0.5;
	                        } else {
	                        // If the 2 step neighbour is NOT in a straight line
	                            currentPriority += 1.0;
	                        }
	                        // Finally add Hexagon and score 
	                        queue.add(tempNeighbour, currentPriority);
	                    }
	                }
	            }
	        }
	    }
	    
		// Check if queue is empty 
		if (queue.isEmpty()) {
			return null;
		} else {
			// If not, return min priority/best Hexagon from stack 
			return queue.removeMin();
		}
	}

		
	// Calculate the priority of a Hexagon cell
	private double calcPriority(Hexagon calcCell) {
		// Initial Priority (Placeholder)
		double outputPriority = -2.0;
		
			// If the current cell is a reeds cell and is near alligator
	    	if (calcCell.isReedsCell() && isNearAlligator(calcCell)) {
	    		outputPriority = 10.0;
	        
	        // If the current cell is a water cell
	    	} else if (calcCell.isWaterCell()) {
	        	outputPriority = 6.0;
	        	
	        // If the current cell is a reeds cell but is not near alligator
	    	} else if (calcCell.isReedsCell()&& !isNearAlligator(calcCell)) {
	        	outputPriority = 5.0;
	        	
	        // If the current cell is a LilyPad cell
	    	} else if (calcCell.isLilyPadCell()) {
	       		outputPriority = 4.0;
	       		
	       	// If current cell is Franny
	    	} else if (calcCell.isEnd()) {
	        	outputPriority = 3.0;
	        	
	        // If the current cell has some flies
	    	} else if (calcCell instanceof FoodHexagon) {
	        	FoodHexagon foodCell = (FoodHexagon) calcCell;
	        	int numFlies = foodCell.getNumFlies();
	        	// Adjust priority based on the number of flies.
	        	if (numFlies == 3) {
	        		outputPriority = 0.0;
	        	} else if (numFlies == 2) {
	        		outputPriority = 1.0;
	        	} else if (numFlies == 1) {
	        		outputPriority = 2.0;
	        }

	    }
	    
	    // Final Priority score
	    return outputPriority;
	}
	
	// Checking if Hexagon is safe to move into
	private boolean isSafeToMove(Hexagon calcCell) {
		
	    // Frog is allergic to Mud
	    if (calcCell.isMudCell()) {
	    	return false;
	    }
	    
	    // Alligators are dangerous
	    if(calcCell.isAlligator()) {
	    	return false;
	    }
	    
	    // Checking for Alligators
	    if(isNearAlligator(calcCell)) {
	    		// Frog can only move near alligator if its a ReedsCell
	            if(calcCell.isReedsCell()) {
	            	return true;
	            } else {
	            	return false;
	            }
	        }
	    return true;
	}
	
	
	// Checking if any cell is near an alligator
	private boolean isNearAlligator(Hexagon calcCell) {
		// Iterating through neighbouring cells
	    for (int i = 0; i < 6; i++) {
	        Hexagon currentNeighbour = calcCell.getNeighbour(i);
	        if (currentNeighbour != null && currentNeighbour.isAlligator()) {
	        	// Alligator found in one of the neighbors.
	            return true;  
	        }
	    }
	    // No alligators found after checking all neighbors.
	    return false;  
	}

	

	// Path the frog follows from the start to end cell
	public String findPath() {
		
		// Initialize stack with the start cell
		ArrayStack<Hexagon> S = new ArrayStack<Hexagon>();
		
		// Push the first cell in stack and mark start as in stack
		S.push(pond.getStart());
		pond.getStart().markInStack();
		
		// To count the # of files eaten in total
		int fliesEaten = 0;
		String Output = "";
		
		// Iterating through stack.
		while (!S.isEmpty()) {
			Hexagon curr = S.peek();
			// Add current ID of cell to String
			Output+= (curr.getID() + " ");
			
	        if (curr.isEnd()) { // If we are at the end stop.
	        	break;
	        }
	        
	        // If the cell has some flies
	        if (curr instanceof FoodHexagon) {
	        	// Casting to FoodHexagon
	        	FoodHexagon foodCell = (FoodHexagon) curr;
	        	// Calculating the flies eaten
	        	fliesEaten += foodCell.getNumFlies();
	        	foodCell.removeFlies();
	        	
	        }
	        
	        // Find the next best Hexagon to jump to
	        Hexagon next = findBest(curr);
	        if (next == null) {
	            // No next cell to visit, Go back
	            S.pop();
	            curr.markOutStack();
	        } else {
	            // Next cell found, proceed with the path and mark it in stack.
	            S.push(next);
	            next.markInStack(); 
	        }
	    }
		
		// Check if there is a solution or not
		if (S.isEmpty()) {
			return "No solution";
		} else {
			// Return the number of flies eaten if completed
			Output += "ate "+ fliesEaten + " flies";
		}

		return Output;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Testing 
	public static void main (String[] args) {
		 if (args.length != 1) {
		 System.out.println("No map file specified in the arguments");
		 return;
		 }
		 FrogPath fp = new FrogPath("pond9.txt");
		 Hexagon.TIME_DELAY = 100; 
		 String result = fp.findPath();
		 System.out.println(result);
		}
}
