package src;

/* Johnson Phan
 * Luwen Jiang
 */
import java.util.ArrayList;
import java.util.List;

public class index {

    private static void inorder(Node n, int i) {
        if(n != null) {
            inorder(n.left, i + 1);
            System.out.println("Height: "+i+"   |Interval:  ["+n.interv.getLow()+", "+n.interv.getHigh()+"]  |Priority: "+n.getPriority());
            inorder(n.right, i + 1);
        }
    }
    public static void main(String[] args) {
        IntervalTreap n = new IntervalTreap();
        List<Node> lst = new ArrayList<Node>();
      
        lst.add(new Node(new Interval(16, 21), 29));//
        lst.add(new Node(new Interval(8, 9), 20));//
        lst.add(new Node(new Interval(5, 8), 80));//
        lst.add(new Node(new Interval(6, 10), 19));//
        lst.add(new Node(new Interval(0, 3), 73));//
        lst.add(new Node(new Interval(7, 25), 22));//
        lst.add(new Node(new Interval(15, 23), 36));//
        lst.add(new Node(new Interval(25, 30), 73));
        lst.add(new Node(new Interval(17, 19), 42));
        lst.add(new Node(new Interval(19, 20), 55));
        lst.add(new Node(new Interval(26, 26), 79));
        

        /*
        lst.add(new Node(new Interval(16, 21)));
        lst.add(new Node(new Interval(8, 9)));
        lst.add(new Node(new Interval(5, 8)));
        lst.add(new Node(new Interval(6, 10)));
        lst.add(new Node(new Interval(0, 3)));
        lst.add(new Node(new Interval(7, 25)));
        lst.add(new Node(new Interval(15, 23)));
        lst.add(new Node(new Interval(25, 30)));
        lst.add(new Node(new Interval(17, 19)));
        lst.add(new Node(new Interval(19, 20)));
        lst.add(new Node(new Interval(26, 26)));
        */


        System.out.println("Inserting!");
        for(int i = 0; i < lst.size(); i++) {
            n.intervalInsert(lst.get(i));
        }
        inorder(n.root, 0);

        System.out.println("Deleting!");
            for(int i = lst.size() - 1; i >= 0; i--) {
            Node temp = lst.get(i);
            System.out.println("currrent root: [" + n.root.interv.LOW + ", " + n.root.interv.HIGH + "]");
            /*            if(n.root.left != null){
                System.out.println("currrent root.left: [" + n.root.left.interv.LOW + ", " + n.root.left.interv.HIGH + "]");
            }
            */
            //System.out.println("currrent root.right: [" + n.root.right.interv.LOW + ", " + n.root.right.interv.HIGH + "]");
            System.out.println("deleted node: [" + temp.interv.LOW + ", " + temp.interv.HIGH + "]");
            //if(temp.parent != null){
            //    System.out.println("the parent of deleted node: [" + temp.parent.interv.LOW + ", " + temp.parent.interv.HIGH + "]");
            //}
            n.intervalDelete(temp);
            inorder(n.root, 0);
        }

        if(n.root == null){
            System.out.println("Yes, the treap is null now~");
        }else{
            System.out.println("No, still something...");
        }

        //System.out.println("currrent root: [" + n.root.interv.LOW + ", " + n.root.interv.HIGH + "]");
        //System.out.println("n.left:[" + n.root.left.interv.LOW + ", " + n.root.left.interv.HIGH + "]");
        //System.out.println("n.right:[" + n.root.right.interv.LOW + ", " + n.root.right.interv.HIGH + "]");
        
        //n.intervalDelete(n.root);
        //inorder(n.root, 0);

        n.intervalDelete(n.root);
        inorder(n.root, 0);

    }
}