import Aluno.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        AlunoComparators.CompareByName compareByName = new AlunoComparators.CompareByName();
        BinaryTree<Aluno> arvoreNome = new BinaryTree<>(compareByName);

        String[][] alunos = readFile("entradaBalanceada10000000.txt");
        Aluno aluno;

        for(String[] dados : alunos){
            aluno = new Aluno(Integer.parseInt(dados[0]), dados[1], Integer.parseInt(dados[2]));
            arvoreNome.insert(aluno);
        }
        System.out.println(arvoreNome.size());
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