import Aluno.*;
import geradorarquivos.GeradorArquivos;
import geradorarquivos.GeradorArquivos.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    /* CONFIGURAÇÕES DE GERAÇÃO DE ARQUIVOS
     * TAM == QUANTIDADE DE ALUNOS GERADOS
     * tipoArquivo == Os ID dos alunos serão crescente(Ordenados) ou não
     * */
    private static final int TAM = 5000;
    private static final TipoArquivo tipoArquivo = TipoArquivo.BALANCEADO;

    public static void main(String[] args) {

        GeradorArquivos geradorArquivos = new GeradorArquivos();
        String nomeArquivo = "entrada";

        if (tipoArquivo == TipoArquivo.ORDENADO) {
            nomeArquivo += "Ordenada";
            geradorArquivos.geraArqOrdenado(TAM);
        } else {
            nomeArquivo += "Balanceada";
            geradorArquivos.geraArqBalanceado(TAM);
        }

        nomeArquivo += TAM + ".txt";

        Aluno.CompareByName compareByName = new Aluno.CompareByName();
        Aluno.CompareByID compareByID = new Aluno.CompareByID();

        BinaryTree<Aluno> arvoreNome = new BinaryTree<>(compareByName);
        BinaryTree<Aluno> arvoreMatricula = new BinaryTree<>(compareByID);

        System.out.println("Carregando dados de " + nomeArquivo);

        readFile(nomeArquivo, arvoreMatricula, arvoreNome);

        System.out.println(
                "Carregamento concluido!"
                        + "Tamanho da arvore por nome: " + arvoreNome.size()
                        + "Tamanho da arvore por matricula: " + arvoreMatricula.size());


        CommandLineInterface cli = new CommandLineInterface(arvoreMatricula, arvoreNome);
        cli.mainloop();
    }

    private static void readFile(String fileName, BinaryTree<Aluno> t1, BinaryTree<Aluno> t2) {

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            int numLines = Integer.parseInt(br.readLine());
            String[] dados;
            Aluno aluno;
            for (int i = 0; i < numLines; i++) {
                dados = br.readLine().split(";");
                aluno = new Aluno(Integer.parseInt(dados[0]), dados[1], Integer.parseInt(dados[2]));
                t1.insert(aluno);
                t2.insert(aluno);
            }

        } catch (IOException err) {
            System.err.println("Erro ao ler arquivo: " + err);
        }
    }
}
