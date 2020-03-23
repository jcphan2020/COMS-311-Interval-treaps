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
        imaxFix1(p);
        //replace p with its parent
        x = x.parent;
    }
}

public void intervalDelete(Node z){
    Node rt = this.root;
    //check empty tree
    if(rt == null){
        return null;
    }

    // if the z.imax < root.imax, search in the left sub treap
    if(z.interv.LOW < rt.interv.LOW){
            rt.left = intervalDelete(z);
    }

    //if the z.imax > root.imax, search in the right sub treap
    else if(z.interv.LOW > rt.interv.LOW){
            rt.right = intervalDelete(z);
    }

    //if key found
    else{
        //Case 1: the node has no child
        if(rt.left == null && rt.right == null){
            //save rt parent in p
            p = rt.parent;

            //delete z
            rt = null;

            // fix the imax of all nodes in the path from the former parent of z to the real root of the treap
            imaxFixAll(p);
        }

        //Case 2: the node has two children
        else if(rt.left != null && rt.right != null){
            //if the priority of the left child less than that of the right one
            if(rt.left.priority < rt.right.priority){
                leftRotate(rt);
                rt.left = intervalDelete(z);
            }
            //if the priority of the right child less than that of the left one
            else{
                rightRotate(rt);
                rt.right = intervalDelete(z);
            }
            //after deleted original rt, fix the imax of all nodes in the path from new rt to the real root of the treap
            imaxFixAll(rt);
        }

        //Case 3: the node has one child only
        else{
            // the node has right child only, replace the node with its right child
            if(rt.left == Null){
                rt = rt.right;
            }
            // the node has left child only, replace the node with its left child
            else{
                rt = rt.left;
            }
            //after deleted original rt, fix the imax of all nodes in the path from new rt to the real root of the treap
            imaxFixAll(rt);
        }
    }

    // update imax by two possible paths 

    return rt;
}