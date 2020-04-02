package src;
/* 
 * Fairly simple based on document.
 * Create interval for storing just the left and right end points.
 * There doesn't seem to be any use for floats or double
 *
 * Johnson Phan
 * Luwen Jiang
 */
public class Interval {
	int LOW;
	int HIGH;
	
	//Create Object interval for interval treap
	public Interval(int a, int b) {
		this.LOW = a;
		this.HIGH = b;
	}
	
	//Return the left end point
	public int getLow() {
		return this.LOW;
	}
	
	//Return the right end point
	public int getHigh() {
		return this.HIGH;
	}
}
