package Aluno;

import java.util.Comparator;

public class AlunoComparators {
    public static class CompareByID implements Comparator<Aluno> {

        @Override
        public int compare(Aluno a1, Aluno a2) {
            if(a1.getId() > a2.getId()) return 1;

            if(a1.getId() < a2.getId()) return -1;

            return 0;
        }

    }

    public static class CompareByName implements Comparator<Aluno> {

        @Override
        public int compare(Aluno a1, Aluno a2) {
            int comp = a1.getName().compareTo(a2.getName());
            return comp;
        }
    }
}

