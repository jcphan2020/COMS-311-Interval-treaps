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
	
	//Insert nodes into tree
	public void intervalInsert(Node z) {
		Random rand = new Random();
		int priority = rand.nextInt(10 + this.size);
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

	}
	
	//Rotate left node
	private void leftRotate(Node x){
		Node y = x.right;
		x.right = y.left;
		if(y.left != null){
			y.left.parent = x;
		}
		y.parent = x.parent;
		if(x.parent == null){
			this.root = y;
		}else if(x == x.parent.left){
			x.parent.left = y;
		}else{
			x.parent.right = y;
		}
		y.left = x;
		x.parent = y;
	}

	//Rotate right node
	private void rightRotate(Node x){
		Node y = x.left;
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
		
		return x;
	}
	
	//Implement for extra credit
	public List<Interval> overlappingIntervals(Interval i){
		List<Interval> lst = new ArrayList<Interval>();
		
		return lst;
	}

	public static void main(String[] args) {
		System.out.println("Begin Test!");
		IntervalTreap n = new IntervalTreap();
		
	}
}
