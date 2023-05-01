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
     // seria tao mais facil utilizar nos duplamente ligados

    public AVLTree (Comparator<T> comp) {
        super(comp);
    }

    private Node<T> leftRotation (Node<T> root) {
        Node<T> newRoot = root.getRightNode();
        root.setRightNode(newRoot.getLeftNode());
        newRoot.setLeftNode(root);
        return newRoot;
    }

    private Node<T> rightRotation (Node<T> root) {
        Node<T> newRoot = root.getLeftNode();
        root.setLeftNode(newRoot.getRightNode());
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

    private void checkAndRotate (
        Node<T> node,
        Node<T> parent,
        boolean parentIsLeft
    ) {
        int balanceFactor = node.balanceFactor();

        int leftBalanceFactor = 0;
        if (node.getLeftNode() != null)
            leftBalanceFactor = node.getLeftNode().balanceFactor();
        int rightBalanceFactor = 0;
        if (node.getRightNode() != null)
            rightBalanceFactor = node.getRightNode().balanceFactor();

        if (leftBalanceFactor != 0)
            checkAndRotate(node.getLeftNode(), node, false);
        if (rightBalanceFactor != 0)
            checkAndRotate(node.getRightNode(), node, true);

        if (balanceFactor > 0) {

            if (rightBalanceFactor > 0) {

                if (parent == null) {
                    root = leftRotation(node);
                    propagateHeightCorrection(root);
                } else {

                    if (parentIsLeft)
                        parent.setRightNode(leftRotation(node));
                    else
                        parent.setLeftNode(leftRotation(node));
                    propagateHeightCorrection(parent);

                }

            } else if (rightBalanceFactor < 0) {

                if (parent == null) {
                    root = rightLeftRotation(node);
                    propagateHeightCorrection(root);
                } else {
                    if (parentIsLeft)
                        parent.setRightNode(rightLeftRotation(node));
                    else
                        parent.setLeftNode(rightLeftRotation(node));
                    propagateHeightCorrection(parent);
                }

            }

        } else if (balanceFactor < 0) {

            if (leftBalanceFactor > 0) {

                if (parent == null) {
                    root = leftRightRotation(node);
                    propagateHeightCorrection(root);
                } else {
                    if (parentIsLeft)
                        parent.setRightNode(leftRightRotation(node));
                    else
                        parent.setLeftNode(leftRightRotation(node));
                    propagateHeightCorrection(parent);
                }

            } else if (leftBalanceFactor < 0) {

                if (parent == null) {
                    root = rightRotation(node);
                    propagateHeightCorrection(root);
                } else {
                    if (parentIsLeft)
                        parent.setRightNode(rightRotation(node));
                    else
                        parent.setLeftNode(rightRotation(node));
                    propagateHeightCorrection(parent);
                }

            }

        }
    }

    @Override
    public void insert (T value) {
        // onde colocar correcao de altura?
        if (root == null) {
            root = new Node<>(value);
        } else {
            insert(root, new Node<>(value));
            checkAndRotate(root, null, false);
        }
    }

    private void insert (Node<T> node, Node<T> newNode) {
        int resultComp = comp.compare(node.getValue(), newNode.getValue());
        if (resultComp > 0) {

            if (node.getLeftNode() == null) {
                node.setLeftNode(newNode);
                node.incHeightByOne();
            } else {
                node.incHeightByOne();
                insert(node.getLeftNode(), newNode);
            }

        } else if (resultComp < 0) {

            if (node.getRightNode() == null) {
                node.setRightNode(newNode);
                node.incHeightByOne();
            } else {
                node.incHeightByOne();
                insert(node.getRightNode(), newNode);
            }

        } else {
            System.err.println("Tentativa de inserir no ja existente na arvore ignorada.");
            System.err.println("No: "+newNode);
        }
    }
}
