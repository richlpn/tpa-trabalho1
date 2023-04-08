import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class BinaryTree<T> {
    private final Comparator<T> comp;
    private Node<T> root;

    public BinaryTree(Comparator<T> comp) {
        this.comp = comp;
    }


    public void insert(T value) {
        if (root == null)
            this.root = new Node<T>(value);
        else insert(this.root, new Node<T>(value));
    }

    private void insert(Node<T> node, Node<T> newNode) {
        /*
        Se "value" é menor que o valor do nó, ele verifica se o nó à esquerda é nulo.
        Se for,ele cria um novo nó com o valor e define-o como filho esquerdo do nó atual.
        Se não, ele chama recursivamente o método insert() com o nó à esquerda e o valor.

        Se o valor é maior que o valor do nó, ele segue um processo semelhante com a subárvore direita.

        Se o valor já existe na árvore, o método imprime uma mensagem de erro e não insere o valor novamente.
        */
        int resultComp = comp.compare(node.getValue(), newNode.getValue());
        if (resultComp > 0) {

            if (node.getRightNode() == null)
                node.setRightNode(newNode);
            else
                insert(node.getRightNode(), newNode);

        }
        else if (resultComp < 0) {

            if (node.getLeftNode() == null)
                node.setLeftNode(newNode);
            else
                insert(node.getLeftNode(), newNode);

        } else
            System.err.println("Tentativa de inserir no ja existente na arvore ignorada.");
    }

    public T search (T value) {
        Node<T> current = root;
        int count = 0, resultComp = 0;
        while (current != null) {
            ++count;
            resultComp = comp.compare(current.getValue(), value);
            if ( resultComp == 0) {
                System.out.println("Numero de nos percorridos ate encontrar elemento: " + count);
                return current.getValue();
            }
            // Caso o valor seja menor
            else if (resultComp < 0) current = current.getLeftNode();

            else current = current.getRightNode();
        }
        return null;
    }

    // public T search(T value) {
    //     if (comp.compare(this.root.getValue(), value) == 0){
    //         return root.getValue();
    //     }

    //     return search(root, value);
    // }

    // private T search(Node<T> node, T value) {
    //     if (node == null)
    //         return null;
    //     int res = comp.compare(node.getValue(), value);

    //     // Se o no atual for menor que o valor a ser achado, procurar nos a
    //     // esquerda
    //     if (res < 0) return search(node.getLeftNode(), value);

    //         //Se o valor foi encontrado
    //     else if (res == 0) return node.getValue();

    //         // Se o no atual for maior que o valor a ser achado, procurar nos
    //         // a direta
    //     else return search(root.getRightNode(), value);
    //}

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

    public T remove(T value) {
        return remove(value, this.root, null, false).getValue();
    }

    private Node<T> remove(T value, Node<T> root, Node<T> parent,
                           boolean rootIsLeftChild) {

        int resultComp = comp.compare(root.getValue(), value);
        // Valor é está a direita da sub arvore
        if (resultComp > 0) {
            return remove(value, root.getRightNode(), root, false);
        }
        // Valor foi encontrado
        else if (resultComp == 0) {
            int nChildren = root.numChildren();

            // É folha
            if (nChildren == 0) {
                if (parent == null) this.root = null;
                else if (rootIsLeftChild) parent.setLeftNode(null);
                else parent.setRightNode(null);
                return root;
            }
            // Possui apenas um filho
            else if (nChildren == 1) return removeOneChildren(root, parent, rootIsLeftChild);

            //é uma arvore completa
            else return removeTwoChildren(root, parent, rootIsLeftChild);
        }
        // Valor está a esquerda da sub arvore
        else return remove(value, root.getLeftNode(), root, true);

    }

    private Node<T> removeTwoChildren(Node<T> node, Node<T> parent, boolean rootIsLeftChild){
        /*
        * removeMinimum é usada para encontrar o nó mínimo na subárvore esquerda do nó a ser removido.
        * O nó mínimo encontrado é então definido como a nova raiz da subárvore e
        * os filhos esquerdo e direito do nó a ser removido são adicionados como filhos da nova raiz.
        */
        Node<T> newRoot = removeMinimum(root.getLeftNode(), root);
        newRoot.setLeftNode(root.getLeftNode());
        newRoot.setRightNode(root.getRightNode());

        if (parent == null) this.root = newRoot;
        else if (rootIsLeftChild) parent.setLeftNode(newRoot);
        else parent.setRightNode(newRoot);

        return root;
    }
    private Node<T> removeOneChildren(Node<T> node, Node<T> parent, boolean rootIsLeftChild){
        Node<T> child = node.getLeftNode() != null ? node.getLeftNode() : node.getRightNode();
        /*
        * identifica se o filho é o filho esquerdo ou o filho direito do nó a ser removido e atribui este filho ao pai do nó a ser removido.
        * Em seguida, o nó a ser removido é desconectado da árvore e retornado pelo método.
        * */
        if (parent == null) this.root = child;
        else if (rootIsLeftChild) parent.setLeftNode(child);
        else parent.setRightNode(child);
        return root;
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

    public void writeInOrder(String filename){
        try {
            FileWriter fileWriter = new FileWriter(filename);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            writeInOrder(this.root, bufferedWriter);
            bufferedWriter.close();
        } catch (IOException ex) {
            System.err.println("Erro ao escrever no arquivo.");
        }
    }

    private void writeInOrder(Node<T> node, BufferedWriter writer) throws IOException{
        if (node != null) {
            writeInOrder(node.getRightNode(), writer);
            writer.write(node.getValue().toString() + "\n");
            writeInOrder(node.getLeftNode(), writer);
        }
    }
    public void printInOrder () {
        printInOrder(root);
    }
    private void printInOrder (Node<T> node) {
        if (node != null) {
            printInOrder(node.getRightNode());
            System.out.println(node.getValue());
            printInOrder(node.getLeftNode());
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
        if (root == null) return null;
        else if (root.getLeftNode() == null) return root;
        return getSmallestNode(root.getLeftNode());
    }
    private Node<T> getSmallestNode (Node<T> node) {
        if (node.getLeftNode() == null) return node;
        return getSmallestNode(node.getLeftNode());
    }

    public Node<T> getBiggestNode () {
        if (root == null) return null;
        else if (root.getRightNode() == null) return root;
        return getBiggestNode(root.getRightNode());
    }
    private Node<T> getBiggestNode (Node<T> root) {

        if(root.getRightNode() == null) return root;
        return getBiggestNode(root.getRightNode());
    }
}
