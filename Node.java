import java.util.Comparator;

public class Node <T> {
    private T value;
    private Node<T> leftNode;
    private Node<T> rightNode;

    public Node(Comparator<T> comp) {
        this.value = null;
        this.leftNode = null;
        this.rightNode = null;
    }

    public Node(T value) {
        this.value = value;
        this.leftNode = null;
        this.rightNode = null;
    }
// ######## GET & SETTERS
    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node<T> getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(Node<T> leftNode) {
        this.leftNode = leftNode;
    }

    public Node<T> getRightNode() {
        return rightNode;
    }

    public void setRightNode(Node<T> rightNode) {
        this.rightNode = rightNode;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }
}
