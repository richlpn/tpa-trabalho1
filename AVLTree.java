import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class AVLTree<T> extends BinaryTree<T> {
    /* Autores:
     * Lucas Vieira da Silva
     * Richard Lucas Pereira Nunes
     * */

    public AVLTree (Comparator<T> comp) {
        super(comp);
    }

    private Node<T> leftRotation (Node<T> root) {
        Node<T> newRoot = root.getRightNode();
        root.setLeftNode(newRoot.getLeftNode());
        newRoot.setLeftNode(root);
        return newRoot;
    }

    private Node<T> rightRotation (Node<T> root) {
        Node<T> newRoot = root.getLeftNode();
        root.setRightNode(newRoot.getRightNode());
        newRoot.setRightNode(root);
        return newRoot;
    }

    private Node<T> leftRightRotation (Node<T> root) {
        root.setLeftNode(leftRotation(root.getLeftNode()));
        return rightRotation(root);
    }

    private Node<T> rightLeftRotation (Node<T> root) {
        root.setRightNode(rightRotation(root.getRightNode()));
        return leftRotation(root);
    }

    private void propagateHeightCorrection (Node<T> node) {
        if (node != null) {
            node.setHeight(super.height(node));
            propagateHeightCorrection(node.getLeftNode());
            propagateHeightCorrection(node.getRightNode());
        }

    }

    private Node<T> checkAndRotate (Node<T> node) {
        Node<T> newNode;
        int balanceFactor = node.balanceFactor();
        int leftBalanceFactor = node.getLeftNode().balanceFactor();
        int rightBalanceFactor = node.getRightNode().balanceFactor();

        if (balanceFactor == 2) {

            if (rightBalanceFactor > 0) {
                newNode = leftRotation(node);
                propagateHeightCorrection(newNode);
                return newNode;
            } else if (rightBalanceFactor < 0) {
                newNode = rightLeftRotation(node);
                propagateHeightCorrection(newNode);
                return newNode;
            }

        } else if (balanceFactor == -2) {

            if (leftBalanceFactor > 0) {
                newNode = leftRightRotation(node);
                propagateHeightCorrection(newNode);
                return newNode;
            } else if (leftBalanceFactor < 0) {
                newNode = rightRotation(node);
                propagateHeightCorrection(newNode);
                return newNode;
            }

        }
        return node;
    }

    @Override
    public void insert (T value) {
        if (root == null) {
            root = new Node<>(value);
        } else
            insert(root, new Node<>(value));
    }

    private boolean insert (Node<T> node, Node<T> newNode) {
        int resultComp = comp.compare(node.getValue(), newNode.getValue());
        if (resultComp > 0) {

            if (node.getLeftNode() == null) {
                node.setLeftNode(newNode);
                node.incHeightByOne();
                return true;
            } else if (insert(node.getLeftNode(), newNode)) {
                node.setLeftNode(checkAndRotate(node.getLeftNode()));
                node.incHeightByOne();
                return true;
            }

        } else if (resultComp < 0) {

            if (node.getRightNode() == null) {
                node.setRightNode(newNode);
                node.incHeightByOne();
                return true;
            } else if (insert(node.getRightNode(), newNode)) {
                node.setRightNode(checkAndRotate(node.getRightNode()));
                node.incHeightByOne();
                return true;
            }

        }
        System.err.println("Tentativa de inserir no ja existente na arvore ignorada.");
        return false;
    }
}
