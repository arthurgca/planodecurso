package models;

import java.util.*;

import javax.validation.*;

import play.data.validation.Constraints.*;

public class Periodo {

    @Required
    private int semestre;

    @Valid
    private List<Disciplina> disciplinas;

    public Periodo(int semestre, List<Disciplina> disciplinas) {
        this.semestre = semestre;
        this.disciplinas = disciplinas;
    }

    public int getSemestre() {
        return semestre;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void addDisciplina(Disciplina disciplina) {
        this.disciplinas.add(disciplina);
    }

    public int getTotalCreditos() {
        int sum = 0;
        for (Disciplina disciplina : getDisciplinas()) {
            sum += disciplina.getCreditos();
        }
        return sum;
    }

}
