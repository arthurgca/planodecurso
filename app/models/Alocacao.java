package models;

public class Alocacao {

    public int semestre;

    public Disciplina disciplina;

    public Alocacao(int semestre, Disciplina disciplina) {
        if (semestre < 1) {
            throw new IllegalArgumentException("semestre deve ser >= 1");
        }

        this.semestre = semestre;
        this.disciplina = disciplina;
    }

}
