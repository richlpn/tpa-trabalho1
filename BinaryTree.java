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


}
