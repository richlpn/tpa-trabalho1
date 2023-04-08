import java.util.InputMismatchException;
import java.util.Scanner;

import Aluno.Aluno;

public class CommandLineInterface {
    private final BinaryTree<Aluno> byId, byName;
    private Scanner scn;


    public CommandLineInterface(BinaryTree<Aluno> byId,
                                BinaryTree<Aluno> byName) {
        this.byId = byId;
        this.byName = byName;
    }

    public void mainloop() {
        System.out.println();

        scn = new Scanner(System.in);
        scn.useDelimiter("\n");
        int opt = 10;

        while (opt != -1) {
            System.out.print("""
                    "O que deseja fazer?"
                    1. Buscar aluno
                    2. Excluir aluno
                    3. Incluir aluno
                    4. Exibir estatisticas da arvore
                    9. Printar arvore
                    -1. Sair
                    ->\s""");
            try {
                opt = scn.nextInt();
                switch (opt) {
                    case -1 -> byId.writeInOrder("saida.txt");
                    case 1 -> {
                        System.out.println();
                        search();
                        System.out.println();
                    }
                    case 2 -> {
                        System.out.println();
                        delete();
                        System.out.println();
                    }
                    case 3 -> {
                        System.out.println();
                        insert();
                        System.out.println();
                    }
                    case 4 -> {
                        System.out.println();
                        treeStatistics();
                        System.out.println();
                    }
                    case 9 -> {
                        System.out.println();
                        printTree();
                        System.out.println();
                    }
                    default -> System.out.println("Opcao nao reconhecida, tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("erro na main");
                break;
            }
        }
        scn.close();
    }

    private void search() {
        System.out.print("""
                Selecione a opcao:
                1. Buscar por matricula
                2. Buscar por nome
                Para voltar, digite qualquer outro numero.
                """);
        try {
            int opt = scn.nextInt();
            if (opt == 1) {
                System.out.print("""
                        Digite a matricula do aluno a ser pesquisado
                        (Apenas numeros): \s
                        """);
                int id = scn.nextInt();
                Aluno searching = new Aluno(id);
                Aluno found = byId.search(searching);
                if (found != null)
                    System.out.println(found);
                else
                    System.out.println("Aluno com matricula " + id + " nao encontrado.");
            } else if (opt == 2) {
                String name = scn.next();
                Aluno searching = new Aluno(name);
                Aluno found = byName.search(searching);
                if (found != null)
                    System.out.println(found);
                else
                    System.out.println("Aluno com nome " + name + " nao encontrado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("faz direito ai porra");
        }
    }

    private void delete() {
        System.out.print("""
                Selecione a opcao:
                1. Excluir por matricula
                2. Excluir por nome
                Para voltar, digite qualquer outro numero.
                ->\s""");
        try {
            int opt = scn.nextInt();
            if (opt == 1) {

                System.out.print("Digite a matricula do aluno a ser excluido\n"
                        + "(Apenas numeros): ");

                int id = scn.nextInt();
                Aluno deleting = new Aluno(id);
                Aluno deleted = byId.remove(deleting);

                if (deleted != null) {
                    byName.remove(deleted);
                    System.out.println(deleted);
                }
                else System.out.println("Aluno com matricula " + id + " nao encontrado.");
            }
            else if (opt == 2) {

                String name = scn.next();
                Aluno deleting = new Aluno(name);
                Aluno deleted = byName.remove(deleting);

                if (deleted != null) {
                    byId.remove(deleted);
                    System.out.println(deleted);
                }
                else
                    System.out.println("Aluno com nome " + name + " nao encontrado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Valor invalido! Tente Novamente!");
        }
    }

    private void insert() {
        Aluno inserting;
        try {
            System.out.println("Digite a matricula do aluno: ");
            int id = scn.nextInt();
            System.out.println("Digite o nome do aluno: ");
            String name = scn.next();
            System.out.println("Digite a nota do aluno: ");
            int age = scn.nextInt();

            inserting = new Aluno(id, name, age);
            byId.insert(inserting);
            byName.insert(inserting);
        } catch (InputMismatchException e) {
            System.out.println("faz direito ai porra");
        }
    }

    private void treeStatistics() {
        BinaryTree<Aluno> current;
        System.out.print("""
                Selecione a opcao:
                1. Arvore por matricula
                2. Arvore por nome
                Para voltar, digite qualquer outro numero.
                ->\s""");
        try {
            int optType = scn.nextInt();
            if (optType == 1)
                current = byId;
            else
                current = byName;

            System.out.println("Quantidade total de elementos: " + current.size());
            System.out.println("Altura da arvore: " + current.height());
            if (optType == 1)
                System.out.println("Aluno com maior matricula: " + current.getBiggestNode().getValue());
            else
                System.out.println("Aluno com maior nome: " + current.getBiggestNode().getValue());
            if (optType == 1)
                System.out.println("Aluno com menor matricula: " + current.getSmallestNode().getValue());
            else
                System.out.println("Aluno com menor nome: " + current.getSmallestNode().getValue());

        } catch (InputMismatchException e) {
            System.out.println("faz direito ai porra");
        }
    }

    private void printTree() {
        BinaryTree<Aluno> current;
        System.out.print("""
                Selecione a opcao:
                1. Imprimir arvore por matricula
                2. Imprimir arvore por nome
                Para voltar, digite qualquer outro numero.
                ->\s""");
        try {
            int optType = scn.nextInt();
            if (optType == 1)
                current = byId;
            else
                current = byName;

            System.out.println("""
                    Deseja imprimir em qual ordem:
                    pre ordem
                    2. em ordem
                    3. pos ordem
                    4. em nivel
                    ->\s""");
            int opt = scn.nextInt();

            switch (opt) {
                case 1 -> current.printPreOrder();
                case 2 -> current.printInOrder();
                case 3 -> current.printPostOrder();
                case 4 -> byId.printLevelOrder();
            }
        } catch (InputMismatchException e) {
            System.out.println("faz direito ai porra");
        }
    }
}
