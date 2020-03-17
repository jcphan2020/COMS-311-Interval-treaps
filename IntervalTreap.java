
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
	 * rightRotation, leftRotation, and imaxFix have no loops therefore takes O(1) time.
	 * Finally conclusion, T(n) = c log n + c log n = O(log n)
	 */
	public void intervalInsert(Node z) {
		this.size = this.size + 1;
		z.imax = z.interv.HIGH;
		int current_height = 0;
		Node y = null;
		Node x = this.root;
		while(x != null) {
			if(x.imax < z.imax){
				x.imax = z.imax;
			}
			current_height++;
			y = x;
			if(z.interv.LOW < x.interv.LOW){
				x = x.left;
			}else{
				x = x.right;
			}
		}
		z.parent = y;
		if(y == null){
			this.root = z;
		}else if(z.interv.LOW < y.interv.LOW){
			y.left = z;
		}else{
			y.right = z;
		}
		Node s = z.parent;
		while(s != null && z.priority < s.priority){
			if(s.right == z){
				leftRotate(s);
			}else{
				rightRotate(s);
			}
			s = z.parent;
		}
		if(this.height < current_height) {
			this.height = current_height;
		}
	}
	
	//Implement needed
	public void intervalDelete(Node z) {
		
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
	public List<Interval> overlappingIntervals(Interval i){
		List<Interval> lst = new ArrayList<Interval>();
		
		return lst;
	}

	//Making the right child node the parent node with the current node as left child node
	//Based on Algorithms from Red Black Tree, modified with imax
	/*
	 * Simple switch function. Takes O(1) time.
	 */
	private void leftRotate(Node x){
		Node y = x.right;
		x.right = y.left;
		if(y.left != null) {
			y.left.parent = x;
		}
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
	}

	//Making the left child node the parent node with the current node as right child node
	//Based on Algorithms from Red Black Tree, modified with imax
	/*
	 * Simple switch function. Takes O(1) time.
	 */
	private void rightRotate(Node y){
		Node x = y.left;
		y.left = x.right;
		if(x.right != null) {
			x.right.parent = y;
		}
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
			System.out.println("Height: "+i+": ["+n.interv.LOW +","+n.interv.HIGH+"]: IMAX: "+ Integer.toString(n.imax)+": Priority - "+ Integer.toString(n.priority));
			inorder(n.right, i + 1);
		}
	}

	public static void main(String[] args) {
		IntervalTreap n = new IntervalTreap();
		System.out.println("Initializing!");
		n.root = new Node(new Interval(16, 21));
		System.out.println("Begin Test!");
		System.out.println("Root: [" + n.root.interv.LOW + ", " + n.root.interv.HIGH +"]");
		n.inorder(n.root, 0);
		System.out.println(n.getHeight());
	}
}
