
public class Node<T> {
    private T value;
    private Node<T> leftNode = null;
    private Node<T> rightNode = null;
    private int height = 0;


    public Node(T value) {
        this.value = value;
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

    public int getHeight () {
        return height;
    }

    public void setHeight (int newHeight) {
        height = newHeight;
    }

    public void incHeightByOne () {
        ++height;
    }

    public void decHeightByOne () {
        --height;
    }

    public int numChildren() {
        int res = 0;
        if (this.getLeftNode() != null)
            ++res;
        if (this.getRightNode() != null)
            ++res;
        return res;
    }

    public int balanceFactor () {
        int leftHeight = -1;
        int rightHeight = -1;
        if (leftNode != null)
            leftHeight = leftNode.getHeight();
        if (rightNode != null)
            rightHeight = rightNode.getHeight();

        return rightHeight - leftHeight;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }
}
