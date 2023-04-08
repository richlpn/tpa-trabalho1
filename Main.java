import Aluno.*;
import geradorarquivos.GeradorArquivos;
import geradorarquivos.GeradorArquivos.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        int TAM = 5000;
        TipoArquivo tipoArquivo = TipoArquivo.ORDENADO;
        GeradorArquivos geradorArquivos = new GeradorArquivos();
        String nomeArquivo = "entrada";

        if(tipoArquivo == TipoArquivo.ORDENADO) {
            nomeArquivo+="Ordenada";
            geradorArquivos.geraArqOrdenado(TAM);
        }
        else{
            nomeArquivo+="Balanceada";
            geradorArquivos.geraArqBalanceado(TAM);
        }

        nomeArquivo += TAM + ".txt";

        Aluno.CompareByName compareByName = new Aluno.CompareByName();
        Aluno.CompareByID compareByID = new Aluno.CompareByID();

        BinaryTree<Aluno> arvoreNome = new BinaryTree<>(compareByName);
        BinaryTree<Aluno> arvoreMatricula = new BinaryTree<>(compareByID);

        System.out.println("Lendo arquivo de entrada.");
        String[][] alunos = readFile(nomeArquivo);
        System.out.println("Arquivo de entrada lido.");
        Aluno aluno;

        System.out.println("Gravando dados de alunos.");
        for(String[] dados : alunos){
            aluno = new Aluno(Integer.parseInt(dados[0]), dados[1], Integer.parseInt(dados[2]));
            arvoreMatricula.insert(aluno);
            arvoreNome.insert(aluno);
        }
        System.out.println("Dados de alunos gravados.");
        System.out.println("Tamanho da arvore por nome: " + arvoreNome.size());
        System.out.println("Tamanho da arvore por matricula: " + arvoreMatricula.size());
        System.out.println();

        CommandLineInterface cli = new CommandLineInterface(arvoreMatricula,
                                                            arvoreNome);
        // arvoreMatricula.printPreOrder();
        cli.main();
    }

    private static String[][] readFile(String fileName) {
        String[][] alunos = new String[0][];

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            int numLines = Integer.parseInt(br.readLine());

            alunos = new String[numLines][3];

            for (int i = 0; i < numLines; i++) {
                alunos[i] = br.readLine().split(";");
            }

        } catch (IOException err) {
            System.err.println("Erro ao ler arquivo: " + err);
        }
        return alunos;
    }
}
