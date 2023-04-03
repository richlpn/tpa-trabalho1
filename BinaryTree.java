import java.util.ArrayList;
import java.util.Comparator;

public class BinaryTree<T> {
    private Comparator<T> comp;
    private Node<T> root;

    public BinaryTree(Comparator<T> comp) {
        this.comp = comp;
    }


    public void insert(T value) {
        if (root == null)
            this.root = new Node<T>(value);
        else insert(this.root, value);
    }

    private void insert(Node<T> node, T value) {
        if (comp.compare(node.getValue(), value) < 0) {

            if (node.getRightNode() == null)
                node.setRightNode(new Node<T>(value));
            else
                insert(node.getRightNode(), value);

        } else if (comp.compare(node.getValue(), value) > 0) {

            if (node.getLeftNode() == null)
                node.setLeftNode(new Node<T>(value));
            else
                insert(node.getLeftNode(), value);

        } else
            System.err.println("Tentativa de inserir no ja existente na arvore ignorada.");
    }

    public T search(T value) {
        if (comp.compare(this.root.getValue(), value) == 0) return root.getValue();

        return search(root, value);
    }

    private T search(Node<T> node, T value) {
        if (node == null) return null;
        int res = comp.compare(root.getValue(), value);

        //Se o valor é menor que o valor no Nó atual procurar nos filhos a esquerda
        if (res < 0) return search(root.getLeftNode(), value);

            //Se o valor foi encontrado
        else if (res == 0) return node.getValue();

            //Se o valor é menor que o valor no Nó atual procurar nos filhos a direita
        else return search(root.getRightNode(), value);
    }

    private Node<T> removeMinimum(Node<T> root, Node<T> parent) {
        if (root.getLeftNode() != null)
            return removeMinimum(root.getLeftNode(), root);

        if (root.getRightNode() != null)
            parent.setLeftNode(root.getRightNode());
        else
            parent.setLeftNode(null);
        root.setRightNode(null);
        return root;
    }

    private Node<T> removeMaximum(Node<T> root, Node<T> parent) {
        if (root.getRightNode() != null)
            return removeMaximum(root.getRightNode(), root);

        else if (root.getLeftNode() != null)
            parent.setRightNode(root.getLeftNode());
        else
            parent.setLeftNode(null);
        root.setLeftNode(null);
        return root;
    }

    public void remove(T value) {
        remove(value, this.root, null, false);
    }

    private Node<T> remove(T value, Node<T> root, Node<T> parent,
                           boolean rootIsLeftChild) {
        if (comp.compare(root.getValue(), value) < 0) {
            return remove(value, root.getRightNode(), root, false);
        } else if (comp.compare(root.getValue(), value) == 0) {
            int nChildren = root.numChildren();
            if (nChildren == 0) {
                if (parent == null) {
                    this.root = null;
                } else if (rootIsLeftChild) {
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

    public int size() {
        if (root == null) return 0;
        
        return size(root);
    }

    private int size(Node<T> node) {
        if (node == null) return 0;

        return size(node.getLeftNode()) + size(node.getRightNode()) + 1;

    }


    public int height () {
        if (root == null)
            return -1;
        return height(root, 0);
    }
    private int height (Node<T> node, int heightAccumulator) {
        int heightLeft = -1, heightRight = -1;

        if (node.getLeftNode() != null) {
            heightLeft = height(node.getLeftNode(), heightAccumulator + 1);
        }

        if (node.getRightNode() != null) {
            heightLeft = height(node.getRightNode(), heightAccumulator + 1);
        }

        if (heightLeft > heightRight)
            return heightLeft;
        else if (heightLeft < heightRight)
            return heightRight;
        else if (heightLeft != -1)
            return heightLeft;
        return heightAccumulator;
    }

    public void printPreOrder () {
        printPreOrder(root);
    }
    private void printPreOrder(Node<T> node) {
        if (node != null) {
            System.out.println(node.getValue());
            printPreOrder(node.getLeftNode());
            printPreOrder(node.getRightNode());
        }
    }

    public void printInOrder () {
        printInOrder(root);
    }
    private void printInOrder (Node<T> node) {
        if (node != null) {
            printInOrder(node.getLeftNode());
            System.out.println(node.getValue());
            printInOrder(node.getRightNode());
        }
    }

    public void printPostOrder () {
        printPostOrder(root);
    }
    private void printPostOrder (Node<T> node) {
        if (node != null) {
            printPostOrder(node.getLeftNode());
            printPostOrder(node.getRightNode());
            System.out.println(node.getValue());
        }
    }

    public void printLevelOrder () {
        // funciona pfv
        if (root == null)
            return;

        ArrayList<Node<T>>[] levels = new ArrayList[height() + 1];
        for (int i = 0; i < height() + 1; ++i) {
            levels[i] = new ArrayList<Node<T>>();
        }

        printLeverOrder(root, 0, levels);

        for (ArrayList<Node<T>> level : levels)
            for (Node<T> node : level)
                System.out.println(node.getValue());
    }
    private void printLeverOrder (Node<T> node, int level, ArrayList<Node<T>>[] levels) {
        if (node == null)
            return;
        levels[level].add(node);
        printLeverOrder(node.getLeftNode(), level + 1, levels);
        printLeverOrder(node.getRightNode(), level + 1, levels);
    }

    public Node<T> getSmallestNode () {
        if (root == null)
            return null;
        return getSmallestNode(root.getLeftNode());
    }
    private Node<T> getSmallestNode (Node<T> node) {
        if (node.getLeftNode() == null)
            return node;
        return getSmallestNode(node.getLeftNode());
    }

    public Node<T> getBiggestNode () {
        if (root == null)
            return null;
        return getBiggestNode(root.getRightNode());
    }
    private Node<T> getBiggestNode (Node<T> node) {
        if (node.getRightNode() == null)
            return node;
        return getBiggestNode(node.getRightNode());
    }
}
