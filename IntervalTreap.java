
import java.util.ArrayList;
import java.util.List;
/*
 * The main function of the project. Need several implementing.
 */
public class IntervalTreap {
	Node root;
	int size;
	int height;
	
	//Create Object IntervalTreap as empty
	public IntervalTreap() {
		this.root = null;
		this.size = 0;
		this.height = 0;
	}

	//Return the root node
	public Node getRoot() {
		return this.root;
	}
	
	//Return the number of nodes in treap
	public int getSize() {
		return this.size;
	}
	
	//Return the height of treap
	public int getHeight() {
		return this.height;
	}
	
	//Insert nodes into tree, based on Algorithms from Binary Search Tree, modified
	/*
	 * Note that the first while loop is going down the root node to a leaf branch.
	 * Therefore, this takes O(log n) time.
	 * The next while loop is going back up the branch until root. This also takes
	 * O(log n).
	 * rightRotation, leftRotation, imaxFix, and heightFix have no loops therefore takes O(1) time.
	 * The while loop after the rotations is a continuation of the rotation loop returning to the root,
	 * therefore the rotation loop + continuation loop = O(log n)
	 * Finally conclusion, T(n) = c log n + c log n = O(log n)
	 */
	public void intervalInsert(Node z) {
		this.size = this.size + 1;
		z.imax = z.interv.HIGH;
		int height = 0;
		Node y = null;
		Node x = this.root;
		while(x != null) {
			if(x.imax < z.imax){
				x.imax = z.imax;
			}
			y = x;
			y.height = height;
			if(z.interv.LOW < x.interv.LOW){
				x = x.left;
			}else{
				x = x.right;
			}
			height += 1;
		}
		z.parent = y;
		z.height = height;
		if(y == null) {
			this.root = z;
		}else if(z.interv.LOW < y.interv.LOW){
			y.left = z;
		}else{
			y.right = z;
		}

		while(y != null && z.priority < y.priority){
			if(y.right == z){
				leftRotate(y);
			}else{
				rightRotate(y);
			}
			heightFix(z);
			y = z.parent;
		}
		while(y != null) {
			heightFix(y);
			y = y.parent;
		}
		this.height = this.root.height;
	}
	

	//fix the imax of only one node based on its children
	private void imaxFix1(Node x){
    	if(x.left != null && x.right != null){
	        x.imax = max(x.interv.HIGH, x.left.imax, x.right.imax);
    	}else if(x.right != null){
        	x.imax = max(x.interv.HIGH, x.right.imax);
	    }else if(x.left != null){
	        x.imax = max(x.interv.HIGH, x.left.imax);
	    }else{
 	       x.imax = x.interv.HIGH;
 	   }
	}

	//fix the imax from node x to the root of the treap
	private void imaxFixAll(Node x){
	    while(x != null){// loop stops when x == null
 	       //Fix the imax of x itself
 	       imaxFix1(x);
 	       //replace x with its parent
 	       x = x.parent;
 	   }
	}

