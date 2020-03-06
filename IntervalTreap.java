package project1;

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
	public IntervalTreap() {}

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
	public void intervalInsert(Node z) {
		this.size = this.size + 1;
		z.imax = z.interv.HIGH;
		Node y = null;
		Node x = this.root;
		while(x != null) {
			if(x.imax < z.imax){
				x.imax = z.imax;
			}
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
	}
	
	//Implement needed
	public void intervalDelete(Node z) {
		
	}
	
	//Return the node with this interval
	public Node intervalSearch(Interval i) {
		Node x = this.root;
		while(x != null && (i.HIGH < x.interv.LOW || x.interv.HIGH < i.LOW)) {
			if(x.left != null && i.LOW <= x.left.imax) {
				x = x.left;
			}else {
				x = x.right;
			}
		}
		return x;
	}
	
	//Implement for extra credit
	public Node intervalSearchExactly(Interval i) {
		Node x = this.root;
		while(x != null) {
			if(x.interv.LOW == i.LOW && x.interv.HIGH == i.HIGH){
				return x;
			}else if(x.left != null && i.LOW <= x.left.imax) {
				x = x.left;
			}else {
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
	private int max(int a, int b) {
		if(a > b) {
			return a;
		}
		return b;
	}

	//Return the largest of value a, b, or c.
	private int max(int a, int b, int c) {
		return max(max(a, b), c);
	}

	private void inorder(Node n) {
		if(n != null) {
			inorder(n.left);
			System.out.println("["+n.interv.LOW +","+n.interv.HIGH+"]: IMAX: "+ Integer.toString(n.imax)+": Priority - "+ Integer.toString(n.priority));
			inorder(n.right);
		}
	}

	public static void main(String[] args) {
		IntervalTreap n = new IntervalTreap();
		System.out.println("Begin Test!");
		n.intervalInsert(new Node(new Interval(16, 21), 8));
		n.intervalInsert(new Node(new Interval(25, 30), 10));
		n.intervalInsert(new Node(new Interval(19, 20), 17));
		n.intervalInsert(new Node(new Interval(26, 26), 11));
		n.intervalInsert(new Node(new Interval(17, 19), 13));
		n.intervalInsert(new Node(new Interval(0, 3), 21));
		n.intervalInsert(new Node(new Interval(15, 23), 16));
		n.intervalInsert(new Node(new Interval(5, 8), 17));
		n.intervalInsert(new Node(new Interval(8, 9), 12));
		n.intervalInsert(new Node(new Interval(6, 10), 20));
		n.intervalInsert(new Node(new Interval(16, 25), 9));
		n.inorder(n.root);
	}
}
