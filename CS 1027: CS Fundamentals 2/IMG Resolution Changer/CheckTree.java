public class CheckTree {

/* 
This program uses the constructor of your QuadrantTree class to build the tree shown in page 4 of the assignment. Note that the node ID's are not part of the assignment; they assigned by the code to make it wasier for you to check whether the tree built by your code is correct. 
*/          

	/* Traverse the tree rooted at r in preorder and print the information stored in the nodes */
	private static void print(QTreeNode r, String id){
		try {
			if (r == null) return;
			if (id.equals("R")) msg(0,"Root Node. ID = "+id);
			else msg(0,"Node ID = "+id);
			msg(1,"x = "+r.getx()+"; y = "+r.gety()+"; size = "+r.getSize()+"; color = "+r.getColor());
			if (id.equals("R")) // Root node has ID = R
				if (r.getParent() != null) msg(1,"Error: Parent must be null"); 
				else msg(1,"Parent = null");
			else if (r.getParent() == null) msg(1,"Error: Parent must not be null"); 
				 else msg(1,"Parent ID = "+id.substring(0,id.length()-1));
			
			if (r.isLeaf()) msg(1,"Node is a leaf all children are null\n");
			else {
				for (int i = 0; i < 4; ++i)
					if (r.getChild(i) != null) msg(1,"Child #"+i+" has ID "+(id+(i+1)));
					else msg(1,"Child #"+i+" is null");
				msg(0," ");
				for (int i = 0; i < 4; ++i) print(r.getChild(i),id+(i+1));
			}
		}
		catch (Exception e) {
			msg(2,"Program crashed while processing node with ID = "+id);
			msg(2,e.getMessage());
			System.exit(0);
		}
	}
	
	private static void msg(int type,String mssg) {
		if (type == 0) System.out.println(mssg);
		else if (type ==1 ) System.out.println("\t"+mssg);
		else if (type == 2) {
				System.out.println("***************************************************");
				System.out.println(mssg);
				System.out.println("***************************************************");
		}
	}
	
	public static void main(String[] args) {
		int[][] pixels = {{2,2,8,8},{4,4,4,4},{2,8,1,1},{8,6,1,1}};;
		try {
			QuadrantTree tree = new QuadrantTree(pixels);
			print(tree.getRoot(),"R");
		}
		catch (Exception e) {
			msg(2,"Your code crashed with this exception:");
			msg(2,e.getMessage());
		}
	}
}

