package src;

import java.util.ArrayList;
import java.util.List;
/*
 * The main function of the project. Need several implementing.
 * 
 * Johnson Phan
 * Luwen Jiang
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
		if(y != null && y.priority == z.priority) {
			z.priority += 1;
		}
		while(y != null && z.priority < y.priority){
			if(y.right == z){
				leftRotate(y);
			}else{
				rightRotate(y);
			}
			heightFix(z);
			y = z.parent;
			if(y != null && y.priority == z.priority) {
				z.priority += 1;
			}
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
		if(z == this.root){// Case 1: if z is the root
			if(z.left == null && z.right == null){//Case 1.1: if z is the root, the node, z, has no child
				this.root = null;// empty the hole treap
			}else if(z.left != null && z.right != null){//Case 1.2: if z is the root, the node, z, has two children
				if(z.left.priority < z.right.priority){
					rightRotate(z);
					//after right rotation above, z became the right child of its original right child
					intervalDelete(z);
				}
				//if the priority of the right child less than that of the left one
				else{
					leftRotate(z);
					//after left rotation above, z became the left child of its original left child
					intervalDelete(z);
				}
			}else{//Case 1.3: if z is the root, the node, z, has one children only
				if(z.left == null){//z has right child only, set its right child as root
					this.root = z.right;
					z.right.parent = null;
					z = null;

				}else{//z has left child only, set its left child as root
					this.root = z.left;
					z.left.parent = null;
					z = null;
				}
			}
		}else{//Case 2: z is not the root
			Node p = z.parent;

			//Case 2.1: z is NOT the root, the node, z, has no child
			if(z.left == null && z.right == null){
				//delete z
				if(z == p.left){// if z is the left child of its parent
					p.left = null; //empty left child of z's parent
					z = null; //empty z
				}else{// if z is the right child of its parent
					p.right = null;
					z = null;
				}
				// fix the imax of all nodes in the path from the former parent of z to the real root of the treap
				imaxFixAll(p);
			}
			//Case 2.2: z is NOT the root, the node, z, has two children
			else if(z.left != null && z.right != null){
				//if the priority of the left child less than that of the right one
				if(z.left.priority < z.right.priority){
					rightRotate(z);
					intervalDelete(z);
				}
				//if the priority of the right child less than that of the left one
				else{
					leftRotate(z);
					intervalDelete(z);
				}
			}
			//Case 2.3: z is NOT the root, the node, z, has one child only
			else{
				int p_left_or_right = 0; //Check whether z is left child (1) or right child (2) of its parent, p.

				// the node has right child only, replace the node with its right child
				if(z.left == null){
					if(z == p.left){//if z is the left child of its parent
						p_left_or_right = 1;

						//link z.parent with z right child
						p.left = z.right;
						z.right.parent = p;

						z = null;
					}else{//if z is the right child of its parent
						p_left_or_right = 2;
					
						//link z.parent with z right child
						p.right = z.right;
						z.right.parent = p;
						
						z = null;
					}
				}
				// the node, z, has left child only, replace the node with its left child
				else{
					if(z == p.left){
						p_left_or_right = 1;

						//link z.parent with z left child
						p.left = z.left;
						z.left.parent = p;

						z = null;
					}else{
						p_left_or_right = 2;

						//link z.parent with z left child
						p.right = z.left;
						z.left.parent = p;

						z = null;
					}
				}
				//after deleted original z, fix the imax of all nodes in the path from new z to the real root of the treap
				if(p_left_or_right == 1){//z is the left child of its parent, p
					imaxFixAll(p.left);
				}else{//z is the right child of its parent, p
					imaxFixAll(p.right);
				}
			}
		}
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
	
	//Use private search function.
	public List<Interval> overlappingIntervals(Interval i){
		List<Interval> lst = new ArrayList<Interval>();
		lst = searchTree(lst, this.root, i);
		return lst;
	}

	//Private recurrence. Search left if there is overlap. Search right for overlap.
	private List<Interval> searchTree(List<Interval> lst, Node n, Interval i) {
		List<Interval> tmp = lst;
		if(n != null) {
			if(n.left != null && i.LOW <= n.left.imax) {
				tmp = searchTree(tmp, n.left, i);
			}
			
			if((i.LOW <= n.interv.HIGH && n.interv.LOW <= i.HIGH) || (i.LOW <= n.interv.HIGH && n.interv.LOW <= i.HIGH)) {
				tmp.add(n.interv);
			}

			tmp = searchTree(tmp, n.right, i);
		}
		return tmp;
	}

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
}
