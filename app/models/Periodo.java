package models;

import java.util.ArrayList;
import java.util.List;

public class Periodo {
    public int semestre;
    public List<Disciplina> disciplinas;

    public Periodo() {
    }

    public Periodo(int semestre) {
        if (semestre < 1) {
            throw new IllegalArgumentException("o semestre deve ser >= 1");
        }

        this.semestre = semestre;
        this.disciplinas = new ArrayList<Disciplina>();
    }

    public void alocar(Disciplina disciplina) {
        disciplinas.add(disciplina);
    }

    public void desalocar(Disciplina disciplina) {
        disciplinas.remove(disciplina);
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

}
