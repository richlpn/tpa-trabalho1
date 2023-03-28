import java.util.Comparator;

public class BinaryTree<T> {
    private Comparator<T> comp;
    private Node<T> root;

    public BinaryTree(Comparator<T> comp) {
        this.comp = comp;
    }


    public void insert(T value){
        if(root == null) this.root = new Node<T>(value);
        else insert(this.root, value);
    }
    private void insert(Node<T> node, T value){
        if(node == null) node = new Node<T>(value);

        else if(comp.compare(node.getValue(), value) < 0)
            insert(node.getLeftNode(), value);
        else
            insert(node.getRightNode(), value);
    }

    public T search(T value){
        if(comp.compare(this.root.getValue(), value) == 0) return root.getValue();

        return search(root, value);
    }
    private T search(Node<T> node, T value){
        if(node == null) return null;

        else if (comp.compare(root.getValue(), value) < 0) return search(root.getLeftNode(), value);
        else return search(root.getRightNode(), value);
    }

    private static Node<T> getMinimum (Node<T> root) {
        if (root.getLeftNode() != null)
            return getMinimum(root.getLeftNode());
        return root;
    }
    private static Node<T> getMaximum (Node<T> root) {
        if (root.getRightNode() != null)
            return getMaximum(root.getRightNode());
        return root;
    }
    private Node<T> removeLeaf (Node<T> leaf, Node<T> leftParent
                                Node<T> rightParent) {
        // removes leaf from tree and returns it
        if (leftParent != null) {
            leftParent.setRightNode(null);
        } else if (rightParent != null) {
            rightParent.setLeftNode(null); //
        } else {
            root = null;
        }
        return leaf;
    }

    private Node<T> removeBranchOneChild (Node<T> branch, Node<T> leftParent,
                                          Node<T> rightParent) {
        // removes branch with one child from tree and returns it
        Node<T> child;
        if (branch.getLeftNode() != null) {
            child = branch.getLeftNode();
            branch.setLeftNode(null);
        } else if (branch.getRightNode() != null) {
            child = branch.getRightNode();
            branch.setRightNode(null);
        }

        if (leftParent != null) {
            leftParent.setRightNode(child);
        } else if (rightParent != null) {
            rightParent.setLeftNode(child);
        } else {
            root = child;
        }
        return branch;
    }

    private Node<T> removeMinimum (Node<T> root, Node<T> parent) {
        if (root.getLeftNode() != null)
            return removeMinimum(root.getLeftNode(), root);

        if (root.getRightNode() != null)
            parent.setLeftNode(root.getRightNode());
        else
            parent.setLeftNode(null);
        root.setRightNode(null);
        return root;
    }
    private Node<T> removeMaximum (Node<T> root, Node<T> parent) {
        if (root.getRightNode() != null)
            return removeMaximum(root.getRightNode(), root);

        else if (root.getLeftNode() != null)
            parent.setRightNode(root.getLeftNode());
        else
            parent.setLeftNode(null);
        root.setLeftNode(null);
        return root;
    }

    public void remove (T value) {
        remove(value, this.root, null, false);
    }

    private Node<T> remove (T value, Node<T> root, Node<T> parent,
                            boolean rootIsLeftChild) {
        if (comp.compare(root.getValue(), value) < 0) {
            return remove(value, root.getRightNode(), root, false);
        } else if (comp.compare(root.getValue(), value) == 0) {
            int nChildren = root.numChildren();
            if (nChildren == 0) {
                if (parent == null) {
                    this.root = null;
                } if (rootIsLeftChild) {
                    parent.setLeftNode(null);
                } else {
                    parent.setRightNode(null);
                }
                return root;
            } else if (nChildren == 1) {
                if (root.getLeftNode() != null) {
                    if (parent == null) {
                        this.root = root.getLeftNode();
                    } else if (rootIsLeftChild) {
                        parent.setLeftNode(root.getLeftNode());
                    } else {
                        parent.setRightNode(root.getLeftNode());
                    }
                } else {
                    if (parent == null) {
                        this.root = root.getRightNode();
                    } else if (rootIsLeftChild) {
                        parent.setLeftNode(root.getRightNode());
                    } else {
                        parent.setRightNode(root.getRightNode());
                    }
                }
                return root;
            } else {
                Node<T> newRoot = removeMinimum(root.getLeftNode(), root);
                newRoot.setLeftNode(root.getLeftNode());
                newRoot.setRightNode(root.getRightNode());
                if (parent == null) {
                    this.root = newRoot;
                } else if (rootIsLeftChild)
                    parent.setLeftNode(newRoot);
                else
                    parent.setRightNode(newRoot);
                return root;
            }
        } else {
            return remove(value, root.getLeftNode(), root, true);
        }
    }

}
