package Aluno;


public class Aluno {
    private Integer id;
    private String name;
    private Integer nota;

    public Aluno(Integer id, String name, Integer nota) {
        this.id = id;
        this.name = name;
        this.nota = nota;
    }

    public Aluno(String name) {
        this.name = name;
    }

    public Aluno(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return id + ";" +name + ";" + nota + ";";
    }
}