	public void intervalDelete(Node z){
	//	Node rt = this.root;
		//Based on the pseudocode in BST, we don't need to check whether the Node z exists or not.
    	//check empty tree
    //	if(rt == null){
	//		System.out.println("The Node doesn't exist!");
   	//	}

    //	// if the z.imax < root.imax, search in the left sub treap
    //	if(z.interv.LOW < rt.interv.LOW){
    //		rt.left = intervalDelete(z.left);
    //	}

    	//if the z.imax > root.imax, search in the right sub treap
    //	else if(z.interv.LOW > rt.interv.LOW){
    //        rt.right = intervalDelete(z.right);
    //	}

    	//if key found
    //	else{
       		//Case 1: the node has no child
        	if(z.left == null && z.right == null){
        	    //save rt parent in p
        	    Node p = z.parent; // Johnson -> Need the word Node at the start to get rid of error sign. Look at my codes in intervalInsert for examples

        	    //delete z
				z = null;

				/* Johnson ->
				 * "z" is considered a storage. You can store other objects in "z"
				 * Currently, "z" is stored as a Node Object. If you set "z" to "null". You are just storing "null"
				 * The Node Object in "z" is still exist in the parent node and in the left and right node so
				 * setting "z" to "null" likely won't change anything.
				 * 
				 * imaxFixAll(Node x) works fine though.
				 */

        	    // fix the imax of all nodes in the path from the former parent of z to the real root of the treap
        	    imaxFixAll(p);
    	    }

        	//Case 2: the node has two children
        	else if(z.left != null && z.right != null){
        	    //if the priority of the left child less than that of the right one
        	    if(z.left.priority < z.right.priority){
					rightRotate(z);
					//after left rotation above, z became the left child of its original right child
        	        intervalDelete(z);
        	    }
        	    //if the priority of the right child less than that of the left one
        	    else{
					leftRotate(z);
					//after right rotation above, z became the right child of its original left child
        			intervalDelete(z);
            	}
        	}

        	//Case 3: the node has one child only
        	else{
        	    // the node has right child only, replace the node with its right child
        	    if(z.left == null){ // Johnson -> Is this "rt" supposed to be here?
        	        z = z.right;	// Johnson -> If this is supposed to be deleting "z" please read my comment on line 135
        	    }
        	    // the node has left child only, replace the node with its left child
        	    else{
        	        z = z.left;		// Johnson -> If this is supposed to be deleting "z" please read my comment on line 135
        	    }
        	    //after deleted original z, fix the imax of all nodes in the path from new z to the real root of the treap
        	    imaxFixAll(z);
        	}
    	//}
	}
	
	//Using a portion of IntervalInsert Algorithm, it follows that path to find the Node.
	/*
	 * Algorithm goes from root down to one leaf branch therefore taking O(log n) time.
	 */
	public Node intervalSearch(Interval i) {
		Node x = this.root;
		while(x != null) {
			if((i.LOW <= x.interv.HIGH && x.interv.LOW <= i.HIGH) || (i.LOW <= x.interv.HIGH && x.interv.LOW <= i.HIGH)) {
				return x;
			}
			if(x.left != null && i.LOW <= x.left.imax) {
				x = x.left;
			}else {
				x = x.right;
			}
		}
		return x;
	}

	//Using a portion of IntervalInsert Algorithm, it follows that path to find the Node.
	/*
	 * Algorithm goes from root down to one leaf branch therefore taking O(log n) time.
	 */
	public Node intervalSearchExactly(Interval i) {
		Node x = this.root;
		while(x != null) {
			if(x.interv.LOW == i.LOW && x.interv.HIGH == i.HIGH) {
				return x;
			}else if(i.LOW < x.interv.LOW){
				x = x.left;
			}else{
				x = x.right;
			}
		}
		return x;
	}
	
	//Implement for extra credit
	//public List<Interval> overlappingIntervals(Interval i){ //Johnson -> I'll leave this one to you if you want some extra credit!
	//	List<Interval> lst = new ArrayList<Interval>();
	//	
	//	return lst;
	//}

	//Making the right child node the parent node with the current node as left child node
	//Based on Algorithms from Red Black Tree, modified with imax. Additionally, modify x.left node height and y.right node height.
	/*
	 * Simple switch function. Takes O(1) time.
	 * The change in height takes 7 iterations or O(1) running time.
	 * Total running time is O(1)
	 */
	private void leftRotate(Node x){
		Node y = x.right;

		if(x.left != null) x.left.height += 1;
		x.height += 1;
		y.height -= 1;
		if(y.right != null) {
			y.right.height -= 1;
			if(y.right.left != null) y.right.left.height -= 1;
			if(y.right.right != null) y.right.right.height -= 1;
		}

		x.right = y.left;
		if(y.left != null) y.left.parent = x;
		y.parent = x.parent;
		if(x.parent == null) {
			this.root = y;
		}else if(x == x.parent.left) {
			x.parent.left = y;
		}else{
			x.parent.right = y;
		}
		y.left = x;
		x.parent = y;

		imaxFix(y, x);
		heightFix(x);
		if(y.right != null) heightFix(y.right);
		heightFix(y);
	}

