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
	
	//Implement needed
	public void intervalInsert(Node z) {
		
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
}
