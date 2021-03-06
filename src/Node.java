package src;

import java.util.Random;

/* 
 * Simple functions from the document.
 * This is a node for storing key values and other nodes.
 * 
 * Johnson Phan
 * Luwen Jiang
 */
public class Node {
	Node parent, left, right;
	Interval interv;
	int imax;
	int priority;
	int height;
	
	//Create Object node for interval treap
	public Node(Interval t) {
		this.interv = t;
		this.imax = t.getHigh();
		this.height = 0;
		Random rand = new Random();
		this.priority = 1 + rand.nextInt(100);
	}

	//priority by hand for testing
	public Node(Interval t, int pr) {
		this.interv = t;
		this.imax = t.getHigh();
		this.height = 0;
		//Random rand = new Random();
		this.priority = pr;
	}
	
	//Return the parent node
	public Node getParent() {
		return this.parent;
	}
	
	//Return the left node
	public Node getLeft() {
		return this.left;
	}
	
	//Return the right node
	public Node getRight() {
		return this.right;
	}
	
	//Return the interval
	public Interval getInterv() {
		return this.interv;
	}
	
	//Return the imax value
	public int getIMax() {
		return this.imax;
	}
	
	//Return the priority
	public int getPriority() {
		return this.priority;
	}
}