	//Making the left child node the parent node with the current node as right child node
	//Based on Algorithms from Red Black Tree, modified with imax. Additionally, modify x.left node height and y.right node height.
	/*
	 * Simple switch function. Takes O(1) time.
	 * The change in height takes 7 iterations or O(1) running time.
	 * Total running time is O(1)
	 */
	private void rightRotate(Node y){
		Node x = y.left;
		
		if(y.right != null) y.right.height += 1;
		y.height += 1;
		x.height -= 1;
		if(x.left != null) {
			x.left.height -= 1;
			if(x.left.left != null) x.left.left.height -= 1;
			if(x.left.right != null) x.left.right.height -= 1;
		}

		y.left = x.right;
		if(x.right != null) x.right.parent = y;
		x.parent = y.parent;
		if(y.parent == null) {
			this.root = x;
		}else if(y == y.parent.right) {
			y.parent.right = x;
		}else {
			y.parent.left = x;
		}
		x.right = y;
		y.parent = x;

		imaxFix(x, y);
		heightFix(y);
		if(x.left != null) heightFix(x.left);
		heightFix(x);
	}

	//Created to fix the height of the current node with the left or right. No changes if no children nodes.
	/*
	 * Takes 6 iterations or O(1) running time.
	 */
	private void heightFix(Node x) {
		if(x.left != null && x.right != null) {
			x.height = max(x.left.height, x.right.height);
		}else if(x.left != null) {
			x.height = x.left.height;
		}else if(x.right != null) {
			x.height = x.right.height;
		}
	}
	//Reconfigure the two nodes that were rotated, so both have the accurate imax
	/*
	 * Simple switch function. Takes O(1) time.
	 */
	private void imaxFix(Node y, Node x) {
		if(x.left != null && x.right != null){
			x.imax = max(x.interv.HIGH, x.left.imax, x.right.imax);
		}else if(x.right != null){
			x.imax = max(x.interv.HIGH, x.right.imax);
		}else if(x.left != null){
			x.imax = max(x.interv.HIGH, x.left.imax);
		}else{
			x.imax = x.interv.HIGH;
		}
		if(y.left != null && y.right != null){
			y.imax = max(y.interv.HIGH, y.left.imax, y.right.imax);
		}else if(y.right != null){
			y.imax = max(y.interv.HIGH, y.right.imax);
		}else if(y.left != null){
			y.imax = max(y.interv.HIGH, y.left.imax);
		}else{
			y.imax = y.interv.HIGH;
		}
	}

	//Return the largest value of a or b.
	//Takes O(1) time
	private int max(int a, int b) {
		if(a > b) {
			return a;
		}
		return b;
	}

	//Return the largest of value a, b, or c.
	//Still takes O(1) time
	private int max(int a, int b, int c) {
		return max(max(a, b), c);
	}

	//Delete after completion of project
	private void inorder(Node n, int i) {
		if(n != null) {
			inorder(n.left, i + 1);
			System.out.println("Actual Height: "+i+" |Stored Height: "+n.height+"| ["+n.interv.LOW +","+n.interv.HIGH+"]| IMAX: "+ Integer.toString(n.imax)+"| Priority: "+ Integer.toString(n.priority));
			inorder(n.right, i + 1);
		}
	}

	public static void main(String[] args) {
		IntervalTreap n = new IntervalTreap();
		System.out.println("Initializing!");
		n.intervalInsert(new Node(new Interval(16, 21)));
		n.intervalInsert(new Node(new Interval(8, 9)));
		n.intervalInsert(new Node(new Interval(25, 30)));
		n.intervalInsert(new Node(new Interval(5, 8)));
		n.intervalInsert(new Node(new Interval(15, 23)));
		n.intervalInsert(new Node(new Interval(0, 3)));
		n.intervalInsert(new Node(new Interval(6, 10)));
		n.intervalInsert(new Node(new Interval(17, 19)));
		n.intervalInsert(new Node(new Interval(26, 26)));
		n.intervalInsert(new Node(new Interval(19, 20)));
		System.out.println("Begin Test!");
		n.inorder(n.root, 0);
		System.out.println("Root: [" + n.root.height +"]");
	}
}
